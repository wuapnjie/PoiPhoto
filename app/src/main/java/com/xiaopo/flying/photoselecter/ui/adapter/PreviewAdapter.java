package com.xiaopo.flying.photoselecter.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.xiaopo.flying.photoselecter.R;
import com.xiaopo.flying.photoselecter.ui.custom.SquareImageView;
import com.xiaopo.flying.poiphoto.datatype.Photo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Flying SnowBean on 16-4-9.
 */
public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.PreviewViewHolder> {
    private final String TAG = PreviewAdapter.class.getSimpleName();

    private List<Photo> mData;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void refershData(List<Photo> dataNew) {
        this.mData = dataNew;
        notifyDataSetChanged();
    }

    @Override
    public PreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview, parent, false);
        return new PreviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PreviewViewHolder holder, final int position) {
        Photo photo = mData.get(position);
        Picasso.with(holder.itemView.getContext())
                .load("file:///" + photo.getPath())
                .fit()
                .centerInside()
                .into(holder.mIvPreviews);

        holder.mIvPreviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class PreviewViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_previews)
        SquareImageView mIvPreviews;

        public PreviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
