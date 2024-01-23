package com.github.yyy123454321.aasd.dto.solid.item

import com.github.yyy123454321.aasd.dto.solid.Point
import kotlinx.serialization.Serializable

@Serializable
data class Cuboid(val p0: Point, val p1: Point) : Solid {

    val center: Point = (p0 + p1) / 2.0

    override fun toFaces(): List<Face> {
        return listOf(
            getPointsWithFixedX(p0.x),
            getPointsWithFixedX(p1.x),
            getPointsWithFixedY(p0.y),
            getPointsWithFixedY(p1.y),
            getPointsWithFixedZ(p0.z),
            getPointsWithFixedZ(p1.z)
        ).map {
            listOf(
                Face(it[0], it[1], it[3], center),
                Face(it[1], it[2], it[3], center)
            )
        }.flatten()
    }

    private fun getPoints(
        p0First: Double,
        p1First: Double,
        p0Second: Double,
        p1Second: Double,
        function: (Double, Double) -> Point
    ): List<Point> {
        return listOf(
            function(p0First, p0Second),
            function(p0First, p1Second),
            function(p1First, p1Second),
            function(p1First, p0Second)
        )
    }

    private fun getPointsWithFixedX(x: Double): List<Point> {
        return getPoints(
            p0.y, p1.y,
            p0.z, p1.z,
        ) { a, b -> Point(x, a, b) }
    }

    private fun getPointsWithFixedY(y: Double): List<Point> {
        return getPoints(
            p0.x, p1.x,
            p0.z, p1.z,
        ) { a, b -> Point(a, y, b) }
    }

    private fun getPointsWithFixedZ(z: Double): List<Point> {
        return getPoints(
            p0.x, p1.x,
            p0.y, p1.y,
        ) { a, b -> Point(a, b, z) }
    }

}
