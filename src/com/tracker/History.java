package com.tracker;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
        
        final ListView contactView = (ListView) findViewById(R.id.history);

        DbOpenHelper dbOpenHelper = new DbOpenHelper(History.this);
	    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.setVersion(1);
        db.setLockingEnabled(true);
        
        Cursor cur = db.query("history", null, null, null, null, null, null);
        cur.moveToFirst();
        int count = cur.getCount();
   	 	List<Contact> arr = new ArrayList<Contact>(count);
   	 	for(int i = 0; i < count; i++)
   	 	{
   	 		cur.moveToPosition(i);
   	 		Contact cont = new Contact(cur.getInt(0), cur.getString(2), cur.getString(1), cur.getInt(5), cur.getString(3));   	 		
   	 		arr.add(cont);
   	 	}
        db.close();
        CustomAdapter adapter = new CustomAdapter(this,R.layout.row, arr);
        contactView.setAdapter(adapter);
        
        contactView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
				// TODO Auto-generated method stub
				Contact cont = (Contact) contactView.getItemAtPosition(position);
				Intent myIntent = new Intent(view.getContext(), ContactInfo.class);
				myIntent.putExtra("id", cont.id);
			    startActivityForResult(myIntent, 0);
			}
		});
    }
}