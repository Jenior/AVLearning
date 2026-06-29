package com.xiang.av.bmp

import com.xiang.av.binary.ByteReader
import com.xiang.av.bmp.parser.BmpParser
import org.junit.Assert.assertEquals
import org.junit.Test

class BmpParserTest {

    @Test
    fun parseFileHeader() {
        val reader = ByteReader(
            byteArrayOf(
                0x42,
                0x4D,
                0x36,
                0x28,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x00,
                0x36,
                0x00,
                0x00,
                0x00
            )
        )

        val header = BmpParser().parseFileHeader(reader)

        assertEquals("BM", header.signature)
        assertEquals(10294, header.fileSize)
        assertEquals(0, header.reserved1.toInt())
        assertEquals(0, header.reserved2.toInt())
        assertEquals(54, header.pixelDataOffset)
    }
}