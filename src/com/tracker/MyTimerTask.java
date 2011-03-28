package com.tracker;

import java.util.TimerTask;

import com.tracker.R.string;

import android.graphics.Color;
import android.os.Handler;
import android.widget.TextView;

public class MyTimerTask extends TimerTask 
{
	TextView txtView;
	long lastDate = 0;
	Handler handler = new Handler();
	public MyTimerTask(TextView txtView, long lastDate)
	{
		this.txtView = txtView;
		this.lastDate = lastDate;
	}
	
    public void run() 
    {   
    	handler.post(new Runnable() 
    	{
    		public void run() 
    		{
    			long millis = System.currentTimeMillis() - lastDate;
    		 	int minutes = (int)(millis/60000);
    		 	int hours = minutes / 60;
    		 	int days = hours / 24;
    		 	hours = hours % 24;
    		 	minutes = minutes % 60;
    		 	if(days <= 1)
    		 		txtView.setBackgroundColor(Color.GREEN);
    		 	else if(days <= 2)
    		 		txtView.setBackgroundColor(Color.YELLOW);
    		 	else
    		 		txtView.setBackgroundColor(Color.RED);
    		 	txtView.setText(String.format("%d days %d hours %d minutes", days, hours, minutes));    			
			}
        
    	});
    }
}
