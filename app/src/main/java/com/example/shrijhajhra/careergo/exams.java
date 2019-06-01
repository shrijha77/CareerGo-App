package com.example.shrijhajhra.careergo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class exams extends AppCompatActivity {


   private Toolbar toolbar;
   private ListView listView;


    exam_list list_adapter;
    private Handler handler;
    private ProgressDialog PD;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("Exams");
    private ArrayList<String> exam = new ArrayList();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
        PD = new ProgressDialog(this);
        PD.setMessage("Loading Exams");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
        PD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //loading data
        PD.show();
        handler = new Handler()
        {

            @Override
            public void handleMessage(Message msg)
            {


            }

        };

        new Thread()
        {
            public void run()
            {
                // Write Your Downloading logic here
                // at the end write this.
                handler.sendEmptyMessage(0);
            }

        }.start();

        init();

       // listView.setAdapter(list_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {

                Intent intent = new Intent(getApplicationContext(),Exam_details.class);
                intent.putExtra("exam name",exam.get(position));
                startActivity(intent);
               // Toast.makeText(exams.this, "You Clicked at " + exam.get(position), Toast.LENGTH_SHORT).show();

            }
        });

        dref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot myitem : dataSnapshot.getChildren()) {
                    String key = myitem.getKey().toString();
                    if (key.equals("name")) {
                        // Toast.makeText(careers.this, "hello : " + myitem.getKey(), Toast.LENGTH_SHORT).show();
                        String name = myitem.getValue(String.class);
                        exam.add(name);
                    }

                    //Collections.sort(career_list);
                    // Collections.sort(file_list);
                    adapter = new list_image_text(getApplicationContext(), android.R.layout.simple_list_item_1, exam);
                   // adapter = new ArrayAdapter(exams.this, android.R.layout.simple_list_item_1, exam);
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




        });



    }

    private void init() {


       // list_adapter = new exam_list(this, languages, language_images);
        listView = (ListView) findViewById(R.id.card_listView);
        adapter = new list_image_text(getApplicationContext(), android.R.layout.simple_list_item_1, exam);
    }
}
