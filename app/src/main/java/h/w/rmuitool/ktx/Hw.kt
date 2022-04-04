package h.w.rmuitool.ktx

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.IOException

object Hw {
    private val out = ByteArrayOutputStream()
    fun Bitmap.toByte(): ByteArray {
        this.compress(Bitmap.CompressFormat.PNG, 100, out)
        try {
            out.flush()
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return out.toByteArray()
    }
}

