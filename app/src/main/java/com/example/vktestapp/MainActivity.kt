package com.example.vktestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.example.vktestapp.ui.theme.VkTestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkTestAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val finalPadding: PaddingValues = PaddingValues.Absolute(
                        top = innerPadding.calculateTopPadding() + 8.dp,
                        left = innerPadding.calculateLeftPadding(LocalLayoutDirection.current) + 8.dp,
                        right = innerPadding.calculateRightPadding(LocalLayoutDirection.current) + 8.dp,
                        bottom = innerPadding.calculateBottomPadding() + 8.dp
                    )
                    PostCard(modifier = Modifier.padding(finalPadding))
                }
            }
        }
    }
}