package h.w.rmuitool.hook.browser

import android.view.View
import de.robv.android.xposed.callbacks.XC_LayoutInflated
import h.w.rmuitool.HookInit

class HideDownloadListFooter {
    fun init() {
        HookInit.resources.hookLayout("com.heytap.browser", "layout", "download_list_footer_view",
            object : XC_LayoutInflated() {
                override fun handleLayoutInflated(liparam: LayoutInflatedParam) {

                    val view = liparam.view.findViewById<View>(
                        liparam.res.getIdentifier(
                            "download_app_list", "id", liparam.res.packageName
                        )
                    )

                    View.GONE.also {
                        liparam.view.visibility = it
                        view.visibility = it
                    }
                }
            })
    }
}