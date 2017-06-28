package com.zzcar.zzc.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.RefundOrderAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.OrderItemModle;
import com.zzcar.zzc.models.OrderitemsModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.OrderDetailResponse;
import com.zzcar.zzc.networks.responses.TransferDetailResponse;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：提现详情
 * 作者：黄如辉  时间 2017/5/20.
 */

@EFragment(R.layout.fragment_trnsfer_detail)
public class TransferDetailFragment extends BaseFragment{

    @ViewById(R.id.textView113)
    TextView txtMoney;
    /*交易时间*/
    @ViewById(R.id.textView121)
    TextView txtSwitTimel;
    @ViewById(R.id.textView119)
    TextView txtOrderid;
    @ViewById(R.id.textView117)
    TextView txtRefundType;
    @ViewById(R.id.textView116)
    TextView txtRefundMoney;
    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    /*状态*/
    @ViewById(R.id.textView215)
    TextView textView215;

    private String id;
    private String titleBar = "";

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        id = getArguments().getString("id");
        titleBar = getArguments().getString("title");

    }

    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle(titleBar);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        getRefundOrder();
    }

    void getRefundOrder(){
        showProgress();
        Subscriber subscriber = new PosetSubscriber<TransferDetailResponse>().getSubscriber(callback_refundorder);
        UserManager.getTransferDetail(String.valueOf(id), subscriber);
    }


    ResponseResultListener callback_refundorder = new ResponseResultListener<TransferDetailResponse>() {
        @Override
        public void success(TransferDetailResponse returnMsg) {
            closeProgress();
            txtMoney.setText("¥"+returnMsg.getAmount());
            txtSwitTimel.setText(returnMsg.getAccount_name());
            txtOrderid.setText(returnMsg.getOrder_no());
            txtRefundMoney.setText("¥"+returnMsg.getAmount());
            textView215.setText(returnMsg.getStatus());
            txtRefundType.setText(returnMsg.getPay_info().getPay_name());
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
