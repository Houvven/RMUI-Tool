package h.w.rmuitool.ui.tool

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import h.w.rmuitool.ktx.GlobalContext

class ScreenProperty {
    companion object {
        var screenWidth: Int = 0
        var screenHeight: Int = 0
    }
    fun get() {
        val context = GlobalContext.context
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        val density = dm.density
        val densityDpi = dm.densityDpi
        screenWidth = (width / density).toInt()
        screenHeight = (height / density).toInt()
    }
}