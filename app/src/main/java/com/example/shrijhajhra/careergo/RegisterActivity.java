package com.example.shrijhajhra.careergo;

/**
 * Created by shri jhajhra on 08/03/2018.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnSignup, btnLogin;
    private ProgressDialog PD;
    TextView signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("User Registration");

        auth = FirebaseAuth.getInstance();
        inputEmail = (EditText)findViewById(R.id.email);
        inputPassword = (EditText)findViewById((R.id.password));
        signIn = (TextView) findViewById(R.id.sign_out_button);
        btnSignup = (Button)findViewById(R.id.sign_up_button) ;

        PD = new ProgressDialog(this);
        PD.setMessage("Authenticating");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                callsignup(email, password);
            }
        });



        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        finish();
                        startActivity(intent);

            }
        });
    }
    private void callsignup(String email, String password) {
        if (password.length() >= 6 && email.length() > 0) {
            PD.show();
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Testing", "createUserWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                Intent intent = new Intent(getApplicationContext(),edit_user_profile.class);
                                finish();
                                startActivity(intent);


                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();


                            }
                            PD.dismiss();

                        }
                    });
        }
        else if(password.length() <6 ){
            Toast.makeText(
                    RegisterActivity.this,
                    "Password length Should be Atleast 6",
                    Toast.LENGTH_LONG).show();
        }
    }

}

