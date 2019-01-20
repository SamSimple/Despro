package com.example.maru.despro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.facebook.share.Share;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import petrov.kristiyan.colorpicker.ColorPicker;

public class LineFollowerActivity extends BaseActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference("Users");
    String uName;
    int pos, col;
    String HexColor,LFcolors;
    ArrayList<String> colors = new ArrayList<>();
    TextView lfcolor;


    int[] imageId = {
            R.drawable.bed,
            R.drawable.kitchen,
            R.drawable.livingroom,
            R.drawable.toilet,
            R.drawable.ic_two,
    };
    String[] web = {"Bedroom", "Kitchen", "LivingRoom", "Toilet", "Emergency"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_line_follower, contentFrameLayout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);
        colors.add("#ff0000");
        colors.add("#0000ff");
        colors.add("#ffff00");
        colors.add("#000000");
        colors.add("#ffffff");
        SharedPreferences prefs = getSharedPreferences("Information",MODE_PRIVATE);
        uName = prefs.getString("email","");
        ImageAdapter adapter = new ImageAdapter(this, web, imageId);
        GridView grid = (GridView) findViewById(R.id.gridView);
        grid.setNumColumns(2);
        grid.setColumnWidth(25);
        grid.setVerticalSpacing(100);
        grid.setHorizontalSpacing(100);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                openColorpicker();
            }
        });
            lfcolor =  findViewById(R.id.TVlfcolor);
                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            LFcolors ="Bedroom: "+ dataSnapshot.child(uName).child("LineFollowerInformation").child("Bedroom").getValue()+ "\n"+"Kitchen: "+dataSnapshot.child(uName).child("LineFollowerInformation").child("Kitchen").getValue()+ "\n"
                                    +"LivingRoom: "+dataSnapshot.child(uName).child("LineFollowerInformation").child("LivingRoom").getValue()+ "\n"+"Toilet: "+dataSnapshot.child(uName).child("LineFollowerInformation").child("Toilet").getValue()+ "\n"
                            +"Emergency: "+dataSnapshot.child(uName).child("LineFollowerInformation").child("Emergency").getValue();
                            lfcolor.setText(LFcolors);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

    }

    public void openColorpicker() {
        ColorPicker colorPicker = new ColorPicker(LineFollowerActivity.this);

        colorPicker.setColors(colors).setColumns(2).setRoundColorButton(true).setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                col = color;
                if (pos == 0) {
                    HexColor = String.format("#%06x", (0xFFFFFF) & col);
                    mRef.child(uName).child("LineFollowerInformation").child("Bedroom").setValue(HexColor);
                    colors.remove(String.format("#%06x",(0xFFFFFF) & col));
                }
                if (pos == 1) {
                    HexColor = String.format("#%06x", (0xFFFFFF) & col);
                    mRef.child(uName).child("LineFollowerInformation").child("Kitchen").setValue(HexColor);
                    colors.remove(String.format("#%06x",(0xFFFFFF) & col));
                }
                if (pos == 2) {
                    HexColor = String.format("#%06x", (0xFFFFFF) & col);
                    mRef.child(uName).child("LineFollowerInformation").child("LivingRoom").setValue(HexColor);
                    colors.remove(String.format("#%06x",(0xFFFFFF) & col));
                }

                if (pos == 3) {
                    HexColor = String.format("#%06x", (0xFFFFFF) & col);
                    mRef.child(uName).child("LineFollowerInformation").child("Toilet").setValue(HexColor);
                    colors.remove(String.format("#%06x",(0xFFFFFF) & col));
                }
                if (pos == 4) {
                    HexColor = String.format("#%06x", (0xFFFFFF) & col);
                    mRef.child(uName).child("LineFollowerInformation").child("Emergency").setValue(HexColor);
                    colors.remove(String.format("#%06x",(0xFFFFFF) & col));
                }

            }

            @Override
            public void onCancel() {

            }
        }).show();


    }


}
