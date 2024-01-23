package com.github.yyy123454321.aasd.dto.solid

import com.github.yyy123454321.aasd.dto.solid.item.Face
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SolidTest {

    @Test
    fun faceDirectionTest() {
        var f = Face(
            Point(3.0, 4.0, 0.0),
            Point(5.0, 2.0, 0.0),
            Point(8.0,7.0,0.0),
            Point(0.0, 1.0, -1.0)
        )

        assert(Vector(0.0, 0.0, 1.0).dotProduct(f.direction) > 0.99)
    }

    @Test
    fun matrixTimesTest() {
        var v1 = Matrix(
            listOf(
                0.0, 2.0, 3.0, 4.0,
                0.0, 2.0, 5.0, 2.0,
                0.0, 3.0, 0.0, 0.0,
                0.0, 0.0, 4.0, 0.0
            )
        )

        var v2 = Matrix(
            listOf(
                0.0, 1.0, 0.0, 0.0,
                0.0, 0.0, 4.0, 0.0,
                0.0, 3.0, 0.0, 3.0,
                0.0, 0.0, 2.0, 0.0
            )
        )

        var answerMatrix = Matrix(
            listOf(
                0.0, 9.0, 16.0, 9.0,
                0.0, 15.0, 12.0, 15.0,
                0.0, 0.0, 12.0, 0.0,
                0.0, 12.0, 0.0, 12.0
            )
        )

        assertEquals(v1 * v2, answerMatrix)

        var mm1 = Matrix.defaultMatrix
        var mm2 = Vector(1.0, 2.0, 3.0).toUnit()
        var mm3 = mm1 * mm2
        assert(mm2.dotProduct(mm3) > 0.99)
    }
}