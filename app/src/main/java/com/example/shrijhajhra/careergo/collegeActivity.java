package com.example.shrijhajhra.careergo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class collegeActivity extends AppCompatActivity {

    private ListView listView;

    private  Handler handler;
    private ProgressDialog PD;
    ArrayAdapter<String> adapter;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("collegs");
    private ArrayList<String> college_heading = new ArrayList();

    ArrayList<String > links_site=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college);



        init();


        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header_layout,listView,false);
        listView.addHeaderView(header);
        listView.setAdapter(adapter);



        //listview listner
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {

                if(position!=0) {
                    Intent intent = new Intent(getApplicationContext(), Webview.class);
                    //file index as college type
                    intent.putExtra("file index", links_site.get(position - 1));
                    startActivity(intent);
                    Toast.makeText(collegeActivity.this, "You Clicked at " + college_heading.get(position - 1), Toast.LENGTH_SHORT).show();
                }
            }
        });



       /* dref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot myitem : dataSnapshot.getChildren()) {
                    String key = myitem.getKey().toString();
                    if (key.equals("name")) {
                        // Toast.makeText(careers.this, "hello : " + myitem.getKey(), Toast.LENGTH_SHORT).show();
                        String name = myitem.getValue(String.class);
                        college_heading.add(name);
                    }

                    //Collections.sort(career_list);
                    // Collections.sort(file_list);
                    adapter = new ArrayAdapter(collegeActivity.this, android.R.layout.simple_list_item_1, exam);
                    listView.setAdapter(adapter);

                }
                PD.dismiss();

            }





            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }


            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }


            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }


            public void onCancelled(DatabaseError databaseError) {

            }




        }); */




    }

    private void init() {
        listView = (ListView)findViewById(R.id.college_listView);


        college_heading.add("AIIMS");
        links_site.add("https://careergotk.000webhostapp.com/Institutions/AIIMs.html");
        college_heading.add("IIITs");
        links_site.add("https://careergotk.000webhostapp.com/Institutions/IIITs.html");
        college_heading.add("IITs");
        links_site.add("https://careergotk.000webhostapp.com/Institutions/IITs.html");
        college_heading.add("IIMs");
        links_site.add("https://careergotk.000webhostapp.com/Institutions/IIMs.html");
        college_heading.add("NITs");
        links_site.add("https://careergotk.000webhostapp.com/Institutions/NITs.html");

       // adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, college_heading);
        adapter = new list_image_text(getApplicationContext(), android.R.layout.simple_list_item_1, college_heading);

    }

}
