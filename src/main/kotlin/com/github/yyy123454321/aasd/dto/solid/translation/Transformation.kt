package com.github.yyy123454321.aasd.dto.solid.translation

import com.github.yyy123454321.aasd.dto.solid.Matrix
import kotlinx.serialization.Serializable

@Serializable
sealed interface Transformation {

    fun toMatrix(): Matrix

}