package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zzcar.zzc.R;
import com.zzcar.zzc.networks.requests.SearchRequest;

/**
    * 1最新(默认)、2销量高、3价格高到低、4价格低到高、5按车龄最小，6按里程最少
    * 创建作者： 黄如辉
    * 创建时间： 2017/4/24 14:10
   **/

public class PaixuPopwindow {

    private PaixuListener paixuListener;
    private String popCode;
     private  ItemTextImgView itemTextImgView1,itemTextImgView2,itemTextImgView3,itemTextImgView4,itemTextImgView5,itemTextImgView6;

    public PopupWindow showPopupWindow(Context mContext, Drawable bgdrawable,
                                       int bgcolor, PaixuListener paixuListener, SearchRequest searchRequest) {
        this.popCode = searchRequest.getSort();
        this.paixuListener = paixuListener;
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
        itemTextImgView1 = (ItemTextImgView) contentView.findViewById(R.id.itemTextImgView1);
        itemTextImgView2 = (ItemTextImgView) contentView.findViewById(R.id.itemTextImgView2);
        itemTextImgView3 = (ItemTextImgView) contentView.findViewById(R.id.itemTextImgView3);
        itemTextImgView4 = (ItemTextImgView) contentView.findViewById(R.id.itemTextImgView4);
        itemTextImgView5 = (ItemTextImgView) contentView.findViewById(R.id.itemTextImgView5);
        itemTextImgView6 = (ItemTextImgView) contentView.findViewById(R.id.itemTextImgView6);

        itemTextImgView1.setTextValue("最新");
        itemTextImgView2.setTextValue("销量高");
        itemTextImgView3.setTextValue("价格高到低");
        itemTextImgView4.setTextValue("价格低到高");
        itemTextImgView5.setTextValue("按车龄最小");
        itemTextImgView6.setTextValue("按里程最少");

        showImage();


        /*默认排序*/
        itemTextImgView1.setOnSelectListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paixuListener.defaultValue("最新", "1");
                popCode = "1";
                showImage();
            }
        });

        /*销售量发布*/
        itemTextImgView2.setOnSelectListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paixuListener.defaultValue("销量高", "2");
                popCode = "2";
                showImage();
            }
        });

        /*价格从高到低*/
        itemTextImgView3.setOnSelectListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paixuListener.defaultValue("价格高到低", "3");
                popCode = "3";
                showImage();
            }
        });

        /*价格从低到高*/
        itemTextImgView4.setOnSelectListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paixuListener.defaultValue("价格低到高", "4");
                popCode = "4";
                showImage();
            }
        });

        /*按车龄最小*/
        itemTextImgView5.setOnSelectListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paixuListener.defaultValue("按车龄最小", "5");
                popCode = "5";
                showImage();
            }
        });

        /*按里程最少*/
        itemTextImgView6.setOnSelectListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paixuListener.defaultValue("按里程最少", "6");
                popCode = "6";
                showImage();
            }
        });



        return contentView;
    }

    public interface PaixuListener{
        public void defaultValue(String title, String value);
    }

     private void showImage(){
         if (popCode.equals("1")){
             itemTextImgView1.setSelect();
             itemTextImgView2.setUnselect();
             itemTextImgView3.setUnselect();
             itemTextImgView4.setUnselect();
             itemTextImgView5.setUnselect();
             itemTextImgView6.setUnselect();
         }else if(popCode.equals("2")){
             itemTextImgView1.setUnselect();
             itemTextImgView2.setSelect();
             itemTextImgView3.setUnselect();
             itemTextImgView4.setUnselect();
             itemTextImgView5.setUnselect();
             itemTextImgView6.setUnselect();
         }else if(popCode.equals("3")){
             itemTextImgView1.setUnselect();
             itemTextImgView2.setUnselect();
             itemTextImgView3.setSelect();
             itemTextImgView4.setUnselect();
             itemTextImgView5.setUnselect();
             itemTextImgView6.setUnselect();
         }else if(popCode.equals("4")){
             itemTextImgView1.setUnselect();
             itemTextImgView2.setUnselect();
             itemTextImgView3.setUnselect();
             itemTextImgView4.setSelect();
             itemTextImgView5.setUnselect();
             itemTextImgView6.setUnselect();
         }else if(popCode.equals("5")){
             itemTextImgView1.setUnselect();
             itemTextImgView2.setUnselect();
             itemTextImgView3.setUnselect();
             itemTextImgView4.setUnselect();
             itemTextImgView5.setSelect();
             itemTextImgView6.setUnselect();
         }else if(popCode.equals("6")){
             itemTextImgView1.setUnselect();
             itemTextImgView2.setUnselect();
             itemTextImgView3.setUnselect();
             itemTextImgView4.setUnselect();
             itemTextImgView5.setUnselect();
             itemTextImgView6.setSelect();
         }
     }

}
