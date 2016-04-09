package com.xiaopo.flying.photoselecter;

import android.app.Application;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

/**
 * Created by Flying SnowBean on 16-4-4.
 */
public class App extends Application {
    private final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        long maxMem = Runtime.getRuntime().maxMemory();
        new Picasso.Builder(this).memoryCache(new LruCache((int) (maxMem / 8))).build();
        Picasso.with(this).setIndicatorsEnabled(true);
    }


}
