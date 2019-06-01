package com.example.shrijhajhra.careergo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class edit_user_profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button save;
    private Spinner spinner;

    private FirebaseAuth auth;
   private FirebaseUser user;
   private EditText username;
   private EditText phone;
   private TextView qualification;
   private ImageView imageview;
    private Uri mImageUri;
    private final int PICK_IMAGE_REQUEST = 71;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ProgressBar mProgressBar;

    private StorageTask mUploadTask;
    private FirebaseAuth.AuthStateListener fireBaseAuthListner;
    public static final String[] spinner_items = {"Select Qualification","10th", "12th", "graduation"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        //edit name on action bar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("Edit Profile");
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
        auth = FirebaseAuth.getInstance();
        qualification = (TextView)findViewById(R.id.select_Q);
        phone = (EditText)findViewById(R.id.phone_number);
        username = (EditText)findViewById(R.id.username);
        imageview = (ImageView) findViewById(R.id.user_profile_photo);


        mProgressBar = findViewById(R.id.progress_bar);
        //code for spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner_items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);

        //first set initial values on edit screen
        if (auth.getCurrentUser() != null) {
            final String userId = auth.getCurrentUser().getUid();



            final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users");//.child(userId);
            current_user_db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //stored data

                    String UsernameI = dataSnapshot.child(userId).child("Username").getValue(String.class);
                    String phone_numberI = dataSnapshot.child(userId).child("PhoneNumber").getValue(String.class);
                    String qualifiI= dataSnapshot.child(userId).child("Qualification").getValue(String.class);

                    if(UsernameI!=null)
                    {

                        username.setText(UsernameI);
                    }
                    else
                    {

                        username.setText("User Name");
                    }

                    if(phone_numberI!=null)
                    {
                        phone.setText(phone_numberI);

                    }
                    else
                    {
                        phone.setText("XXXXXXXXXX");
                    }
                    if(qualifiI!=null)
                    {
                        qualification.setText(qualifiI);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        //code for save button
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (auth.getCurrentUser() != null) {
                    //if already logged in get userid then point to user in db
                    String userId = auth.getCurrentUser().getUid();
                    SharedPreferences pref = getSharedPreferences(userId, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                    //get all enterd data
                    String name = username.getText().toString();
                    String phone_number = phone.getText().toString();
                    String qalifi  = qualification.getText().toString();
                    Map newData = new HashMap();
                    if(name.equals("") )
                    {
                        edit.putString("username", "user Name");
                        newData.put("Username","User Name");
                    }
                    else if(!name.equals("") )
                    {
                        edit.putString("username", name);
                        newData.put("Username",name);
                    }
                    if(phone_number.equals(""))
                    {
                        edit.putString("phone", "XXXXXXXXXX");
                        newData.put("PhoneNumber","XXXXXXXXXX");
                    }
                    if(!phone_number.equals(""))
                    {
                        edit.putString("phone", phone_number);
                        newData.put("PhoneNumber",phone_number);
                    }
                    if(qalifi!=null)
                    {
                        edit.putString("quali", qalifi);
                        newData.put("Qualification",qalifi);
                    }
                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(edit_user_profile.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile(userId,name);
                    }

                    edit.putString("mail",auth.getCurrentUser().getEmail());
                    edit.commit();


                    current_user_db.setValue(newData);

                }

                Intent intent = new Intent(getApplicationContext(),user_profile.class);
                finish();
                startActivity(intent);
            }
        });


        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(String userId , String username ) {
        if (mImageUri != null) {
            //file id as userid
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users/"+userId);
            mStorageRef = FirebaseStorage.getInstance().getReference(userId);

            StorageReference fileReference = mStorageRef.child("User profile"
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(edit_user_profile.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(taskSnapshot.getDownloadUrl().toString());

                            mDatabaseRef.child("image").setValue(taskSnapshot.getDownloadUrl().toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(edit_user_profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }


    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(imageview);
        }
    }

    private Boolean Number_Validate(String number)
    {
        return  !TextUtils.isEmpty(number) && (number.length()==10) && android.util.Patterns.PHONE.matcher(number).matches();
    }

    //finction for listner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        if(position!=0)
            qualification.setText(spinner.getSelectedItem().toString());
        spinner.setSelection(0);
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //code for action bar back arrow listner
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
    public void onBackPressed() {
        finish();
    }
}
