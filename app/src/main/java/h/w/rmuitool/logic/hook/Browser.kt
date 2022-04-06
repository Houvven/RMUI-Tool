package h.w.rmuitool.logic.hook

import h.w.rmuitool.logic.hook.browser.HideDownloadListFooter
import h.w.rmuitool.logic.hook.browser.HideHomeContainer
import h.w.rmuitool.logic.hook.browser.ShowHomePageSetting

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