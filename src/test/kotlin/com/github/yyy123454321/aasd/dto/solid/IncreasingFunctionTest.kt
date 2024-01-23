package com.github.yyy123454321.aasd.dto.solid

import org.junit.jupiter.api.Test

class IncreasingFunctionTest {

    @Test
    fun acceleratingIncrease() {
        printIncreasing { it * 1.015625 + 0.3 }
    }

    private fun printIncreasing(function: (Double) -> Double) {
        var i = 0.0
        while (i < 200) {
            println(i)
            i = function(i)
        }
    }
}