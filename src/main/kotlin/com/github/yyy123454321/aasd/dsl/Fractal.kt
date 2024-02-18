package com.github.yyy123454321.aasd.dsl

import com.github.yyy123454321.aasd.listener.command.CustomIO
import kotlinx.coroutines.runBlocking
import kotlin.math.abs

fun fractal() {
    runBlocking {
        init()
        repeat(2) {
            step()
        }
        post()
    }
}

private suspend fun init() {
    with(CustomIO) {
        write("cuboid")
        write("block")
        write("-0.5 -0.5 -0.5")
        write("0.5 0.5 0.5")
        write("255 255 255")
    }
}

private suspend fun step() {
    with(CustomIO) {
        repeat (20) {
            write("copy -s -a")
            write("block")
            write("subblock.$it")
        }

        val coordinates = listOf(-1, 0, 1)
        var index = 0

        for (x in coordinates) {
            for (y in coordinates) {
                for (z in coordinates) {
                    if (abs(x) + abs(y) + abs(z) <= 1) {
                        continue
                    }

                    write("translate")
                    write("subblock.$index")
                    write("$x $y $z")
                    index++
                }
            }
        }

        write("delete -s -a")
        write("block")

        write("multiply")
        write("subblock")
        write("${1.0/3.0} ${1.0/3.0} ${1.0/3.0}")
        write("0 0 0")

        write("facialize")
        write("move -s -a")
        write("")
        write("block.")
    }
}

private suspend fun post() {

    with(CustomIO) {
        write("rotate")
        write("block")
        write("x")
        write("20")
        write("0 0 0")

        write("multiply")
        write("block")
        write("120 120 120")
        write("0 0 0")

        write("translate")
        write("block")
        write("0 0 110")

    }
}