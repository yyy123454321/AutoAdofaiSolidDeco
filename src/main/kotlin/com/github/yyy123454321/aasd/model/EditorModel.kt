package com.github.yyy123454321.aasd.model

import com.github.yyy123454321.aasd.dto.model.PlaneCoefficient
import com.github.yyy123454321.aasd.dto.model.PlanePoint
import com.github.yyy123454321.aasd.dto.model.Polygon
import com.github.yyy123454321.aasd.dto.model.ZBuffer
import com.github.yyy123454321.aasd.dto.solid.item.Face
import com.github.yyy123454321.aasd.dto.solid.Matrix
import com.github.yyy123454321.aasd.dto.solid.Point
import com.github.yyy123454321.aasd.dto.solid.Vector
import com.github.yyy123454321.aasd.dto.solid.item.Item
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class EditorModel {

    companion object {
        private const val WIDTH = 900
        private const val WIDTH_HALF = WIDTH / 2
        private const val WIDTH_MAX = WIDTH_HALF - 1
        private const val WIDTH_MIN = -WIDTH_HALF

        private const val HEIGHT = 600
        private const val HEIGHT_HALF = HEIGHT / 2
        private const val HEIGHT_MAX = HEIGHT_HALF - 1
        private const val HEIGHT_MIN = -HEIGHT_HALF

        private const val BOX_SIZE = 20
    }

    var x = 0
    var y = 0
    var zoom = 150
        set(value) {
            field = if (10 <= value) value else 10
        }

    /**
     * BufferedImage 생성합니다
     */
    fun makeImage(): BufferedImage {
        var zBuffer = ZBuffer(WIDTH, HEIGHT)
        var image = BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB)
        var g = image.graphics

        g.color = Color.black
        g.fillRect(0, 0, WIDTH, HEIGHT)

        // 폴리곤 출력
        Model.process.getProcessedFaces().toPolygonList().forEach outlineForEach@{ polygonItem ->
            var polygon = polygonItem.value
            var yMax = min(maxOf(polygon.p0.y, polygon.p1.y, polygon.p2.y), HEIGHT_MAX)
            var yMin = max(minOf(polygon.p0.y, polygon.p1.y, polygon.p2.y), HEIGHT_MIN)
            var xMaxArray = Array(HEIGHT) { Int.MIN_VALUE }
            var xMinArray = Array(HEIGHT) { Int.MAX_VALUE }

            listOf(
                Pair(polygon.p0, polygon.p1),
                Pair(polygon.p1, polygon.p2),
                Pair(polygon.p2, polygon.p0)
            ).forEach { pair ->
                pair.listOfLine().forEach inlineForEach@{
                    if (it.y !in HEIGHT_MIN..HEIGHT_MAX) return@inlineForEach
                    if (it.x > xMaxArray.getSignIndexed(it.y)) {
                        xMaxArray.setSignIndexed(it.y, it.x)
                    }
                    if (it.x < xMinArray.getSignIndexed(it.y)) {
                        xMinArray.setSignIndexed(it.y, it.x)
                    }
                    //if (it.x !in -WIDTH / 2 until WIDTH / 2) return@inlineForEach
                    //image.setRGB(it.x + (WIDTH / 2), it.y + (HEIGHT / 2), Color.WHITE.rgb)
                }
            }

            for (i in yMin..yMax) {
                if (xMinArray.getSignIndexed(i) > xMaxArray.getSignIndexed(i)) continue
                if (WIDTH_MAX <= xMinArray.getSignIndexed(i)) continue
                if (WIDTH_MIN > xMaxArray.getSignIndexed(i)) continue

                xMaxArray.setSignIndexed(i, min(WIDTH_MAX, xMaxArray.getSignIndexed(i)))
                xMinArray.setSignIndexed(i, max(WIDTH_MIN, xMinArray.getSignIndexed(i)))

                for (j in xMinArray.getSignIndexed(i)..xMaxArray.getSignIndexed(i)) {
                    val convertedZ = polygon.coefficient.getZ(j.toDouble(), i.toDouble())
                    if (convertedZ < 0.0) continue
                    if (convertedZ < (zBuffer.get(j, i) ?: 0.0)) {
                        zBuffer.set(j, i, convertedZ)
                        image.setRGB(j + (WIDTH / 2), i + (HEIGHT / 2), polygonItem.color.toColor().rgb)
                    }
                }
            }
        }

        g.color = Color.white
        g.drawRect(
            (WIDTH - BOX_SIZE) / 2 + (x * zoom / 150),
            (HEIGHT - BOX_SIZE) / 2 + (y * zoom / 150),
            BOX_SIZE,
            BOX_SIZE
        )

        /*
        for (i in WIDTH_MIN..WIDTH_MAX) {
            for (j in HEIGHT_MIN..HEIGHT_MAX) {
                var colorr = Color.BLACK.overlapBy(
                    Color.WHITE,
                    1.0 / ((zBuffer.get(i, j) ?: 0.0) + 1.0)
                )
                image.setRGB(i + WIDTH_HALF, j + HEIGHT_HALF, colorr.rgb)
            }
        }
         */

        g.color = Color.white
        g.font = Font("굴림", Font.PLAIN, 15)
        g.drawString("x: $x, y: $y, zoom: $zoom", 20, 20)

        return image
    }

    /**
     * Face 목록을 Polygon 목록으로 변환합니다
     */
    fun List<Item<Face>>.toPolygonList(): List<Item<Polygon>> {
        return this.map {
            val face = Matrix.translation(Vector(x / 150.0, y / 150.0, 0.0)) * it.value

            return@map Item(
                it.tag,
                Polygon(
                    face.p0.toPlanePoint(zoom),
                    face.p1.toPlanePoint(zoom),
                    face.p2.toPlanePoint(zoom),
                    face.p0.toPlaneCoefficient(it.value.direction, zoom)
                ),
                it.color
            )
        }
    }

    /**
     * 3차원 점을 2차원 점으로 변환합니다.
     */
    private fun Point.toPlanePoint(scale: Int) = PlanePoint(
        (this.x / this.z * scale).toInt(),
        (this.y / this.z * scale).toInt()
    )

    /**
     * 벡터와 수직인 평면의 방정식의 계수를 구합니다.
     * https://jebae.github.io/z-buffer
     */
    private fun Point.toPlaneCoefficient(v: Vector, s: Int) = PlaneCoefficient(
        v.x / s, v.y / s, v.z, -(v.x * this.x) - (v.y * this.y) - (v.z * this.z)
    )

    /**
     * 부호 있는 [index]로 [Array]의 값을 가져옵니다.
     */
    private fun <T> Array<T>.getSignIndexed(index: Int): T = this[index + (this.size / 2)]

    /**
     * 부호 있는 [index]로 [Array]의 값을 정합니다.
     */
    private fun <T> Array<T>.setSignIndexed(index: Int, value: T): Boolean = try {
        this[index + (this.size / 2)] = value
        true
    } catch (e: IndexOutOfBoundsException) {
        false
    }

    /**
     * Pair<PlanePoint, PlanePoint>를 받아 두 점의 선에 포함된 List<PlanePoint>를 반환합니다.
     */
    private fun Pair<PlanePoint, PlanePoint>.listOfLine(): List<PlanePoint> {
        val xDiff = abs(this.first.x - this.second.x)
        val yDiff = abs(this.first.y - this.second.y)

        return if (xDiff >= yDiff) {
            this.listOfLineByX()
        } else {
            Pair(
                PlanePoint(this.first.y, this.first.x),
                PlanePoint(this.second.y, this.second.x)
            )
                .listOfLineByX()
                .map {
                    PlanePoint(it.y, it.x)
                }
        }
    }

    /**
     * 무적권 x만을 기준으로 직선을 반환합니다
     */
    private fun Pair<PlanePoint, PlanePoint>.listOfLineByX(): List<PlanePoint> {
        var p0 = this.first
        var p1 = this.second
        if (p0.x > p1.x) {
            val temp = p0
            p0 = p1
            p1 = temp
        }

        val planePointArrayList = ArrayList<PlanePoint>()
        val xSignedDiff = p1.x - p0.x
        val ySignedDiff = p1.y - p0.y

        if (xSignedDiff == 0) {
            return listOf(this.first)
        } else {
            for (i in p0.x..p1.x) {
                planePointArrayList.add(
                    PlanePoint(i, p0.y + ((i - p0.x) * ySignedDiff / xSignedDiff))
                )
            }
        }

        return planePointArrayList.toList()
    }
}