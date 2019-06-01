package com.example.shrijhajhra.careergo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Map;

public class user_profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    public static final String[] spinner_items = {"10th", "12th", "graduation"};
    TextView logout ;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private TextView username,phone,email,qualification,test_taken;
    private TextView edit_profile,share;
    private ImageView photo;
    GenericTypeIndicator<Map<String, Integer>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Integer>>() {};

    Map<String, Integer> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
       // getSupportActionBar().hide();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        //code for spinner

        test_taken = (TextView)findViewById(R.id.test_taken);
        username = (TextView)findViewById(R.id.user_profile_name);
        phone = (TextView)findViewById(R.id.phoneno);
        email = (TextView)findViewById(R.id.emailid);
        photo = (ImageView) findViewById(R.id.user_profile_photo);
        qualification = (TextView)findViewById(R.id.select_Q);
        share = (TextView)findViewById(R.id.share_Text);
        //code for logout button
        logout= (TextView)findViewById(R.id.logout_Text);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(user_profile.this);
                builder.setMessage("Do You Want To Log Out");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(user_profile.this, "Signing out" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                        finish();
                        startActivity(intent);
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
        });

        //code for editprofile
        edit_profile = (TextView)findViewById(R.id.edit_profile_Text);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),edit_user_profile.class);

                startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        test_taken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!test_taken.getText().equals("No Test Taken"))
                {
                    Intent intent = new Intent(getApplicationContext(),test_result.class);
                    intent.putExtra("map", (Serializable) result);
                    startActivity(intent);
                }

            }
        });

        if (auth.getCurrentUser() != null) {
            final String userId = auth.getCurrentUser().getUid();
            SharedPreferences pref = getSharedPreferences(userId, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = pref.edit();
          /*  if (pref.getString("mail", null) != null && pref.getString("username", null) != null && pref.getString("phone", null) != null && pref.getString("quali", null) != null) {
                username.setText(pref.getString("username", null));
                phone.setText(pref.getString("phone", null));
                qualification.setText(pref.getString("quali", null));
                email.setText(pref.getString("mail", null));
            }*/
                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users");//.child(userId);
           current_user_db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //stored data
                    email.setText(user.getEmail());
                   // String test_date = dataSnapshot.child(userId).child("test_date").getValue(String.class);
                    String Username = dataSnapshot.child(userId).child("Username").getValue(String.class);
                    String phone_number = dataSnapshot.child(userId).child("PhoneNumber").getValue(String.class);
                    String qualifi= dataSnapshot.child(userId).child("Qualification").getValue(String.class);
                    String imageurl = dataSnapshot.child(userId).child("image").getValue(String.class);
                     result = dataSnapshot.child(userId).child("test_result").getValue(genericTypeIndicator);

                    if(Username!=null)
                    {
                        username.setText(Username);
                       // getSupportActionBar().setTitle(Username);
                    }
                    if(phone_number!=null)
                    {
                        phone.setText(phone_number);
                    }
                    if(qualifi!=null)
                    {
                        qualification.setText(qualifi);
                    }
                    if(imageurl!=null)
                    {
                        Picasso.with(getApplicationContext()).load(imageurl).into(photo);
                    }
                    if(result!=null)
                    {
                        test_taken.setText("Test taken");
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected

            case  android.R.id.home :
                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                finish();
                startActivity(intent);

                break;
            default:
                break;
        }

        return true;
    }
}
