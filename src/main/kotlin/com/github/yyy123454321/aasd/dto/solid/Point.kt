package com.github.yyy123454321.aasd.dto.solid

import com.github.yyy123454321.aasd.dto.model.PlanePoint
import kotlinx.serialization.Serializable

@Serializable
data class Point(val x: Double, val y: Double, val z: Double) {

    // Getter

    operator fun get(index: Int) = when (index) {
        0 -> x; 1 -> y; 2 -> z
        else -> throw IndexOutOfBoundsException()
    }


    // Operator

    operator fun plus(p: Point) = Point(x + p.x, y + p.y, z + p.z)
    operator fun minus(p: Point) = Point(x - p.x, y - p.y, z - p.z)
    operator fun times(d: Double) = Point(x * d, y * d, z * d)
    operator fun div(d: Double) = times(1 / d)


    // Type cast

    fun toList() = listOf(this[0], this[1], this[2])
    fun toVector() = Vector(x, y, z)
    fun toPlainPoint(scale: Int) = toPlainPoint(scale.toDouble())
    fun toPlainPoint(scale: Double = 1.0) = PlanePoint((x * scale).toInt(), (y * scale).toInt())

}