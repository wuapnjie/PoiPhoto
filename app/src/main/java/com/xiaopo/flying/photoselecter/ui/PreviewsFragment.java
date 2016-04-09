package com.xiaopo.flying.photoselecter.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.xiaopo.flying.photoselecter.R;
import com.xiaopo.flying.photoselecter.ui.custom.SquareImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Flying SnowBean on 16-4-9.
 */
public class PreviewsFragment extends Fragment {
    private final String TAG = PreviewsFragment.class.getSimpleName();
    public static final String ARGUMENT_PATH = "path";
    @Bind(R.id.iv_previews)
    SquareImageView mIvPreviews;

    private String mPath;

    public static PreviewsFragment createInstance(String path) {
        PreviewsFragment fragment = new PreviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_PATH, path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPath = bundle.getString(ARGUMENT_PATH);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_previews, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.with(getContext())
                .load("file:///" + mPath)
                .fit()
                .centerInside()
                .into(mIvPreviews);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
