package com.xiang.pcm.player

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import java.io.InputStream
import java.nio.Buffer

class PcmPlayer {
    private var audioTrack: AudioTrack? = null

    companion object {
        private const val BUFFER_SIZE = 4096

    }

    fun prepare(sampleRate: Int, channels: Int, bitsPerSample: Int) {
        val channelConfig = when (channels) {
            1 -> AudioFormat.CHANNEL_OUT_MONO
            2 -> AudioFormat.CHANNEL_OUT_STEREO
            else -> error("Unsupported channels: $channels")
        }
        val encoding = when (bitsPerSample) {
            8 -> AudioFormat.ENCODING_PCM_8BIT
            16 -> AudioFormat.ENCODING_PCM_16BIT
            else -> error("Unsupported bitsPerSample: $bitsPerSample")
        }
        val minBufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, encoding)
        require(minBufferSize > 0) {
            "Invalid buffer size"
        }


        val attributes =
            AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_MUSIC).build()

        val format = AudioFormat.Builder()
            .setChannelMask(channelConfig)
            .setEncoding(encoding)
            .setSampleRate(sampleRate)
            .build()
        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(attributes)
            .setAudioFormat(format)
            .setBufferSizeInBytes(minBufferSize)
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()
        require(audioTrack?.state == AudioTrack.STATE_INITIALIZED) {
            "AudioTrack initialize failed."
        }
    }

    fun play(input: InputStream) {
        val track = audioTrack ?: error("call prepare first")
        require(track.state == AudioTrack.STATE_INITIALIZED)
        val buffer = ByteArray(BUFFER_SIZE)
        while (true) {
            val length = input.read(buffer)
            if (length <= 0) {
                break
            }
            var offset = 0
            while (offset < length) {
                val written = track.write(buffer, offset, length - offset)
                if (written <= 0) {
                    break
                }
                offset += written
            }
        }
    }

    fun play(data: ByteArray) {
        val track = audioTrack ?: error("call prepare first")
        require(track.state == AudioTrack.STATE_INITIALIZED)
        track.play()
        var offset = 0
        while (offset < data.size) {
            val write = track.write(data, offset, data.size - offset)
            if (write <= 0) {
                break
            }
            offset += write
        }
    }

    fun stop() {
        audioTrack?.let {
            if (it.playState == AudioTrack.PLAYSTATE_PLAYING) {
                it.stop()
            }
        }
    }

    fun release() {
        audioTrack?.apply {
            if (playState == AudioTrack.PLAYSTATE_PLAYING) {
                stop()
            }
            flush()
            release()
        }

        audioTrack = null
    }
}