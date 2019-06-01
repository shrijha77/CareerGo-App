package com.example.shrijhajhra.careergo;

/**
 * Created by shri jhajhra on 10/04/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends PagerAdapter {

    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    private Context context;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public MyAdapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }
    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
      //  myImage.setImageResource(images.get(position));
        Picasso.with(context).load(images.get(position)).into(myImage);
        myImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(images.get(position).equals(R.drawable.civil_services)) {
                    Intent intent = new Intent(context,Category_based_career.class);
                    intent.putExtra("category","CIVIL SERVICES");
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                }
               else  if(images.get(position).equals(R.drawable.aviation_industry_p)) {
                    Intent intent = new Intent(context,Category_based_career.class);
                    intent.putExtra("category","AVIATION AND AEROSPACE");
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                }
                else if(images.get(position).equals(R.drawable.banking_p_p)) {
                    Intent intent = new Intent(context,Category_based_career.class);
                    intent.putExtra("category","BANKING & FINANCE");
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                }
                else if(images.get(position).equals(R.drawable.beauty_p)) {
                    Intent intent = new Intent(context,Category_based_career.class);
                    intent.putExtra("category","BEAUTY AND WELLNESS");
                   context.startActivity(intent);
                    Toast.makeText(context, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                }
                else if(images.get(position).equals(R.drawable.education_p)) {
                    Intent intent = new Intent(context,Category_based_career.class);
                    intent.putExtra("category","EDUCATION AND TEACHING");
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                }
                else if(images.get(position).equals(R.drawable.eng_p)) {
                    Intent intent = new Intent(context,Category_based_career.class);
                    intent.putExtra("category","ENGINEERING & MANUFACTURING");
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                }
                else if(images.get(position).equals(R.drawable.management)) {
                    Intent intent = new Intent(context,Category_based_career.class);
                    intent.putExtra("category","MANAGEMENT & MARKETING");
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                }
                else if(images.get(position).equals(R.drawable.it_p)) {
                    Intent intent = new Intent(context,Category_based_career.class);
                    intent.putExtra("category","INFORMATION TECHNOLOGY");
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                }
                else if(images.get(position).equals(R.drawable.law_1)) {
                    Intent intent = new Intent(context,Category_based_career.class);
                    intent.putExtra("category","LAW");
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                }
                else if(images.get(position).equals(R.drawable.medical_p)) {
                    Intent intent = new Intent(context,Category_based_career.class);
                    intent.putExtra("category","MEDICAL");
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                }
                else if(images.get(position).equals(R.drawable.media_p)) {
                    Intent intent = new Intent(context,Category_based_career.class);
                    intent.putExtra("category","MEDIA AND ENTERTAINMENT");
                    context.startActivity(intent);
                    Toast.makeText(context, "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                }

            }
        });
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}