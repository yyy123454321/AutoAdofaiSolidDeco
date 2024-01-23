package com.github.yyy123454321.aasd.listener.window

import com.github.yyy123454321.aasd.model.Model
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener

class MouseMotionListenerImpl : MouseMotionListener {

    private var previousX = 0
    private var previousY = 0

    override fun mouseDragged(e: MouseEvent) {
        Model.editor.x += (e.x  - previousX) * 150 / Model.editor.zoom
        Model.editor.y += (e.y - previousY) * 150 / Model.editor.zoom
        savePos(e)
    }

    override fun mouseMoved(e: MouseEvent?) {
        savePos(e)
    }

    private fun savePos(e: MouseEvent?) {
        previousX = e?.x ?: 0
        previousY = e?.y ?: 0
    }

}