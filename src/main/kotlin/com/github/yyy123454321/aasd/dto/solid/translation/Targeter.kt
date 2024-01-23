package com.github.yyy123454321.aasd.dto.solid.translation

import kotlinx.serialization.Serializable

@Serializable
data class Targeter<T>(val target: String, val value: T)