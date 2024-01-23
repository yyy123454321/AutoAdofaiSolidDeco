package com.github.yyy123454321.aasd.listener.window

import java.awt.Component
import javax.swing.JFrame

/**
 * [JFrame]에 적용되는 리스너를 모아둔 객체
 */
object WindowListener {

    private var mouseListener = MouseListenerImpl()
    private var mouseMotionListener = MouseMotionListenerImpl()
    private var mouseWheelListener = MouseWheelListenerImpl()

    fun Component.addWindowListener() {
        this.addMouseListener(mouseListener)
        this.addMouseMotionListener(mouseMotionListener)
        this.addMouseWheelListener(mouseWheelListener)
    }

}