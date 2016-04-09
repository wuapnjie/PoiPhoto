package com.xiaopo.flying.photoselecter.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.xiaopo.flying.photoselecter.datatype.Photo;
import com.xiaopo.flying.photoselecter.R;
import com.xiaopo.flying.photoselecter.ui.custom.SquareImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Flying SnowBean on 16-4-4.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private final String TAG = PhotoAdapter.class.getSimpleName();

    private List<Photo> mData;
    private ArrayList<Photo> mSelectedPhotos;
    private Set<Integer> mSelectedPhotoPositions;

    private OnPhotoSelectedListener mOnPhotoSelectedListener;
    private OnPhotoUnSelectedListener mOnPhotoUnSelectedListener;

    public PhotoAdapter() {
        mSelectedPhotos = new ArrayList<>();
        mSelectedPhotoPositions = new HashSet<>();
    }

    public void setOnPhotoSelectedListener(OnPhotoSelectedListener onPhotoSelectedListener) {
        mOnPhotoSelectedListener = onPhotoSelectedListener;
    }

    public void setOnPhotoUnSelectedListener(OnPhotoUnSelectedListener onPhotoUnSelectedListener) {
        mOnPhotoUnSelectedListener = onPhotoUnSelectedListener;
    }

    public ArrayList<Photo> getSelectedPhotos() {
        return mSelectedPhotos;
    }

    public ArrayList<String> getSelectedPhotoPaths() {
        ArrayList<String> paths = new ArrayList<>();
        for (Photo photo : mSelectedPhotos) {
            paths.add(photo.getPath());
        }

        return paths;
    }

    public void refreshData(List<Photo> dataNew) {
        mData = dataNew;
        mSelectedPhotos.clear();
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        //解决View复用时的问题
        if (!mSelectedPhotoPositions.contains(position)&&holder.mShadow.getVisibility()==View.VISIBLE){
            holder.mShadow.setVisibility(View.GONE);
        }

        Picasso.with(holder.itemView.getContext())
                .load("file:///" + mData.get(position).getPath())
                .fit()
                .centerInside()
                .into(holder.mIvPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPhotoPositions.contains(position)) {

                    mSelectedPhotoPositions.remove(position);
                    mSelectedPhotos.remove(mData.get(position));
                    if (mOnPhotoUnSelectedListener != null) {
                        mOnPhotoUnSelectedListener.onPhotoUnSelected(mData.get(position), position);
                    }

                    holder.mShadow.setVisibility(View.GONE);

                } else {
                    mSelectedPhotoPositions.add(position);
                    mSelectedPhotos.add(mData.get(position));
                    if (mOnPhotoSelectedListener != null) {
                        mOnPhotoSelectedListener.onPhotoSelected(mData.get(position), position);
                    }
                    holder.mShadow.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_photo)
        SquareImageView mIvPhoto;
        @Bind(R.id.shadow)
        View mShadow;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnPhotoSelectedListener {
        void onPhotoSelected(Photo photo, int position);
    }

    public interface OnPhotoUnSelectedListener {
        void onPhotoUnSelected(Photo photo, int position);
    }
}
