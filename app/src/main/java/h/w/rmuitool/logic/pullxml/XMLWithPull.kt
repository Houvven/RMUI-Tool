package h.w.rmuitool.logic.pullxml

import h.w.rmuitool.logic.utils.GlobalContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class XMLWithPull {

    fun parseXMLWithPull(): List<String> {
        val supportPkgList = mutableListOf<String>()
        try {
            val inputStream = GlobalContext.context.openFileInput("sys_zoom_window_config.xml")
            val factory = XmlPullParserFactory.newInstance()
            val xmlPullParser = factory.newPullParser()
            xmlPullParser.setInput(inputStream, "UTF-8")
            var eventType = xmlPullParser.eventType
            var pkg: String
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val nodeName = xmlPullParser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (nodeName == "pkg") {
                            pkg = xmlPullParser.nextText()
                            supportPkgList.add(pkg)
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if ("support_pkg_list" == nodeName) {
                            break
                        }
                    }
                }
                eventType = xmlPullParser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return supportPkgList.toList()
    }

}