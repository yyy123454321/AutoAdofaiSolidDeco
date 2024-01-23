package com.github.yyy123454321.aasd.dto.model

data class PlanePoint(val x: Int, val y: Int) {

    operator fun plus(pp: PlanePoint) = PlanePoint(this.x + pp.x, this.y + pp.y)

    operator fun minus(pp: PlanePoint) = PlanePoint(this.x - pp.x, this.y - pp.y)

}