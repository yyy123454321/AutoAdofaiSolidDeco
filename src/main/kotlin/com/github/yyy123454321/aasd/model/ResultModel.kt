package com.github.yyy123454321.aasd.model

import com.github.yyy123454321.aasd.dto.model.ResultShape
import com.github.yyy123454321.aasd.dto.solid.item.Face
import com.github.yyy123454321.aasd.dto.solid.intersect
import com.github.yyy123454321.aasd.dto.solid.item.Item
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Double.max
import java.lang.Double.min
import javax.imageio.ImageIO

class ResultModel {

    private var scale = 5.0

    private var floor: Int = 1927

    private var maxZPos = 200.0

    var fileName: (Double) -> String = { "p3_aasd1_$it.png" }

    var tag: String = "p3_aasd1 p3"

    fun makeImages() {
        // makeImages(2000, 3000) { it * 1.015625 + 0.3 }
        makeImages(3000, 3000) { it + 1 }
    }

    private fun makeImages(width: Int, height: Int, increasingFunction: (Double) -> Double) {
        var realZPos = 0.0
        var progress = 0.0
        var decorations = ""

        while (realZPos <= maxZPos) {
            var nextZPos = increasingFunction(realZPos)
            var fakeZPos = realZPos

            val saveImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
            var isEmpty = true

            while (fakeZPos < nextZPos) {
                if (fakeZPos == 0.0) {
                    fakeZPos += 0.015625
                    continue
                }

                val image = makeImage(width, height, 100.0 / fakeZPos, 100.0 / realZPos)
                if (image != null) {
                    saveImage.graphics.drawImage(image, 0, 0, null)
                    isEmpty = false
                }
                fakeZPos += 0.015625
            }

            if (!isEmpty) {
                ImageIO.write(
                    saveImage,
                    "png",
                    File("E:/ADOFAI/3d/output/${fileName(realZPos)}")
                )

                decorations +=
                    "{ \"floor\": $floor, " +
                            "\"eventType\": \"AddDecoration\", " +
                            "\"decorationImage\": \"${fileName(realZPos)}\", " +
                            "\"relativeTo\": \"Tile\", " +
                            "\"scale\": [${100 * scale}, ${100 * scale}], " +
                            "\"opacity\": 100, " +
                            "\"depth\": ${1000 - (realZPos * 10).toInt()}, " +
                            "\"parallax\": [${100.0 - realZPos}, ${100.0 - realZPos}], " +
                            "\"tag\": \"$tag\", " +
                            "\"imageSmoothing\": \"Disabled\" },\n"

            }

            if (progress < realZPos) {
                println("$progress/$maxZPos")
                while (progress < realZPos) {
                    progress += 1.0
                }
            }
            realZPos = nextZPos
        }

        File("E:/ADOFAI/3d/output/output.txt").writeText(decorations)
        println("Success.")
    }

    private fun makeImage(width: Int, height: Int, zPos: Double, sizeZPos: Double): BufferedImage? {
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val graphics = image.graphics

        Model.process.getProcessedFaces()
            .filter { it.value.minZ <= zPos && zPos < it.value.maxZ }
            .also { if (it.isEmpty()) return null }
            .map {
                val list = it.value
                    .let { face -> listOf(face.p0, face.p1, face.p2, face.solidCenter) }
                    .sortedBy { point -> point.z }

                Item(it.tag, list, it.color)
            }
            .map {
                val points = it.value
                val intersectPoints = when {
                    points[0].z <= zPos && zPos < points[1].z -> {
                        listOf(
                            zPos.intersect(points[0], points[1])!!,
                            zPos.intersect(points[0], points[2])!!,
                            zPos.intersect(points[0], points[3])!!
                        )
                    }

                    points[1].z <= zPos && zPos < points[2].z -> {
                        listOf(
                            zPos.intersect(points[0], points[2])!!,
                            zPos.intersect(points[1], points[2])!!,
                            zPos.intersect(points[1], points[3])!!,
                            zPos.intersect(points[0], points[3])!!
                        )
                    }

                    points[2].z <= zPos && zPos < points[3].z -> {
                        listOf(
                            zPos.intersect(points[0], points[3])!!,
                            zPos.intersect(points[1], points[3])!!,
                            zPos.intersect(points[2], points[3])!!
                        )
                    }

                    else -> {
                        throw AssertionError()
                    }
                }

                val planePoints = intersectPoints.map { intersectPoint -> intersectPoint.toPlainPoint(150 / sizeZPos / scale) }
                val shape = ResultShape(planePoints[0], planePoints[1], planePoints[2], planePoints.getOrNull(3))
                Item(it.tag, shape, it.color)
            }.forEach {
                graphics.color = it.color.toColor()
                with(it.value) {
                    if (p3 == null) {
                        graphics.fillPolygon(
                            intArrayOf(p0.x + (width / 2), p1.x + (width / 2), p2.x + (width / 2)),
                            intArrayOf(p0.y + (height / 2), p1.y + (height / 2), p2.y + (height / 2)),
                            3
                        )
                    } else {
                        graphics.fillPolygon(
                            intArrayOf(p0.x + (width / 2), p1.x + (width / 2), p2.x + (width / 2), p3.x + (width / 2)),
                            intArrayOf(
                                p0.y + (height / 2),
                                p1.y + (height / 2),
                                p2.y + (height / 2),
                                p3.y + (height / 2)
                            ),
                            4
                        )
                    }
                }
            }

        return image
    }

    private val Face.minZ: Double
        get() {
            return min(min(p0.z, p1.z), min(p2.z, solidCenter.z))
        }

    private val Face.maxZ: Double
        get() {
            return max(max(p0.z, p1.z), max(p2.z, solidCenter.z))
        }

}