package com.xiang.av.bmp.parser

import com.xiang.av.binary.ByteReader
import com.xiang.av.bmp.model.BmpFileHeader

class BmpParser {
    /**
     * 42 4D              // "BM"
     * 36 28 00 00        // fileSize = 10294
     * 00 00              // reserved1
     * 00 00              // reserved2
     * 36 00 00 00        // pixelDataOffset = 54
     *
     */
    fun parseFileHeader(reader: ByteReader): BmpFileHeader {
        val signature = reader.readString(2)
        val fileSize = reader.readIntLE()
        val reserved1 = reader.readShortLE()
        val reserved2 = reader.readShortLE()
        val pixelDataOffset = reader.readIntLE()

        val header = BmpFileHeader(signature, fileSize, reserved1, reserved2, pixelDataOffset)

        return header
    }


}