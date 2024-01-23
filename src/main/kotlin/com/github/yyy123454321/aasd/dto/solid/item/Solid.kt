package com.github.yyy123454321.aasd.dto.solid.item

import kotlinx.serialization.Serializable

@Serializable
sealed interface Solid {

    fun toFaces(): List<Face>

}