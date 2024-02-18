package com.github.yyy123454321.aasd.listener.command

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object CustomIO {

    init {
        GlobalScope.launch {
            while (true) {
                channel.send(readln())
                delay(0)
            }
        }
    }

    suspend fun write(str: String) {
        channel.send(str)
        println(str)
        delay(10)

    }

    val channel = Channel<String>()

}