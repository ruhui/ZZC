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

import java.net.URL;

import uk.co.senab.photoview.PhotoView;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/9.
 */

public class ImageStrandAdapter extends BaseRecyclerAdapter<String, ItemImageView> {

    private Context mContext;

    public ImageStrandAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    protected ItemImageView onCreateItemView(ViewGroup parent, int viewType) {
        return ItemImageView_.build(parent.getContext());
    }

    @Override
    protected void onBindView(ItemImageView itemView, String picture, int position) {
        itemView.bind(picture, 100, 73);
        final String pictureurl = Tool.getPicUrl(mContext, picture, 300, 200);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShowPhotoAcitivity_.class);
                intent.putExtra("imagePath", pictureurl);
                mContext.startActivity(intent);
//                photoView.setImageURI(new uri(pictureurl));
            }
        });
    }
}
