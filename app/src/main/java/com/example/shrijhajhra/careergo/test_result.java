package com.example.shrijhajhra.careergo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class test_result extends AppCompatActivity {
    String[] categories = {"ARTS","AVIATION AND AEROSPACE","BEAUTY AND WELLNESS","CIVIL SERVICES","EDUCATION & TRAINING","ENGINEERING & MANUFACTURING","MANAGEMENT, MARKETING & SALES",
            "FINANCE AND ACCOUNTS","HEALTHCARE","INFORMATION TECHNOLOGY","NATURAL RESOURCES AND FOOD PROCESSING","MEDIA AND ENTERTAINMENT"

    } ;
    ArrayList<String > labels_category = new ArrayList<>();
   // ArrayList<Integer> interst = new ArrayList<>();
    ArrayList<String > labels_category_name = new ArrayList<>();
    //ArrayList<Integer > image = new ArrayList<>();
    TextView cat1;
    TextView cat2;
    ListView list;
    String[] categories1;
    Integer[] image;
    String[] intrest;
    final Calendar c = Calendar.getInstance();
    DatabaseReference current_user_db;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private String userId = auth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        //getSupportActionBar().hide();

        SharedPreferences pref = getSharedPreferences(userId, Context.MODE_PRIVATE);
        // We need an editor object to make changes
        SharedPreferences.Editor edit = pref.edit();
        // Set/Store data


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //get values that we have passed
        //store result in userprofile
        ScrollView sv = (ScrollView) findViewById(R.id.scrollview);
        sv.scrollTo(0,0);
        final String userId = auth.getCurrentUser().getUid();
        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("test_date");
        current_user_db.setValue(c.getTime());
        current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("test_result");

        Intent intent = getIntent();
        HashMap<String, Integer> hashMap = (HashMap<String, Integer>) intent.getSerializableExtra("map");
        current_user_db.setValue(hashMap);
        //initilise bar chart
        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        cat1= (TextView)findViewById(R.id.cat1);
        cat2= (TextView)findViewById(R.id.cat2);



	    /* For Java 8, try this lambda
		Map<Integer, String> treeMap = new TreeMap<>(
		                (Comparator<Integer>) (o1, o2) -> o2.compareTo(o1)
		        );
		*/


	    intrest = new String[hashMap.size()];
	    categories1 = new String[hashMap.size()];
	    image = new Integer[hashMap.size()];
        List<BarEntry> entries = new ArrayList<>();
        int i=0,j=1;
        //hashmap name count
        for (Map.Entry<String,Integer> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            float value = (entry.getValue());
           // Toast.makeText(test_result.this, key + " "+ value, Toast.LENGTH_SHORT).show();
            entries.add(new BarEntry(value,i));
            intrest[i]= "Intrest "+String.valueOf(((value/6)*100));
            labels_category.add("Cat"+j);
            categories1[i]=key;
           if(key.equals("AVIATION AND AEROSPACE"))
            {
                image[i]= R.drawable.aviation_new;
            }
            else if(key.equals("BEAUTY AND WELLNESS"))
            {
                image[i] =R.drawable.beauty_1;
            }
            else if(key.equals("CIVIL SERVICES"))
            {
                image[i] =R.drawable.civil_services;
            }

            else if(key.equals("EDUCATION AND TEACHING"))
            {
                image[i] =R.drawable.school_education;
            }
            else if(key.equals("ENGINEERING & MANUFACTURING"))
            {
                image[i]= R.drawable.eng_manf_new;
            }
            else if(key.equals("MANAGEMENT"))
            {
                image[i]= R.drawable.management;
            }
            else if(key.equals("BANKING AND FINANCE"))
            {
                image[i]=R.drawable.banking_and_finance_p;
            }
            else if(key.equals("MEDICAL"))
            {
                image[i]= R.drawable.medical_p;
            }
            else if(key.equals("INFORMATION TECHNOLOGY"))
            {
                image[i] =R.drawable.it;
            }
            else if(key.equals("MEDIA AND ENTERTAINMENT"))
            {
                image[i]= R.drawable.media_new;
            }
            else if(key.equals("LAW"))
            {
                image[i]= R.drawable.law_1;
            }
            else
           {
               image[i]= R.drawable.logo_final;
           }


            i++;
            j++;

            // do stuff
        }

       // Toast.makeText(test_result.this, labels_category_name.size()+" "+image.size()+" "+interst.size(), Toast.LENGTH_SHORT).show();

        //populate listview with test result
        CustomListTest adapter = new
                CustomListTest(test_result.this, categories1, image,intrest);
        list=(ListView)findViewById(R.id.list_test_result);
        list.setAdapter( adapter);
        setListViewHeightBasedOnChildren(list);
        hashMap = (HashMap<String, Integer>) sortByValue(hashMap);

        int count  = 0;
        int hashSize = hashMap.size()-1;
        for (Map.Entry<String,Integer> entry : hashMap.entrySet() ) {
            String key = entry.getKey();
            float value = (entry.getValue());
            //update user recommendation




            if(count==hashSize)
            {
                cat1.setText(key);
                edit.putString("recom1", key);

            }
            else if(count==hashSize-1)
            {
                cat2.setText(key);
                edit.putString("recom2", key);


            }
            else if(count==hashSize-2)
            {
                edit.putString("recom3",key);
            }
            count++;
            edit.commit();
            // do stuff
        }
        BarDataSet bardataset = new BarDataSet(entries, "category");
        BarData data = new BarData(labels_category, bardataset);
        //bardataset.setColor(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data);
        barChart.setDescription("Intrest in particular category based on test");
        bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(true);
        barChart.canScrollHorizontally(1);
        barChart.animateY(500);
        barChart.getAxisRight().setDrawGridLines(true);
        barChart.getAxisLeft().setDrawGridLines(true);
        barChart.getXAxis().setDrawGridLines(true);






        cat1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Category_based_career.class);
                intent.putExtra("category",cat1.getText());
               // Toast.makeText(CategoryActivity.this, "You Clicked at " +categories[+ position], Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });
        cat2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Category_based_career.class);
                intent.putExtra("category",cat2.getText());
                // Toast.makeText(CategoryActivity.this, "You Clicked at " +categories[+ position], Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });
    }
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
        finish();
        startActivity(intent);
    }
    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}

