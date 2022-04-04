package h.w.rmuitool

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.drawable.toBitmap
import h.w.rmuitool.ktx.GlobalContext
import h.w.rmuitool.ktx.makeSP
import h.w.rmuitool.pullxml.XMLWithPull
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.Collator
import java.util.*

data class AppInfo(
    val label: String,
    val packageName: String,
    val icon: Bitmap,
)

class GetAppList {
    companion object;

    private val context = GlobalContext.context
    private val listLabel = mutableListOf<String>()
    private val listPackageName = mutableListOf<String>()
    private val isSystemAppValues = mutableListOf<Boolean>()
    private val dbHelper = AppInfoDB(context, "app_info.db", 1)
    private val db = dbHelper.writableDatabase

    @SuppressLint("QueryPermissionsNeeded", "Recycle", "CommitPrefEdits")
    fun putAppInfo() {
        val packageManager: PackageManager = context.packageManager
        val packageInfoList: List<PackageInfo> = packageManager.getInstalledPackages(0)
        var icon: Bitmap
        var packageName: String
        for (packageInfo: PackageInfo in packageInfoList) {
            if ((packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
                isSystemAppValues.add(false)
            } else {
                isSystemAppValues.add(true)
            }
            listLabel.add(
                packageManager.getApplicationLabel(packageInfo.applicationInfo).toString()
            )
            packageName = packageInfo.packageName.toString()
            icon = packageInfo.applicationInfo.loadIcon(packageManager).toBitmap()
            saveIcon(icon, packageName)
            listPackageName.add(packageName)
        }
        val tempListLabel = mutableListOf<String>()
        tempListLabel.addAll(listLabel)
        val collator = Collator.getInstance(Locale.CHINA)
        listLabel.sortWith(collator)
        val zoomList = XMLWithPull().parseXMLWithPull()
        var sub: Int
        listLabel.forEach {
            sub = tempListLabel.indexOf(it)
            val pn = listPackageName[sub]
            val value = ContentValues().apply {
                put("label", it)
                put("packageName", pn)
                put("isSystemApp", isSystemAppValues[sub].toString())
                put("zoomIsEnable", (zoomList.contains(pn)).toString())
//                put("darkModeIsEnable", zoomList.contains(it).toString())
//                put("doubleOpenIsEnable text", zoomList.contains(it).toString())
            }
            db.insert("AllAppInfo", null, value)
        }
        "database".makeSP().edit().run {
            putBoolean("have_database", true)
            apply()
        }
    }

    @SuppressLint("Range")
    fun selectAppInfo(showSystemApp: Boolean): List<AppInfo> {
        val mutableList = mutableListOf<AppInfo>()
        val cursor = db.rawQuery(
            "select * from AllAppInfo where isSystemApp = ?", arrayOf(showSystemApp.toString())
        )
        if (cursor.moveToFirst()) {
            do {
                val label = cursor.getString(cursor.getColumnIndex("label"))
                val packageName = cursor.getString(cursor.getColumnIndex("packageName"))
                val icon = BitmapFactory.decodeFile(
                    GlobalContext.context.getExternalFilesDir(null)
                        .toString() + "/app_icon/${packageName}.png"
                )
                mutableList.add(AppInfo(label, packageName, icon))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return mutableList.toList()
    }

    @SuppressLint("Recycle", "Range")
    fun selectEnableInfo(packageName: String): Boolean {
        val cursor = db.rawQuery(
            "select ZoomIsEnable from AllAppInfo where packageName = ?", arrayOf(packageName)
        )
        var value = false
        if (cursor.moveToFirst()) {
            value = cursor.getString(cursor.getColumnIndex("zoomIsEnable")).toBoolean()
        }
        cursor.close()
        return value
    }

    fun updateAppInfo(checked: Boolean, packageName: String) {
        db.execSQL(
            "update AllAppInfo set zoomIsEnable = ? where packageName = ?",
            arrayOf(checked.toString(), packageName)
        )
    }

    private fun saveIcon(icon: Bitmap, packageName: String) {
        val iconSavePath =
            context.getExternalFilesDir(null).toString() + "/app_icon/$packageName.png"
        try {
            val iconFile = File(iconSavePath)
            if (!iconFile.exists()) {
                iconFile.run {
                    parentFile?.mkdirs()
                    createNewFile()
                }
            }
            val fos = FileOutputStream(iconFile)
            fos.run {
                icon.compress(Bitmap.CompressFormat.PNG, 100, this)
                flush()
                close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

//SQLite DATABASE
class AppInfoDB(context: Context?, name: String?, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    private val createTable: String
        get() = "create table AllAppInfo(" +
                "label text," +
                "packageName text  primary key," +
                "isSystemApp text," +
                "zoomIsEnable text," +
                "darkModeIsEnable text," +
                "doubleOpenIsEnable text" +
                ")"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}