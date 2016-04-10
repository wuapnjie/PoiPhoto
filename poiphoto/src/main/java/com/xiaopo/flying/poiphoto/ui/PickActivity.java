package com.xiaopo.flying.poiphoto.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.xiaopo.flying.poiphoto.Configure;
import com.xiaopo.flying.poiphoto.Define;
import com.xiaopo.flying.poiphoto.R;


public class PickActivity extends AppCompatActivity {

    private Configure mConfigure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);

        Intent intent = getIntent();
        mConfigure = intent.getParcelableExtra(Define.CONFIGURE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            changeStatusBar(mConfigure.getStatusBarColor());
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new AlbumFragment()).commit();
        }

    }

    @TargetApi(21)
    private void changeStatusBar(int statusBarColor) {
        getWindow().setStatusBarColor(statusBarColor);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //TODO 需要判断
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new AlbumFragment()).commitAllowingStateLoss();

    }


    public void setConfigure(Configure configure) {
        mConfigure = configure;
    }

    public Configure getConfigure() {
        return mConfigure;
    }


}
