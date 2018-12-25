package com.example.maru.despro;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import petrov.kristiyan.colorpicker.ColorPicker;

public class LineFollowerActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference("Users");
    String uName;
    int pos, col;
    String HexColor;
    List<String> list;
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
        setContentView(R.layout.activity_line_follower);
        uName = getIntent().getStringExtra("UserName");
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
                Log.d("pos",String.valueOf(pos));
                openColorpicker();
            }
        });



    }

    public void openColorpicker() {
        final ColorPicker colorPicker = new ColorPicker(this);
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#ff0000");
        colors.add("#0000ff");
        colors.add("#ffff00");
        colors.add("#000000");
        colors.add("#ffffff");

        colorPicker.setColors(colors).setColumns(2).setRoundColorButton(true).setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                col = color;
                Log.d("pos",String.valueOf(pos));

                if (pos == 0) {
                    HexColor = String.format("#%06x", (0xFFFFFF) & col);
                    mRef.child(uName).child("Bedroom").setValue(HexColor);
                }
                if (pos == 1) {
                    HexColor = String.format("#%06x", (0xFFFFFF) & col);
                    mRef.child(uName).child("Kitchen").setValue(HexColor);
                }
                if (pos == 2) {
                    HexColor = String.format("#%06x", (0xFFFFFF) & col);
                    mRef.child(uName).child("LivingRoom").setValue(HexColor);
                }

                if (pos == 3) {
                    HexColor = String.format("#%06x", (0xFFFFFF) & col);
                    mRef.child(uName).child("Toilet").setValue(HexColor);
                }
                if (pos == 4) {
                    HexColor = String.format("#%06x", (0xFFFFFF) & col);
                    mRef.child(uName).child("Emergency").setValue(HexColor);
                }

                Log.d("UserName",uName);
            }

            @Override
            public void onCancel() {

            }
        }).show();


    }
}
