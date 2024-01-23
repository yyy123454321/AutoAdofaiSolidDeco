package com.github.yyy123454321.aasd.listener.command.file

import com.github.yyy123454321.aasd.dto.solid.item.Face
import com.github.yyy123454321.aasd.dto.solid.item.Item
import com.github.yyy123454321.aasd.dto.solid.item.Solid
import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.model.ItemModel
import com.github.yyy123454321.aasd.model.Model

class FacializeCommand : Command {

    override val commandName: List<String> = listOf("facialize")

    override fun onCommand(args: List<String>) {
        println("Wait...")
        val new = ItemModel()
        with(Model.process) {
            Model.item
                .getAllItemizedSolids()
                .toItemizedFaces()
                .toTransformedFaces(Model.item.getAllTransformations())
                .mapIndexed<Item<Face>, Item<Solid>> { index, item ->
                    Item(index.toString(), item.value, item.color)
                }.forEach { new.setItemizedSolid(it) }
        }

        Model.item = new
        println("Success.")
    }

}