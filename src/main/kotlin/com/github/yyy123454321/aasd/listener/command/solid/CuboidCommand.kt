package com.github.yyy123454321.aasd.listener.command.solid

import com.github.yyy123454321.aasd.dto.solid.item.Cuboid
import com.github.yyy123454321.aasd.dto.solid.item.Item
import com.github.yyy123454321.aasd.dto.solid.item.Solid
import com.github.yyy123454321.aasd.listener.command.*
import com.github.yyy123454321.aasd.model.Model

class CuboidCommand : Command {

    override val commandName = listOf("cuboid")

    override fun onCommand(args: List<String>) {
        val tag = readTag() ?: return cancel()
        val points = readPoints(2) ?: return cancel()
        val color = readColor() ?: return cancel()

        val cuboid = Cuboid(points[0], points[1])
        val item: Item<Solid> = Item(tag, cuboid, color)
        Model.item.setItemizedSolid(item)

        println("Success.")
    }

}