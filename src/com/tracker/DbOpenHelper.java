
package com.tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper{
	
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "tracker";

    public static final String TABLE_NAME_HISTORY = "history";
    public static final String TABLE_NAME_SETTINGS = "settings";
    public static final String ID = "id";
    public static final String Date = "date";
    public static final String Name = "name";
    public static final String Photo = "photo";
    public static final String Comments = "comments";
    public static final String Rating = "rating";
    public static final String Position = "position";
    public static final String Password = "Password";
    private static final String CREATE_TABLE_HISTORY = "create table " + TABLE_NAME_HISTORY + " (id integer primary key autoincrement, "
            						+ Date + " TEXT, " + Name + " TEXT, " + Photo + " BLOB, " + Comments + " TEXT, " + Rating + " NUMERIC, " + Position + " NUMERIC)";
    private static final String CREATE_TABLE_SETTINGS = "create table " + TABLE_NAME_SETTINGS + " (id integer primary key autoincrement, "
    								+ OptionEnum.First_Load + " NUMERIC, " + OptionEnum.Password_Protection + " NUMERIC, " + Password + " TEXT, " + OptionEnum.Sound + " NUMERIC, " + OptionEnum.Twitter + " NUMERIC, " + OptionEnum.Facebook + " NUMERIC)";


    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_HISTORY);
        sqLiteDatabase.execSQL(CREATE_TABLE_SETTINGS);
        ContentValues cv = new ContentValues();
        cv.put(OptionEnum.First_Load.name(), 1);
        cv.put(OptionEnum.Password_Protection.name(), 1);
        cv.put(Password, "");
        cv.put(OptionEnum.Sound.name(), 1);
        cv.put(OptionEnum.Twitter.name(), 1);
        cv.put(OptionEnum.Facebook.name(), 1);
        sqLiteDatabase.insert(DbOpenHelper.TABLE_NAME_SETTINGS, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //To change body of implemented methods use File | Settings | File Templates.
    	sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HISTORY); 
    	sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SETTINGS); 
    	onCreate(sqLiteDatabase);
    }
}
