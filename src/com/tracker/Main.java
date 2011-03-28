package com.tracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.tracker.DbOpenHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

public class Main extends Activity 
{
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // start timer
        SetTimer();
        
        Button history_btn = (Button) findViewById(R.id.history_btn);
        history_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
			    Intent myIntent = new Intent(view.getContext(), History.class);
			    startActivityForResult(myIntent, 0);
			}
	    });
        
        Button save_event_btn = (Button) findViewById(R.id.save_event_btn);
        
        save_event_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
			    DbOpenHelper dbOpenHelper = new DbOpenHelper(Main.this);
			    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			    ContentValues cv = new ContentValues();
			    cv.put(DbOpenHelper.DATE, new Date().toString());
			    db.insert(DbOpenHelper.TABLE_NAME, null, cv);
			    db.close();
			    SetTimer();
			}
	    });
    }
    
    /**
     * Retrieves the latest entry and starts timer
     */
    public void SetTimer()
    {
    	TextView txt = (TextView) findViewById(R.id.txtCounter);
        DbOpenHelper dbOpenHelper = new DbOpenHelper(Main.this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT date FROM history ORDER BY id DESC LIMIT 1", null);
        cursor.moveToLast();
        db.close();

        long lastDate = Date.parse(cursor.getString(0));        
        Timer timer = new Timer();
        TimerTask task = new MyTimerTask(txt, lastDate);
        timer.schedule(task, 1, 300);        
    }
}
