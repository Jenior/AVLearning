package com.xiang.avlearning

import org.junit.Test

import org.junit.Assert.*
import java.util.Locale
import kotlin.experimental.and
import kotlin.jvm.Throws

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        println("123")
        val bytes =byteArrayOf( 0x48,
            0x65,
            0x6c,
            0x6c,
            0x6f)
        println(bytes.toHexString())

        val result= "GG".hexToByteArray()
        result.forEach {
            println(it.toString())
        }


    }

}

fun ByteArray.toHexString(): String {
    val sb = StringBuilder()
    this.forEach {
        val hex = String.format("%02X", it)
        sb.append(hex.uppercase(Locale.ROOT) + " ")
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
                    "Expected a 1nd or 2nd hex character (0-9, A-F)."
        }

        bytes[index] = byteInt.toByte()
    }
    return bytes
}
