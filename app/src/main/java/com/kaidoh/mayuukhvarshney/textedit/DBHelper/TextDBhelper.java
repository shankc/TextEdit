package com.kaidoh.mayuukhvarshney.textedit.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.kaidoh.mayuukhvarshney.textedit.Contracts.TextEditContract.SQL_CREATE_ENTRIES;
import static com.kaidoh.mayuukhvarshney.textedit.Contracts.TextEditContract.SQL_DELETE_ENTRIES;

/**
 * Created by mayuukhvarshney on 10/09/17.
 */

public class TextDBhelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TextEdit.db";

    public TextDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
