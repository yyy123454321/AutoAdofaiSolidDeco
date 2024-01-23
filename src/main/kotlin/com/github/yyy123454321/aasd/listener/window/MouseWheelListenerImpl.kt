package com.github.yyy123454321.aasd.listener.window

import com.github.yyy123454321.aasd.model.Model
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener

class MouseWheelListenerImpl : MouseWheelListener {

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        Model.editor.zoom += -e.wheelRotation * 5
    }

}