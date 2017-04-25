package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.ChannelAdapter;
import com.zzcar.zzc.networks.responses.CarChanelResponse;

import java.util.ArrayList;
import java.util.List;

/**
   * 1最新(默认)、2销量高、3价格高到低、4价格低到高、5按车龄最小，6按里程最少
   * 创建作者： 黄如辉
   * 创建时间： 2017/4/24 14:10
  **/

public class ChannelPopwindow {

   private ChannelListener channelListener;
    private ChannelAdapter adapter;
    private List<CarChanelResponse> mChannelList = new ArrayList<>();

    public PopupWindow showPopupWindow(Context mContext, Drawable bgdrawable, int bgcolor, List<CarChanelResponse> mList, ChannelListener channelListener) {
       this.channelListener = channelListener;
        mChannelList.addAll(mList);
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
               R.layout.layout_recycleview, null);


       RecyclerView mRecyclerView = (RecyclerView) contentView.findViewById(R.id.mRecyclerView);
       mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
       mRecyclerView.setAdapter(adapter = new ChannelAdapter(mContext, mChannelList, itemClickListener));
       return contentView;
   }

   public interface ChannelListener{
       public void selectItem(String title, String value, int position);
   }

    public void setAdapter(List<CarChanelResponse> mList, int position){
        mChannelList.addAll(mList);
        adapter.setData(mChannelList, position);
    }

    public void setAdapter( int position){
        adapter.setData(position);
    }

    ChannelAdapter.ItemClickListener itemClickListener = new ChannelAdapter.ItemClickListener() {
        @Override
        public void setOnItemClickListener(String text, String value, int position) {
            channelListener.selectItem(text, value, position);
        }
    };
}
