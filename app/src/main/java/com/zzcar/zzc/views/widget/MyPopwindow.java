package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zzcar.zzc.R;

/**
 * Created by asus-pc on 2017/4/23.
 */

public class MyPopwindow {

    public PopupWindow showPopupWindow(Context mContext, Drawable bgdrawable, int bgcolor) {
        View contentView = initView(mContext);
        PopupWindow popupWindow;
        popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
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
//            popupWindow_paixu.showAsDropDown(view);
        return popupWindow;
    }

    private View initView(Context mContext) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_paixu, null);
        RelativeLayout relaPaixu = (RelativeLayout) contentView.findViewById(R.id.relaPaixu);
        RelativeLayout relaNewfabu = (RelativeLayout) contentView.findViewById(R.id.relaNewfabu);
        RelativeLayout relaPriceUP = (RelativeLayout) contentView.findViewById(R.id.relaPriceUP);
        RelativeLayout relaPriceDown = (RelativeLayout) contentView.findViewById(R.id.relaPriceDown);
        RelativeLayout relaDriverTime = (RelativeLayout) contentView.findViewById(R.id.relaDriverTime);
        RelativeLayout relaLicheng = (RelativeLayout) contentView.findViewById(R.id.relaLicheng);
        RelativeLayout relaDown = (RelativeLayout) contentView.findViewById(R.id.relaDown);


        relaPaixu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        relaDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return contentView;
    }
}
