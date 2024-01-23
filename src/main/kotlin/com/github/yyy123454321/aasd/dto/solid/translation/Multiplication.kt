package com.github.yyy123454321.aasd.dto.solid.translation

import com.github.yyy123454321.aasd.dto.solid.Matrix
import com.github.yyy123454321.aasd.dto.solid.Point
import com.github.yyy123454321.aasd.dto.solid.Vector
import kotlinx.serialization.Serializable

@Serializable
data class Multiplication(val vector: Vector, override val datum: Point?): Datumable, Transformation {

    override fun toRawMatrix(): Matrix {
        return Matrix.multiplication(vector)
    }

}