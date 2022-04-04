package h.w.rmuitool.ktx

import android.annotation.SuppressLint
import android.app.Application

import android.content.Context
import h.w.rmuitool.GetAppList
import h.w.rmuitool.ui.tool.ScreenProperty
import h.w.rmuitool.ui.view.Functions
import kotlin.concurrent.thread

class GlobalContext : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        var hasRoot: Boolean = true
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        ScreenProperty().get()
        thread {
            Functions().putFunction()
        }
        try {
            Shell.process()
        } catch (t: Throwable) {
            hasRoot = false
        }
        if (!"database".makeSP().getBoolean("have_database", false) && hasRoot
        ) {
            Shell.cp()
            GetAppList().putAppInfo()
        }
    }
}