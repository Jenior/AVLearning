package com.xiang.av.bmp.model

data class BmpFile(
    val fileHeader: BmpFileHeader, val infoHeader: BmpInfoHeader,
    val pixels: List<RgbPixel> = emptyList()
)