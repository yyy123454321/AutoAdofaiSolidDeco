package com.github.yyy123454321.aasd.dto.solid.item

import com.github.yyy123454321.aasd.dto.solid.Point
import com.github.yyy123454321.aasd.dto.solid.Vector
import kotlinx.serialization.Serializable

@Serializable
data class Face(val p0: Point, val p1: Point, val p2: Point, val solidCenter: Point): Solid {

    val direction: Vector
    val faceCenter: Point = (p0 + p1 + p2) / 3.0

    init {
        val v1 = Vector.vectorOf(p0, p1).toUnit()
        val v2 = Vector.vectorOf(p0, p2).toUnit()
        val v3 = Vector.vectorOf(solidCenter, faceCenter).toUnit()
        val unsigned = v1.crossProduct(v2).toUnit()

        direction = if (unsigned.dotProduct(v3) > 0) unsigned
        else unsigned * (-1.0)
    }

    override fun toFaces(): List<Face> = listOf(this)

}