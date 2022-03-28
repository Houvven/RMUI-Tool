package h.w.rmuitool

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import java.text.Collator
import java.util.*


class GetAppList {
    @SuppressLint("QueryPermissionsNeeded")
    fun init(context: Context, hideSystemApp: Boolean): ArrayList<AppInfo> {

        val appInfoList: ArrayList<AppInfo> = ArrayList()

        val packageManager: PackageManager = context.packageManager
        val packageInfoList: List<PackageInfo> = packageManager.getInstalledPackages(0)

        val listLabel = mutableListOf<String>()
        val listPackageName = mutableListOf<String>()
        val listIcon = mutableListOf<Drawable>()

        for (packageInfo: PackageInfo in packageInfoList) {
            if ((packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0 || !hideSystemApp) {
                listLabel.add(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString())
                listPackageName.add(packageInfo.packageName.toString())
                listIcon.add(packageInfo.applicationInfo.loadIcon(packageManager))
            }
        }

        val tempListLabel = mutableListOf<String>()
        tempListLabel.addAll(listLabel)

        val collator = Collator.getInstance(Locale.CHINA)
        listLabel.sortWith(collator)

        for (label in listLabel) {
            val sub = tempListLabel.indexOf(label)
            appInfoList.add(AppInfo(label, listPackageName[sub], listIcon[sub]))
        }

        return appInfoList
    }

    fun save() {

    }
}