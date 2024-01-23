package com.github.yyy123454321.aasd.dto.solid.item

import kotlinx.serialization.Serializable

@Serializable
data class Item<T>(val tag: String, val value: T, val color: SimpleColor)