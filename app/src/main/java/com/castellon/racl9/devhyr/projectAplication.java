package com.castellon.racl9.devhyr;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tumblr.remember.Remember;

public class projectAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Remember.init(getApplicationContext(),"com.example.racl9.devhyr");
    }
}
