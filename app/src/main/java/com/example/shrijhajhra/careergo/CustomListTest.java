package com.example.shrijhajhra.careergo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomListTest extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    private final String[] intrest;
    //private ArrayList<Integer> intrest = new ArrayList<>();
    public CustomListTest(Activity context,
                          String[] web, Integer[] imageId, String[] intrest) {
        super(context, R.layout.test_result_list_items, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.intrest = intrest;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.test_result_list_items, null, true);
        TextView catname = (TextView) rowView.findViewById(R.id.catg_name);
        ImageView image = (ImageView) rowView.findViewById(R.id.imagView);
        TextView intrest1 = (TextView) rowView.findViewById(R.id.intr);

       // Toast.makeText(context, web.length+" "+imageId.length+" "+intrest.length, Toast.LENGTH_SHORT).show();

        catname.setText(web[position]);
        intrest1.setText(intrest[position]);

        Picasso.with(context).load(imageId[position]).into(image);
       //image.setImageResource(imageId[position]);
        return rowView;
    }
}
