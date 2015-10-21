package com.simpl.android.sdk.sample;

import android.app.Application;

import com.simpl.android.sdk.Simpl;

/**
 * Class extending {@link Application} for initializing Simpl SDK
 *
 * @author : Akshay Deo
 * @date : 15/10/15 : 6:14 PM
 * @email : akshay@betacraft.co
 */
public class SampleApplication extends Application {
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // For initializing the Simpl SDK
        Simpl.init(getApplicationContext(), true);
    }
}
