package com.tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class History extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        
        Button back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Main.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        TextView view = (TextView) findViewById(R.id.history);

        DbOpenHelper dbOpenHelper = new DbOpenHelper(History.this);
	    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.setVersion(1);
        db.setLockingEnabled(true);
        
        Cursor cur = db.query("history", 
       		null, null, null, null, null, null);
        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            view.append("\n" + cur.getString(1));
       	    cur.moveToNext();
        }
        cur.close();

    }
}