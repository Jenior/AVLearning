package com.xiang.avlearning

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.xiang.av.binary.ByteReader
import com.xiang.avlearning.base.BaseActivity
import com.xiang.pcm.player.PcmPlayer
import com.xiang.wav.parser.WavParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {
    private val scope = CoroutineScope(Dispatchers.IO)
    @Composable
    override fun InitView() {
        Column() {
            Text(text = stringFromJNI())

            Text(text = helloFromJNI())
        }
    }

    override fun initData() {
        scope.launch {
            val bytes =
                assets.open("test.wav")
                    .use {
                        it.readBytes()
                    }
            val input = assets.open("test.wav")


            val reader = ByteReader(bytes)

            val wav =
                WavParser()
                    .parseWavFile(reader)

            val fmt = wav.fmt

            val player = PcmPlayer()
            player.play(input)

            player.prepare(
                sampleRate = fmt.sampleRate,
                channels = fmt.channels.toInt(),
                bitsPerSample = fmt.bitsPerSample.toInt()
            )

            player.play(
                wav.data.pcmData
            )
        }

    }

    override fun providerTitle(): String {
        return "这是标题"
    }

    external fun stringFromJNI(): String
    external fun helloFromJNI(): String

    companion object {
        // Used to load the 'avlearning' library on application startup.
        init {
            System.loadLibrary("avlearning")
        }
    }
}