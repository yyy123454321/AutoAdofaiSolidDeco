package com.github.yyy123454321.aasd.listener.command

import com.github.yyy123454321.aasd.dto.solid.Point
import com.github.yyy123454321.aasd.dto.solid.Vector
import com.github.yyy123454321.aasd.dto.solid.item.SimpleColor

var tagPrefix = "";

fun readString(propertyName: String): String? {
    print("$propertyName >> ")
    val string = readln().trim()
    if (string == "cancel") return null
    return string
}

fun readTag(propertyName: String) : String? = readString(propertyName)?.let { tagPrefix + it }

fun readTag(): String? = readTag("Tag")


fun readVector(propertyName: String): Vector? {
    val rawVector = readString(propertyName) ?: return null
    val rawNumbers = rawVector.split(" ")

    if (rawNumbers.size != 3) {
        println("Please enter three numbers.")
        return null
    }

    return runCatching {
        Vector(rawNumbers[0].toDouble(), rawNumbers[1].toDouble(), rawNumbers[2].toDouble())
    }.onFailure {
        println("Please enter number.")
        return null
    }.getOrThrow()
}

fun readVector(): Vector? = readVector("Vector")

fun readPoint(propertyName: String): Point? = readVector(propertyName)?.toPoint()

fun readPoint(): Point? = readPoint("Point")

fun readPoints(size: Int): List<Point>? {
    return (0 until size).map {
        readPoint("Point $it") ?: return null
    }
}

fun readColor(): SimpleColor? {
    val rawColor = readString("Color") ?: return null
    val rawNumbers = rawColor.split(" ")

    if (rawNumbers.size != 3) {
        println("Please enter three numbers.")
        return null
    }

    return runCatching { SimpleColor(rawNumbers[0].toInt(), rawNumbers[1].toInt(), rawNumbers[2].toInt()) }
        .onFailure {
            println("Please enter number.")
            return null
        }
        .getOrThrow()
}

fun cancel() {
    println("Cancelled.")
}