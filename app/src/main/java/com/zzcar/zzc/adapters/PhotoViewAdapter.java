package com.zzcar.zzc.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/6.
 */

public class PhotoViewAdapter extends StaticPagerAdapter {

    private Context mContext;
    private int windowWidth = 0;
    private List<String> mList = new ArrayList<>();

    public PhotoViewAdapter(Context context, int widthPixels, List<String> bundleList) {
        this.mContext = context;
        this.windowWidth = widthPixels;
        mList.clear();
        mList.addAll(bundleList);
    }

    public void setPicture( List<String> bundleList){
        mList.clear();
        mList.addAll(bundleList);
        notifyDataSetChanged();
    }


    @Override
    public View getView(ViewGroup container, int position) {
        PhotoView view = new PhotoView(container.getContext());
        ImageLoader.loadImage(Tool.getPicUrl(mContext, mList.get(position), windowWidth, 331), view);
//        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setScaleType(ImageView.ScaleType.CENTER);
        return view;
    }


    @Override
    public int getCount() {
        return mList.size();
    }
}