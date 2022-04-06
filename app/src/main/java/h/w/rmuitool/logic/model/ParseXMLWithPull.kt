package h.w.rmuitool.logic.model

import h.w.rmuitool.logic.utils.GlobalContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.FileInputStream

class ParseXMLWithPull {
    private var inputStream: FileInputStream
    private val values = mutableListOf<String>()

    constructor(inputStream: FileInputStream) {
        this.inputStream = inputStream
    }

    constructor(path: String) {
        this.inputStream = GlobalContext.context.openFileInput(path)
    }

    private fun parse(START_TAG: String, END_TAG: String, ): List<String> {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val xmlPullParser = factory.newPullParser()
            xmlPullParser.setInput(inputStream, "UTF-8")
            var eventType = xmlPullParser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val nodeName = xmlPullParser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> if (nodeName == START_TAG) { values.add(xmlPullParser.nextText()) }
                    XmlPullParser.END_TAG -> if (nodeName == END_TAG) break
                }
                eventType = xmlPullParser.next()
            }
        } catch (e: Exception) { return emptyList() } ; return values.toList()
    }

    fun parseWithZoom() = parse("pkg", "support_pkg_list")

    fun parseWithDarkMode() = parse("", "")

}