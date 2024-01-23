package com.github.yyy123454321.aasd.listener.command.crud

import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.listener.command.cancel
import com.github.yyy123454321.aasd.listener.command.readString
import com.github.yyy123454321.aasd.listener.command.readTag
import com.github.yyy123454321.aasd.model.Model

class DeleteCommand : Command {

    override val commandName: List<String> = listOf("delete", "remove")

    override fun onCommand(args: List<String>) {
        val (solid, transformation, all) = parseArgs(args)

        val tag = readTag() ?: return cancel()

        if (tag.lowercase() == "byindex") {
            deleteTransformationByIndex()
            return
        }

        if (solid) {
            deleteSolids(tag, all)
        }

        if (transformation) {
            deleteTransformations(tag, all)
        }
    }

    private fun deleteSolids(tag: String, all: Boolean) {
        getSolids(tag, all)
            .forEach { Model.item.removeItemizedSolid(it.tag) }
    }

    private fun deleteTransformations(tag: String, all: Boolean) {
        getTransformations(tag, all)
            .forEach { Model.item.removeTransformation(it) }
    }

    private fun deleteTransformationByIndex() {
        val rawIndex = readString("Index") ?: return cancel()

        val index = runCatching {
            rawIndex.toInt()
        }.onFailure {
            println("Please enter number.")
            return cancel()
        }.getOrThrow()

        Model.item.removeTransformation(index)
    }

}