package com.example.maru.despro;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class MonitorActivity extends AppCompatActivity {
    Button profile, linef,shit;
    ImageView temp;
    LineGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_monitor);
        final String uName = getIntent().getStringExtra("UserName");
        profile = findViewById (R.id.button1);
        linef = findViewById (R.id.button2);
        temp=findViewById (R.id.temp);
        shit=findViewById (R.id.shit);
        double y,x;
        Random random = new Random ();
        x= random.nextInt(5);

        shit.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                ((TransitionDrawable) temp.getDrawable()).startTransition (3000);
            }
        });

        profile.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MonitorActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("UserName",uName);
                startActivity (intent);
            }
        });

        linef.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MonitorActivity.this, LineFollowerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("UserName",uName);
                startActivity (intent);
            }
        });

        GraphView graph = findViewById (R.id.graph);
        series= new LineGraphSeries<DataPoint> ();
        for (int i=0; i<500; i++){
            x=x+.1;
            y= Math.sin(x);
            series.appendData(new DataPoint (x,y), true,500);
        }
        graph.addSeries (series);
    }
}
