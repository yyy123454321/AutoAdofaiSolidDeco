package com.github.yyy123454321.aasd.listener.command.crud

import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.listener.command.cancel
import com.github.yyy123454321.aasd.listener.command.readTag

class ReadCommand : Command {

    override val commandName: List<String> = listOf("read", "get")

    override fun onCommand(args: List<String>) {
        val (solid, transformation, all) = parseArgs(args)

        val tag = readTag() ?: return cancel()

        if (solid) {
            readSolids(tag, all)
        }

        if (transformation) {
            readTransformations(tag, all)
        }
    }

    private fun readSolids(tag: String, readAll: Boolean) {
        getSolids(tag, readAll)
            .sortedBy { it.tag }
            .forEach { println("${it.tag}:\n    ${it.value}\n    ${it.color}") }
    }

    private fun readTransformations(tag: String, readAll: Boolean) {
        getTransformations(tag, readAll)
            .forEachIndexed { index, it ->
                println("$index:\n    Target: ${it.target}\n    ${it.value}")
            }
    }

}