package com.github.yyy123454321.aasd.listener.command.file

import com.github.yyy123454321.aasd.io.FileManager
import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.listener.command.cancel
import com.github.yyy123454321.aasd.listener.command.readString
import com.github.yyy123454321.aasd.model.Model

class SaveCommand : Command {

    override val commandName = listOf("save")

    override fun onCommand(args: List<String>) {
        val path = readString("Path") ?: return cancel()
        if (FileManager.save(path, Model.item)) {
            println("Success.")
        } else {
            println("Failed.")
        }
    }

}