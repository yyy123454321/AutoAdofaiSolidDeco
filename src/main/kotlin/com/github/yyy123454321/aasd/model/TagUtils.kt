package com.github.yyy123454321.aasd.model


fun String.includes(child: String): Boolean {
    val parentArgs = this.split(".")
    val childArgs = child.split(".")

    if (parentArgs.size > childArgs.size) return false

    parentArgs.forEachIndexed { index, value ->
        if (value != childArgs[index]) return false
    }

    return true
}