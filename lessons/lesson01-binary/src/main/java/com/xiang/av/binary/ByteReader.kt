package com.xiang.av.binary


class ByteReader(private val data: ByteArray) {


    private companion object {
        const val SIZE_OF_SHORT = 2
        const val SIZE_OF_INT = 4
        const val SIZE_OF_STRING = 8
    }

    var position: Int = 0
        private set

    val size: Int
        get() = data.size

    /**
     * 判断是否读取完成
     */
    val remaining: Int
        get() = size - position


    /**
     * 读取 byte
     */
    fun readByte(): Byte {
        requireRemaining(1)
        val byte = data[position]
        position++
        return byte
    }

    /**
     * 读取为 short
     */
    fun readShortLE(): Short {

        requireRemaining(SIZE_OF_SHORT)
        val b0 = data[position].toInt() and 0xff
        val b1 = data[position + 1].toInt() and 0xff
        position += SIZE_OF_SHORT

        return (b1.shl(8) or b0).toShort()
    }

    /**
     * 读取为 int
     */
    fun readIntLE(): Int {
        requireRemaining(SIZE_OF_INT)

        val b0 = data[position].toInt() and 0xff
        val b1 = data[position + 1].toInt() and 0xff
        val b2 = data[position + 2].toInt() and 0xff
        val b3 = data[position + 3].toInt() and 0xff
        position += SIZE_OF_INT

        return b3.shl(24) or b2.shl(16) or b1.shl(8) or b0
    }

    /**
     * Reads a fixed-length ASCII string.
     */
    fun readString(length: Int): String {
        // 1. 检查剩余空间是否足够
        requireRemaining(length)

        // 2. 将字节数组的一部分转换为字符串
        // data 是原始 ByteArray, position 是当前指针
        val result = String(data, position, length, Charsets.US_ASCII)

        // 3. 更新指针
        position += length

        return result
    }

    /**
     * 往后 count 之后读取
     * 注意判断溢出
     */
    fun skip(count: Int) {
        requireRemaining(count)
        seek(position + count)
    }

    /**
     * 移动 到 指定 position
     * 注意判断 position 超出 -1，more than max length
     */
    fun seek(position: Int) {
        require(position in 0..size) {
            "position ($position) must be in range [0, $size]"
        }
        this.position = position
    }


    private fun requireRemaining(count: Int) {
        require(count in 0..remaining) {
            "Need $count bytes, but only $remaining remaining."
        }
    }

}