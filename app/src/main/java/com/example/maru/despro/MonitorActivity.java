package com.example.maru.despro;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class MonitorActivity extends AppCompatActivity {
    Button profile, linef, shit;
    ImageView temp;
    double values[];
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    GraphView graph;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        final String uName = getIntent().getStringExtra("UserName");
        profile = findViewById(R.id.button1);
        linef = findViewById(R.id.button2);
        temp = findViewById(R.id.temp);
        shit = findViewById(R.id.shit);

        graph = findViewById(R.id.graph);

        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        Viewport viewport = graph.getViewport();

        viewport.setXAxisBoundsManual(true);

        viewport.setMinX(0);
        viewport.setMaxX(20);
        viewport.setScrollable(true);



        shit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TransitionDrawable) temp.getDrawable()).startTransition(3000);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonitorActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("UserName", uName);
                startActivity(intent);
            }
        });

        linef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonitorActivity.this, LineFollowerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("UserName", uName);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (i = 0; i<20; i++){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry();
                        }
                    });
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                i = 0;

            }
        }).start();
    }

    private void addEntry(){
         series.appendData(new DataPoint(lastX++,RANDOM.nextDouble()*20d),true,20);

    }

}
