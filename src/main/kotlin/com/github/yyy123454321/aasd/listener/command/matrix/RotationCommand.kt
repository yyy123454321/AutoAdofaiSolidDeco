package com.github.yyy123454321.aasd.listener.command.matrix

import com.github.yyy123454321.aasd.dto.solid.translation.Rotation
import com.github.yyy123454321.aasd.dto.solid.translation.Targeter
import com.github.yyy123454321.aasd.dto.solid.translation.toAxis
import com.github.yyy123454321.aasd.listener.command.*
import com.github.yyy123454321.aasd.model.Model

class RotationCommand : Command {

    override val commandName: List<String> = listOf("rotate", "rotation")

    override fun onCommand(args: List<String>) {
        val tag = readTag() ?: return cancel()
        val axis = readString("Axis")?.toAxis() ?: return cancel()
        val rawDegree = readString("degree") ?: return cancel()
        val benchmark = readPoint("Benchmark")

        val degree = runCatching {
            rawDegree.toDouble()
        }.onFailure {
            println("Please enter number.")
            return cancel()
        }.getOrThrow()

        Model.item.addTransformation(Targeter(tag, Rotation(axis, degree, benchmark)))
        println("Successfully added rotation.")
    }

}