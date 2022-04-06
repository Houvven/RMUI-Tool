package h.w.rmuitool.logic.utils.ktx

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.widget.Toast
import h.w.rmuitool.logic.utils.GlobalContext.Companion.context
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException



//显示Toast
fun String.showToast(length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, length).show()
}


//创建SharedPreferences
@SuppressLint("WrongConstant")
fun String.getSharedPreferences(mode: Int = Application.MODE_APPEND) = context.getSharedPreferences(this, mode)!!


//保存bitmap到本地
fun Bitmap.saveToLocal(path: String? = null, packageName: String? = null) {
    val iconFile = if (path == null) {
        File("""${context.getExternalFilesDir(null).toString()}/app_icon/$packageName.png""")
    } else {
        File(path)
    }
    if (!iconFile.exists()) iconFile.run {
        parentFile?.mkdirs()
        createNewFile()
    }
    FileOutputStream(iconFile).let {
        this.compress(Bitmap.CompressFormat.PNG, 100, it)
        it.flush()
        it.close()
    }
}


//将bitmap转换为二进制
fun Bitmap.toByte(): ByteArray {
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    try {
        with(outputStream) { flush(); close() }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return outputStream.toByteArray()
}



