package com.example.shrijhajhra.careergo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class careers extends AppCompatActivity {

    //final FirebaseDatabase databaseReference = FirebaseDatabase.getInstance();

   // ArrayList<String> career_list;

    ListView listView;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("careers");
    ArrayList<String>  career_list = new ArrayList<String>();
    ArrayList<String>  file_list = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    public static final String TAG ="running" ;

    private Handler handler;
    private ProgressDialog PD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careers);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  //      getSupportActionBar().setDisplayUseLogoEnabled(true);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
        //setting progress bar
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



        listView = (ListView)findViewById(R.id.list_career);
        listView.setAdapter( adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Intent intent = new Intent(getApplicationContext(),Webview.class);
                if(file_list.size()>position) {
                    intent.putExtra("file index", file_list.get(position));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(careers.this, "Not Available", Toast.LENGTH_SHORT).show();

                }


                //Toast.makeText(careers.this, "You Clicked at " + career_list.get(+position), Toast.LENGTH_SHORT).show();

            }
        });




        dref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                showdata(dataSnapshot);




            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                showdata(dataSnapshot);
                setListViewHeightBasedOnChildren(listView);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                showdata(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showdata(DataSnapshot dataSnapshot) {

        for (DataSnapshot myitem : dataSnapshot.getChildren()) {

            //datasnapshot points to c1,c2... so on
           // Toast.makeText(careers.this, "datasnapshot :  " + dataSnapshot, Toast.LENGTH_SHORT).show();
            //myitem points to inner childs of c1 and so on so their value is {key = name ,value=arts}
           // String name = myitem.child("name").getValue(String.class);
           // Toast.makeText(careers.this, "myitem : " + myitem.getKey(), Toast.LENGTH_SHORT).show();
            //myitem.key is childs of c1 like name , file
           // Toast.makeText(careers.this, "key : " + myitem.getKey(), Toast.LENGTH_SHORT).show();
            //myitem.value is value of inner childs like name = arts
           // Toast.makeText(careers.this, "value: " + myitem.getValue(String.class), Toast.LENGTH_SHORT).show();



            String key = myitem.getKey().toString();
            if(key.equals("name"))
            {
               // Toast.makeText(careers.this, "hello : " + myitem.getKey(), Toast.LENGTH_SHORT).show();
                 String name = myitem.getValue(String.class);
                 career_list.add(name);
            }
            else if(key.equals("file"))
            {
                file_list.add(myitem.getValue(String.class));
            }

            //Collections.sort(career_list);
           // Collections.sort(file_list);
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,career_list);
            listView.setAdapter(adapter);

        }
        PD.dismiss();


    }
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




