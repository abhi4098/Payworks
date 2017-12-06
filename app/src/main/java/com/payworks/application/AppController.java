package com.payworks.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;

public class AppController extends Application {





    @Override
    public void onCreate() {
        super.onCreate();

        //CustomActivityOnCrash.install(this);
        //Fabric.with(this, new Crashlytics());
        FacebookSdk.sdkInitialize(getApplicationContext());




       // createDirectory();
    }

/*    private void createDirectory() {
       new File(Environment.getExternalStorageDirectory(), "PSTC/IMG").mkdirs();
       new File(Environment.getExternalStorageDirectory(), "PSTC/FILE").mkdirs();

    }*/


    @Override
    public void onTerminate() {
        super.onTerminate();
        /*stopService(new Intent(getApplicationContext(),ChatService.class));*/
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
