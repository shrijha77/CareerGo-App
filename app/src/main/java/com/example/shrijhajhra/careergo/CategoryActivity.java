package com.example.shrijhajhra.careergo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
public class CategoryActivity extends AppCompatActivity {

    ListView list;
    String[] categories = {"AVIATION AND AEROSPACE","BANKING & FINANCE","BEAUTY AND WELLNESS","CIVIL SERVICES","EDUCATION AND TEACHING","ENGINEERING & MANUFACTURING","MANAGEMENT & MARKETING"
            ,"INFORMATION TECHNOLOGY","LAW","MEDICAL","MEDIA AND ENTERTAINMENT",

    } ;
    Integer[] imageId = {

            R.drawable.aviation_new,
            R.drawable.finance_new,
            R.drawable.beauty_1,
            R.drawable.civil_services,
            R.drawable.school_education,
            R.drawable.eng_manf_new,
            R.drawable.management,


            R.drawable.it,
            R.drawable.law,
            R.drawable.medical_p,
            R.drawable.media_new,



    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
        CustomList adapter = new
                CustomList(CategoryActivity.this, categories, imageId);
        list=(ListView)findViewById(R.id.List);
        list.setAdapter( adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(),Category_based_career.class);
                intent.putExtra("category",categories[position]);
                //Toast.makeText(CategoryActivity.this, "You Clicked at " +categories[+ position], Toast.LENGTH_SHORT).show();
                startActivity(intent);


            }
        });



    }
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
}
