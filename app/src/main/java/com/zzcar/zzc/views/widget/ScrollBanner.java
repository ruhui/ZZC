package com.zzcar.zzc.views.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.HomeLiveMode;
import com.zzcar.zzc.utils.LogUtil;

import java.util.List;

/**
 * 创建时间： 2017/6/7.
 * 作者：黄如辉
 * 功能描述：广告条自动滚动控件
 *
 */

public class ScrollBanner extends LinearLayout {

    private TextView mBannerTV1;
    private TextView mBannerTV2;
    private Handler handler;
    private boolean isShow;
    private int startY1, endY1, startY2, endY2;
    private Runnable runnable;
    private  List<HomeLiveMode> list;
    private int position = 0;
    private int offsetY = 100;
    private HomeLiveMode homeLiveMode;


    public ScrollBanner(Context context) {
        this(context, null);
    }

    public ScrollBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final View view = LayoutInflater.from(context).inflate(R.layout.view_scroll_banner, this);
        mBannerTV1 = (TextView) view.findViewById(R.id.tv_banner1);
        mBannerTV2 = (TextView) view.findViewById(R.id.tv_banner2);
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                isShow = !isShow;

                if (position == list.size())
                    position = 0;

                if (isShow) {
                    mBannerTV1.setText(list.get(position++).getContent());
                } else {
                    mBannerTV2.setText(list.get(position++).getContent());
                }

                startY1 = isShow ? 0 : offsetY;
                endY1 = isShow ? -offsetY : 0;


                ObjectAnimator.ofFloat(mBannerTV1, "translationY", startY1, endY1).setDuration(300).start();

                startY2 = isShow ? offsetY : 0;
                endY2 = isShow ? 0 : -offsetY;
                ObjectAnimator.ofFloat(mBannerTV2, "translationY", startY2, endY2).setDuration(300).start();

                handler.postDelayed(runnable, 3000);
            }
        };



    }


    public  List<HomeLiveMode> getList() {
        return list;
    }

    public void setList( List<HomeLiveMode> list) {
        this.list = list;
    }

    public void startScroll() {
        handler.post(runnable);
    }

    public void stopScroll() {
        handler.removeCallbacks(runnable);
    }

}
