package com.github.yyy123454321.aasd.dto.solid.translation

import com.github.yyy123454321.aasd.dto.solid.Matrix
import com.github.yyy123454321.aasd.dto.solid.Point
import kotlinx.serialization.Serializable

@Serializable
data class Rotation(val axis: Axis, val degree: Double, override val datum: Point?) : Datumable, Transformation {

    override fun toRawMatrix(): Matrix {
        return when (axis) {
            Axis.X -> Matrix.xAxisRotation(degree)
            Axis.Y -> Matrix.yAxisRotation(degree)
            Axis.Z -> Matrix.zAxisRotation(degree)
        }
    }

}

@Serializable
enum class Axis {
    X, Y, Z;
}

fun String.toAxis(): Axis? {
    return when (this.lowercase()) {
        "x" -> Axis.X
        "y" -> Axis.Y
        "z" -> Axis.Z
        else -> null
    }
}