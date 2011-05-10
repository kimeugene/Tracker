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
import android.graphics.drawable.Drawable.Callback;

public class Main extends Activity 
{
	Timer timer;
	ImageView save_event_imv;
	ImageView splash_imv;
	Button history_btn;
	private final static int DURATION = 100;
    private AnimationDrawable mAnimation = null;
    
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        SetTimer();
        
        history_btn = (Button) findViewById(R.id.history_btn);
        history_btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
			    Intent myIntent = new Intent(view.getContext(), History.class);
     		    startActivityForResult(myIntent, 0);
			}
	    });
        
        save_event_imv = (ImageView) findViewById(R.id.save_event_imv);
        save_event_imv.setBackgroundResource(R.drawable.button_1);
        save_event_imv.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		startAnimation();
        		Thread splashTread = new Thread() {
                    @Override
                    public void run() {
                        try {
                        		sleep(1800);
							    Intent myIntent = new Intent(getApplicationContext(), ContactInfo.class);
							    startActivityForResult(myIntent, 0);
                        }
                        catch (Exception e) {
							// TODO: handle exception
						}
                    }};
                splashTread.start();
			    /*if(timer != null)
			    	timer.cancel();
			    SetTimer(); */
            }
        });
    }
    
    private void startAnimation() 
    {
    	save_event_imv.setBackgroundResource(0);
    	mAnimation = new AnimationDrawable();
        mAnimation.setOneShot(true);
    	
		BitmapDrawable[] frames = new BitmapDrawable[15];
		
		for (int i = 1; i <= 15; i++) {
			int resID = getResources().getIdentifier("button_" + i, "drawable", getPackageName());
			frames[i - 1] = (BitmapDrawable) getResources().getDrawable(resID);
		}    	
    	
		for (int i = 0; i < 15; i++) {
	    	mAnimation.addFrame(frames[i], DURATION);
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
    	save_event_imv.setBackgroundResource(R.drawable.button_1);
    }
}
