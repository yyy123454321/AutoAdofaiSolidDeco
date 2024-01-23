package com.github.yyy123454321.aasd.listener.command.crud

import com.github.yyy123454321.aasd.dto.solid.item.Item
import com.github.yyy123454321.aasd.dto.solid.translation.Targeter
import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.listener.command.cancel
import com.github.yyy123454321.aasd.listener.command.readTag
import com.github.yyy123454321.aasd.model.Model

class CopyCommand : Command {

    override val commandName: List<String> = listOf("copy")

    override fun onCommand(args: List<String>) {
        val (solid, transformation, all) = parseArgs(args)

        val tag = readTag() ?: return cancel()
        val to = readTag("To") ?: return cancel()

        if (solid) {
            copySolids(tag, to, all)
        }

        if (transformation) {
            copyTransformations(tag, to, all)
        }
        println("Successfully copied solids and transformations.")
    }

    private fun copySolids(tag: String, to: String, all: Boolean) {
        getSolids(tag, all)
            .map { Item(to + it.tag.drop(tag.length), it.value, it.color) }
            .forEach { Model.item.setItemizedSolid(it) }
    }

    private fun copyTransformations(tag: String, to: String, all: Boolean) {
        getTransformations(tag, all)
            .map { Targeter(to + it.target.drop(tag.length), it.value) }
            .forEach { Model.item.addTransformation(it) }
    }

}