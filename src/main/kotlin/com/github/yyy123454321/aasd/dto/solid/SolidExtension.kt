package com.github.yyy123454321.aasd.dto.solid

import com.github.yyy123454321.aasd.dto.solid.item.Tetrahedron

operator fun Double.times(v: Vector): Vector = v.scalarMultiple(this)

fun Array<Point>.toTetrahedron(): Tetrahedron? =
    if (this.size < 4) null
    else Tetrahedron(this[0], this[1], this[2], this[3])

fun Double.intersect(p1: Point, p2: Point): Point? {
    val a: Point
    val b: Point

    if (p1.z < p2.z) {
        a = p1
        b = p2
    } else if (p1.z > p2.z) {
        a = p2
        b = p1
    } else {
        print("$p1 $p2 $this")
        return null
    }

    if (a.z > this || this >= b.z) {
        print("$p1 $p2 $this")
        return null
    }

    val ratio = (this - a.z)/(b.z - a.z)
    return Point(
        ratio * (b.x - a.x) + a.x,
        ratio * (b.y - a.y) + a.y,
        this
    )
}