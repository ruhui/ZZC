package com.zzcar.zzc.views.widget.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.CommentListener;
import com.zzcar.zzc.views.widget.PayOrderView;

/**
 * 创建时间： 2017/6/13.
 * 作者：黄如辉
 * 功能描述：
 */

public class BuyTpyeDialog extends Dialog {


    private PayOrderView mPayOrderView;
    private AdapterListener adapterListener;

    public BuyTpyeDialog(Context context, AdapterListener adapterListener) {
        super(context,  R.style.MyDialogTheme);
        this.adapterListener = adapterListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_buytype);
        mPayOrderView = (PayOrderView) findViewById(R.id.mPayOrderView);


        mPayOrderView.setSelectOnWechatListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayOrderView.selectWechatListener();
            }
        });
        mPayOrderView.setSelectOnZhifuListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayOrderView.selectZhifubaoListener();
            }
        });

        findViewById(R.id.sureSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemListener(getType(), mPayOrderView.getSlect());
            }
        });


    }

    /*是否选择*/
    public boolean hasselect(){
        if (!mPayOrderView.hasChecked()){
            return false;
        }else{
            return true;
        }
    }

    /*选择的类型*/
    public String getType(){
        String pay_code = "";
        int selectposition = mPayOrderView.getSlect();
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
