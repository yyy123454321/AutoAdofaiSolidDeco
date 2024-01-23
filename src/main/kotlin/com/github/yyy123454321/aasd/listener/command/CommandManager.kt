package com.github.yyy123454321.aasd.listener.command

import com.github.yyy123454321.aasd.listener.command.crud.*
import com.github.yyy123454321.aasd.listener.command.etc.PrefixCommand
import com.github.yyy123454321.aasd.listener.command.etc.TestCommand
import com.github.yyy123454321.aasd.listener.command.etc.WrongCommand
import com.github.yyy123454321.aasd.listener.command.file.*
import com.github.yyy123454321.aasd.listener.command.matrix.*
import com.github.yyy123454321.aasd.listener.command.solid.*
import kotlinx.coroutines.*
import java.util.*

object CommandManager {

    /**
     * Initialize the list of commands and pass the commands to the assign function
     */
    fun run() {
        val commandExecutorHashMap = HashMap<String, Command>()
        val wrongCommand = WrongCommand()

        val commandList: List<Command> = listOf(
            SaveCommand(),
            LoadCommand(),
            MergeCommand(),
            ExportCommand(),
            FacializeCommand(),

            TetrahedronCommand(),
            CuboidCommand(),
            FaceCommand(),

            TranslationCommand(),
            MultiplicationCommand(),
            RotationCommand(),

            ReadCommand(),
            UpdateCommand(),
            CopyCommand(),
            MoveCommand(),
            DeleteCommand(),

            TestCommand(),
            PrefixCommand()
        )

        commandList.forEach { command ->
            command.commandName.forEach { name ->
                commandExecutorHashMap[name] = command
            }
        }

        CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                assign(readlnOrNull()?.lowercase() ?: "", commandExecutorHashMap, wrongCommand)
                delay(0)
            }
        }
    }

    /**
     * Assign command to listener
     */
    private fun assign(command: String, hash: HashMap<String, Command>, wrong: Command) {
        val args = command.split(" ")
        val commandExecutor = hash[args[0]]

        if (args.isEmpty() || commandExecutor == null) {
            wrong.onCommand(emptyList())
            return
        }

        commandExecutor.onCommand(args.drop(1))
    }

}