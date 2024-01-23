package com.github.yyy123454321.aasd.listener.command.solid

import com.github.yyy123454321.aasd.dto.solid.item.Item
import com.github.yyy123454321.aasd.dto.solid.item.Solid
import com.github.yyy123454321.aasd.dto.solid.item.Tetrahedron
import com.github.yyy123454321.aasd.listener.command.*
import com.github.yyy123454321.aasd.model.Model

class TetrahedronCommand : Command {

    override val commandName = listOf("tetra", "tetrahedron")

    override fun onCommand(args: List<String>) {
        val tag = readTag() ?: return cancel()
        val points = readPoints(4) ?: return cancel()
        val color = readColor() ?: return cancel()

        val tetrahedron = Tetrahedron(points[0], points[1], points[2], points[3])
        val item: Item<Solid> = Item(tag, tetrahedron, color)
        Model.item.setItemizedSolid(item)

        println("Success.")
    }

}