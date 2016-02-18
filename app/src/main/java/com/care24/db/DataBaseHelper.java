package com.care24.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vishal on 17/2/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private final String TAG = DataBaseHelper.class.getSimpleName();
    public static String DATABASE_NAME = "Vcare";
    public static int VERSION = 1;

    public static String PHOTOS_TABLE_NAME = "photos";
    public static String PHOTOS_ID = "id";
    public static String PHOTOS_URL = "url";
    public static String PHOTOS_TITLE = "title";

    private String PHOTOS_CREATE_TABLE = "create table " + PHOTOS_TABLE_NAME + "("
            + PHOTOS_ID + " int primary key, "
            + PHOTOS_URL + " text not null, "
            + PHOTOS_TITLE + " text);";

    private String PHOTOS_ALTER_TABLE = "alter table column "
            + PHOTOS_ID + " integer primary key);";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PHOTOS_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PHOTOS_ALTER_TABLE);

    }
}
