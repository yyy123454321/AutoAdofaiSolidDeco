package com.github.yyy123454321.aasd.dto.solid.item

import kotlinx.serialization.Serializable
import java.awt.Color

@Serializable
data class SimpleColor(val red: Int, val green: Int, val blue: Int) {
    fun toColor(): Color = Color(red, green, blue)

    /**
     * [Color] 위에 [color]가 [alpha]만큼의 투명도로 겹쳐집니다.
     */
    fun overlap(color: SimpleColor, alpha: Double): SimpleColor = try {
        SimpleColor(
            this.red.overlap(color.red, alpha).toInt(),
            this.green.overlap(color.green, alpha).toInt(),
            this.blue.overlap(color.blue, alpha).toInt()
        )
    } catch (e: Exception) {
        println("앙에러띠")
        SimpleColor(255, 255, 255)
    }

    /**
     * [Int]를 [alpha]만큼의 투명도를 가진 [int]로 겹칩니다.
     */
    private fun Int.overlap(int: Int, alpha: Double): Double = ((int * (1.0 - alpha)) + (this * alpha))

}
