package com.example.scihalallist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    Handler mHandler;
    Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mHandler = new Handler();
        mRunnable = () -> {
            Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        };
        // its trigger runnable after 4000 millisecond.
        mHandler.postDelayed(mRunnable,4000);
    }
}