package com.github.yyy123454321.aasd.listener.command.file

import com.github.yyy123454321.aasd.io.FileManager
import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.listener.command.cancel
import com.github.yyy123454321.aasd.listener.command.readString
import com.github.yyy123454321.aasd.model.Model

class LoadCommand : Command {

    override val commandName = listOf("load")

    override fun onCommand(args: List<String>) {
        val path = readString("Path") ?: return cancel()
        val itemModel = FileManager.load(path)

        if (itemModel == null) {
            println("Failed.")
        } else {
            Model.item = itemModel
            Model.process.lastTransformedTime = 0L
            println("Success.")
        }
    }

}