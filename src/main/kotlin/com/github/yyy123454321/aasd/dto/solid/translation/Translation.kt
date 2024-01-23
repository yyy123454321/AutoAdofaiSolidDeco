package com.github.yyy123454321.aasd.dto.solid.translation

import com.github.yyy123454321.aasd.dto.solid.Matrix
import com.github.yyy123454321.aasd.dto.solid.Vector
import kotlinx.serialization.Serializable

@Serializable
data class Translation(val vector: Vector): Transformation {

    override fun toMatrix(): Matrix = Matrix.translation(vector)

}