package com.github.yyy123454321.aasd.listener.command

interface Command {

    val commandName: List<String>

    fun onCommand(args: List<String>)

}