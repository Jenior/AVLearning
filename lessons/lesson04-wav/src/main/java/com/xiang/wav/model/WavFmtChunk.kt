package com.xiang.wav.model

data class WavFmtChunk(

    /** PCM = 1 */
    val audioFormat: Short,

    /** 声道数 */
    val channels: Short,

    /** 采样率 */
    val sampleRate: Int,

    /** 每秒字节数 */
    val byteRate: Int,

    /** Block Align */
    val blockAlign: Short,

    /** 位深 */
    val bitsPerSample: Short
)