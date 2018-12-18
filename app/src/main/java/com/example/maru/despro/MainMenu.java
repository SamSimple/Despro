package com.example.maru.despro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    Button profile, monitor, linef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main_menu);

        profile = findViewById (R.id.button1);
        monitor = findViewById (R.id.button2);
        linef = findViewById (R.id.button3);

        profile.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainMenu.this, ProfileActivity.class);
                startActivity (intent);
            }
        });
        monitor.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainMenu.this, MonitorActivity.class);
                startActivity (intent);
            }
        });
        linef.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainMenu.this, LineFollowerActivity.class);
                startActivity (intent);
            }
        });
    }


}
