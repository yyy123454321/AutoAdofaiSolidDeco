package com.github.yyy123454321.aasd.listener.command.crud

import com.github.yyy123454321.aasd.dto.solid.item.Item
import com.github.yyy123454321.aasd.dto.solid.item.Solid
import com.github.yyy123454321.aasd.dto.solid.translation.Targeter
import com.github.yyy123454321.aasd.dto.solid.translation.Transformation
import com.github.yyy123454321.aasd.model.Model

fun parseArgs(args: List<String>): Array<Boolean> {
    var solids = false
    var transformations = false
    var all = false

    args.forEach {
        when (it.lowercase()) {
            "-s" -> solids = true
            "-t" -> transformations = true
            "-a" -> all = true
        }
    }

    return arrayOf(solids, transformations, all)
}

fun getSolids(tag: String, all: Boolean): List<Item<Solid>> {
    return if (tag == "") {
        Model.item.getAllItemizedSolids()
    } else if (all) {
        Model.item.getAllItemizedSolids(tag)
    } else {
        Model.item.getItemizedSolid(tag)?.let { listOf(it) } ?: emptyList()
    }
}

fun getTransformations(tag: String, all: Boolean): List<Targeter<Transformation>> {
    return if (tag == "") {
        Model.item.getAllTransformations()
    } else if (all) {
        Model.item.getAllTransformations(tag)
    } else {
        Model.item.getTransformations(tag)
    }
}