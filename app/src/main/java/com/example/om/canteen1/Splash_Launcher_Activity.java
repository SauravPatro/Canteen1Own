package com.example.om.canteen1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splash_Launcher_Activity extends AppCompatActivity {

    public static final long DELAY=3500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__launcher_);

        Timer RunSplash=new Timer();
        TimerTask ShowSplash=new TimerTask() {
            @Override
            public void run() {
                finish();
                Intent splashIntent = new Intent(Splash_Launcher_Activity.this, MainActivity.class);
                startActivity(splashIntent);


            }
        };

        RunSplash.schedule(ShowSplash,DELAY);
    }
}
