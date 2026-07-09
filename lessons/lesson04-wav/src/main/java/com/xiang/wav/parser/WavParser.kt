package com.xiang.wav.parser

import com.xiang.av.binary.ByteReader
import com.xiang.wav.model.WavChunkHeader
import com.xiang.wav.model.WavDataChunk
import com.xiang.wav.model.WavFile
import com.xiang.wav.model.WavFileHeader
import com.xiang.wav.model.WavFmtChunk

class WavParser {

    private companion object {
        const val CHUNK_FMT = "fmt "
        const val CHUNK_DATA = "data"
    }

    fun parseHeader(reader: ByteReader): WavFileHeader {
        val riff = reader.readString(4)
        val fileSize = reader.readIntLE()
        val wave = reader.readString(4)
        require(riff == "RIFF") {
            "Invalid RIFF file."
        }

        require(wave == "WAVE") {
            "Not a WAVE file."
        }
        return WavFileHeader(riff, fileSize, wave)
    }

    fun parseWavFile(reader: ByteReader): WavFile {
        val header = parseHeader(reader)

        var fmt: WavFmtChunk? = null
        var data: WavDataChunk? = null

        while (reader.remaining > 0) {

            val chunk = parseChunkHeader(reader)

            when (chunk.id) {
                CHUNK_FMT -> {
                    // 解析 fmt
                    require(fmt == null) {
                        "Duplicate fmt chunk."
                    }
                    fmt = parseFmtChunk(reader, chunk)
                }

                CHUNK_DATA -> {
                    // 解析 data
                    require(data == null) {
                        "Duplicate data chunk."
                    }
                    data = parseChunkData(reader, chunk)
                }

                else -> {

                    reader.skip(chunk.size)

                    if ((chunk.size and 1) == 1) {
                        reader.skip(1)
                    }
                }
            }
        }
        require(fmt != null) {
            "Missing fmt chunk."
        }

        require(data != null) {
            "Missing data chunk."
        }
        return WavFile(header, fmt, data)
    }

    /**
     * | Offset | Size | 字段            |
     * | -----: | ---: | ------------- |
     * |      0 |    2 | audioFormat   |
     * |      2 |    2 | channels      |
     * |      4 |    4 | sampleRate    |
     * |      8 |    4 | byteRate      |
     * |     12 |    2 | blockAlign    |
     * |     14 |    2 | bitsPerSample |
     *
     */
    fun parseFmtChunk(reader: ByteReader, chunkHeader: WavChunkHeader): WavFmtChunk {
        val audioFormat = reader.readShortLE()
        val channels = reader.readShortLE()
        val sampleRate = reader.readIntLE()
        val byteRate = reader.readIntLE()
        val blockAlign = reader.readShortLE()
        val bitsPerSample = reader.readShortLE()
        if (chunkHeader.size > 16) {
            reader.skip(chunkHeader.size - 16)
        }
        return WavFmtChunk(audioFormat, channels, sampleRate, byteRate, blockAlign, bitsPerSample)
    }

    fun parseChunkData(reader: ByteReader, chunkHeader: WavChunkHeader): WavDataChunk {
        val pcmData = reader.readBytes(chunkHeader.size)
        return WavDataChunk(pcmData)
    }

    fun parseChunkHeader(reader: ByteReader): WavChunkHeader {
        val id = reader.readString(4)
        val size = reader.readIntLE()
        return WavChunkHeader(id, size)
    }
}