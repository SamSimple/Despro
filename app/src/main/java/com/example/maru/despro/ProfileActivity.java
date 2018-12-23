package com.example.maru.despro;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance ();
    DatabaseReference mRef = database.getReference ("Users");
    FirebaseAuth mAuth = FirebaseAuth.getInstance ();
    String email, name, age, cp, password;
    User user = new User (name, age, cp, email, password);
    final static int Gallery_Pick = 1;
    Uri uri;
    CircleImageView ProfileImage;
    private Intent CropIntent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile);

        final TextView tvName = (TextView) findViewById (R.id.TvUserName);
        final TextView tvCp = (TextView) findViewById (R.id.TvCp);
        final TextView tvEmail = (TextView) findViewById (R.id.TvEmail);
        final TextView tvAge = (TextView) findViewById (R.id.TvAge);
        ProfileImage = findViewById (R.id.profile);

        ProfileImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                CropImage.activity ()
                        .setAspectRatio (1, 1)
                        .setCropShape (CropImageView.CropShape.OVAL)
                        .start (ProfileActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult (data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri ();
                ProfileImage.setImageURI (resultUri);
            }else if(resultCode== CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error= result.getError ();
                Toast.makeText(this,""+error,Toast.LENGTH_SHORT).show ();
            }
        }

    }
}
