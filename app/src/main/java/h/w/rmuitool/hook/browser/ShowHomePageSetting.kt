package h.w.rmuitool.hook.browser

import android.content.Context
import h.w.rmuitool.ktx.beforeHookMethod

class ShowHomePageSetting {
    fun init() {
        "com.heytap.browser.settings.component.BrowserPreferencesFragment".beforeHookMethod(
            "W3",
            Context::class.java
        ) {
            it.result = true
        }
    }
}