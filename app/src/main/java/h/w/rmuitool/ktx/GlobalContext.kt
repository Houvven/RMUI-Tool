package h.w.rmuitool.ktx

import android.annotation.SuppressLint
import android.app.Application

import android.content.Context

class GlobalContext : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}