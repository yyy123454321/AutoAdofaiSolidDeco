package com.github.yyy123454321.aasd

import com.github.yyy123454321.aasd.dsl.fractal
import com.github.yyy123454321.aasd.listener.command.CommandManager
import com.github.yyy123454321.aasd.window.WindowManager

fun main() {
    // TODO("Implement file i/o")
    /*
    print("Enter your solid deco file address: ")
    var decoAddress = readLine() ?: ""
     */

    CommandManager.run()
    WindowManager.run()
}