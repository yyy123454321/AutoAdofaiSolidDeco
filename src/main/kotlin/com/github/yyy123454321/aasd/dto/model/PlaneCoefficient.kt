package com.github.yyy123454321.aasd.dto.model

data class PlaneCoefficient(val a: Double, val b: Double, val c: Double, val d: Double) {

    fun getZ(x: Double, y: Double): Double = -d / ((a * x) + (b * y) + c)

}