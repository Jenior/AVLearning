package com.xiang.avlearning.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

abstract class BaseActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)

        setContent {
            MainPage()
            LaunchedEffect(Unit) {
                initData()
            }
        }

    }

    @Preview
    @Composable
    private fun MainPage() {
        MaterialTheme {
            androidx.compose.material3.Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Surface(
                    // 使用 innerPadding 避开状态栏
                    modifier = Modifier
                        .fillMaxSize(),
                    // 建议背景色别用 primary，太深了
                ) {
                    Column() {
                        HeaderView(innerPadding)
                        InitView()
                    }

                }
            }

        }
    }

    @Composable
    fun HeaderView(innerPadding: PaddingValues) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xffffc933))
                .padding(
                    top = innerPadding.calculateTopPadding(),
                )
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(onClick = { /* 执行返回逻辑 */ }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
            }

            Text(
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                text = providerTitle()
            )
            IconButton(onClick = { /* 执行返回逻辑 */ }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "设置")
            }
        }
    }

    /**
     * A native method that is implemented by the 'avlearning' native library,
     * which is packaged with this application.
     */

    @Composable
    abstract fun InitView()

    abstract fun initData()

    open fun providerTitle(): String {
        return ""
    }


}