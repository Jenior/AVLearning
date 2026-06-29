package com.xiang.avlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.xiang.avlearning.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
//        binding.sampleText.text = stringFromJNI()
        binding.sampleText.text = helloFromJNI()
    }

    /**
     * A native method that is implemented by the 'avlearning' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun helloFromJNI(): String

    companion object {
        // Used to load the 'avlearning' library on application startup.
        init {
            System.loadLibrary("avlearning")
        }
    }
}