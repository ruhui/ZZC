package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.CarSeriesResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.utils.Utils;

import java.util.List;

import rx.Subscriber;

/**
 * 车系
 * Created by asus-pc on 2017/4/30.
 */

public class CarSeriesPopwindow {
    private CarSeriListener mListener;
    public PopupWindow showPopupWindow(Context mContext, Drawable bgdrawable, int bgcolor, CarSeriListener mListener) {
        this.mListener = mListener;
        View contentView = initView(mContext);

        int width = Utils.dip2px(mContext, mContext.getResources().getDimension(R.dimen.popwidth));

        PopupWindow popupWindow = new PopupWindow(contentView, width,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        // getResources().getDrawable(
        // R.drawable.select_main_item)
        // getActivity().getResources().getColor(R.color.mdtp_transparent_black)
        popupWindow.setBackgroundDrawable(bgdrawable);
        ColorDrawable dw = new ColorDrawable(bgcolor);
        popupWindow.setBackgroundDrawable(dw);
        // 设置好参数之后再show
//        popupWindow.showas(view);
        return popupWindow;
    }

    private View initView(Context mContext) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.layout_popwindiw_carseries, null);

        ListView mListView = (ListView) contentView.findViewById(R.id.mListView);

        View vHeadview = LayoutInflater.from(mContext).inflate(R.layout.layout_text, null);
        TextView ignoreCarseries = (TextView) vHeadview.findViewById(R.id.textView6);
        ignoreCarseries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mListView.addHeaderView(vHeadview);

        return contentView;
    }

    public void getDate(int brandid){
        getCarSeries(brandid);
    }


    /**
     * 获取车系
     */
    private void getCarSeries(int brandid) {
        Subscriber subscriber = new PosetSubscriber<List<CarChanelResponse>>().getSubscriber(callback_carseries);
        UserManager.getCarseries(brandid, subscriber);
    }

    ResponseResultListener callback_carseries = new ResponseResultListener<List<CarSeriesResponse>>() {
        @Override
        public void success(List<CarSeriesResponse> returnMsg) {
            LogUtil.E("success", "success");
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };

    public interface CarSeriListener {
        public void onClickItem();
    }
}

