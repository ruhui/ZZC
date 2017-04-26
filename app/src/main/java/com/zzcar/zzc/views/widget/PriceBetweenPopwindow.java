package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.ChannelAdapter;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;

import java.util.ArrayList;
import java.util.List;

/**
   * 1最新(默认)、2销量高、3价格高到低、4价格低到高、5按车龄最小，6按里程最少
   * 创建作者： 黄如辉
   * 创建时间： 2017/4/24 14:10
  **/

public class PriceBetweenPopwindow {

   private PriceBetweenListener channelListener;
    private ChannelAdapter adapter;
    private List<CarChanelResponse> mChannelList = new ArrayList<>();

    public PopupWindow showPopupWindow(Context mContext, Drawable bgdrawable, int bgcolor, List<CarChanelResponse> mList, PriceBetweenListener channelListener) {
       this.channelListener = channelListener;
        mChannelList.addAll(mList);
       View contentView = initView(mContext);
       PopupWindow popupWindow;
       popupWindow = new PopupWindow(contentView,
               LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
       popupWindow.setTouchable(true);
       popupWindow.setFocusable(true);
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
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return popupWindow;
   }


   private View initView(final Context mContext) {
       // 一个自定义的布局，作为显示的内容
       View contentView = LayoutInflater.from(mContext).inflate(
               R.layout.layout_popwindow_pricebetween, null);

       final EditText startPrice = (EditText) contentView.findViewById(R.id.editText);
       final EditText endPrice = (EditText) contentView.findViewById(R.id.editText2);
       RelativeLayout relaClearPrice = (RelativeLayout) contentView.findViewById(R.id.relaClearPrice);
       TextView sureSubmit = (TextView) contentView.findViewById(R.id.txtRight);
       RecyclerView mRecyclerView = (RecyclerView) contentView.findViewById(R.id.mRecyclerView);
       mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
       mRecyclerView.setAdapter(adapter = new ChannelAdapter(mContext, mChannelList, itemClickListener));

       /*自定义确认*/
       sureSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String minprice = startPrice.getText().toString();
               String maxprice = endPrice.getText().toString();
               if (TextUtils.isEmpty(minprice)){
                   ToastUtil.showToast("请输入金额上限");
                   return;
               }
               if (TextUtils.isEmpty(maxprice)){
                   ToastUtil.showToast("请输入金额下限");
                   return;
               }
               if (Double.valueOf(minprice) > Double.valueOf(maxprice)){
                   ToastUtil.showToast("请输入正确的价格区间");
                   return;
               }
               channelListener.sureSelfPrice("自定义", minprice, maxprice);
           }
       });

       /*清空价格数据*/
       relaClearPrice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               channelListener.selectItem("价格", "" , 0);
               channelListener.sureSelfPrice("价格", "", "");
           }
       });

       return contentView;
   }

   public interface PriceBetweenListener{
       /*单击出发*/
       public void selectItem(String title, String value, int position);
       /*自定义价格区间*/
       public void sureSelfPrice(String title, String startprice, String endprice);
   }

    public void setAdapter(List<CarChanelResponse> mList, SearchRequest searchRequest){
        mChannelList.addAll(mList);
        adapter.setData(mChannelList, searchRequest);
    }

    public void setAdapter(SearchRequest searchRequest){
        adapter.setData(searchRequest);
    }

    ChannelAdapter.ItemClickListener itemClickListener = new ChannelAdapter.ItemClickListener() {
        @Override
        public void setOnItemClickListener(String text, String value, int position) {
            channelListener.selectItem(text, value, position);
        }
    };
}
