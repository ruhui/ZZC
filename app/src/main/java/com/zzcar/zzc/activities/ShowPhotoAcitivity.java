package com.zzcar.zzc.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.jude.rollviewpager.hintview.TextHintView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.PhotoViewAdapter;
import com.zzcar.zzc.adapters.PictureAdapter;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

@EActivity(R.layout.activity_show_photo_acitivity)
public class ShowPhotoAcitivity extends BaseActivity {

    @ViewById(R.id.mRollPagerView)
    RollPagerView mRollViewPager;
    private PhotoViewAdapter adapter;


    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView() {
        List<String> imagepathList = (List<String>) getIntent().getSerializableExtra("imagepathList");
        int position = getIntent().getIntExtra("position",0);
        initRollView(imagepathList, position);
    }

    private void initRollView(List<String> picList, int position) {
        //获取屏幕宽度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(200000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        adapter = new PhotoViewAdapter(ShowPhotoAcitivity.this, dm.widthPixels, picList);
        //设置适配器
        mRollViewPager.setAdapter(adapter);
        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        mRollViewPager.setHintView(new TextHintView(getActivity()));
    }
}
