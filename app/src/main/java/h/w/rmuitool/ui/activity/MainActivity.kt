package h.w.rmuitool.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import h.w.rmuitool.ui.theme.RMUIToolTheme
import h.w.rmuitool.ui.view.MyBottomNavigation
import h.w.rmuitool.ui.view.StatusbarTransparent


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            RMUIToolTheme {
                StatusbarTransparent()
                MyBottomNavigation()
            }
        }
    }
}