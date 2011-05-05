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

public class Main extends Activity 
{
	Timer timer;
	ImageView save_event_imv;
	ImageView splash_imv;
	Button history_btn;
	private boolean isOpen;
	private final static int DURATION = 100;
    private AnimationDrawable mAnimation = null;
    
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        isOpen = false;
        SetTimer();
        
        history_btn = (Button) findViewById(R.id.history_btn);
        history_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
			    Intent myIntent = new Intent(view.getContext(), History.class);
     		    startActivityForResult(myIntent, 0);
			}
	    });
        
        save_event_imv = (ImageView) findViewById(R.id.save_event_imv);
        save_event_imv.setBackgroundResource(R.drawable.button_close);
        save_event_imv.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		startAnimation();
        		if(!isOpen){        			
				    Intent myIntent = new Intent(view.getContext(), ContactInfo.class);
				    startActivityForResult(myIntent, 0);
				    if(timer != null)
				    	timer.cancel();
				    SetTimer(); 
        		}
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
   		BitmapDrawable[] frames = new BitmapDrawable[17];
    		frames[0] = (BitmapDrawable) getResources().getDrawable(R.drawable.button_open);
    		
    		// frames 1,2,3
    		// button_pressing 1,2,3
    		for (int i = 1; i < 4; i++) {
    			int resID = getResources().getIdentifier("button_pressing" + i, "drawable", getPackageName());
    			frames[i] = (BitmapDrawable) getResources().getDrawable(resID);
    		}

    		// frames 4,5,6 
    		// button_pressing 3,2,1
    		for (int i = 3; i > 0; i--) {
    			int resID = getResources().getIdentifier("button_pressing" + i, "drawable", getPackageName());
    			frames[7 - i] = (BitmapDrawable) getResources().getDrawable(resID);
    		}

    		// frames 7..15
    		// button_open_up 9..1
    		for (int i = 7; i < 16; i++) {
    			int resID = getResources().getIdentifier("button_open_up" + (16 - i), "drawable", getPackageName());
    			frames[i] = (BitmapDrawable) getResources().getDrawable(resID);
    		}
	    	frames[16] = (BitmapDrawable) getResources().getDrawable(R.drawable.button_close);
	    	
    		for (int i = 0; i <= 16; i++) {
    	    	mAnimation.addFrame(frames[i], DURATION);
    		}
		    isOpen = false;
    	}
    	else
    	{
    		BitmapDrawable[] frames = new BitmapDrawable[11];
    		
    		frames[0] = (BitmapDrawable)getResources().getDrawable(R.drawable.button_close); 
    		
    		// frames 2..10
    		// button_open_up 1..9
    		for (int i = 1; i <= 9; i++) {
    			int resID = getResources().getIdentifier("button_open_up" + i, "drawable", getPackageName());
    			frames[i] = (BitmapDrawable) getResources().getDrawable(resID);
    		}
    		
    		frames[10] = (BitmapDrawable)getResources().getDrawable(R.drawable.button_open);	    	
	    	
    		for (int i = 0; i <= 10; i++) {
    	    	mAnimation.addFrame(frames[i], DURATION);
    		}
   		
	        isOpen = true;
    	}
    	save_event_imv.setBackgroundDrawable(mAnimation);
        mAnimation.setVisible(false,false);
        mAnimation.start();
	}
    
    /**
     * Retrieves the latest entry and starts timer
     */
    public void SetTimer()
    {
    	DbOpenHelper dbOpenHelper = new DbOpenHelper(Main.this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT date FROM history ORDER BY id DESC LIMIT 1", null);
        int cnt = cursor.getCount();
        db.close();
        if (cnt > 0){
        	cursor.moveToFirst();
            String lastDate = cursor.getString(0);  
            long dte = Date.parse(lastDate);
            TextView txt = (TextView) findViewById(R.id.txtCounter);
            timer = new Timer();
            TimerTask task = new MyTimerTask(txt, dte);
            timer.schedule(task, 1, 300);    
        }
    }
    
    @Override 
    protected void onStop(){
    	super.onStop(); 
    	if(timer != null)
    		timer.cancel();
    }
}
