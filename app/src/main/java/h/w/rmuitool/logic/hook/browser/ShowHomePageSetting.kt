package h.w.rmuitool.logic.hook.browser

import android.content.Context
import h.w.rmuitool.HookInit.Companion.xsp
import h.w.rmuitool.logic.utils.ktx.beforeHookMethod

class ShowHomePageSetting {
    fun init() {
        if (!xsp.getBoolean("home_page", false)) return
        "com.heytap.browser.settings.component.BrowserPreferencesFragment".beforeHookMethod(
            "W3",
            Context::class.java
        ) {
            it.result = true
        }
    }
}