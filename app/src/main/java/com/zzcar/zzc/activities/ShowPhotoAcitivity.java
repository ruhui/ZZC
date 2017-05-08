package com.zzcar.zzc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.utils.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import uk.co.senab.photoview.PhotoView;

@EActivity(R.layout.activity_show_photo_acitivity)
public class ShowPhotoAcitivity extends BaseActivity {

    @ViewById(R.id.mPhotoView)
    PhotoView mPhotoView;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        String imagepath = getIntent().getStringExtra("imagePath");
        ImageLoader.loadImage(imagepath, mPhotoView);
    }
}
