package h.w.rmuitool.pullxml

import h.w.rmuitool.ktx.GlobalContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class PullTest {

    fun parseXMLWithPull(): List<String> {
        val supportPkgList = mutableListOf<String>()
        try {
            val inputStream = GlobalContext.context.openFileInput("test.xml")
            val factory = XmlPullParserFactory.newInstance()
            val xmlPullParser = factory.newPullParser()
            xmlPullParser.setInput(inputStream, "UTF-8")
            var eventType = xmlPullParser.eventType
            var pkg = ""
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