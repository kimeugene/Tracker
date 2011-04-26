package com.tracker;

import java.util.ArrayList;
import java.util.Date;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ContactInfo extends Activity{
	
	Date date;
	ImageView imgView_Photo = null;
	Uri imgUri = null;
	ArrayAdapter<String> arrayAdapter;
    LayoutInflater inflater;
    protected Drawable drawable1, drawable2;
	ViewGroup v;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactinfo);
         
        TextView txtView_ID = (TextView) findViewById(R.id.txtView_ID);    
        TextView txtView_Date = (TextView) findViewById(R.id.txtView_Date);
        final EditText edTxt_Name = (EditText) findViewById(R.id.edTxt_Name);
        imgView_Photo = (ImageView) findViewById(R.id.imgView_Photo);
        final EditText edTxt_Comments = (EditText) findViewById(R.id.edTxt_Comments);
        final TextView txtView_Rating = (TextView) findViewById(R.id.txtView_Rating);
        final SeekBar skBar_Rating = (SeekBar) findViewById(R.id.skBar_Rating);
        final Spinner spinner_Positions = (Spinner) findViewById(R.id.spinner_Positions);
        Button btn_Save = (Button) findViewById(R.id.btn_Save);
        
        inflater = getLayoutInflater();
        drawable1 = getResources().getDrawable(R.drawable.icon);
        ArrayList<String> items = new ArrayList<String>();
        items.add("Position1");
        items.add("Position2");
        items.add("Position3");
        items.add("Position4");
        items.add("Position5");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) 
            {
                if (convertView == null) 
                {
                    convertView = inflater.inflate(R.layout.position_item, null);
                }
                CheckedTextView tv = (CheckedTextView) convertView;
                tv.setCompoundDrawablesWithIntrinsicBounds(drawable1, null, null, null);
                tv.setText(getItem(position));
                return convertView;
            }
        };
        spinner_Positions.setAdapter(arrayAdapter);
        
        int id;
        Bundle bg = getIntent().getExtras();
        if(bg != null) //Pokaz sushestvuyushego kontakta
        {
        	id = bg.getInt("id");
        	DbOpenHelper dbOpenHelper = new DbOpenHelper(ContactInfo.this);
            SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM history WHERE id = " + String.valueOf(id), null);
            if (cursor.getCount() > 0)
            {
            	cursor.moveToFirst();
            	String Date = cursor.getString(1);
            	String Name = cursor.getString(2);
            	String Photo = cursor.getString(3);
            	String Comments = cursor.getString(4);
            	int Rating = cursor.getInt(5);
            	int Position = cursor.getInt(6);
            	
            	txtView_ID.setText("ID: " + String.valueOf(id));
            	txtView_Date.setText("Date: " + Date);
            	edTxt_Name.setText(Name);
            	imgView_Photo.setImageURI(Uri.parse(Photo));
            	edTxt_Comments.setText(Comments);
            	skBar_Rating.setProgress(Rating - 1);
            	txtView_Rating.setText(String.format("Rating: %d", Integer.valueOf(skBar_Rating.getProgress() + 1)));
            	spinner_Positions.setSelection(Position);
            	
            	edTxt_Name.setFocusable(false);
            	imgView_Photo.setFocusable(false);
            	edTxt_Comments.setFocusable(false);
            	skBar_Rating.setEnabled(false);
            	spinner_Positions.setClickable(false);
            	btn_Save.setText("Back");
            	btn_Save.setOnClickListener(new View.OnClickListener() {
        			public void onClick(View view) {
        				finish();
        			}
        		});
            }
            db.close();
        }
        else //Dobavlenie novogo kontakta
        {
        	//ID
        	DbOpenHelper dbOpenHelper = new DbOpenHelper(ContactInfo.this);
            SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT id FROM history ORDER BY id DESC LIMIT 1", null);
            if (cursor.getCount() > 0)
            {
            	cursor.moveToFirst();
            	int count = cursor.getInt(0) + 1;        
            	txtView_ID.setText("ID: " + String.valueOf(count));
            }
            db.close();
            
            //Date
            date = new Date();
            txtView_Date.setText("Date: " + date.toLocaleString());
            
            //Photo
            imgView_Photo.setOnClickListener(new View.OnClickListener() {
            	public void onClick(View view) {
    		        Intent chooseFileIntent = new Intent();
    		        chooseFileIntent.setAction(Intent.ACTION_GET_CONTENT);
    		        chooseFileIntent.setType("image/jpeg");
    		        startActivityForResult(chooseFileIntent, 1);
            	}
            });
            
            //Rating
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
            
            //Position
            spinner_Positions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                    
                }
                public void onNothingSelected(AdapterView adapterView) {}
            });

    		/**
            * Save 
            */
            btn_Save.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View view) {
    				
    				if(edTxt_Name.getText().toString().length() == 0)
    				{
    					Toast.makeText(getApplicationContext(), "Fill all data!", 5000).show();
    					return;
    				}
    				DbOpenHelper dbOpenHelper = new DbOpenHelper(ContactInfo.this);
    				SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
    				ContentValues cv = new ContentValues();
    			    cv.put(DbOpenHelper.Date, date.toLocaleString());
    			    cv.put(DbOpenHelper.Name, edTxt_Name.getText().toString());
    			    if(imgUri == null)
    			    {
    			    	cv.put(DbOpenHelper.Photo, "No_Photo");
    			    }
    			    else
    			    {
    			    	cv.put(DbOpenHelper.Photo, imgUri.toString());
    			    }
    			    cv.put(DbOpenHelper.Comments, edTxt_Comments.getText().toString());
    			    cv.put(DbOpenHelper.Rating, skBar_Rating.getProgress() + 1);
    			    cv.put(DbOpenHelper.Position, spinner_Positions.getSelectedItemPosition());
    			    db.insert(DbOpenHelper.TABLE_NAME, null, cv);
    			    db.close();
    			    Intent myIntent = new Intent(view.getContext(), Main.class);
                    startActivityForResult(myIntent, 0);
    			}
    	    });
        }
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			switch (requestCode) 
			{
				case 1:
				{
					if(data != null)
				 	{
						imgUri = data.getData();
						imgView_Photo.setImageURI(imgUri);
				 	}	
					break;
				}
				default:
					break;
			}
	}
}
