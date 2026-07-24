package com.xiang.pcm

object PcmUtils {
    fun calculateByteRate(
        sampleRate: Int,
        channels: Int,
        bitsPerSample: Int
    ): Int {
        val byteRate = sampleRate * channels * bitsPerSample / 8
        return byteRate
    }

    fun calculateDurationMs(
        pcmSize: Int,
        byteRate: Int
    ): Long {
        return pcmSize * 1000L / byteRate
    }
}