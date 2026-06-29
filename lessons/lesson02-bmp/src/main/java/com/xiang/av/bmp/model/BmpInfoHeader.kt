package com.xiang.av.bmp.model

data class BmpInfoHeader(
    /** Header 大小，通常为40 */
    val headerSize: Int,

    /** 图片宽度 */
    val width: Int,

    /** 图片高度 */
    val height: Int,

    /** 色彩平面，一般固定为1 */
    val planes: Short,

    /** 每像素位数 */
    val bitCount: Short,

    /** 压缩方式 */
    val compression: Int,

    /** 图片数据大小 */
    val imageSize: Int,

    /** 水平分辨率 */
    val xPixelsPerMeter: Int,

    /** 垂直分辨率 */
    val yPixelsPerMeter: Int,

    /** 调色板颜色数 */
    val colorsUsed: Int,

    /** 重要颜色数 */
    val importantColors: Int)