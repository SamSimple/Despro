package com.example.maru.despro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText LogEmail, LogPassword;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

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

        String emailAdd = LogEmail.getText().toString().trim();
        String Password = LogPassword.getText().toString().trim();
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
        } else if (!mAuth.getCurrentUser().isEmailVerified()) {
            mAuth.getCurrentUser().reload();
            Toast.makeText(MainActivity.this, "Please Verify your Email first.", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Logging In...");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(emailAdd, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, MainMenu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        progressDialog.dismiss();


                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }

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
