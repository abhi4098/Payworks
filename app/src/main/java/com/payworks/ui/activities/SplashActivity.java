package com.payworks.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.payworks.R;
import com.payworks.utils.PrefUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    Context mContext;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
        mContext = SplashActivity.this;
        calltoSplash();
    }


    public void calltoSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckLogin();
                finish();
            }
        }, SPLASH_TIME_OUT);
    }



    private void CheckLogin() {
        Boolean isLoggedIn = PrefUtils.getUserLoggedIn(this);
        if (isLoggedIn) {
            Intent i = new Intent(SplashActivity.this, NavigationalActivity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
    }


