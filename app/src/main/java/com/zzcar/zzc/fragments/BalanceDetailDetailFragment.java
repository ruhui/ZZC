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
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.RefundOrderResponse;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：列表明细的明细
 * 作者：黄如辉  时间 2017/5/20.
 */

@EFragment(R.layout.fragment_balance_detail)
public class BalanceDetailDetailFragment extends BaseFragment{

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
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    private int id;
    private String titleBar = "";
    private RefundOrderAdapter adapter;
    private List<OrderItemModle> orderList = new ArrayList<>();

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        id = getArguments().getInt("id");
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
        adapter = new RefundOrderAdapter();
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter);
        adapter.addAll(orderList);
    }

    void getRefundOrder(){
        Subscriber subscriber = new PosetSubscriber<RefundOrderResponse>().getSubscriber(callback_refundorder);
        UserManager.getRefundorder(id, subscriber);
    }


    ResponseResultListener callback_refundorder = new ResponseResultListener<RefundOrderResponse>() {
        @Override
        public void success(RefundOrderResponse returnMsg) {
            txtMoney.setText(returnMsg.getAmount());
            txtSwitTimel.setText(returnMsg.getApply_time());
            txtOrderid.setText(returnMsg.getOrder_no());
            txtRefundType.setText(returnMsg.getPay_name());
            txtRefundMoney.setText("¥"+returnMsg.getAmount());
            if (adapter != null){
                orderList.clear();
                orderList.addAll(returnMsg.getOrder_items());
                adapter.clear();
                adapter.addAll(orderList);
            }
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };
}
