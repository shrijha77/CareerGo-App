package com.example.shrijhajhra.careergo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shri jhajhra on 13/03/2018.
 */
public class Listadapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    ArrayList<test_item> items;
    LayoutInflater inflter;
    public static ArrayList<String> selectedAnswers;

    public Listadapter(Context context, ArrayList<test_item> items) {
        this.context = context;
        this.items = items;
        selectedAnswers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            selectedAnswers.add("Not Attempted");
        }
        inflter = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public void onClick(View view) {

    }

    static class ViewHolder {
        protected TextView text;
        int ref;
        protected RadioGroup mgroup;

    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {


        ViewHolder viewHolder = null;
        if (convertView == null) {
            // LayoutInflater inflator = context.getLayoutInflater();
            LayoutInflater inflator =(LayoutInflater.from(context));
            convertView = inflator.inflate(R.layout.list_test_item, null);
           // view = inflter.inflate(R.layout.list_test_item, null);
             //viewHolder = new ViewHolder();
            TextView text = (TextView) convertView.findViewById(R.id.txt);
            ImageView image = (ImageView)convertView.findViewById(R.id.image_view);
           // viewHolder.mgroup = (RadioGroup) convertView.findViewById(R.id.myRadioGroup);



            convertView.setTag(viewHolder);
            //viewHolder.mgroup.setTag(items.get(i));
        }
        else{
            //viewHolder = (ViewHolder)convertView.getTag();
        }




       // viewHolder.mgroup.clearCheck();
        viewHolder.mgroup.removeAllViews();
        final RadioButton[] rb = new RadioButton[items.get(i).getAns_list()];
        for (int j = 0; j < items.get(i).getAns_list(); j++) {
            rb[j] = new RadioButton(context);
            rb[j].setTag(j);
            rb[j].setId(j);
            rb[j].setText(items.get(i).getans_atIndex(j));
            rb[j].setOnClickListener(this);
            viewHolder.mgroup.addView(rb[j]);
            // rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

            // radioGroup.addView(rb[j],rprms);
        }

        viewHolder.mgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            RadioButton radioButton=null;
            public void onCheckedChanged(RadioGroup mRadioGroup, int checkedId) {
                switch(checkedId){
                    case 0:
                        // int getPosition = (Integer) buttonView.getTag();
                        radioButton = (RadioButton)mRadioGroup. findViewById(checkedId);
                        rb[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         boolean isChecked) {
                                //  Step - 1 get the tag position while it is checked
                                int getPosition = (Integer) buttonView.getTag();
                                // Step - 2 On The Business Class Object set the boolean value state
                               // items.get(getPosition).setchecked(buttonView.isChecked());
                            }
                        });
// Step - 3 set the Tag position
                        rb[0].setTag(i);
//Step - 4 Set the value of the item
                       // rb[0].setChecked(items.get(i).checked);


                        selectedAnswers.set(i, (String)radioButton.getText());



                        // do operations specific to this selection
                        break;
                    case 1:
                        radioButton = (RadioButton)mRadioGroup. findViewById(checkedId);
                        rb[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         boolean isChecked) {
                                //  Step - 1 get the tag position while it is checked
                                int getPosition = (Integer) buttonView.getTag();
                                // Step - 2 On The Business Class Object set the boolean value state
                               // items.get(getPosition).setchecked(buttonView.isChecked());
                            }
                        });
// Step - 3 set the Tag position
                        rb[1].setTag(i);
//Step - 4 Set the value of the item
                       // rb[1].setChecked(items.get(i).checked);
                        selectedAnswers.set(i, (String)radioButton.getText());
                        Toast.makeText(context, "view  :  " +checkedId, Toast.LENGTH_SHORT).show();



                        // do operations specific to this selection
                        break;
                    case 2:
                        radioButton = (RadioButton)mRadioGroup. findViewById(checkedId);
                        rb[2].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         boolean isChecked) {
                                //  Step - 1 get the tag position while it is checked
                                int getPosition = (Integer) buttonView.getTag();
                                // Step - 2 On The Business Class Object set the boolean value state
                            //    items.get(getPosition).setchecked(buttonView.isChecked());
                            }
                        });
// Step - 3 set the Tag position
                        rb[2].setTag(i);
//Step - 4 Set the value of the item
                       // rb[2].setChecked(items.get(i).checked);
                        selectedAnswers.set(i, (String)radioButton.getText());
                        // do operations specific to this selection


                        break;
                    case 3:
                        radioButton = (RadioButton)mRadioGroup. findViewById(checkedId);
                        selectedAnswers.set(i, (String)radioButton.getText());



                        break;
                    default:
                }
            }
        });
       //ViewHolder holder = (ViewHolder) view.getTag();
        viewHolder.mgroup.setTag(i);
        int j=i+1;
        viewHolder.text.setText("Q"+j+" : "+items.get(i).getQuestion());






       /* view = inflter.inflate(R.layout.list_test_item,null);
        TextView textView = (TextView)view.findViewById(R.id.question);
        //final RadioButton radioButton1 = (RadioButton)view.findViewById(R.id.sound);
       // final RadioButton radioButton2 = (RadioButton)view.findViewById(R.id.vibration);

        // radioButton1.setChecked(false);

        RadioButton rdbtn = null;
        final RadioButton[] rb = new RadioButton[items.get(i).getAns_list()];
        final RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.myRadioGroup);
        RadioGroup.LayoutParams rprms = null;

            for (int j = 0; j < items.get(i).getAns_list(); j++) {
                rb[j] = new RadioButton(context);
                rb[j].setId( j);
                rb[j].setText(items.get(i).getans_atIndex(j));
                rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

                radioGroup.addView(rb[j],rprms);
            }

        int j=i+1;



        //store the response
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            RadioButton radioButton;
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case 0:
                       // int getPosition = (Integer) buttonView.getTag();
                        radioButton = (RadioButton)group. findViewById(checkedId);
                        selectedAnswers.set(i, (String)radioButton.getText());



                        // do operations specific to this selection
                        break;
                    case 1:
                       radioButton = (RadioButton)group. findViewById(checkedId);
                        selectedAnswers.set(i, (String)radioButton.getText());
                        Toast.makeText(context, "view  :  " +checkedId, Toast.LENGTH_SHORT).show();



                        // do operations specific to this selection
                        break;
                    case 2:
                        radioButton = (RadioButton)group. findViewById(checkedId);
                        selectedAnswers.set(i, (String)radioButton.getText());
                        // do operations specific to this selection


                        break;
                    case 3:
                        radioButton = (RadioButton)group. findViewById(checkedId);
                        selectedAnswers.set(i, (String)radioButton.getText());



                        break;
                        default:


                }
            }
        });





    // radioButton1.setText(items.get(i).getans_atIndex(0));
        //radioButton2.setText(items.get(i).getans_atIndex(1));
        /*radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// set Yes values in ArrayList if RadioButton is checked
                if (isChecked) {
                    selectedAnswers.set(i, (String) radioButton1.getText());


                }
            }
        });
        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// set No values in ArrayList if RadioButton is checked
                if (isChecked)
                    selectedAnswers.set(i, (String) radioButton2.getText());

            }
        });
        */
        // textView.setText("Q"+j+" : "+items.get(i).getQuestion());
        return convertView;
    }


}

