package com.zzcar.zzc.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.activities.ShowPhotoAcitivity;
import com.zzcar.zzc.activities.ShowPhotoAcitivity_;
import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.ItemImageView;
import com.zzcar.zzc.views.widget.ItemImageView_;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/9.
 */

public class ImageStrandAdapter extends BaseRecyclerAdapter<String, ItemImageView> {

    private Context mContext;
    private List<String> imagepath;

    public ImageStrandAdapter(Context mContext, List<String> image_path){
        this.mContext = mContext;
        this.imagepath = image_path;
    }

    @Override
    protected ItemImageView onCreateItemView(ViewGroup parent, int viewType) {
        return ItemImageView_.build(parent.getContext());
    }

    @Override
    protected void onBindView(ItemImageView itemView, String picture, final int position) {
        itemView.bind(picture, 100, 73);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShowPhotoAcitivity_.class);
                intent.putExtra("imagepathList", (Serializable) imagepath);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }
}
