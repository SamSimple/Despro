package com.example.maru.despro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    StorageReference mStorage;
    String email, name, age, cp, password, verified, uName,bedroom,kitchen,livingroom,toilet,emergency,currentUserID;
    User user = new User(name, age, cp, email, password, verified,bedroom,kitchen,livingroom,toilet,emergency);
    TextView tvName, tvCp, tvEmail, tvAge;
    CircleImageView ProfileImage;
    ProgressDialog mProgressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserID= mAuth.getCurrentUser().getUid ();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("Users");
        mStorage = FirebaseStorage.getInstance().getReference ().child ("Profile Images");
        uName = getIntent().getStringExtra("UserName");
        tvName = (TextView) findViewById(R.id.TvUserName);
        tvCp = (TextView) findViewById(R.id.TvCp);
        tvEmail = (TextView) findViewById(R.id.TvEmail);
        tvAge = (TextView) findViewById(R.id.TvAge);
        Log.d("Wiw", uName);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvName.setText(String.valueOf(dataSnapshot.child(uName).child("Name").getValue()));
                tvCp.setText(String.valueOf(dataSnapshot.child(uName).child("Cp").getValue()));
                tvEmail.setText(String.valueOf(dataSnapshot.child(uName).child("Email").getValue()));
                tvAge.setText(String.valueOf(dataSnapshot.child(uName).child("Age").getValue()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ProfileImage = findViewById(R.id.profile);

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(ProfileActivity.this);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                StorageReference storageReference= mStorage.child (currentUserID +  ".jpg");
                storageReference.putFile (resultUri).addOnCompleteListener (new OnCompleteListener<UploadTask.TaskSnapshot> () {
                    @Override
                    public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful ()){
                            toastMessage ("Uploaded Successfully");
                            final String downloadUrl= task.getResult().toString ();

                        }
                    }
                });
                ProfileImage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                toastMessage ("" + error);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent (ProfileActivity.this,MonitorActivity.class);
        startActivity(intent);
        finish();
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
