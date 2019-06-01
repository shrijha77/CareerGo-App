package com.example.shrijhajhra.careergo;

/**
 * Created by shri jhajhra on 17/04/2018.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class exam_list extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;

    public exam_list(exams mainActivity, String[] prgmNameList, int[] prgmImages) {
// TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
// TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv_language;
        ImageView im_language;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        Holder holder=new Holder();
        View view;
        view = inflater.inflate(R.layout.exam_list_item, null);

        holder.tv_language=(TextView) view.findViewById(R.id.line1);
        //holder.im_language=(ImageView) view.findViewById(R.id.im_language);

        holder.tv_language.setText(result[position]);
       // Picasso.with(context).load(imageId[position]).into(holder.im_language);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}