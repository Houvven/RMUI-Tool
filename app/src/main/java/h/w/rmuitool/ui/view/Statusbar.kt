package h.w.rmuitool.ui.view

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun StatusbarTransparent() {
    rememberSystemUiController().setStatusBarColor(
        color = MaterialTheme.colors.surface,
        darkIcons = MaterialTheme.colors.isLight
    )
}