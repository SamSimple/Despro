package com.example.maru.despro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MonitorActivity extends AppCompatActivity {
    Button profile, monitor, linef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_monitor);
        profile = findViewById (R.id.button1);

        linef = findViewById (R.id.button2);

        profile.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MonitorActivity.this, ProfileActivity.class);
                startActivity (intent);
            }
        });

        linef.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MonitorActivity.this, LineFollowerActivity.class);
                startActivity (intent);
            }
        });
    }
}
