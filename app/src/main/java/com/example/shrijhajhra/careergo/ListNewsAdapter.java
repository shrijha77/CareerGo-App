package com.example.shrijhajhra.careergo;

/**
 * Created by shri jhajhra on 22/04/2018.
 */


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SHAJIB-PC on 10/23/2017.
 */

public class ListNewsAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public ListNewsAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
    }
    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ListNewsViewHolder holder = null;
        if (convertView == null) {
            holder = new ListNewsViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.list_articles, parent, false);
            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.sdetails = (TextView) convertView.findViewById(R.id.sdetails);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);
        holder.time.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try{
            if(song.get(Articles.KEY_AUTHOR).toString().length()!=0 && song.get(Articles.KEY_AUTHOR).toString()!=null)
            {
                holder.author.setText(song.get(Articles.KEY_AUTHOR));
            }
            else
            {
                holder.author.setText("not available");
            }



                holder.time.setText((song.get(Articles.KEY_PUBLISHEDAT)).toString().substring(0,16));


            holder.title.setText(song.get(Articles.KEY_TITLE));
            //holder.time.setText(song.get(Articles.KEY_PUBLISHEDAT));


            if(song.get(Articles.KEY_DESCRIPTION).toString().length()!=0)
            {
                holder.sdetails.setText(song.get(Articles.KEY_DESCRIPTION));
            }
            else
            {
                holder.sdetails.setText("not text");

            }


            if(song.get(Articles.KEY_URLTOIMAGE).toString().length() < 5)
            {
                Picasso.with(activity)
                    .load(R.drawable.logo_final)
                    .resize(300, 200)
                    .into(holder.galleryImage);

            }else{
                Picasso.with(activity)
                        .load(song.get(Articles.KEY_URLTOIMAGE).toString())
                        .resize(300, 200)
                        .into(holder.galleryImage);
            }
        }catch(Exception e) {}
        return convertView;
    }
}

class ListNewsViewHolder {
    ImageView galleryImage;
    TextView author, title, sdetails, time;
}