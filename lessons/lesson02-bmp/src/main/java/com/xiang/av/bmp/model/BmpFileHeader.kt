package com.xiang.av.bmp.model

/**
 * BMP 文件头（14 Byte）
 */
data class BmpFileHeader(

    /** 文件标识，应为 "BM" */
    val signature: String,

    /** BMP 文件总大小（Byte） */
    val fileSize: Int,

    /** 保留字段，一般为 0 */
    val reserved1: Short,

    /** 保留字段，一般为 0 */
    val reserved2: Short,

    /** 像素数据起始偏移 */
    val pixelDataOffset: Int
)