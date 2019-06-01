package com.example.shrijhajhra.careergo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {


    GridLayout grid;
    BottomNavigationView bottomNavigationView;
    private ImageView qualification,category,career,test,article,exam,college,notification;
    private static ViewPager mPager;
    private static int currentPage = 0;
    long DELAY_MS = 5000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;
    String[] name = {"Check by Qualification","Notification","Categories","Careers","Test based","Recent Articles"} ;
    private int[] recommend = new int[3];
    private static  final int[] imageId = {
            R.drawable.aviation_industry_p,
            R.drawable.banking_p_p,
            R.drawable.beauty_p,
            R.drawable.civil_services,
            R.drawable.education_p,
            R.drawable.eng_p,
            R.drawable.management,


            R.drawable.it_p,
            R.drawable.law_1,
            R.drawable.medical_p,
            R.drawable.media_p,


    };
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    private ArrayList<Integer> XMENArray1 = new ArrayList<Integer>();

    FirebaseAuth auth =FirebaseAuth.getInstance();
    FirebaseUser user;

    private float initialX;
    android.support.v7.widget.Toolbar toolbar ;
    Timer timer;
    private String userId;
    public String recom1 ;
    public String recom2 ;
    public String recom3 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
      if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));

        //intilize using consturctor
        //place image and text on adapter using customgrid class and list single xml
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //if user logged in collect data

        //get data from shared prefrence

        toolbar = (android.support.v7.widget.Toolbar ) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

       // Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);
        mPager = (ViewPager) findViewById(R.id.pager);
        article = (ImageView)findViewById(R.id.article);
        notification = (ImageView)findViewById(R.id.notification);
        qualification = (ImageView)findViewById(R.id.qualification);
        category = (ImageView)findViewById(R.id.category);
        career = (ImageView)findViewById(R.id.career);
        test = (ImageView)findViewById(R.id.test1);
        exam = (ImageView)findViewById(R.id.exam);
        college = (ImageView)findViewById(R.id.college);
        category.setOnClickListener(this);
        career.setOnClickListener(this);
        test.setOnClickListener(this);
        qualification.setOnClickListener(this);
        notification.setOnClickListener(this);
        exam.setOnClickListener(this);
        college.setOnClickListener(this);
        article.setOnClickListener(this);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigationView);

        //for iniatialization of viewpager

        for(int i=0;i<imageId.length;i++)
            XMENArray.add(imageId[i]);
        //adapter on viewpager
        //if logged in show recommendation based on profile otherwise default
        if (auth.getCurrentUser() != null) {
            userId = user.getUid();
            SharedPreferences pref = getSharedPreferences(userId, Context.MODE_PRIVATE);
            recom1 = pref.getString("recom1", null);
            recom2 = pref.getString("recom2", null);
            recom3 = pref.getString("recom3", null);
            if(recom1!=null && recom2!=null && recom3!=null)
            {
                showRecommendation();
                mPager.setAdapter(new MyAdapter(MainActivity2.this, XMENArray1));
            }
            else
            {
                mPager.setAdapter(new MyAdapter(MainActivity2.this,XMENArray));
            }
        }
        else {

            mPager.setAdapter(new MyAdapter(MainActivity2.this,XMENArray));
        }






        final CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

        //indicator listner
        indicator.setViewPager(mPager);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mPager.post(new Runnable(){

                    @Override
                    public void run() {
                        mPager.setCurrentItem((mPager.getCurrentItem()+1)%imageId.length);
                        DELAY_MS++;
                    }
                });
            }
        };
        timer = new Timer();
        //timer.schedule(timerTask, DELAY_MS, PERIOD_MS);


      //  grid = (GridLayout)findViewById(R.id.maingrid);
      //  setSingleEvent(grid);







        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    // action with ID action_refresh was selected
                    case R.id.Profile:
                        //if already logged in go to profile activity else go to login screen
                        if (auth.getCurrentUser() != null) {
                            Intent intent = new Intent(getApplicationContext(),user_profile.class);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    // action with ID action_settings was selected
                    case R.id.more:
                        Toast.makeText(MainActivity2.this, "No More Details", Toast.LENGTH_SHORT)
                                .show();
                        break;



                    case  android.R.id.home :
                        Toast.makeText(MainActivity2.this, "home selected", Toast.LENGTH_SHORT)
                                .show();
                        finish();
                        break;

                    default:
                        break;
                }
                return true;
            }


        });
    }

    private void showRecommendation() {

        int i=0;
        if(recom1.equals("AVIATION AND AEROSPACE"))
        {
            recommend[i]= R.drawable.aviation_industry_p;
        }
        else if(recom1.equals("BEAUTY AND WELLNESS"))
        {
            recommend[i] =R.drawable.beauty_p;
        }
        else if(recom1.equals("CIVIL SERVICES"))
        {
            recommend[i] =R.drawable.civil_services;
        }
        else if(recom1.equals("EDUCATION & TRAINING"))
        {
            recommend[i]= R.drawable.education_p;
        }
        else if(recom1.equals("ENGINEERING & MANUFACTURING"))
        {
            recommend[i]= R.drawable.eng_p;
        }
        else if(recom1.equals("MANAGEMENT"))
        {
            recommend[i]= R.drawable.management;
        }
        else if(recom1.equals("BANKING AND FINANCE"))
        {
            recommend[i]=R.drawable.banking_p_p;
        }
        else if(recom1.equals("MEDICAL"))
        {
            recommend[i]= R.drawable.medical_p;
        }
        else if(recom1.equals("INFORMATION TECHNOLOGY"))
        {
            recommend[i] =R.drawable.it_p;
        }
        else if(recom1.equals("MEDIA AND ENTERTAINMENT"))
        {
            recommend[i]= R.drawable.media_p;
        }
        else if(recom1.equals("LAW"))
        {
            recommend[i]= R.drawable.law_1;
        }
        else{
            recommend[i]= R.drawable.career_go_logo;
        }
        i++;

        //RECOMMED 2
        if(recom2.equals("AVIATION AND AEROSPACE"))
        {
            recommend[i]= R.drawable.aviation_industry_p;
        }
        else if(recom2.equals("BEAUTY AND WELLNESS"))
        {
            recommend[i] =R.drawable.beauty_p;
        }
        else if(recom2.equals("CIVIL SERVICES"))
        {
            recommend[i] =R.drawable.civil_services;
        }
        else if(recom2.equals("EDUCATION & TRAINING"))
        {
            recommend[i]= R.drawable.education_p;
        }
        else if(recom2.equals("ENGINEERING & MANUFACTURING"))
        {
            recommend[i]= R.drawable.eng_p;
        }
        else if(recom2.equals("MANAGEMENT"))
        {
            recommend[i]= R.drawable.management;
        }
        else if(recom2.equals("BANKING AND FINANCE"))
        {
            recommend[i]=R.drawable.banking_p_p;
        }
        else if(recom2.equals("MEDICAL"))
        {
            recommend[i]= R.drawable.medical_p;
        }
        else if(recom2.equals("INFORMATION TECHNOLOGY"))
        {
            recommend[i] =R.drawable.it_p;
        }
        else if(recom2.equals("MEDIA AND ENTERTAINMENT"))
        {
            recommend[i]= R.drawable.media_p;
        }
        else if(recom3.equals("LAW"))
        {
            recommend[i]= R.drawable.law_1;
        }
        else{
            recommend[i]= R.drawable.logo_final;
        }
        i++;


        if(recom3.equals("AVIATION AND AEROSPACE"))
        {
            recommend[i]= R.drawable.aviation_industry_p;
        }
        else if(recom3.equals("BEAUTY AND WELLNESS"))
        {
            recommend[i] =R.drawable.beauty_p;
        }
        else if(recom3.equals("CIVIL SERVICES"))
        {
            recommend[i] =R.drawable.civil_services;
        }
        else if(recom3.equals("EDUCATION AND TRAINING"))
        {
            recommend[i]= R.drawable.education_p;
        }
        else if(recom3.equals("ENGINEERING & MANUFACTURING"))
        {
            recommend[i]= R.drawable.eng_p;
        }
        else if(recom3.equals("MANAGEMENT"))
        {
            recommend[i]= R.drawable.management;
        }
        else if(recom3.equals("BANKING AND FINANCE"))
        {
            recommend[i]=R.drawable.banking_p_p;
        }
        else if(recom3.equals("MEDICAL"))
        {
            recommend[i]= R.drawable.medical_p;
        }
        else if(recom3.equals("INFORMATION TECHNOLOGY"))
        {
            recommend[i] =R.drawable.it_p;
        }
        else if(recom3.equals("MEDIA AND ENTERTAINMENT"))
        {
            recommend[i]= R.drawable.media_p;
        }
        else if(recom3.equals("LAW"))
        {
            recommend[i]= R.drawable.law_1;
        }
        else{
            recommend[i]= R.drawable.logo_final;
        }
        i++;
        for(int j=0;j<recommend.length;j++)
            XMENArray1.add(recommend[j]);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.category:
                //Do something
                Intent intent1 = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent1);

                break;
            case R.id.career:
                Intent intent2 = new Intent(getApplicationContext(), careers.class);
                startActivity(intent2);
                break;
            case R.id.exam:
                Intent intent3 = new Intent(getApplicationContext(), exams.class);
                startActivity(intent3);
                break;
            case R.id.test1:
                if (auth.getCurrentUser() != null) {

                    Intent intent4 = new Intent(getApplicationContext(),testActivity.class);
                    startActivity(intent4);

                }
                else
                {
                    //to show a dialog
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                    builder.setMessage("You Need To Log In To Give Test");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //Toast.makeText(RegisterActivity.this, "Signing out" , Toast.LENGTH_SHORT).show();
                            Intent intent3 = new Intent(getApplicationContext(),LoginActivity.class);

                            startActivity(intent3);

                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener()     {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
                break;
            case R.id.college:
                Intent intent4 = new Intent(getApplicationContext(),collegeActivity.class);
                startActivity(intent4);

                break;


            case R.id.article:
                Intent intent = new Intent(getApplicationContext(),Article_category.class);
                startActivity(intent);
                break;
            case R.id.notification:
                Toast.makeText(MainActivity2.this, "No Details Added", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.qualification:
                Toast.makeText(MainActivity2.this, "No Details Added", Toast.LENGTH_SHORT)
                        .show();
                break;

        }
    }
    private void setSupportActionBar(Toolbar mTopToolbar) {
    }


    private void init() {


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == imageId.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }




    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.actionbar_menu, menu);

            return true;
        }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.log_in:
                //if already logged in go to profile activity else go to login screen
                if (auth.getCurrentUser() != null) {
                    Intent intent = new Intent(getApplicationContext(),user_profile.class);

                     startActivity(intent);
                    Toast.makeText(this, "go to profile activity", Toast.LENGTH_SHORT)
                            .show();

                }
                else {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);

                     startActivity(intent);
                    Toast.makeText(this, "Login ", Toast.LENGTH_SHORT)
                            .show();
                }

                break;
            // action with ID action_settings was selected
            case R.id.setting:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            case  android.R.id.home :
                Toast.makeText(this, "home selected", Toast.LENGTH_SHORT)
                        .show();
                    finish();
            case R.id.about:
                Toast.makeText(this, "home selected", Toast.LENGTH_SHORT)
                        .show();
                //Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
                //startActivity(intent);


                break;



            default:
                break;
        }

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
