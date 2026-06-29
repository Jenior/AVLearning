package com.xiang.av.binary

import java.util.Locale
import kotlin.experimental.and


fun ByteArray.toHexString(): String {
    val sb = StringBuilder()
    this.forEach {
        val hex = String.format("%02X", it)
        sb.append(hex)
        sb.append(" ")
    }
    return sb.toString().trim()
}


fun String.hexToByteArray(): ByteArray  {
    val result = this.trim().split(Regex("\\s+"))
    val bytes = ByteArray(result.size)
    result.forEachIndexed { index, ch ->
        val byteInt = ch.toIntOrNull(16)
        // 使用 require 进行校验，如果不满足条件会抛出 IllegalArgumentException
        require(byteInt != null && ch.length <= 2) {
            "Invalid hex string found at index $index: '$ch'. " +
                    "Expected a 1st or 2nd hex character (0-9, A-F)."
        }

        bytes[index] = byteInt.toByte()
    }
    return bytes
}





