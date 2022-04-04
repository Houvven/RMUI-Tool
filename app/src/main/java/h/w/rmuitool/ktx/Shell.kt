package h.w.rmuitool.ktx

import android.annotation.SuppressLint


object Shell {
    fun process(): Process = Runtime.getRuntime().exec("su")

    private fun String.su() {
        Runtime.getRuntime().exec("su -c $this")
    }

    @SuppressLint("SdCardPath")
    fun cp() {
        val filePath = "/data/data/h.w.rmuitool/files"
        "mkdir -p $filePath".su()
        "cp -rf /data/oplus/zoom/sys_zoom_window_config.xml /data/oplus/zoom/sys_zoom_window_config.xml.bak".su()
        "cp -rf /data/oplus/os/darkmode/sys_dark_mode_third_app_managed.xml /data/oplus/os/darkmode/sys_dark_mode_third_app_managed.xml.bak".su()
        "cp -rf /data/oplus/zoom/sys_zoom_window_config.xml $filePath/".su()
        "cp -rf /data/oplus/os/darkmode/sys_dark_mode_third_app_managed.xml $filePath/".su()
        "chmod 777 $filePath/sys_zoom_window_config.xml".su()
        "chmod 777 $filePath/sys_dark_mode_third_app_managed.xml".su()
    }
    
    fun String.deleteXMLTag() {
        "sed -i '/<pkg>$this</pkg>/d' /data/oplus/zoom/sys_zoom_window_config.xml".su()
    }

    fun String.addXMLTag() {
        """sed -i '/<support_pkg_list>/a\<pkg>$this</pkg>' /data/oplus/zoom/sys_zoom_window_config.xml""".su()
    }
}

