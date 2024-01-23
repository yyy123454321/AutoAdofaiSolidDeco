package com.github.yyy123454321.aasd.model

import com.github.yyy123454321.aasd.dto.solid.item.Item
import com.github.yyy123454321.aasd.dto.solid.item.Solid
import com.github.yyy123454321.aasd.dto.solid.translation.Targeter
import com.github.yyy123454321.aasd.dto.solid.translation.Transformation
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class ItemModel {

    private val itemizedSolidMap = HashMap<String, Item<Solid>>()
    private val transformationList = ArrayList<Targeter<Transformation>>()

    fun setItemizedSolid(solid: Item<Solid>) {
        itemizedSolidMap[solid.tag] = solid
        updateLastModifiedTime()
    }

    fun removeItemizedSolid(tag: String) {
        itemizedSolidMap.remove(tag)
        updateLastModifiedTime()
    }

    fun getAllItemizedSolids(): List<Item<Solid>> {
        return itemizedSolidMap.map { it.value }
    }

    fun getAllItemizedSolids(tag: String): List<Item<Solid>> {
        return getAllItemizedSolids().filter { tag.includes(it.tag) }
    }

    fun getItemizedSolid(tag: String): Item<Solid>? {
        return itemizedSolidMap[tag]
    }

    fun addTransformation(transformation: Targeter<Transformation>) {
        transformationList.add(transformation)
        updateLastModifiedTime()
    }

    fun removeTransformation(index: Int) {
        transformationList.removeAt(index)
        updateLastModifiedTime()
    }

    fun removeTransformation(element: Targeter<Transformation>) {
        transformationList.remove(element)
        updateLastModifiedTime()
    }

    fun getAllTransformations(): List<Targeter<Transformation>> {
        return transformationList.toList()
    }

    fun getAllTransformations(tag: String): List<Targeter<Transformation>> {
        return getAllTransformations().filter { tag.includes(it.target) }
    }

    fun getTransformations(tag: String): List<Targeter<Transformation>> {
        return getAllTransformations().filter { tag == it.target }
    }

    @Transient
    var lastModifiedTime = 1L
        private set

    private fun updateLastModifiedTime() {
        lastModifiedTime = System.currentTimeMillis()
    }

}