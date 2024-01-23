package com.github.yyy123454321.aasd.listener.command.crud

import com.github.yyy123454321.aasd.dto.solid.item.Item
import com.github.yyy123454321.aasd.dto.solid.translation.Targeter
import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.listener.command.cancel
import com.github.yyy123454321.aasd.listener.command.readTag
import com.github.yyy123454321.aasd.model.Model

class MoveCommand : Command {

    override val commandName: List<String> = listOf("move")

    override fun onCommand(args: List<String>) {
        val (solid, transformation, all) = parseArgs(args)

        val tag = readTag() ?: return cancel()
        val to = readTag("To") ?: return cancel()

        if (solid) {
            moveSolids(tag, to, all)
        }

        if (transformation) {
            moveTransformations(tag, to, all)
        }
        println("Successfully moved solids and transformations.")
    }

    private fun moveSolids(tag: String, to: String, all: Boolean) {
        getSolids(tag, all).forEach {
            val new = Item(to + it.tag.drop(tag.length), it.value, it.color)
            Model.item.removeItemizedSolid(it.tag)
            Model.item.setItemizedSolid(new)
        }
    }

    private fun moveTransformations(tag: String, to: String, all: Boolean) {
        getTransformations(tag, all).forEach {
            val new = Targeter(to + it.target.drop(tag.length), it.value)
            Model.item.removeTransformation(it)
            Model.item.addTransformation(new)
        }
    }

}