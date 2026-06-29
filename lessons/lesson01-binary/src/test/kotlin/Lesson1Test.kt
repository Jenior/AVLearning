import org.junit.Test

class Lesson1Test {
    @Test
    fun hexUtils_test(){
        val bytes =byteArrayOf( 0x48,
            0x65,
            0x6c,
            0x6c,
            0x6f)
        println(bytes.toHexString())

//        val result= "GG".hexToByteArray()
//        result.forEach {
//            println(it.toString())
//        }

    }
}