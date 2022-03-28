package h.w.rmuitool.ktx

fun process(): Process = Runtime.getRuntime().exec("su")

fun String.su() {
    Runtime.getRuntime().exec("su -c $this")
}

fun cp() {
    "mkdir -p /data/data/h.w.rmuitool/files/".su()
    "cp - rf /data/oplus/zoom/sys_zoom_window_config.xml /data/data/h.w.rmuitool/files/".su()
    "chmod 777 /data/data/h.w.rmuitool/files/sys_zoom_window_config.xml".su()
}

fun String.deleteXMLTag() {
    "sed -i '/<pkg>$this</pkg>/d' /data/oplus/zoom/sys_zoom_window_config.xml".su()
}

fun String.addXMLTag() {
    """sed -i '/<support_pkg_list>/a\    <pkg>$this</pkg>' /data/oplus/zoom/sys_zoom_window_config.xml""".su()
}