package com.example.examattendance

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.examattendance.ui.navigation.AppNavHost
import com.example.examattendance.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    SafeAreaScreen { AppNavHost() }
                }
            }
        }
    }
}
@Composable
fun SafeAreaScreen(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()

            .padding(
                bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding(),
                top =  WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
                )
    ) {
        content()
    }
}
