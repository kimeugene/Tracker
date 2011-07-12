package com.tracker;

import java.util.Date;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DateControl extends Activity{
	DatePicker datePicker;
	TimePicker timePicker;
	 /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_control);
        
        datePicker =(DatePicker) findViewById(R.id.DateControl_datePicker);
        timePicker = (TimePicker) findViewById(R.id.DateControl_timePicker);
        Button btn_ok = (Button) findViewById(R.id.DateControl_btn_OK);
        btn_ok.setOnClickListener(fff);
    }
    
    OnClickListener fff = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String date = new Date(datePicker.getYear() - 1900, 
								datePicker.getMonth(), 
								datePicker.getDayOfMonth(), 
								timePicker.getCurrentHour(), 
								timePicker.getCurrentMinute())
								.toLocaleString();
            Intent intent = new Intent();
            intent.putExtra("date", date);
            setResult(RESULT_OK, intent);
            finish();
		}
    };
}

