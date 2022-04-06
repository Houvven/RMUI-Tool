package h.w.rmuitool.logic.model.appinfos

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import h.w.rmuitool.logic.utils.GlobalContext

class AppInfoSQL(name: String?, version: Int) :
    SQLiteOpenHelper(
        GlobalContext.context,
        name,
        null,
        version
    ) {
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