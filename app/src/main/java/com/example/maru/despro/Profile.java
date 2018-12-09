package com.example.maru.despro;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference("Users");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String email,name,age,cp,password;
    User user = new User(name,age,cp,email,password);

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        email=mAuth.getCurrentUser().getEmail();
        View rootView = inflater.inflate(R.layout.profile_tab1, container, false);
        final TextView tvName = (TextView) rootView.findViewById(R.id.TvUserName);
        final TextView tvCp = (TextView) rootView.findViewById(R.id.TvCp);
        final TextView tvEmail = (TextView) rootView.findViewById(R.id.TvEmail);
        final TextView tvAge = (TextView) rootView.findViewById(R.id.TvAge);


            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                       String email1 = snapshot.getKey().replace(",",".");
                        if(email1.equals(email)){
                            tvName.setText("Name: " + snapshot.child("Name").getValue().toString());
                            tvAge.setText("Age: "+ snapshot.child("Age").getValue().toString());
                            tvCp.setText("CellPhone Number : "+ snapshot.child("Cp").getValue().toString());
                            tvEmail.setText("Email Address: "+ snapshot.child("Email").getValue().toString());
                        }


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        return rootView;
    }

}
