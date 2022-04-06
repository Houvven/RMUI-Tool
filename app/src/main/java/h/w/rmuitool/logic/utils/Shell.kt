package h.w.rmuitool.logic.utils

import android.annotation.SuppressLint
import h.w.rmuitool.BuildConfig
import kotlin.concurrent.thread

private const val packageName = BuildConfig.APPLICATION_ID


fun process(): Process = Runtime.getRuntime().exec("su")

fun String.su(): Process = Runtime.getRuntime().exec("su -c $this")

fun haveRoot(): Boolean {

    return true
}


@SuppressLint("SdCardPath")
fun cp() {
    thread {
        val filePath = "/data/data/$packageName/files"
        "mkdir -p $filePath".su()
//    "chmod 755 $filePath".su()
        "cp -rf /data/oplus/zoom/sys_zoom_window_config.xml /data/oplus/zoom/sys_zoom_window_config.xml.bak".su()
        "cp -rf /data/oplus/os/darkmode/sys_dark_mode_third_app_managed.xml /data/oplus/os/darkmode/sys_dark_mode_third_app_managed.xml.bak".su()
        "cp -rf /data/oplus/zoom/sys_zoom_window_config.xml $filePath/".su()
        "cp -rf /data/oplus/os/darkmode/sys_dark_mode_third_app_managed.xml $filePath/".su()
        "chmod 777 /data/data/h.w.rmuitool/files/sys_zoom_window_config.xml".su()
        "chmod 777 /data/data/h.w.rmuitool/files/sys_dark_mode_third_app_managed.xml".su()
    }
}

fun String.deleteXMLTag() {
    "sed -i '/<pkg>$this</pkg>/d' /data/oplus/zoom/sys_zoom_window_config.xml".su()
}

fun String.addXMLTag() {
    """sed -i '/<support_pkg_list>/a\<pkg>$this</pkg>' /data/oplus/zoom/sys_zoom_window_config.xml""".su()
}


