package h.w.rmuitool.logic.model.appinfos

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.ApplicationInfo
import android.graphics.BitmapFactory
import androidx.core.graphics.drawable.toBitmap
import h.w.rmuitool.logic.utils.GlobalContext
import h.w.rmuitool.logic.utils.ktx.getSharedPreferences
import h.w.rmuitool.logic.utils.ktx.saveToLocal
import java.text.Collator
import java.util.*

class AppInfo {
    companion object;

    private val context = GlobalContext.context //获取全局context
    private val dbHelper = AppInfoSQL("app_info.db", 1)
    private val db = dbHelper.writableDatabase
    private val appInfoList = mutableListOf<AppInfoData>() //用于存储AppInfo

    fun putAllAppInfo() {
        val packageManager = context.packageManager
        val packageInfoList = packageManager.getInstalledPackages(0)
        val labels = mutableListOf<String>()
        val packageNames = mutableListOf<String>()
        val isSystemApps = mutableListOf<Boolean>()
        for (packageInfo in packageInfoList) {
            labels.add(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString())
            val packageName = packageInfo.packageName //获取包名
            packageNames.add(packageName)
            packageInfo.applicationInfo.loadIcon(packageManager).toBitmap().saveToLocal(packageName = packageName) //获取icon并保存
            isSystemApps.add(packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0)
        }
        val tempLabels = mutableListOf<String>()
        tempLabels.addAll(labels)
        tempLabels.sortWith(Collator.getInstance(Locale.CHINA)) //按名称对app进行排序
        labels.forEach { label ->
            val sub = tempLabels.indexOf(label)
            val packageName = packageNames[sub]
            val value = ContentValues().apply {
                put("label", label)
                put("packageName", packageName)
                put("isSystemApp", isSystemApps[sub].toString())
            }
            db.insert("AllAppInfo", null, value)
        }
        "database".getSharedPreferences().edit().run { putBoolean("have_database", true); apply() }
    }


    @SuppressLint("Range")
    fun selectAllAppInfo(showSystemApp: Boolean = false): List<AppInfoData> {
        val allAppInfo = mutableListOf<AppInfoData>()
        val cursor = db.rawQuery("select * from AllAppInfo where isSystemApp = ?", arrayOf(showSystemApp.toString()))
        if (cursor.moveToFirst()) {
            do {
                val label = cursor.getString(cursor.getColumnIndex("label"))
                val packageName = cursor.getString(cursor.getColumnIndex("packageName"))
                val icon = BitmapFactory.decodeFile(GlobalContext.context.getExternalFilesDir(null).toString() + "/app_icon/${packageName}.png")
                allAppInfo += AppInfoData(label, packageName, icon)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return allAppInfo.toList()
    }


    fun setFunctionInfo(function: String, checked: Boolean, packageName: String) {
        db.execSQL("update AllAppInfo set $function = ? where packageName = ?", arrayOf(checked.toString(), packageName))
    }

    @SuppressLint("Recycle", "Range")
    fun getFunctionInfo(function: String, packageName: String): Boolean {
        var isEnable = false
        val cursor = db.rawQuery("select $function from AllAppInfo where packageName = ?", arrayOf(packageName))
        if (cursor.moveToFirst()) {
            isEnable = cursor.getString(cursor.getColumnIndex("zoomIsEnable")).toBoolean()
        }
        return isEnable
    }

}