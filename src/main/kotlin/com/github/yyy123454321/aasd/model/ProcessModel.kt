package com.github.yyy123454321.aasd.model

import com.github.yyy123454321.aasd.dto.solid.item.Face
import com.github.yyy123454321.aasd.dto.solid.Matrix
import com.github.yyy123454321.aasd.dto.solid.Vector
import com.github.yyy123454321.aasd.dto.solid.item.Item
import com.github.yyy123454321.aasd.dto.solid.item.SimpleColor
import com.github.yyy123454321.aasd.dto.solid.item.Solid
import com.github.yyy123454321.aasd.dto.solid.translation.Targeter
import com.github.yyy123454321.aasd.dto.solid.translation.Transformation
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class ProcessModel {

    fun getProcessedFaces(): List<Item<Face>> {
        if (Model.item.lastModifiedTime != lastTransformedTime) {
            processedFaces = Model.item
                .getAllItemizedSolids()
                .toItemizedFaces()
                .toTransformedFaces(Model.item.getAllTransformations())
                .toShadedFaces()
                .toPostProcessedFaces()

            updateLastTransformedTime()
        }

        return processedFaces
    }

    @Transient
    private var processedFaces: List<Item<Face>> = emptyList()

    @Transient
    var lastTransformedTime = 0L

    private fun updateLastTransformedTime() {
        lastTransformedTime = Model.item.lastModifiedTime
    }



    fun List<Item<Solid>>.toItemizedFaces(): List<Item<Face>> {
        return map { it.toItemizedFaces() }.flatten()
    }

    fun List<Item<Face>>.toTransformedFaces(matrices: List<Targeter<Transformation>>): List<Item<Face>> {
        return map { it.toTransformedFace(matrices) }
    }

    private fun List<Item<Face>>.toShadedFaces(): List<Item<Face>> {
        return map { it.toShadedFace() }
    }

    private fun List<Item<Face>>.toPostProcessedFaces(): List<Item<Face>> {
        return map { it.toPostProcessedFace() }
    }



    private fun Item<Solid>.toItemizedFaces(): List<Item<Face>> {
        return value.toFaces().map { Item(tag, it, color) }
    }

    private fun Item<Face>.toTransformedFace(matrices: List<Targeter<Transformation>>): Item<Face> {
        var face = value
        matrices.forEach {
            if (it.target.includes(tag)) {
                face = it.value.toMatrix() * face
            }
        }
        return Item(tag, face, color)
    }

    var lightDirection: Vector = Vector(0.0, -3.0, -1.0).toUnit()

    var transparency: Double = 0.0

    var darkColor: SimpleColor = SimpleColor(0, 0, 0)

    private fun Item<Face>.toShadedFace(): Item<Face> {
        val concordance = (value.direction.dotProduct(lightDirection) + 1.0) * transparency * 0.5
        return Item(tag, value, darkColor.overlap(color, concordance))
    }

    val zCoefficient: Double = 0.09

    val postProcessingMatrix
        get() =
            Matrix.translation(Vector(0.0, 0.0, 1.0)) *
            Matrix.multiplication(Vector(1.0, -1.0, zCoefficient))


    private fun Item<Face>.toPostProcessedFace(): Item<Face> {
        return Item(tag, postProcessingMatrix * value, color)
    }

}