package com.github.yyy123454321.aasd.listener.command.etc

import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.model.Model

class TestCommand : Command {
    override val commandName: List<String> = listOf("test")

    override fun onCommand(args: List<String>) {
        with(Model.editor) {
            println(Model.process.getProcessedFaces())
            println(Model.process.getProcessedFaces().toPolygonList())
        }
    }
}