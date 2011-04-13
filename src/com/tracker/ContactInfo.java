package com.tracker;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class ContactInfo extends Activity{
	
	Date date; 
	String name;
	String photo;
	String coments;
	int rating;
	String contact;
	int[] postion = new int[0];
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactinfo);
        
        //ID
        TextView txtView_ID = (TextView) findViewById(R.id.txtView_ID);        
        DbOpenHelper dbOpenHelper = new DbOpenHelper(ContactInfo.this);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM history ORDER BY id DESC LIMIT 1", null);
        if (cursor.getCount() > 0)
        {
        	cursor.moveToFirst();
        	String count = cursor.getString(0);        
        	txtView_ID.setText("ID: " + count);
        }
        db.close();
        
        //Date
        TextView txtView_Date = (TextView) findViewById(R.id.txtView_Date);
        date = new Date();
        txtView_Date.setText("Date: " + date.toString());
        
        //Name
        final EditText edTxt_Name = (EditText) findViewById(R.id.edTxt_Name);
        
        //Photo
        ImageView imgView_Photo = (ImageView) findViewById(R.id.imgView_Photo);
        
        //Comments
        EditText edTxt_Comments = (EditText) findViewById(R.id.edTxt_Comments);
        
        //Rating
        final TextView txtView_Rating = (TextView) findViewById(R.id.txtView_Rating);
        SeekBar skBar_Rating = (SeekBar) findViewById(R.id.skBar_Rating);
        skBar_Rating.setProgress(5);
        skBar_Rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() 
        {			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				int prog =  progress + 1;
				txtView_Rating.setText(String.format("Rating: %d", Integer.valueOf(prog)));
			}
		});
        
        //Positions
        Spinner spinner_Positions = (Spinner) findViewById(R.id.spinner_Positions);
    
		//Save 
        Button btn_Save = (Button) findViewById(R.id.btn_Save);
        btn_Save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
			}
	    });
    }
}
