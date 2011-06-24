package com.tracker;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class Prefs extends Activity implements OnClickListener, OnCheckedChangeListener
{
	Button btn_save;
	Button btn_cancel;
	CheckBox chb_Password_Protection_Option;
	CheckBox chb_Sound_Option;
	CheckBox chb_Twitter_Option;
	EditText edTxt_TwitterLogin;
	EditText edTxt_TwitterPassword;
	CheckBox chb_Facebook_Option;
	EditText edTxt_FacebookLogin;
	EditText edTxt_FacebookPassword;
	EditText edTxt_StatusMessage;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.options);
	   btn_save = (Button) findViewById(R.id.btn_Save_Option);
	   btn_save.setOnClickListener(this);
	   btn_cancel = (Button) findViewById(R.id.btn_Cancel_Option);
	   btn_cancel.setOnClickListener(this);
	   chb_Password_Protection_Option = (CheckBox) findViewById(R.id.chb_Password_Protection_Option);
	   chb_Password_Protection_Option.setChecked(Prefs.getBooleanOption(this, OptionEnum.Password_Protection)); 
	   chb_Sound_Option = (CheckBox) findViewById(R.id.chb_Sound_Option);
	   chb_Sound_Option.setChecked(Prefs.getBooleanOption(this, OptionEnum.Sound));
	   
	   chb_Twitter_Option = (CheckBox) findViewById(R.id.chb_Twitter_Option);
	   chb_Twitter_Option.setChecked(Prefs.getBooleanOption(this, OptionEnum.Twitter));
	   chb_Twitter_Option.setOnCheckedChangeListener(this);
	   
	   edTxt_TwitterLogin = (EditText)findViewById(R.id.options_edTxt_TwitterLogin);
	   edTxt_TwitterLogin.setText(Prefs.getStringOption(this, OptionEnum.LoginOnTwitter));
	   edTxt_TwitterLogin.setEnabled(chb_Twitter_Option.isChecked());
		
	   edTxt_TwitterPassword = (EditText)findViewById(R.id.options_edTxt_TwitterPassword);
	   edTxt_TwitterPassword.setText(Prefs.getStringOption(this, OptionEnum.PasswordOnTwitter));
	   edTxt_TwitterPassword.setEnabled(chb_Twitter_Option.isChecked());
	   
	   chb_Facebook_Option = (CheckBox) findViewById(R.id.chb_Facebook_Option);
	   chb_Facebook_Option.setChecked(Prefs.getBooleanOption(this, OptionEnum.Facebook));
	   chb_Facebook_Option.setOnCheckedChangeListener(this);
	   
	   edTxt_FacebookLogin = (EditText)findViewById(R.id.options_edTxt_FacebookLogin);
	   edTxt_FacebookLogin.setText(Prefs.getStringOption(this, OptionEnum.LoginOnFacebook));
	   edTxt_FacebookLogin.setEnabled(chb_Facebook_Option.isChecked());
	   
	   edTxt_FacebookPassword = (EditText)findViewById(R.id.options_edTxt_FacebookPassword);
	   edTxt_FacebookPassword.setText(Prefs.getStringOption(this, OptionEnum.PasswordOnFacebook));
	   edTxt_FacebookPassword.setEnabled(chb_Facebook_Option.isChecked());
	   
	   edTxt_StatusMessage = (EditText) findViewById(R.id.options_edTxt_StatusMessage);
	   edTxt_StatusMessage.setText(Prefs.getStringOption(this, OptionEnum.StatusMessage));	   
	}

   
	/** Get the current boolean value of the option */
	public static boolean getBooleanOption(Context context, OptionEnum option) {
		DbOpenHelper dbOpenHelper = new DbOpenHelper(context);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String params[] = new String[1];
		params[0] = option.name();
		Cursor cursor = db.query(DbOpenHelper.TABLE_NAME_SETTINGS, params, null, null, null, null, null);
		cursor.moveToLast();
		int count = cursor.getCount();
		boolean result = true;
		if(count > 0)
		{
			result = getBoolean(cursor.getInt(0));    	   
		}
		db.close();
		return result;
	}
	
	/** Get the current string value of the option */
	public static String getStringOption(Context context, OptionEnum option) {
		DbOpenHelper dbOpenHelper = new DbOpenHelper(context);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String params[] = new String[1];
		params[0] = option.name();
		Cursor cursor = db.query(DbOpenHelper.TABLE_NAME_SETTINGS, params, null, null, null, null, null);
		cursor.moveToLast();
		int count = cursor.getCount();
		String result = "";
		if(count > 0)
		{
			result = cursor.getString(0);    	   
		}
		db.close();
		return result;
	}
   
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
			case R.id.btn_Cancel_Option:
				finish();
				break;
			case R.id.btn_Save_Option:
				save();
				finish();
				break;
				
		default:
			break;
		}
	}
   
   private void save()
   {
	   DbOpenHelper dbOpenHelper = new DbOpenHelper(Prefs.this);
	   SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
	   ContentValues cv = new ContentValues();
	   cv.put(OptionEnum.Password_Protection.name(), chb_Password_Protection_Option.isChecked());
	   cv.put(OptionEnum.Sound.name(), chb_Sound_Option.isChecked());
	   cv.put(OptionEnum.Twitter.name(), chb_Twitter_Option.isChecked());
	   cv.put(OptionEnum.LoginOnTwitter.name(), edTxt_TwitterLogin.getText().toString());
	   cv.put(OptionEnum.PasswordOnTwitter.name(), edTxt_TwitterPassword.getText().toString());
	   cv.put(OptionEnum.Facebook.name(), chb_Facebook_Option.isChecked());
	   cv.put(OptionEnum.LoginOnFacebook.name(), edTxt_FacebookLogin.getText().toString());
	   cv.put(OptionEnum.PasswordOnFacebook.name(), edTxt_FacebookPassword.getText().toString());
	   cv.put(OptionEnum.StatusMessage.name(), edTxt_StatusMessage.getText().toString());
	   db.update(DbOpenHelper.TABLE_NAME_SETTINGS, cv, DbOpenHelper.ID + " = 1", null);
	   
   }
   
   public static boolean getBoolean(int value)
   {
	   switch (value) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				return false;
	   }
   }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
			case R.id.chb_Twitter_Option:
				edTxt_TwitterLogin.setEnabled(isChecked);
				edTxt_TwitterPassword.setEnabled(isChecked);
				break;
			case R.id.chb_Facebook_Option:
				edTxt_FacebookLogin.setEnabled(isChecked);
				edTxt_FacebookPassword.setEnabled(isChecked);
				break;
				
			default:
				break;
		}
	}
}
