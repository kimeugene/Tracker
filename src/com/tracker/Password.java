package com.tracker;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends Activity implements OnClickListener
{
	private boolean createPassword = false;
	private Button btn_OK;
	private Button btn_Cancel;
	private EditText edTxt_Password1;
	private EditText edTxt_Password2;
	private DbOpenHelper dbOpenHelper;
	private SQLiteDatabase dataBase;
	private ContentValues contentValues;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);
	        
		createPassword = getIntent().getExtras().getBoolean("first_Load");
		btn_OK = (Button) findViewById(R.id.btn_password_ok);
		btn_OK.setOnClickListener(this);
		btn_Cancel = (Button) findViewById(R.id.btn_password_cancel);
		btn_Cancel.setOnClickListener(this);
		edTxt_Password1  = (EditText) findViewById(R.id.edTxt_Password1);
		edTxt_Password2  = (EditText) findViewById(R.id.edTxt_Password2);
		if(!createPassword)
			edTxt_Password2.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) 
		{
			case R.id.btn_password_ok:
				if(!checkPasswordLength())
					return;
				if(createPassword){
					if(!createPassword())
						return;
				}
				else 
					if(!checkPassword())
						return;
				Intent myIntent = new Intent(this, Main.class);
	        	startActivity(myIntent);
				break;
			case R.id.btn_password_cancel:
				finish();
				Intent myIntent2 = new Intent(this, SplashScreen.class);
	        	startActivity(myIntent2);
				break;
			default:
				break;
		}
	}
	
	private boolean checkPasswordLength()
	{
		if(createPassword)
		{
			if((edTxt_Password1.getText().toString().length() > 3) 
					&& (edTxt_Password2.getText().toString().length() > 3))
				return true;
		}
		else
			if(edTxt_Password1.getText().toString().length() > 3)
				return true;		
		Toast.makeText(getApplicationContext(), "The password should be longer than 4 character!", 5000).show();
		return false;
	}
	
	private boolean checkPassword()
	{
		dbOpenHelper = new DbOpenHelper(this);
    	dataBase = dbOpenHelper.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("Select password from " 
        		+ DbOpenHelper.TABLE_NAME_SETTINGS, null);
        cursor.moveToLast();
        String pass = cursor.getString(0);
        dataBase.close();
        String pass1 = edTxt_Password1.getText().toString();
        if(pass.equals(pass1))
        	return true;
        Toast.makeText(getApplicationContext(), "Wrong password!", 5000).show();
        return false;
	}
	
	private boolean createPassword()
	{
		String pass = edTxt_Password1.getText().toString();
		if(!pass.equals(edTxt_Password2.getText().toString()))
		{
			Toast.makeText(getApplicationContext(), "The passwords you entered do not match. Please try again.", 5000).show();
			return false;
		}
		dbOpenHelper = new DbOpenHelper(getApplicationContext());
		dataBase = dbOpenHelper.getWritableDatabase();
		contentValues = new ContentValues();
		contentValues.put("password", pass);
		contentValues.put(OptionEnum.First_Load.name(), 0);
		contentValues.put(OptionEnum.Password_Protection.name(), 1);
		dataBase.update(DbOpenHelper.TABLE_NAME_SETTINGS, contentValues, DbOpenHelper.ID + " = " + 1, null);
		dataBase.close();
		return true;
	}
}


/*
dbOpenHelper = new DbOpenHelper(getApplicationContext());
dataBase = dbOpenHelper.getWritableDatabase();
contentValues = new ContentValues();
contentValues.put(OptionEnum.First_Load.name(), 0);
contentValues.put(OptionEnum.Password_Protection.name(), 1);
contentValues.put(OptionEnum.Password_Protection.name(), 0);
dataBase.update(DbOpenHelper.TABLE_NAME_SETTINGS, contentValues, DbOpenHelper.ID + " = " + 1, null);
dataBase.close();

The password should be longer than 4 character
*/