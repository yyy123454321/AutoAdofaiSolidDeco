package com.github.yyy123454321.aasd.listener.command.file

import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.model.Model

class ExportCommand : Command {

    override val commandName = listOf("export")

    override fun onCommand(args: List<String>) {
        println("check")
        Model.result.makeImages()
    }

}