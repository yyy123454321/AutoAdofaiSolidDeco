package com.github.yyy123454321.aasd.dto.solid

import com.github.yyy123454321.aasd.dto.solid.item.Face
import kotlinx.serialization.Serializable
import kotlin.math.cos
import kotlin.math.sin

@Serializable
data class Matrix(val doubleList: List<Double>) {

    companion object {

        /**
         * 곱셈 연산에 대한 항등원 행렬.
         */
        val defaultMatrix = Matrix(
            listOf(
                1.0, 0.0, 0.0, 0.0,
                0.0, 1.0, 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, 0.0, 1.0
            )
        )

        /**
         * 인덱스([Int])와 값([String])의 조합으로 이루어진 행렬.
         * 정의되지 않은 인덱스는 [Matrix.defaultMatrix]의 값을 따름.
         * 인덱스가 범위를 벗어나면 [IndexOutOfBoundsException]를 던짐.
         */
        fun matrixOf(args: List<Pair<Int, Double>>): Matrix {
            val array = defaultMatrix.doubleList.toTypedArray()
            args.forEach { array[it.first] = it.second }
            return Matrix(array.toList())
        }

        /**
         * 곱셈연산 시 평행이동하는 행렬을 간단하게 생성함.
         */
        fun translation(v: Vector) = matrixOf(
            listOf(
                Pair(3, v.x),
                Pair(7, v.y),
                Pair(11, v.z)
            )
        )

        fun multiplication(v: Vector) = matrixOf(
            listOf(
                Pair(0, v.x),
                Pair(5, v.y),
                Pair(10, v.z)
            )
        )

        fun xAxisRotation(degree: Double) = rotation(
            degree,
            row(1).column(1),
            row(1).column(2),
            row(2).column(2),
            row(2).column(1)
        )

        fun yAxisRotation(degree: Double) = rotation(
            degree,
            row(2).column(2),
            row(2).column(0),
            row(0).column(0),
            row(0).column(2)
        )

        fun zAxisRotation(degree: Double) = rotation(
            degree,
            row(0).column(0),
            row(0).column(1),
            row(1).column(1),
            row(1).column(0)
        )

        private fun rotation(degree: Double, a1: Int, a2: Int, a3: Int, a4: Int): Matrix {
            val radian = degree * Math.PI / 180.0

            return matrixOf(
                listOf(
                    Pair(a1, cos(radian)),
                    Pair(a2, -sin(radian)),
                    Pair(a3, cos(radian)),
                    Pair(a4, sin(radian))
                )
            )
        }

        fun row(row: Int) = Row(row)

    }

    init {

        if (doubleList.size != 16) throw IllegalArgumentException()

    }

    /**
     * Getter operator.
     */
    operator fun get(index: Int) = doubleList[index]

    /**
     * Times operator for two [Matrix].
     */
    operator fun times(m: Matrix): Matrix {
        val array = Array(16) { 0.0 }

        repeat(4) { i ->
            repeat(4) { j ->
                repeat(4) { k ->
                    array[row(i).column(j)] += this[row(i).column(k)] * m[row(k).column(j)]
                }
            }
        }

        return Matrix(array.toList())
    }

    /**
     * Times operator for [Matrix] and [Vector]
     */
    operator fun times(v: Vector): Vector {
        val array = arrayOf(0.0, 0.0, 0.0)

        repeat(3) { i ->
            repeat(4) { j ->
                array[i] += this[row(i).column(j)] * v[j]
            }
        }

        return Vector(array[0], array[1], array[2])
    }

    /**
     * Times operator for [Matrix] and [Point].
     */
    operator fun times(p: Point): Point = times(p.toVector()).toPoint()

    /**
     * Times operator for [Matrix] and [Face]. This function calculates each [Point] of [Face]
     */
    operator fun times(f: Face): Face = Face(times(f.p0), times(f.p1), times(f.p2), times(f.solidCenter))

    class Row(private val row: Int) {
        fun column(column: Int): Int = row * 4 + column
    }

}
