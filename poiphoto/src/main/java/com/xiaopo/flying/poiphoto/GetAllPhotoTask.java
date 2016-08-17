package com.xiaopo.flying.poiphoto;

import android.os.AsyncTask;

import com.xiaopo.flying.poiphoto.datatype.Photo;

import java.util.List;

/**
 * Created by snowbean on 16-8-17.
 */
public class GetAllPhotoTask extends AsyncTask<PhotoManager, Integer, List<Photo>> {
    private static final String TAG = "GetAllPhotoTask";

    @Override
    protected List<Photo> doInBackground(PhotoManager... params) {
        return params[0].getAllPhoto();
    }
}
