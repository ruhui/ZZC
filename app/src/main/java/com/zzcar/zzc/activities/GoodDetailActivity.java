package com.zzcar.zzc.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.views.widget.MyscrollerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 商品详情
 */

@EActivity(R.layout.fragment_commoditydetail)
public class GoodDetailActivity extends BaseActivity implements MyscrollerView.ScrollerListeners {

    @ViewById(R.id.mRollPagerView)
    RollPagerView mRollViewPager;

    @ViewById(R.id.scrollView)
    MyscrollerView myScrollView;

    @AfterViews
    void initView(){
        String productId = getIntent().getStringExtra("productId");
        myScrollView.MyscrollerView(this);
        initRollView();

    }

    private void initRollView() {
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(2000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
//        mRollViewPager.setAdapter(new TestNormalAdapter(bundle));
        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW, Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    public void scroller(int scrollY) {

    }

    public void setAlpha(float alpha){
//            mToolbar.setAlpha(alpha);
    }
}
