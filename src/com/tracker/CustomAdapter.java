package com.tracker;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import com.tracker.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater mInflater;
    List<Contact> contactslist = new ArrayList<Contact>();

    public CustomAdapter(Context context, int textViewResourceId,
    		List<Contact> ContactsList) {
        super(context, textViewResourceId, ContactsList);
        contactslist = ContactsList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
    	TextView contact_ID;
        TextView contact_name;
        TextView contact_date;
        TextView contact_rating;
        ImageView position_img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if ( convertView == null ) {
            convertView = mInflater.inflate(R.layout.row, null);

            holder = new ViewHolder();
            holder.contact_ID = (TextView) convertView.findViewById(R.id.row_txtView_ContactID);
            holder.contact_name = (TextView) convertView.findViewById(R.id.row_txtView_ContactName);
            holder.contact_date = (TextView) convertView.findViewById(R.id.row_txtView_ContactDate);
            holder.contact_rating = (TextView) convertView.findViewById(R.id.row_txtView_ContactRating);
            
            holder.contact_ID.setTextColor(Color.WHITE);
            holder.contact_name.setTextColor(Color.WHITE);
            holder.contact_date.setTextColor(Color.WHITE);
            holder.contact_rating.setTextColor(Color.WHITE);
            
            holder.position_img = (ImageView) convertView.findViewById(R.id.position_img);
            
            holder.contact_ID.setFocusable(false);
            holder.contact_name.setFocusable(false);
            holder.position_img.setFocusable(false);
            holder.position_img.setClickable(false);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.position_img.setBackgroundColor(Color.BLACK);
        
        Contact cont = contactslist.get(position);
        if ( cont != null ) {
        	holder.contact_ID.setText(String.valueOf(cont.id));
            holder.contact_name.setText(cont.name);
            holder.contact_date.setText(cont.date);
            holder.contact_rating.setText(String.valueOf(cont.rating));
            if(cont.photo.length > 0)
            {
            	holder.position_img.setBackgroundResource(0);
            	ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cont.photo);
	            BitmapDrawable photo = new BitmapDrawable(byteArrayInputStream);
	            holder.position_img.setImageDrawable(photo);
            }
            else
            {
            	holder.position_img.setImageDrawable(null);
            	holder.position_img.setBackgroundResource(R.drawable.no_photo);
            }
        }        
        return convertView;
    }
}
