package com.example.shrijhajhra.careergo;

/**
 * Created by shri jhajhra on 22/04/2018.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

public class list_image_text extends ArrayAdapter<String> {

    private Context activity;
    Context context;
    LayoutInflater inflter;
    private List<String> friendList;

    public list_image_text(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.friendList = objects;
        this.context = context;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public String getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ViewHolder viewHolder = null;


            // LayoutInflater inflator = context.getLayoutInflater();
            LayoutInflater inflator =(LayoutInflater.from(context));
            convertView = inflator.inflate(R.layout.list_image_text, null);
            // view = inflter.inflate(R.layout.list_test_item, null);
           // viewHolder = new ViewHolder(convertView);
            TextView friendName = (TextView) convertView.findViewById(R.id.text);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view);





            //convertView.setTag(viewHolder);
            //viewHolder.mgroup.setTag(items.get(i));
       // holder.friendName.setText(getItem(position));

        //get first letter of each String item
        String firstLetter = String.valueOf(getItem(position).charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(getItem(position));
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(firstLetter, color,4); // radius in px

       imageView.setImageDrawable(drawable);
       friendName.setText(friendList.get(position));

        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView friendName;

        public ViewHolder(View v) {
           // imageView = (ImageView) v.findViewById(R.id.image_view);
           // friendName = (TextView) v.findViewById(R.id.text);
        }

        public ViewHolder() {

        }
    }
}