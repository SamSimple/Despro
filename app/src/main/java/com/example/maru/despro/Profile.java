package com.example.maru.despro;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance ();
    DatabaseReference mRef = database.getReference ("Users");
    FirebaseAuth mAuth = FirebaseAuth.getInstance ();
    String email, name, age, cp, password;
    User user = new User (name, age, cp, email, password);
    final static int Gallery_Pick = 1;
    Uri uri;
    ImageView ProfileImage;
    private Intent CropIntent,GalIntent;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        email = mAuth.getCurrentUser ().getEmail ();
        View rootView = inflater.inflate (R.layout.profile_tab1, container, false);
        final TextView tvName = (TextView) rootView.findViewById (R.id.TvUserName);
        final TextView tvCp = (TextView) rootView.findViewById (R.id.TvCp);
        final TextView tvEmail = (TextView) rootView.findViewById (R.id.TvEmail);
        final TextView tvAge = (TextView) rootView.findViewById (R.id.TvAge);
        ProfileImage = rootView.findViewById (R.id.profile);

        ProfileImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                GalIntent = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult (GalIntent,Gallery_Pick);
            }
        });


        mRef.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren ()) {
                    String email1 = snapshot.getKey ().replace (",", ".");
                    if (email1.equals (email)) {
                        tvName.setText ("Name: " + snapshot.child ("Name").getValue ().toString ());
                        tvAge.setText ("Age: " + snapshot.child ("Age").getValue ().toString ());
                        tvCp.setText ("CellPhone Number : " + snapshot.child ("Cp").getValue ().toString ());
                        tvEmail.setText ("Email Address: " + snapshot.child ("Email").getValue ().toString ());
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 0) {
            ImageCropFunction ();
        } else if (requestCode == 1) {
            if (data != null) {
                uri = data.getData ();
                ImageCropFunction ();

            }
        }
    }

    public void ImageCropFunction(){

        try{
            CropIntent= new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType (uri,"image/*");
            CropIntent.putExtra("crop",true);
            CropIntent.putExtra ("OutputX",180);
            CropIntent.putExtra ("OutputY",180);
            CropIntent.putExtra ("aspectX",3);
            CropIntent.putExtra ("aspectX",4);
            CropIntent.putExtra ("ScaleUpIfneeded",true);
            CropIntent.putExtra ("return-data",true);

            startActivityForResult (CropIntent,1);

        }catch(ActivityNotFoundException e){


        }
    }

}
