package com.github.yyy123454321.aasd.window

import com.github.yyy123454321.aasd.model.Model
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * 창을 관리하는 싱글톤 객체
 * A singleton object that manages window
 */
object WindowManager {

    /**
     * 화면 재생성 주기
     * Screen redraw cycle
     */
    private const val FRAME_TIME = 10

    /**
     * 윈도우 객체 A window object
     */
    private var frame = WindowFrame()

    /**
     * 초기 설정 및 화면을 업데이트하는 코루틴 생성
     * Initial setting and creating coroutine that update screen
     */
    fun run() {
        frame.run()

        CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                val time = measureTimeMillis {
                    try {
                        frame.draw(Model.editor.makeImage())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                if (time < FRAME_TIME) delay(FRAME_TIME - time)
            }
        }
    }

}