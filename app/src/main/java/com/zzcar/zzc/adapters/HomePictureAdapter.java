package com.zzcar.zzc.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.networks.responses.HomeAdverResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/6.
 */

public class HomePictureAdapter extends StaticPagerAdapter {

    private Context mContext;
    private int windowWidth = 0;
    private List<HomeAdverResponse> mList = new ArrayList<>();
    private AdapterListener adapterListener;

    public HomePictureAdapter(Context context, int widthPixels, List<HomeAdverResponse> bundleList, AdapterListener adapterListener) {
        this.mContext = context;
        this.windowWidth = widthPixels;
        this.adapterListener = adapterListener;
        mList.clear();
        mList.addAll(bundleList);
    }

    public void setPicture( List<HomeAdverResponse> bundleList){
        mList.clear();
        mList.addAll(bundleList);
        notifyDataSetChanged();
    }


    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView view = new ImageView(container.getContext());
        String str = Tool.getPicUrl(mContext, mList.get(position).getImage_path(), windowWidth, 160);
        ImageLoader.loadImage(Tool.getPicUrl(mContext, mList.get(position).getImage_path(), windowWidth, 160), view);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setScaleType(ImageView.ScaleType.CENTER);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemListener(mList.get(position), position);
            }
        });
        return view;
    }


    @Override
    public int getCount() {
        return mList.size();
    }
}