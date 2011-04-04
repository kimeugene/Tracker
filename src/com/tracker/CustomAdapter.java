package com.tracker;

import com.tracker.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater;
    private String[] mNames;

    public CustomAdapter(Context context, int textViewResourceId,
            String[] objects) {
        super(context, textViewResourceId, objects);
        mNames = objects;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        TextView contact_name;
        ImageView position_img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if ( convertView == null ) {
            convertView = mInflater.inflate(R.layout.row, null);

            holder = new ViewHolder();
            holder.contact_name = (TextView) convertView.findViewById(R.id.contact_name);
            holder.position_img = (ImageView) convertView.findViewById(R.id.position_img);

            holder.contact_name.setFocusable(false);
            holder.position_img.setFocusable(false);

            holder.position_img.setClickable(false);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String name = mNames[position];
        if ( name != null ) {
            holder.contact_name.setText(name);
        }
        return convertView;
    }
}
