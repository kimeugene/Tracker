package com.tracker;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import org.apache.http.util.ByteArrayBuffer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
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
	
	private String date;
	private ImageView imgView_Photo = null;
	private EditText edTxt_Name;
	private EditText edTxt_Comments;
	private TextView txtView_Rating;
	private SeekBar skBar_Rating;
	private Spinner spinner_Positions;
	private ArrayAdapter<String> arrayAdapter;
	private LayoutInflater inflater;
    private Drawable drawable1;
    private boolean _isEdit = false;
    private int id;
    private ByteArrayBuffer photoArray = new ByteArrayBuffer(0);
    private Uri photoUri = null;
    private final int deletePhoto = 0;
    
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactinfo);
         
        TextView txtView_ID = (TextView) findViewById(R.id.txtView_ID);    
        TextView txtView_Date = (TextView) findViewById(R.id.txtView_Date);
        edTxt_Name = (EditText) findViewById(R.id.edTxt_Name);
        imgView_Photo = (ImageView) findViewById(R.id.imgView_Photo);
        edTxt_Comments = (EditText) findViewById(R.id.edTxt_Comments);
        txtView_Rating = (TextView) findViewById(R.id.txtView_Rating);
        skBar_Rating = (SeekBar) findViewById(R.id.skBar_Rating);
        spinner_Positions = (Spinner) findViewById(R.id.spinner_Positions);
        final Button btn_Save = (Button) findViewById(R.id.btn_Save);
        
        Button btn_Back = (Button) findViewById(R.id.btn_Back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent myIntent = new Intent(v.getContext(), Main.class);
		        startActivityForResult(myIntent, 0);
			}
		});
        
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
				int prog = progress + 1;
				txtView_Rating.setText(String.format("Rating: %d", Integer.valueOf(prog)));
			}
		});
        
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
        
        //Photo
        imgView_Photo.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
		        Intent chooseFileIntent = new Intent();
		        chooseFileIntent.setAction(Intent.ACTION_GET_CONTENT);
		        chooseFileIntent.setType("image/jpeg");
		        startActivityForResult(chooseFileIntent, 1);
        	}
        });

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
            	date = cursor.getString(1);
            	String Name = cursor.getString(2);
            	photoArray.append(cursor.getBlob(3), 0, cursor.getBlob(3).length);
            	String Comments = cursor.getString(4);
            	int Rating = cursor.getInt(5);
            	int Position = cursor.getInt(6);
            	
            	txtView_ID.setText("ID: " + String.valueOf(id));
            	txtView_Date.setText("Date: " + date);
            	edTxt_Name.setText(Name);
            	ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(photoArray.buffer());
	            BitmapDrawable photo = new BitmapDrawable(byteArrayInputStream);
	            imgView_Photo.setImageDrawable(photo);
            	edTxt_Comments.setText(Comments);
            	skBar_Rating.setProgress(Rating - 1);
            	txtView_Rating.setText(String.format("Rating: %d", Integer.valueOf(skBar_Rating.getProgress() + 1)));
            	spinner_Positions.setSelection(Position);
            	
            	setEditable();
            	btn_Save.setText("Edit");
            	btn_Save.setOnClickListener(new View.OnClickListener() {
        			public void onClick(View view) {
        				if(!_isEdit)
        				{
        					_isEdit = true;
        					btn_Save.setText("Save");
        					setEditable();        					
        				}
        				else
        				{
        					if(saveData());
        					{
        						finish();
        						Intent myIntent = new Intent(view.getContext(), Main.class);
        	    		        startActivityForResult(myIntent, 0);
        					}
        				}        				
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
            date = new Date().toLocaleString();
            txtView_Date.setText("Date: " + date);
                      
            
            //Rating
            skBar_Rating.setProgress(5);            
            
            //Position
            spinner_Positions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView adapterView, View view, int i, long l) {}
                public void onNothingSelected(AdapterView adapterView) {}
            });

    		/**
            * Save 
            */
            btn_Save.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View view) {
    				if(saveData())
    				{
    					finish();
	    				Intent myIntent = new Intent(view.getContext(), Main.class);
	    		        startActivityForResult(myIntent, 0);
    				}
    			}
    	    });
        }
    }
	
	void setEditable()
	{
		edTxt_Name.setEnabled(_isEdit);
    	imgView_Photo.setClickable(_isEdit);
    	edTxt_Comments.setEnabled(_isEdit);
    	skBar_Rating.setEnabled(_isEdit);
    	spinner_Positions.setClickable(_isEdit);
	}
	
	boolean saveData()
	{
		if(edTxt_Name.getText().toString().length() == 0)
		{
			Toast.makeText(getApplicationContext(), "Fill all data!", 5000).show();
			return false;
		}
		DbOpenHelper dbOpenHelper = new DbOpenHelper(ContactInfo.this);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
	    cv.put(DbOpenHelper.Date, date);
	    cv.put(DbOpenHelper.Name, edTxt_Name.getText().toString());
	    cv.put(DbOpenHelper.Photo, photoArray.buffer());
	    cv.put(DbOpenHelper.Comments, edTxt_Comments.getText().toString());
	    cv.put(DbOpenHelper.Rating, skBar_Rating.getProgress() + 1);
	    cv.put(DbOpenHelper.Position, spinner_Positions.getSelectedItemPosition());
	    if(_isEdit)
	    	db.update(DbOpenHelper.TABLE_NAME, cv, DbOpenHelper.ID + " = " + id, null);
	    else
	    	db.insert(DbOpenHelper.TABLE_NAME, null, cv);
	    db.close();	    
	    return true;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			switch (requestCode) 
			{
				case 1:
				{
					if(data != null)
				 	{
						try {
							AssetFileDescriptor thePhoto =
								getContentResolver().openAssetFileDescriptor(data.getData(), "r");
							FileInputStream fileInputeStream = thePhoto.createInputStream();
							BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputeStream);
				            int current = 0;
				            while ((current = bufferedInputStream.read()) != -1) {
				            	photoArray.append((byte) current);
				            }
				            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(photoArray.buffer());
				            BitmapDrawable photo = new BitmapDrawable(byteArrayInputStream);
				            imgView_Photo.setImageDrawable(photo);
				            photoUri = data.getData();				            
							showDialog(deletePhoto);
				            
				            
						} catch (Exception e) {} 
				 	}
				}
			}
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
		switch (id) {
		case deletePhoto:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You want to remove this photo from gallery?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	if(photoUri != null)
                		getContentResolver().delete(photoUri, null, null);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    }
            });
            builder.setCancelable(false);
            return builder.create();
        default:
        return null;
        }
	}
}
