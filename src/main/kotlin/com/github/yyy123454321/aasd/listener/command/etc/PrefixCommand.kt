package com.github.yyy123454321.aasd.listener.command.etc

import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.listener.command.cancel
import com.github.yyy123454321.aasd.listener.command.readString
import com.github.yyy123454321.aasd.listener.command.tagPrefix

class PrefixCommand : Command {

    override val commandName: List<String> = listOf("prefix")

    override fun onCommand(args: List<String>) {
        println("Current prefix: $tagPrefix")
        val prefix = readString("Prefix") ?: return cancel()
        tagPrefix = prefix
        println("Current prefix: $tagPrefix")
    }

}