package com.github.yyy123454321.aasd.dto.model

class ZBuffer(private val width: Int, private val height: Int) {

    private var buffer = Array(width) {
        Array(height) {
            Double.POSITIVE_INFINITY
        }
    }

    fun get(x: Int, y: Int): Double? = try {
        buffer[x + (width / 2)][y + (height / 2)]
    } catch (e: IndexOutOfBoundsException) {
        null
    }

    fun set(x: Int, y: Int, d: Double): Boolean = try {
        buffer[x + (width / 2)][y + (height / 2)] = d
        true
    } catch (e: IndexOutOfBoundsException) {
        false
    }

}