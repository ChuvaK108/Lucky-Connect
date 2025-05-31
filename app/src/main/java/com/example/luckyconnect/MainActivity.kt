package com.example.luckyconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.luckyconnect.ui.navigation.VpnNavigation
import com.example.luckyconnect.ui.theme.FuturisticColors
import com.example.luckyconnect.ui.theme.LuckyConnectTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Включаем edge-to-edge режим
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            LuckyConnectTheme {
                // Настройка цветов системных панелей для футуристичного вида
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = false
                    )
                    systemUiController.setNavigationBarColor(
                        color = FuturisticColors.DeepSpace.copy(alpha = 0.8f),
                        darkIcons = false
                    )
                }
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = FuturisticColors.DeepSpace
                ) {
                    VpnNavigation()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VpnAppPreview() {
    LuckyConnectTheme {
        VpnNavigation()
    }
}