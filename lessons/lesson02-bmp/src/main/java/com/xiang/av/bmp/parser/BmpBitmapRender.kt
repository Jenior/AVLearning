package com.xiang.av.bmp.parser

import android.graphics.Bitmap
import com.xiang.av.bmp.model.BmpFile
import com.xiang.av.bmp.model.toArgbArray
import kotlin.math.absoluteValue

object BmpBitmapRenderer {

    fun render(bmp: BmpFile): Bitmap {
        val colors = bmp.toArgbArray()
        val width = bmp.infoHeader.width
        val height = bmp.infoHeader.height.absoluteValue
        return Bitmap.createBitmap(
            colors,
            width,
            height,
            Bitmap.Config.ARGB_8888
        )
    }
}