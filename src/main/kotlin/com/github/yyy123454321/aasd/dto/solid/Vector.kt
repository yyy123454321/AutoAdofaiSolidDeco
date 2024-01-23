package com.github.yyy123454321.aasd.dto.solid

import kotlinx.serialization.Serializable
import kotlin.math.sqrt

@Serializable
data class Vector(val x: Double, val y: Double, val z: Double) {

    val length: Double = sqrt((x * x) + (y * y) + (z * z))

    companion object {
        fun vectorOf(from: Point, to: Point) = Vector(to.x - from.x, to.y - from.y, to.z - from.z)
    }


    // Getter

    operator fun get(index: Int) = when (index) {
        0 -> x; 1 -> y; 2 -> z; 3 -> 1.0
        else -> throw IndexOutOfBoundsException()
    }


    // Operator

    operator fun plus(v: Vector) = Vector(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: Vector) = Vector(x - v.x, y - v.y, z - v.z)
    operator fun times(d: Double) = scalarMultiple(d)
    fun scalarMultiple(d: Double) = Vector(x * d, y * d, z * d)
    fun crossProduct(v: Vector) = Vector((y * v.z) - (z * v.y), (z * v.x) - (x * v.z), (x * v.y) - (y * v.x))
    fun dotProduct(v: Vector) = (x * v.x) + (y * v.y) + (z * v.z)


    // Type cast

    fun toUnit() = Vector(x / length, y / length, z / length)
    fun toList() = listOf(x, y, z)
    fun toPoint() = Point(x, y, z)

}