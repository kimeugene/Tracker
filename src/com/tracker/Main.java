package com.tracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
	private final static int DURATION = 20;
    private AnimationDrawable mAnimation = new AnimationDrawable();
    BitmapDrawable[] frames;
    
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        frames = new BitmapDrawable[31];
		
		for (int i = 1; i <= 31; i++) {
			int resID = getResources().getIdentifier("button_" + i, "drawable", getPackageName());
			frames[i - 1] = (BitmapDrawable) getResources().getDrawable(resID);
		}
    	
		for (int i = 0; i < 31; i++) {
	    	mAnimation.addFrame(frames[i], DURATION);
		}
        history_btn = (Button) findViewById(R.id.btn_history);
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
                        		sleep(2000);
							    Intent myIntent = new Intent(getApplicationContext(), ContactInfo.class);
							    startActivityForResult(myIntent, 0);
                        }
                        catch (Exception e) {
							// TODO: handle exception
						}
                    }};
                splashTread.start();
            }
        });
    }
    
    private void startAnimation() 
    {
    	save_event_imv.setBackgroundResource(0);
        mAnimation.setOneShot(true);
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
        
        BitmapDrawable[][] frames = new BitmapDrawable[10][3];
		
        for (int i = 0; i <= 9; i++){        	
			for (int j = 1; j <= 3; j++) {
				int resID = getResources().getIdentifier("counter_" + i + "_" + j, "drawable", getPackageName());
				frames[i][j - 1] = (BitmapDrawable) getResources().getDrawable(resID);
			}
        }
        
        if (cnt > 0){
        	cursor.moveToFirst();
            String lastDate = cursor.getString(0);  
            long dte = Date.parse(lastDate);
            LinearLayout llView = (LinearLayout) findViewById(R.id.Main_ll_Counter_Img);
            timer = new Timer();
            TimerTask task = new MyTimerTask(llView, frames, dte);
            timer.schedule(task, 0, 1000);
        }
    }
    
    @Override 
    protected void onStop(){
    	if(timer != null)
     	   timer.cancel();
    	super.onStop();     	
    	save_event_imv.setBackgroundResource(R.drawable.button_1);
    }
    
    @Override
    protected void onResume() {
       super.onResume();
       SetTimer();
       Music.play(this, R.raw.main);
    }

    @Override
    protected void onPause() {
       super.onPause();
       if(timer != null)
    	   timer.cancel();
       Music.stop(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
       case R.id.settings:
          startActivity(new Intent(this, Prefs.class));
          return true;
       }
       return false;
    }
}
