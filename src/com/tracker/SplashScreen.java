package com.tracker;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SplashScreen extends Activity 
{
	private boolean _active = true;
	private int _splashTime = 6000;
    private ImageView splash_imv;
    private AnimationDrawable mAnimation;
    private final int set_password_protection = 0;
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase dataBase;
    private ContentValues contentValues;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        /**
         * Uncomment this code if you need upgrade Database
         */
        /*dbOpenHelper = new DbOpenHelper(this);
        dbOpenHelper.onUpgrade(dbOpenHelper.getReadableDatabase(), 0, 00);
        addNewContact(new Date().toLocaleString(), "Aleksandra", new byte[0], "comments", 9, 1);
        addNewContact(new Date().toLocaleString(), "Kristina", new byte[0], "comments", 10, 2);
        addNewContact(new Date().toLocaleString(), "Tanya", new byte[0], "comments", 9, 3);
        */
        splash_imv = (ImageView) findViewById(R.id.splash_imv);
        splash_imv.setBackgroundResource(R.animator.splash_anim);
        
        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                	sleep(1000);
                    int waited = 0;

                    mAnimation = (AnimationDrawable)splash_imv.getBackground();
                    mAnimation.setOneShot(true);
                    mAnimation.start();
                    
                    while(_active && (waited < _splashTime)) {
                        sleep(30);
                        if(_active) {
                            waited += 100;
                        }
                    }
                    mAnimation.stop();
                    runOnUiThread(new Runnable() {
    					@Override
    					public void run() {
    						splash_imv.setBackgroundResource(R.drawable.sextracker22);
    						checkFirstLoad();
    					}});
                    
                    stop();
                }
                catch (Exception e) {
					// TODO: handle exception
                	finish();
				}
            }
        };
        splashTread.start();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
    
    private void checkFirstLoad() {
    	dbOpenHelper = new DbOpenHelper(this);
    	dataBase = dbOpenHelper.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("Select " + OptionEnum.First_Load.name() 
        		+ ", " + OptionEnum.Password_Protection.name()
        		+ " FROM " + DbOpenHelper.TABLE_NAME_SETTINGS, null);
		cursor.moveToLast();
		int first_Load = cursor.getInt(0);
		int password_Protection = cursor.getInt(1);
		dataBase.close();
		if(first_Load == 1){
			showDialog(set_password_protection);
		}
		else if(password_Protection == 1){
			finish();
			Intent myIntent = new Intent(getApplicationContext(), Password.class);
        	myIntent.putExtra("first_Load", false);
        	startActivityForResult(myIntent, 0);
		}
		else {
			finish();
            Intent myIntent = new Intent(getApplicationContext(), Main.class);
        	startActivity(myIntent);
		}
	}
     
    @Override
    protected Dialog onCreateDialog(int id) {
		switch (id) {
		case set_password_protection:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enable password protection?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	Intent myIntent = new Intent(getApplicationContext(), Password.class);
                	myIntent.putExtra("first_Load", true);
                	startActivityForResult(myIntent, 0);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    dbOpenHelper = new DbOpenHelper(getApplicationContext());
                    dataBase = dbOpenHelper.getWritableDatabase();
                    contentValues = new ContentValues();
                    contentValues.put(OptionEnum.First_Load.name(), 0);
                    contentValues.put(OptionEnum.Password_Protection.name(), 0);
                    dataBase.update(DbOpenHelper.TABLE_NAME_SETTINGS, contentValues, DbOpenHelper.ID + " = " + 1, null);
                    dataBase.close();
                    finish();
                    Intent myIntent = new Intent(getApplicationContext(), Main.class);
                	startActivity(myIntent);
                }
            });
            builder.setCancelable(false);
            return builder.create();
        default:
        	return null;
        }
	}
    
    private void addNewContact(String date, String name, byte[] photo, String comments, int rating, int position)
    {
    	dbOpenHelper = new DbOpenHelper(this);
    	dataBase = dbOpenHelper.getWritableDatabase();
    	contentValues = new ContentValues();
    	contentValues.put(DbOpenHelper.Date, date);
        contentValues.put(DbOpenHelper.Name, name);
        contentValues.put(DbOpenHelper.Photo, photo);
        contentValues.put(DbOpenHelper.Comments, comments);
        contentValues.put(DbOpenHelper.Rating, rating);
        contentValues.put(DbOpenHelper.Position, position);
        dataBase.insert(DbOpenHelper.TABLE_NAME_HISTORY, null, contentValues);
    }
}
