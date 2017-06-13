package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.zzcar.zzc.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Administrator on 2017/3/17.
 */

@EViewGroup(R.layout.view_orderpay)
public class PayOrderView extends LinearLayout {

    @ViewById(R.id.checkBoxWechat)
    CheckBox checkBoxWechat;
    @ViewById(R.id.checkBoxZhifu)
    CheckBox checkBoxZhifu;
    @ViewById(R.id.relaWeichat)
    RelativeLayout relaWeichat;
    @ViewById(R.id.relaZhifubao)
    RelativeLayout relaZhifubao;

    private int SELECTPOSITION = 0;

    public PayOrderView(Context context) {
        super(context);
    }

    public PayOrderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    void initView(){
        checkBoxWechat.setChecked(true);
        checkBoxZhifu.setChecked(false);
    }

    public void selectWechatListener(){
        SELECTPOSITION = 0;
        checkBoxWechat.setChecked(true);
        checkBoxZhifu.setChecked(false);
    }

    public void selectZhifubaoListener(){
        SELECTPOSITION = 1;
        checkBoxWechat.setChecked(false);
        checkBoxZhifu.setChecked(true);
    }

    public void setSelectOnWechatListener(OnClickListener listener){
        checkBoxWechat.setOnClickListener(listener);
    }

    public void setSelectOnZhifuListener(OnClickListener listener){
        checkBoxZhifu.setOnClickListener(listener);
    }

    public boolean hasChecked(){
        if (checkBoxWechat.isChecked()){
            return true;
        }else if (checkBoxZhifu.isChecked()){
            return true;
        }else{
            return false;
        }
    }

    public int getSlect(){
        return SELECTPOSITION;
    }

    /*选择的类型*/
    public String getType(){
        String pay_code = "";
        int selectposition = getSlect();
        switch (selectposition){
            case 0:
                //微信支付
                pay_code = "weixin_sdk";
                break;
            case 1:
                //支付宝
                pay_code = "alipay_sdk";
                break;
        }
        return pay_code;
    }
}
