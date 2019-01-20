package com.example.maru.despro;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MonitorActivity extends BaseActivity{

    Button shit,record;
    ImageView temp;
    TextView heartRate;
    int dataCount = 0;
    int count = 0;
    String uName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_monitor, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference hrRef1 = database.getReference("HeartRateTemporary");
        hrRef1.removeValue();

        SharedPreferences pref = getSharedPreferences("Information",MODE_PRIVATE);
        uName = pref.getString("email","");
        temp = findViewById(R.id.temp);
        heartRate = findViewById(R.id.heartrate);
        record= findViewById(R.id.btrecord);



        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("HeartRateTemporary");

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {

                                          FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                          final DatabaseReference databaseReference = firebaseDatabase.getReference("HeartRateTemporary");
                                          databaseReference.child(String.valueOf(count)).setValue(1);
                                          count++;
                                          final List<Double> HRtemporary = new ArrayList<>();
                                          Double total = 0.0;

                                          databaseReference.addValueEventListener(new ValueEventListener() {
                                              @Override
                                              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                  if(dataSnapshot.getChildrenCount() == 60){
                                                      int count = 0;
                                                      for(DataSnapshot ds :dataSnapshot.getChildren()){
                                                          if(count<60){
                                                              HRtemporary.add(Double.valueOf(ds.getValue().toString()));

                                                          }
                                                          else{
                                                              break;
                                                          }
                                                          count++;
                                                      }
                                                      if(count == 60){

                                                      }
                                                  }
                                              }

                                              @Override
                                              public void onCancelled(@NonNull DatabaseError databaseError) {

                                              }
                                          });


                                          if(HRtemporary.size() == 60){
                                              for(Double temp : HRtemporary){
                                                  total += temp;
                                              }
                                              total = total/60;
                                              final Double totalHR = total;

                                              DatabaseReference refHR = firebaseDatabase.getReference("HeartRate");
                                              refHR.child(String.valueOf(dataCount)).setValue(totalHR);
                                              dataCount++;
                                          }

                                      }

                                  }
                    );
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
