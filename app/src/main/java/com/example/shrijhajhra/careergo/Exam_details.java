package com.example.shrijhajhra.careergo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Exam_details extends AppCompatActivity {

    DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("Exams");
    private TextView examName,examDetails,examPattern,examDate,link;
    String link_site;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details);
        Intent intent = getIntent();
        final String exam_name = intent.getExtras().getString("exam name");

        examName = (TextView)findViewById(R.id.exam_name);
        examDetails = (TextView)findViewById(R.id.exam_overview);
        examPattern = (TextView)findViewById(R.id.exam_pattern);
        examDate = (TextView)findViewById(R.id.exam_date);
        link = (TextView)findViewById(R.id.link);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Webview.class);
                intent.putExtra("file index",link_site);
                startActivity(intent);
            }
        });
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String overview = dataSnapshot.child(exam_name).child("overview").getValue(String.class);
                String pattern = dataSnapshot.child(exam_name).child("pattern").getValue(String.class);
                String time = dataSnapshot.child(exam_name).child("date").getValue(String.class);
               // String name = dataSnapshot.child(exam_name).child("name").getValue(String.class);
                link_site = dataSnapshot.child(exam_name).child("link").getValue(String.class);
               // Toast.makeText(Exam_details.this, "overview :  "  + dataSnapshot.child(exam_name), Toast.LENGTH_SHORT).show();
                //examName.setText(name);
                examName.setText(exam_name);

                if(overview!=null)
                {
                    examDetails.setText(overview);

                }
                if(pattern!=null)
                {
                    examPattern.setText(pattern);

                }
                if(time!=null)
                {
                    examDate.setText(time);

                }
                if(link_site!=null)
                {
                    link.setText(link_site);

                }
            }

            public void onCancelled(DatabaseError databaseError) {

            }




        });

    }

}
