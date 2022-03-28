package h.w.rmuitool.hook

import h.w.rmuitool.hook.browser.HideDownloadListFooter
import h.w.rmuitool.hook.browser.HideHomeContainer
import h.w.rmuitool.hook.browser.ShowHomePageSetting
import h.w.rmuitool.ktx.HookInterface

class Browser : HookInterface {

    override fun hookLoadPackage() {
        ShowHomePageSetting().init()
//        HideHomeContainer().init()

    }

    override fun hookInitPackageResources() {
        HideHomeContainer().init()
        HideDownloadListFooter().init()
    }

}