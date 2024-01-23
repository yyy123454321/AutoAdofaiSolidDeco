package com.github.yyy123454321.aasd.listener.command.solid

import com.github.yyy123454321.aasd.dto.solid.item.Face
import com.github.yyy123454321.aasd.dto.solid.item.Item
import com.github.yyy123454321.aasd.dto.solid.item.Solid
import com.github.yyy123454321.aasd.listener.command.*
import com.github.yyy123454321.aasd.model.Model

class FaceCommand : Command {

    override val commandName: List<String> = listOf("face")

    override fun onCommand(args: List<String>) {
        val tag = readTag() ?: return cancel()
        val points = readPoints(3) ?: return cancel()
        val center = readPoint("Solid Center") ?: return cancel()
        val color = readColor() ?: return cancel()

        val face = Face(points[0], points[1], points[2], center)
        val item: Item<Solid> = Item(tag, face, color)
        Model.item.setItemizedSolid(item)
    }

}