package com.xiaopo.flying.photoselecter.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.xiaopo.flying.photoselecter.R;
import com.xiaopo.flying.photoselecter.ui.adapter.PreviewAdapter;
import com.xiaopo.flying.poiphoto.Define;
import com.xiaopo.flying.poiphoto.PhotoPicker;
import com.xiaopo.flying.poiphoto.datatype.Photo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_PHOTO_SELECTED = 100;

    Button mBtnSelected;
    @Bind(R.id.preview_pages)
    ViewPager mPreviewPages;
    @Bind(R.id.previewList)
    RecyclerView mPreviewList;

    private PreviewAdapter mAdapter;
    private List<String> mPaths;
    private List<Photo> mPhotos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mAdapter = new PreviewAdapter();

        mAdapter.setOnItemClickListener(new PreviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPreviewPages.setCurrentItem(position);
            }
        });

        mPreviewList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPreviewList.setAdapter(mAdapter);

        mPreviewPages.setAdapter(new PreviewPageAdapter(getSupportFragmentManager()));
    }

    @OnClick(R.id.btn_selected)
    public void toPick() {
        PhotoPicker.newInstance()
                .setAlbumTitle("Album")
                .setPhotoTitle("Photo")
                .setToolbarColor(Color.BLACK)
                .setToolbarTitleColor(Color.WHITE)
                .setStatusBarColor(Color.BLACK)
                .setMaxCount(6)
                .pick(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Define.DEFAULT_REQUEST_CODE) {
            if (mPaths != null) {
                mPaths.clear();
            }

            mPaths = data.getStringArrayListExtra(Define.PATHS);
            for (String path : mPaths) {
                System.out.println(path);
            }

            mPhotos = data.getParcelableArrayListExtra(Define.PHOTOS);
            mAdapter.refershData(mPhotos);

            mPreviewPages.setAdapter(new PreviewPageAdapter(getSupportFragmentManager()));
        }

    }

    private class PreviewPageAdapter extends FragmentStatePagerAdapter {

        public PreviewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PreviewsFragment.createInstance(mPaths.get(position));
        }

        @Override
        public int getCount() {
            return mPaths == null ? 0 : mPaths.size();
        }
    }
}
