package com.xiang.av.bmp.parser

import android.util.SparseArray
import com.xiang.av.binary.ByteReader
import com.xiang.av.bmp.model.BmpFile
import com.xiang.av.bmp.model.BmpFileHeader
import com.xiang.av.bmp.model.BmpInfoHeader
import com.xiang.av.bmp.model.RgbPixel
import kotlin.math.round

class BmpParser {
    /**
     * 读取 bmpFile
     */
    fun parse(reader: ByteReader): BmpFile {
        val fileHeader = parseFileHeader(reader)
        val infoHeader = parseInfoHeader(reader)
        return BmpFile(fileHeader, infoHeader)
    }

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

    /**
     * 0    4   headerSize	Int
     * 4	4	width	Int
     * 8	4	height	Int
     * 12	2	planes	Short
     * 14	2	bitCount	Short
     * 16	4	compression	Int
     * 20	4	imageSize	Int
     * 24	4	xPixelsPerMeter	Int
     * 28	4	yPixelsPerMeter	Int
     * 32	4	colorsUsed	Int
     * 36	4	importantColors	Int
     */
    fun parseInfoHeader(reader: ByteReader): BmpInfoHeader {
        val headerSize = reader.readIntLE()
        val width = reader.readIntLE()
        val height = reader.readIntLE()
        val planes = reader.readShortLE()
        val bitCount = reader.readShortLE()
        val compression = reader.readIntLE()
        val imageSize = reader.readIntLE()
        val xPixelsPerMeter = reader.readIntLE()
        val yPixelsPerMeter = reader.readIntLE()
        val colorsUsed = reader.readIntLE()
        val importantColors = reader.readIntLE()
        return BmpInfoHeader(
            headerSize,
            width,
            height,
            planes,
            bitCount,
            compression,
            imageSize,
            xPixelsPerMeter,
            yPixelsPerMeter,
            colorsUsed,
            importantColors
        )
    }

    fun parsePixels(
        reader: ByteReader,
        header: BmpFileHeader,
        info: BmpInfoHeader
    ): Array<RgbPixel> {
        require(info.bitCount.toInt() == 24) {
            "Only 24-bit BMP is supported."
        }
        reader.seek(
            header.pixelDataOffset
        )
        val width = info.width
        val height = kotlin.math.abs(info.height)

        val pixels = Array(width * height) {
            RgbPixel(0, 0, 0)
        }
        val bytesPerPixel = info.bitCount.toInt() / 8
        val rawRowSize = width * bytesPerPixel
        val rowSize = ((rawRowSize + 3) / 4) * 4
        val rowPadding = rowSize - rawRowSize

        for (y in 0 until height) {
            for (x in 0 until width) {
                val b = reader.readByte()
                val g = reader.readByte()
                val r = reader.readByte()
                val pixel = RgbPixel(r.toInt() and 0xFF, g.toInt() and 0xFF, b.toInt() and 0xFF)

                val yIndex = if (info.height > 0) {
                    height - y - 1
                } else {
                    y
                }
                val index = yIndex * width + x
                pixels[index] = pixel
            }
            reader.skip(rowPadding)
        }
        return pixels
    }


}