package com.github.yyy123454321.aasd.listener.command.crud

import com.github.yyy123454321.aasd.dto.solid.item.Item
import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.listener.command.cancel
import com.github.yyy123454321.aasd.listener.command.readColor
import com.github.yyy123454321.aasd.listener.command.readTag
import com.github.yyy123454321.aasd.model.Model

class UpdateCommand : Command {

    override val commandName: List<String> = listOf("update")

    override fun onCommand(args: List<String>) {
        val all = args.any { it == "-a" }

        val tag = readTag() ?: return cancel()
        val color = readColor() ?: return cancel()

        getSolids(tag, all)
            .map { Item(it.tag, it.value, color) }
            .forEach { Model.item.setItemizedSolid(it) }
    }

}