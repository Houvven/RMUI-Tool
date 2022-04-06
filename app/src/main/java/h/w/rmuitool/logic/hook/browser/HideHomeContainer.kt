package h.w.rmuitool.logic.hook.browser

import android.view.View
import de.robv.android.xposed.callbacks.XC_LayoutInflated
import h.w.rmuitool.HookInit

class HideHomeContainer {
    fun init() {
        if (!HookInit.xsp.getBoolean("hide_home_page_footer", false)) return
        HookInit.resources.hookLayout("com.heytap.browser", "layout", "browser_navi_simple_hots_container",
            object : XC_LayoutInflated() {
                override fun handleLayoutInflated(liparam: LayoutInflatedParam) {
                    val view = liparam.view.findViewById<View>(
                        liparam.res.getIdentifier("simple_hots_grid_container", "id", liparam.res.packageName)
                    )
                    view.visibility = View.GONE
                }
            })
    }
}