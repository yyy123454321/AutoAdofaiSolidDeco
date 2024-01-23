package com.github.yyy123454321.aasd.window

import com.github.yyy123454321.aasd.listener.window.WindowListener.addWindowListener
import java.awt.Dimension
import java.awt.Image
import javax.swing.JFrame

/**
 * JFrame을 상속받는 클래스
 * A class that extends JFrame
 */
class WindowFrame : JFrame() {

    /**
     * 창 관련 상수
     * Constance of window
     */
    companion object {
        const val WINDOW_WIDTH = 900
        const val WINDOW_HEIGHT = 600
        const val WINDOW_TITLE = "Auto ADOFAI Solid Deco Editor"
    }

    /**
     * 창 활성화
     * Enable window
     */
    fun run() {
        contentPane.preferredSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
        contentPane.addWindowListener()
        pack()
        isResizable = false
        title = WINDOW_TITLE
        defaultCloseOperation = EXIT_ON_CLOSE
        isVisible = true
    }

    /**
     * 캔버스에 이미지를 그림
     * Draw image on canvas
     */
    fun draw(image: Image) {
        // graphics.drawImage(image, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this)
        contentPane.graphics.drawImage(image, 0, 0, this)
    }

}