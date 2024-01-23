package com.github.yyy123454321.aasd.listener.command.matrix

import com.github.yyy123454321.aasd.dto.solid.translation.Multiplication
import com.github.yyy123454321.aasd.dto.solid.translation.Targeter
import com.github.yyy123454321.aasd.listener.command.*
import com.github.yyy123454321.aasd.model.Model

class MultiplicationCommand : Command {

    override val commandName: List<String> = listOf("multiply", "multiplication")

    override fun onCommand(args: List<String>) {
        val tag = readTag() ?: return cancel()
        val vector = readVector() ?: return cancel()
        val benchmark = readPoint("Benchmark")

        Model.item.addTransformation(Targeter(tag, Multiplication(vector, benchmark)))
        println("Successfully added multiplication.")
    }

}