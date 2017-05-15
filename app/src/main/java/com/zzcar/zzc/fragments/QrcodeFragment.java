package com.zzcar.zzc.fragments;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：二维码
 * 作者：黄如辉  时间 2017/5/15.
 */

@EFragment(R.layout.fragment_qrcode)
public class QrcodeFragment extends BaseFragment{

    @ViewById(R.id.imageView19)
    ImageView qrCodeImg;
    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    String qrcode;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        qrcode = getArguments().getString("qrcode");
    }

    @AfterViews
    void initView(){
        ImageLoader.loadImage(Tool.getPicUrl(getActivity(), qrcode, 139, 139), qrCodeImg);
        mNavbar.setMiddleTitle("推荐好友");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
    }

}
