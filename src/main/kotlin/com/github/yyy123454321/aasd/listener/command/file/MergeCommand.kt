package com.github.yyy123454321.aasd.listener.command.file

import com.github.yyy123454321.aasd.io.FileManager
import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.listener.command.cancel
import com.github.yyy123454321.aasd.listener.command.readString
import com.github.yyy123454321.aasd.model.Model

class MergeCommand : Command {

    override val commandName: List<String> = listOf("merge")

    override fun onCommand(args: List<String>) {
        val path = readString("With") ?: return cancel()
        val itemModel = FileManager.load(path)

        if (itemModel == null) {
            println("Failed.")
        } else {
            itemModel.getAllItemizedSolids().forEach {
                Model.item.setItemizedSolid(it)
            }

            itemModel.getAllTransformations().forEach {
                Model.item.addTransformation(it)
            }

            println("Success.")
        }
    }

}