package com.tracker;

import java.util.TimerTask;

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
	private boolean testVal = false;
	
	Handler handler = new Handler();
	
	public MyTimerTask(LinearLayout llView, long lastDate)
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
	}
	
    public void run() 
    {   
    	handler.post(new Runnable() 
    	{
    		public void run() 
    		{
    			if(testVal){
	    			img_day1.setBackgroundResource(R.drawable.counter_2);
	    			img_day2.setBackgroundResource(R.drawable.counter_2);
	    			img_hour1.setBackgroundResource(R.drawable.counter_2);
	    			img_hour2.setBackgroundResource(R.drawable.counter_2);
	    			img_minute1.setBackgroundResource(R.drawable.counter_2);
	    			img_minute2.setBackgroundResource(R.drawable.counter_2);
	    			img_second1.setBackgroundResource(R.drawable.counter_2);
	    			img_second2.setBackgroundResource(R.drawable.counter_2);
	    			testVal = false;
    			}
    			else {
    				img_day1.setBackgroundResource(R.drawable.counter_1);
	    			img_day2.setBackgroundResource(R.drawable.counter_1);
	    			img_hour1.setBackgroundResource(R.drawable.counter_1);
	    			img_hour2.setBackgroundResource(R.drawable.counter_1);
	    			img_minute1.setBackgroundResource(R.drawable.counter_1);
	    			img_minute2.setBackgroundResource(R.drawable.counter_1);
	    			img_second1.setBackgroundResource(R.drawable.counter_1);
	    			img_second2.setBackgroundResource(R.drawable.counter_1);
	    			testVal = true;
    			}
    			/*long millis = System.currentTimeMillis() - lastDate;
    			int seconds = (int)(millis / 1000);
    		 	int minutes = seconds / 60;
    		 	int hours = minutes / 60;
    		 	int days = hours / 24;
    		 	hours = hours % 24;
    		 	minutes = minutes % 60;
    		 	seconds = seconds % 60;
    		 	if(days <= 1)
    		 		txtView.setBackgroundColor(Color.GREEN);
    		 	else if(days <= 2)
    		 		txtView.setBackgroundColor(Color.YELLOW);
    		 	else
    		 		txtView.setBackgroundColor(Color.RED);
    		 	txtView.setText(String.format("%d days \n %d:%d:%d", days, hours, minutes, seconds));   */ 			
			}
        
    	});
    }
}
