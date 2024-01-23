package com.github.yyy123454321.aasd.dto.solid.translation

import com.github.yyy123454321.aasd.dto.solid.Matrix
import com.github.yyy123454321.aasd.dto.solid.Point

interface Datumable : Transformation {

    val datum: Point?

    override fun toMatrix(): Matrix {
        if (datum == null) {
            return toRawMatrix()
        }

        return Matrix.translation(datum!!.toVector()) *
                toRawMatrix() *
                Matrix.translation(datum!!.toVector() * (-1.0))
    }

    fun toRawMatrix(): Matrix

}