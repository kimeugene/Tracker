package com.tracker;

import java.util.ArrayList;
import java.util.List;

import com.tracker.R;
import android.content.Context;
import android.net.Uri;
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
            holder.contact_name = (TextView) convertView.findViewById(R.id.contact_name);
            holder.contact_date = (TextView) convertView.findViewById(R.id.contact_date);
            holder.contact_rating = (TextView) convertView.findViewById(R.id.contact_rating);
            holder.position_img = (ImageView) convertView.findViewById(R.id.position_img);

            holder.contact_name.setFocusable(false);
            holder.position_img.setFocusable(false);

            holder.position_img.setClickable(false);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        Contact cont = contactslist.get(position);
        if ( cont != null ) {
            holder.contact_name.setText(cont.name);
            holder.contact_date.setText(cont.date);
            holder.contact_rating.setText(String.valueOf(String.format("Rating: %d", cont.rating)));
            if(!cont.photo.equals("No_Photo"))
            	holder.position_img.setImageURI(Uri.parse(cont.photo));
            else
            	holder.position_img.setBackgroundResource(R.drawable.icon);
        }
        return convertView;
    }
}
