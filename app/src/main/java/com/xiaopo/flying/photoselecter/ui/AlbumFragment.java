package com.xiaopo.flying.photoselecter.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaopo.flying.photoselecter.datatype.Album;
import com.xiaopo.flying.photoselecter.ui.adapter.AlbumAdapter;
import com.xiaopo.flying.photoselecter.PhotoManager;
import com.xiaopo.flying.photoselecter.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlbumFragment extends Fragment {

    @Bind(R.id.album_list)
    RecyclerView mAlbumList;

    private AlbumAdapter mAlbumAdapter;
    private PhotoManager mPhotoManager;


    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("请选择一个相册");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        init();

        new AlbumTask().execute();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void init() {
        mPhotoManager = new PhotoManager(getContext());

        mAlbumList.setLayoutManager(new LinearLayoutManager(getContext()));

        mAlbumAdapter = new AlbumAdapter();
        mAlbumAdapter.setOnItemClickListener(new AlbumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, PhotoFragment.newInstance(mAlbumAdapter.getBuckedId(position)))
                        .commit();
            }
        });


        mAlbumList.setAdapter(mAlbumAdapter);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        refreshAlbumList();

    }

    private void refreshAlbumList() {
        mAlbumAdapter.refreshtData(mPhotoManager.getAlbum());
    }


    private class AlbumTask extends AsyncTask<Void, Integer, List<Album>> {

        @Override
        protected List<Album> doInBackground(Void... params) {
            return mPhotoManager.getAlbum();
        }

        @Override
        protected void onPostExecute(List<Album> alba) {
            super.onPostExecute(alba);
            refreshAlbumList();
        }
    }

}
