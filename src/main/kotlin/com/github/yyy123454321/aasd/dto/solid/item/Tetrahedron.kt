package com.github.yyy123454321.aasd.dto.solid.item

import com.github.yyy123454321.aasd.dto.solid.Point
import kotlinx.serialization.Serializable

@Serializable
data class Tetrahedron(val p0: Point, val p1: Point, val p2: Point, val p3: Point) : Solid {

    val center: Point = (p0 + p1 + p2 + p3) / 4.0

    override fun toFaces(): List<Face> = listOf(
        Face(p0, p1, p2, center),
        Face(p0, p1, p3, center),
        Face(p0, p2, p3, center),
        Face(p1, p2, p3, center)
    )

    fun toArray(): Array<Point> = arrayOf(p0, p1, p2, p3)

}