package com.tracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import com.tracker.DbOpenHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.content.ContentValues;

public class Main extends Activity 
{
	Timer timer;
	ImageView save_event_imv;
	private boolean isOpen;
	private final static int DURATION = 100;
    private AnimationDrawable mAnimation = null;
    
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        isOpen = false;
        DbOpenHelper dbOpenHelper = new DbOpenHelper(Main.this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT date FROM history ORDER BY id DESC LIMIT 1", null);
        int cnt = cursor.getCount();
        db.close();
        
        if (cnt > 0){
        	cursor.moveToFirst();
            String lastDate = cursor.getString(0);        
            // start timer
            SetTimer(lastDate);
        }
        
        Button history_btn = (Button) findViewById(R.id.history_btn);
        history_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
			    Intent myIntent = new Intent(view.getContext(), History.class);
			    startActivityForResult(myIntent, 0);
			}
	    });
        
        save_event_imv = (ImageView) findViewById(R.id.save_event_imv);
        save_event_imv.setBackgroundResource(R.drawable.button_close);
        save_event_imv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startAnimation();
            }
        });
    }
    
    private void startAnimation() 
    {
    	save_event_imv.setBackgroundResource(0);
    	mAnimation = new AnimationDrawable();
        mAnimation.setOneShot(true);
    	if(isOpen)
    	{    		
	    	BitmapDrawable frame1 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open); 
	    	BitmapDrawable frame2 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_pressing1); 
	    	BitmapDrawable frame3 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_pressing2); 
	    	BitmapDrawable frame4 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_pressing3);
	    	BitmapDrawable frame5 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_pressing3); 
	    	BitmapDrawable frame6 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_pressing2);
	    	BitmapDrawable frame7 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_pressing1);
	    	BitmapDrawable frame8 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up9);
	    	BitmapDrawable frame9 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up8);
	    	BitmapDrawable frame10 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up7);	
	    	BitmapDrawable frame11 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up6);
	    	BitmapDrawable frame12 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up5);
	    	BitmapDrawable frame13 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up4);
	    	BitmapDrawable frame14 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up3);
	    	BitmapDrawable frame15 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up2);
	    	BitmapDrawable frame16 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up1);
	    	BitmapDrawable frame17 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_close);
	    	mAnimation.addFrame(frame1, DURATION);
	        mAnimation.addFrame(frame2, DURATION);
	        mAnimation.addFrame(frame3, DURATION);
	        mAnimation.addFrame(frame4, DURATION);
	        mAnimation.addFrame(frame5, DURATION);
	        mAnimation.addFrame(frame6, DURATION);
	        mAnimation.addFrame(frame7, DURATION);
	        mAnimation.addFrame(frame8, DURATION);
	        mAnimation.addFrame(frame9, DURATION);
	        mAnimation.addFrame(frame10, DURATION);
	        mAnimation.addFrame(frame11, DURATION);
	        mAnimation.addFrame(frame12, DURATION);
	        mAnimation.addFrame(frame13, DURATION);
	        mAnimation.addFrame(frame14, DURATION);
	        mAnimation.addFrame(frame15, DURATION);
	        mAnimation.addFrame(frame16, DURATION);
	        mAnimation.addFrame(frame17, DURATION);
	        
    		DbOpenHelper dbOpenHelper = new DbOpenHelper(Main.this);
		    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		    ContentValues cv = new ContentValues();
		    String lastDate = new Date().toString();
		    cv.put(DbOpenHelper.DATE, lastDate);
		    db.insert(DbOpenHelper.TABLE_NAME, null, cv);
		    db.close();
		    if(timer != null)
		    	timer.cancel();
		    SetTimer(lastDate);
		    isOpen = false;
    	}
    	else
    	{
	    	BitmapDrawable frame1 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_close); 
	    	BitmapDrawable frame2 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up1); 
	    	BitmapDrawable frame3 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up2); 
	    	BitmapDrawable frame4 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up3); 
	    	BitmapDrawable frame5 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up4); 
	    	BitmapDrawable frame6 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up5); 
	    	BitmapDrawable frame7 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up6);
	    	BitmapDrawable frame8 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up7);
	    	BitmapDrawable frame9 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up8);
	    	BitmapDrawable frame10 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open_up9);
	    	BitmapDrawable frame11 = 
	            (BitmapDrawable)getResources().getDrawable(R.drawable.button_open);	    	
	    	
	        mAnimation.addFrame(frame1, DURATION);
	        mAnimation.addFrame(frame2, DURATION);
	        mAnimation.addFrame(frame3, DURATION);
	        mAnimation.addFrame(frame4, DURATION);
	        mAnimation.addFrame(frame5, DURATION);
	        mAnimation.addFrame(frame6, DURATION);
	        mAnimation.addFrame(frame7, DURATION);
	        mAnimation.addFrame(frame8, DURATION);
	        mAnimation.addFrame(frame9, DURATION);
	        mAnimation.addFrame(frame10, DURATION);
	        mAnimation.addFrame(frame11, DURATION);
	        isOpen = true;
    	}
    	save_event_imv.setBackgroundDrawable(mAnimation);
        mAnimation.setVisible(false,false);
        mAnimation.start();
	}

    
    /**
     * Retrieves the latest entry and starts timer
     */
    public void SetTimer(String lastDate)
    {
    	long dte = Date.parse(lastDate);
    	TextView txt = (TextView) findViewById(R.id.txtCounter);
        timer = new Timer();
        TimerTask task = new MyTimerTask(txt, dte);
        timer.schedule(task, 1, 300);        
    }
    
    @Override 
    protected void onStop(){
    	super.onStop(); 
    	timer.cancel();
    }
}
