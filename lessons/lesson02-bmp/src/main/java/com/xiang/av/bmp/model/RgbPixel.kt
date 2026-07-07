package com.xiang.av.bmp.model

data class RgbPixel(
    val r: Int,
    val g: Int,
    val b: Int
)

fun RgbPixel.toArgb(): Int =
    (0xFF shl 24) or
            (r shl 16) or
            (g shl 8) or
            b

fun BmpFile.toArgbArray(): IntArray {
    val colors = IntArray(pixels.size)
    pixels.forEachIndexed { index, pixel ->
        colors[index] = pixel.toArgb()
    }
    return colors
}