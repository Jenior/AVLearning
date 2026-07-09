package com.xiang.wav.model

data class WavFileHeader(
    val riff: String,
    val fileSize: Int,
    val wave: String
)