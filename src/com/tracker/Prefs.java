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

public class Prefs extends Activity implements OnClickListener
{
	Button btn_save;
	Button btn_cancel;
	CheckBox chb_Password_Protection_Option;
	CheckBox chb_Sound_Option;
	CheckBox chb_Twitter_Option;
	CheckBox chb_Facebook_Option;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.options);
	   btn_save = (Button) findViewById(R.id.btn_Save_Option);
	   btn_save.setOnClickListener(this);
	   btn_cancel = (Button) findViewById(R.id.btn_Cancel_Option);
	   btn_cancel.setOnClickListener(this);
	   chb_Password_Protection_Option = (CheckBox) findViewById(R.id.chb_Password_Protection_Option);
	   chb_Password_Protection_Option.setChecked(Prefs.getOption(this, OptionEnum.Password_Protection)); 
	   chb_Sound_Option = (CheckBox) findViewById(R.id.chb_Sound_Option);
	   chb_Sound_Option.setChecked(Prefs.getOption(this, OptionEnum.Sound));
	   chb_Twitter_Option = (CheckBox) findViewById(R.id.chb_Twitter_Option);
	   chb_Twitter_Option.setChecked(Prefs.getOption(this, OptionEnum.Twitter));
	   chb_Facebook_Option = (CheckBox) findViewById(R.id.chb_Facebook_Option);
	   chb_Facebook_Option.setChecked(Prefs.getOption(this, OptionEnum.Facebook));
	}

   
	/** Get the current value of the option */
	public static boolean getOption(Context context, OptionEnum option) {
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
	   cv.put(OptionEnum.Facebook.name(), chb_Facebook_Option.isChecked());
	   db.update(DbOpenHelper.TABLE_NAME_SETTINGS, cv, DbOpenHelper.ID + " = 1", null);
	   
   }
   
   private static boolean getBoolean(int value)
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

	
   
}
