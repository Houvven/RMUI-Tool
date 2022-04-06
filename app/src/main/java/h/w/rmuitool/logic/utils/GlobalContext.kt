package h.w.rmuitool.logic.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import h.w.rmuitool.ui.tool.ScreenProperty

class GlobalContext : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        ScreenProperty().get()
    }

}