package com.xiaopo.flying.photoselecter.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.xiaopo.flying.photoselecter.DateUtil;
import com.xiaopo.flying.photoselecter.Define;
import com.xiaopo.flying.photoselecter.datatype.Photo;
import com.xiaopo.flying.photoselecter.ui.adapter.PhotoAdapter;
import com.xiaopo.flying.photoselecter.PhotoManager;
import com.xiaopo.flying.photoselecter.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoFragment extends Fragment {

    private static final String TAG = PhotoFragment.class.getSimpleName();
    @Bind(R.id.photo_list)
    RecyclerView mPhotoList;

    private PhotoAdapter mAdapter;
    private PhotoManager mPhotoManager;

    public static PhotoFragment newInstance(String bucketId) {
        Bundle bundle = new Bundle();
        bundle.putString("bucketId", bucketId);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(bundle);

        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPhotoManager = new PhotoManager(getContext());
    }


    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("请选择相片");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_pick, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_done:
                Intent intent = new Intent();
                intent.putStringArrayListExtra(Define.PHTHS, mAdapter.getSelectedPhotoPaths());
                intent.putParcelableArrayListExtra(Define.PHOTOS,mAdapter.getSelectedPhotos());
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mPhotoList.setLayoutManager(new GridLayoutManager(getContext(), 3));

        mAdapter = new PhotoAdapter();
        mPhotoList.setHasFixedSize(true);


        mPhotoList.setAdapter(mAdapter);


        String buckedId = getArguments().getString("bucketId");
        new PhotoTask().execute(buckedId);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void onPhotoSelected() {
        List<Photo> photos = mAdapter.getSelectedPhotos();
        for (Photo photo : photos) {
            Log.i(TAG, "onPhotoSelected: path->" + photo.getPath() + ",dateAdded->" + DateUtil.formatDate(photo.getDataAdded() * 1000) + ",dateModified->" + DateUtil.formatDate(photo.getDataModified() * 1000));
        }
    }

    private void refreshPhotoList(List<Photo> photos) {
        mAdapter.refreshData(photos);
    }

    private class PhotoTask extends AsyncTask<String, Integer, List<Photo>> {

        @Override
        protected List<Photo> doInBackground(String... params) {
            return mPhotoManager.getPhoto(params[0]);
        }

        @Override
        protected void onPostExecute(List<Photo> photos) {
            super.onPostExecute(photos);
            refreshPhotoList(photos);

        }
    }

}
