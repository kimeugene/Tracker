package com.tracker;

import java.util.TimerTask;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MyTimerTask extends TimerTask 
{
	private ImageView img_day1;
	private ImageView img_day2;
	private ImageView img_hour1;
	private ImageView img_hour2;
	private ImageView img_minute1;
	private ImageView img_minute2;
	private ImageView img_second1;
	private ImageView img_second2;
	private long lastDate = 0;
	int seconds;
 	int minutes;
 	int hours;
 	int days;
	//long timeMinutes;
	BitmapDrawable[][] frames;
	
	Handler handler = new Handler();
	
	public MyTimerTask(LinearLayout llView, BitmapDrawable[][] frames, long lastDate)
	{
		this.img_day1 = (ImageView)llView.findViewById(R.id.img_day1);
		this.img_day2 = (ImageView)llView.findViewById(R.id.img_day2);
		this.img_hour1 = (ImageView)llView.findViewById(R.id.img_hour1);
		this.img_hour2 = (ImageView)llView.findViewById(R.id.img_hour2);
		this.img_minute1 = (ImageView)llView.findViewById(R.id.img_minute1);
		this.img_minute2 = (ImageView)llView.findViewById(R.id.img_minute2);
		this.img_second1 = (ImageView)llView.findViewById(R.id.img_second1);
		this.img_second2 = (ImageView)llView.findViewById(R.id.img_second2);
		this.lastDate = lastDate;
		this.frames = frames;
		long millis = System.currentTimeMillis() - lastDate;
		seconds = (int)(millis / 1000);
	 	minutes = seconds / 60;
	 	hours = minutes / 60;
	 	days = hours / 24;
	 	hours = hours % 24;
	 	minutes = minutes % 60;
	 	seconds = seconds % 60;
	}
	
    public void run() 
    {   
    	handler.post(new Runnable() 
    	{
    		public void run() 
    		{
    			long millis = System.currentTimeMillis() - lastDate;
    			int secondsCurr = (int)(millis / 1000);
    			int minutesCurr = secondsCurr / 60;
    			int hoursCurr = minutesCurr / 60;
    			int daysCurr = hoursCurr / 24;
    			hoursCurr = hoursCurr % 24;
    		 	minutesCurr = minutesCurr % 60;
    		 	secondsCurr = secondsCurr % 60;
    		 	
    		 	AnimationDrawable animeDays1 = new AnimationDrawable();
    		 	AnimationDrawable animeDays2 = new AnimationDrawable();
    		 	AnimationDrawable animeHours1 = new AnimationDrawable();
    		 	AnimationDrawable animeHours2 = new AnimationDrawable();
    		 	AnimationDrawable animeMinutes1 = new AnimationDrawable();
    		 	AnimationDrawable animeMinutes2 = new AnimationDrawable();
    		 	AnimationDrawable animeSeconds1 = new AnimationDrawable();
    		 	AnimationDrawable animeSeconds2 = new AnimationDrawable();
    		 	
    		 	
    		 	for(int i = 0; i < 3; i++){
    		 		animeDays1.addFrame(frames[daysCurr / 10][i], 333);
    		 	}
    		 	for(int i = 0; i < 3; i++){
    		 		animeDays2.addFrame(frames[daysCurr % 10][i], 333);
    		 	}
    		 	for(int i = 0; i < 3; i++){
    		 		animeHours1.addFrame(frames[hoursCurr / 10][i], 333);
    		 	}
    		 	for(int i = 0; i < 3; i++){
    		 		animeHours2.addFrame(frames[hoursCurr % 10][i], 333);
    		 	}
    		 	for(int i = 0; i < 3; i++){
    		 		animeMinutes1.addFrame(frames[minutesCurr / 10][i], 333);
    		 	}
    		 	for(int i = 0; i < 3; i++){
    		 		animeMinutes2.addFrame(frames[minutesCurr % 10][i], 333);
    		 	}
    		 	for(int i = 0; i < 3; i++){
    		 		animeSeconds1.addFrame(frames[secondsCurr / 10][i], 333);
    		 	}
    		 	for(int i = 0; i < 3; i++){
    		 		animeSeconds2.addFrame(frames[secondsCurr % 10][i], 333);
    		 	}
    		 	
    		 	img_day1.setBackgroundDrawable(animeDays1);
    			img_day2.setBackgroundDrawable(animeDays2);
    			img_hour1.setBackgroundDrawable(animeHours1);
    			img_hour2.setBackgroundDrawable(animeHours2);
    			img_minute1.setBackgroundDrawable(animeMinutes1);
    			img_minute2.setBackgroundDrawable(animeMinutes2);
    		 	img_second1.setBackgroundDrawable(animeSeconds1);
    		 	img_second2.setBackgroundDrawable(animeSeconds2);
    		 	
    		 	animeDays1.setOneShot(true);
    			animeDays2.setOneShot(true);
    			animeHours1.setOneShot(true);
    			animeHours2.setOneShot(true);
    			animeMinutes1.setOneShot(true);
    			animeMinutes2.setOneShot(true);
    		 	animeSeconds1.setOneShot(true);
    		 	animeSeconds2.setOneShot(true);
    		 	
    		 	if(daysCurr / 10 != days / 10)
    		 		animeDays1.start();
    		 	else
    		 		img_day1.setBackgroundDrawable(frames[daysCurr / 10][2]);
    		 	if(daysCurr % 10 != days % 10)
    		 		animeDays2.start();
    		 	else
    		 		img_day2.setBackgroundDrawable(frames[daysCurr % 10][2]);
    		 	if(hoursCurr / 10 != hours / 10)
    		 		animeHours1.start();
    		 	else
    		 		img_hour1.setBackgroundDrawable(frames[hoursCurr / 10][2]);
    		 	if(hoursCurr % 10 != hours % 10)
    		 		animeHours2.start();
    		 	else
    		 		img_hour2.setBackgroundDrawable(frames[hoursCurr % 10][2]);
    		 	if(minutesCurr / 10 != minutes / 10)
    		 		animeMinutes1.start();
    		 	else
    		 		img_minute1.setBackgroundDrawable(frames[minutesCurr / 10][2]);
    		 	if(minutesCurr % 10 != minutes % 10)
    		 		animeMinutes2.start();
    		 	else
    		 		img_minute2.setBackgroundDrawable(frames[minutesCurr % 10][2]);
    		 	if(secondsCurr / 10 != seconds / 10)
    		 		animeSeconds1.start();
    		 	else{
    		 		img_second1.setBackgroundDrawable(frames[secondsCurr / 10][2]);
    		 	}
    		 	if(secondsCurr % 10 != seconds % 10)
    		 		animeSeconds2.start();
    		 	else
    		 		img_second2.setBackgroundDrawable(frames[secondsCurr % 10][2]);
    		 	days = daysCurr;
    		 	hours = hoursCurr;
    		 	minutes = minutesCurr;
    		 	seconds = secondsCurr;
			}
        
    	});
    }
}
