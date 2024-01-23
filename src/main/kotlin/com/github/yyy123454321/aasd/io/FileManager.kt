package com.github.yyy123454321.aasd.io

import com.github.yyy123454321.aasd.model.ItemModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


object FileManager {

    fun save(path: String, itemModel: ItemModel): Boolean {
        runCatching {
            val string = Json.encodeToString(itemModel)
            File(path).writeText(string)
        }.onSuccess {
            return true
        }.onFailure {
            it.printStackTrace()
            return false
        }

        return false
    }

    fun load(path: String): ItemModel? {
        runCatching {
            val string = File(path).inputStream().bufferedReader().use { it.readText() }
            val itemModel = Json.decodeFromString<ItemModel>(string)
            itemModel
        }.onSuccess {
            return it
        }.onFailure {
            it.printStackTrace()
            return null
        }

        return null
    }

}