package com.example.maru.despro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Temperature extends BaseActivity {
    TextView TempData,Dates;
    String tempString = "";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference("Temperature");
    long dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_temperature, contentFrameLayout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(3).setChecked(true);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        Dates = (TextView) findViewById(R.id.TVdate);
//        Calendar date = Calendar.getInstance();
// for your date format use
//        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
// set a string to format your current date
//        String curDate = sdf.format(date.getTime());

      calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
          @Override
          public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
              month = month + 1;

              String newDate = month+"-"+year+"-"+dayOfMonth;
              Dates.setText(newDate);
          }
      });
//



//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot DS:dataSnapshot.getChildren()){
//                    tempString= String.valueOf(tempString + DS.getValue()+"\n");
//                }
//                TempData.setText(tempString);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }
}
