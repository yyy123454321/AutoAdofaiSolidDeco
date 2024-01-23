package com.github.yyy123454321.aasd.listener.command.etc

import com.github.yyy123454321.aasd.listener.command.Command

class WrongCommand : Command {

    override val commandName = listOf(" ")

    override fun onCommand(args: List<String>) = println("잘못된 명령어입니다.")

}