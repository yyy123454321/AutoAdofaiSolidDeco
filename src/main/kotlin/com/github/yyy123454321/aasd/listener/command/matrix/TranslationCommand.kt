package com.github.yyy123454321.aasd.listener.command.matrix

import com.github.yyy123454321.aasd.dto.solid.Matrix
import com.github.yyy123454321.aasd.dto.solid.translation.Targeter
import com.github.yyy123454321.aasd.dto.solid.translation.Translation
import com.github.yyy123454321.aasd.listener.command.Command
import com.github.yyy123454321.aasd.listener.command.cancel
import com.github.yyy123454321.aasd.listener.command.readTag
import com.github.yyy123454321.aasd.listener.command.readVector
import com.github.yyy123454321.aasd.model.Model

class TranslationCommand : Command {

    override val commandName: List<String> = listOf("translate", "translation")

    override fun onCommand(args: List<String>) {
        val tag = readTag() ?: return cancel()
        val vector = readVector() ?: return cancel()

        Model.item.addTransformation(Targeter(tag, Translation(vector)))
        println("Successfully added translation.")
    }


}