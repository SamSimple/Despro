package com.example.maru.despro;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.share.Share;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText LogEmail, LogPassword;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference("Users");
    String emailAdd;
    String Password;
    String verify;
    String nAme, cp, email, age,femail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.TVSignUp).setOnClickListener(this);
        findViewById(R.id.LogInButton).setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        LogEmail = findViewById(R.id.LogInEmail);
        LogPassword = findViewById(R.id.LogInPassword);


    }


    private void userLogin() {
        emailAdd = LogEmail.getText().toString().trim();
        Password = LogPassword.getText().toString().trim();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                verify = String.valueOf(dataSnapshot.child(emailAdd.replace(".", ",")).child("PersonalInformation").child("Verified").getValue());
                if (TextUtils.isEmpty(emailAdd)) {
                    LogEmail.requestFocus();
                    Toast.makeText(MainActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Password)) {
                    LogPassword.requestFocus();
                    Toast.makeText(MainActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAdd).matches()) {
                    Toast.makeText(MainActivity.this, "Please Enter a Valid Email", Toast.LENGTH_SHORT).show();
                    LogEmail.requestFocus();

                } else if (LogPassword.length() < 6) {
                    Toast.makeText(MainActivity.this, "The Password should be 6 characters and above", Toast.LENGTH_SHORT).show();
                    LogPassword.requestFocus();
                } else if (verify.equals("false")) {
                    Toast.makeText(MainActivity.this, "Please Verify your Email first.", Toast.LENGTH_SHORT).show();
                    (Objects.requireNonNull(mAuth.getCurrentUser())).reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mRef.child(emailAdd.replace(".", ",")).child("PersonalInformation").child("Verified").setValue(String.valueOf(mAuth.getCurrentUser().isEmailVerified()));
                        }
                    });

                } else {
//                    progressDialog.setMessage("Logging In...");
//                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(emailAdd, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                progressDialog.dismiss();
                                mRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        nAme = String.valueOf(dataSnapshot.child(emailAdd.replace(".", ",")).child("PersonalInformation").child("Name").getValue());
                                        cp = String.valueOf(dataSnapshot.child(emailAdd.replace(".", ",")).child("PersonalInformation").child("Cp").getValue());
                                        age = String.valueOf(dataSnapshot.child(emailAdd.replace(".", ",")).child("PersonalInformation").child("Age").getValue());
                                        email = String.valueOf(dataSnapshot.child(emailAdd.replace(".", ",")).child("PersonalInformation").child("Email").getValue());
                                        femail = email.replace(".",",");
                                        SharedPreferences.Editor editor = getSharedPreferences("Information", MODE_PRIVATE).edit();
                                        editor.putString("name", nAme);
                                        editor.putString("cp", cp);
                                        editor.putString("email", femail);
                                        editor.putString("age", age);
                                        editor.apply();

                                   }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                Intent intent = new Intent(MainActivity.this, Temperature.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TVSignUp:
                startActivity(new Intent(this, UserSignUp.class));
                break;
            case R.id.LogInButton:
                userLogin();
                break;
        }


    }

}
