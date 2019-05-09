package com.firman.finansial.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Firman on 4/26/2019.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String NAMA_FILE_DB = "keuangan.db";
    private static final int VERSI_DB = 1;

    DBHelper(Context context) {
        super(context, NAMA_FILE_DB, null, VERSI_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableHolder.KEUANGAN.SQL_CREATE);
        db.execSQL(TableHolder.KATEGORI.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableHolder.KEUANGAN.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableHolder.KATEGORI.NAME);
        onCreate(db);
    }
}
