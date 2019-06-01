package com.example.shrijhajhra.careergo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Category_based_career extends AppCompatActivity {

    ListView listView;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("careers");
    ArrayList<String> career_list = new ArrayList<String>();
    public ArrayList<String> file_list = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    String clicked_category;
    private WebView webView;
    private Handler handler;
    private ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category_based_career);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PD = new ProgressDialog(this);
        PD.setMessage("Loading Careers");
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

        //used to get the message we passed in intent
        Intent intent = getIntent();
        clicked_category = intent.getExtras().getString("category");
        setTitle(clicked_category.toLowerCase() + " careers");


        listView = (ListView) findViewById(R.id.listview_id_cat);


        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                showdata(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //for list item click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

               // webView = new WebView(Category_based_career.this);
                Intent intent = new Intent(getApplicationContext(),Webview.class);
                        if(file_list.size()>position && file_list.get(position)!=null) {
                            intent.putExtra("file index", file_list.get(position));
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Category_based_career.this, "Details Are Not Available" , Toast.LENGTH_SHORT).show();

                        }


            }
        });
    }


    private void showdata(DataSnapshot dataSnapshot) {
        for (DataSnapshot myitem : dataSnapshot.getChildren()) {

            //datasnapshot points to c1,c2... so on
            //Toast.makeText(Category_based_career.this, "datasnapshot :  " + dataSnapshot.child("category").getValue(String.class), Toast.LENGTH_SHORT).show();
            //myitem points to inner childs of c1 and so on so their value is {key = name ,value=arts}
            // String name = myitem.child("name").getValue(String.class);
            //Toast.makeText(Category_based_career.this, "myitem : " + myitem.getKey(), Toast.LENGTH_SHORT).show();
            //myitem.key is childs of c1 like name , file
            // Toast.makeText(careers.this, "key : " + myitem.getKey(), Toast.LENGTH_SHORT).show();
            //myitem.value is value of inner childs like name = arts
            // Toast.makeText(careers.this, "value: " + myitem.getValue(String.class), Toast.LENGTH_SHORT).show();


            String key = myitem.getKey().toString();
            String category_pointed = dataSnapshot.child("category").getValue(String.class);
            if (clicked_category.equals(category_pointed)) {
                if (key.equals("name")) {
                    String name = myitem.getValue(String.class);
                    // Toast.makeText(Category_based_career.this, "hello : " + name, Toast.LENGTH_SHORT).show();

                    career_list.add(name);
                } else if (key.equals("file")) {
                    String file_link = myitem.getValue(String.class);
                    // Toast.makeText(Category_based_career.this, "hello : " + name, Toast.LENGTH_SHORT).show();

                    file_list.add(file_link);
                }

               // Collections.sort(career_list);
               // Collections.sort(file_list);
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, career_list);
                listView.setAdapter(adapter);

            }
            PD.dismiss();

        }
    }
    public String get_filename(int i)
    {
        return file_list.get(i);
    }

    //for app bar keys
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected

            case  android.R.id.home :
                Toast.makeText(this, "home selected", Toast.LENGTH_SHORT)
                        .show();
                finish();

                break;
            default:
                break;
        }

        return true;
    }


}

