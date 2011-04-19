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
import android.provider.Contacts.People;
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

public class ContactInfo extends Activity{
	
	Date date;
	ImageView imgView_Photo = null;
	Uri imgUri = null;
	ArrayAdapter arrayAdapter;
    LayoutInflater inflater;
    protected Drawable drawable1, drawable2;
	ViewGroup v;
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
        imgView_Photo = (ImageView) findViewById(R.id.imgView_Photo);
        imgView_Photo.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
		        Intent chooseFileIntent = new Intent();
		        chooseFileIntent.setAction(Intent.ACTION_GET_CONTENT);
		        chooseFileIntent.setType("image/jpeg");
		        startActivityForResult(chooseFileIntent, 1);
        	}
        });
        
        

        
        //Comments
        final EditText edTxt_Comments = (EditText) findViewById(R.id.edTxt_Comments);
        
        //Rating
        final TextView txtView_Rating = (TextView) findViewById(R.id.txtView_Rating);
        final SeekBar skBar_Rating = (SeekBar) findViewById(R.id.skBar_Rating);
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
        spinner_Positions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                
            }
            public void onNothingSelected(AdapterView adapterView) {}
        });

		/**
        * Save 
        * Позже будут добавлены проверки на вводимые данные
        */
        Button btn_Save = (Button) findViewById(R.id.btn_Save);
        btn_Save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				DbOpenHelper dbOpenHelper = new DbOpenHelper(ContactInfo.this);
				SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
				ContentValues cv = new ContentValues();
			    cv.put(DbOpenHelper.Date, date.toString());
			    cv.put(DbOpenHelper.Name, edTxt_Name.getText().toString());
			    cv.put(DbOpenHelper.Photo, imgUri.toString());
			    cv.put(DbOpenHelper.Comments, edTxt_Comments.getText().toString());
			    cv.put(DbOpenHelper.Rating, skBar_Rating.getProgress());
			    db.insert(DbOpenHelper.TABLE_NAME, null, cv);
			    db.close();
			    Intent myIntent = new Intent(view.getContext(), Main.class);
                startActivityForResult(myIntent, 0);
			}
	    });
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
