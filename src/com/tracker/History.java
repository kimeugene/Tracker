package com.tracker;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class History extends Activity {
	
	private int deleteID = -1;
	private final int deleteContact = 0;
	private ListView contactView;
	private CustomAdapter adapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        
        Button back_btn = (Button) findViewById(R.id.history_btn_Back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Main.class);
                startActivityForResult(myIntent, 0);
            }
        });
        
        Button delete_btn = (Button) findViewById(R.id.history_btn_Delete);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(deleteID != -1){
                	showDialog(deleteContact);
                }
            }
        });
        
        contactView = (ListView) findViewById(R.id.history_LView);
        
        contactView.setOnItemSelectedListener( new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Contact cont =(Contact) arg0.getItemAtPosition(arg2);
				deleteID = cont.id;
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});

        
        //db.setVersion(1);
        //db.setLockingEnabled(true);
        
        
        adapter = new CustomAdapter(this,R.layout.row, FillData());
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
    
    private List<Contact> FillData(){    	
    	DbOpenHelper dbOpenHelper = new DbOpenHelper(History.this);
	    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
    	Cursor cur = db.query("history", null, null, null, null, null, null);
        cur.moveToFirst();
        int count = cur.getCount();
   	 	List<Contact> arr = new ArrayList<Contact>(count);
   	 	for(int i = 0; i < count; i++)
   	 	{
   	 		cur.moveToPosition(i);
   	 		Contact cont = new Contact(cur.getInt(0), cur.getString(2), cur.getString(1), cur.getInt(5), cur.getBlob(3));   	 		
   	 		arr.add(cont);
   	 	}
        db.close();
		return arr;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
       case R.id.settings:
          startActivity(new Intent(this, Prefs.class));
          return true;
       }
       return false;
    }
    
    @Override
    protected Dialog onCreateDialog(int id) 
	{
		switch (id) {
		case deleteContact:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You want to remove this contact?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	try{
	                	DbOpenHelper dbOpenHelper = new DbOpenHelper(History.this);
	            	    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
	            	    db.execSQL("DELETE FROM history WHERE id = " + String.valueOf(deleteID));            	    
	            	    db.close();
	            	    adapter = new CustomAdapter(getApplicationContext(),R.layout.row, FillData());
	                    contactView.setAdapter(adapter);
                	}
                	catch(Exception e){
                	}
                	
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