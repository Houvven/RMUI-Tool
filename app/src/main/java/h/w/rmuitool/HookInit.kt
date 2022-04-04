package h.w.rmuitool

import android.content.res.XResources
import de.robv.android.xposed.IXposedHookInitPackageResources
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_InitPackageResources
import de.robv.android.xposed.callbacks.XC_LoadPackage
import h.w.rmuitool.hook.Browser
import h.w.rmuitool.ktx.makeXSP


/*
// Real programmers don’t comment their code.
// If it was hard to write,
// it should be hard to understand.
*/

class HookInit : IXposedHookLoadPackage, IXposedHookInitPackageResources {

    companion object {
        lateinit var classLoader: ClassLoader
        lateinit var resources: XResources
        val xsp = "function".makeXSP()
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {

        classLoader = lpparam.classLoader

        when (lpparam.packageName) {

            "com.oplus.screenrecorder" -> {}

            "com.android.systemui" -> {}

            "com.heytap.browser" -> Browser().hookLoadPackage()
        }
    }

    override fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam) {

        resources = resparam.res

        when (resparam.packageName) {
            "com.heytap.browser" -> Browser().hookInitPackageResources()
        }
    }
}