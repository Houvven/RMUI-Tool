package h.w.rmuitool.hook.browser

import android.view.View
import de.robv.android.xposed.callbacks.XC_LayoutInflated
import h.w.rmuitool.HookInit
import h.w.rmuitool.ktx.HookInterface

class HideHomeContainer : HookInterface {
    override fun init() {
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