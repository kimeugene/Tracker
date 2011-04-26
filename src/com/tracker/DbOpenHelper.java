
package com.tracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper{
	
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "tracker";

    public static final String TABLE_NAME = "history";
    public static final String ID = "id";
    public static final String Date = "date";
    public static final String Name = "name";
    public static final String Photo = "photo";
    public static final String Comments = "comments";
    public static final String Rating = "rating";
    public static final String Position = "position";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (id integer primary key autoincrement, "
            + Date + " TEXT, " + Name + " TEXT, " + Photo + " TEXT, " + Comments + " TEXT, " + Rating + " NUMERIC, " + Position + " NUMERIC)";


    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
