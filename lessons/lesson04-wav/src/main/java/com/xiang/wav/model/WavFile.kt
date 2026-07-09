package com.xiang.wav.model

data class WavFile(
    val header: WavFileHeader,
    val fmt: WavFmtChunk,
    val data: WavDataChunk
)