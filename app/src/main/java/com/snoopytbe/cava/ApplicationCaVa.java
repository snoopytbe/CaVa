package com.snoopytbe.cava;

import android.app.Application;

import timber.log.Timber;

public class ApplicationCaVa extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
