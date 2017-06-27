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
import com.zzcar.zzc.networks.responses.RefundOrderResponse;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：交易收入和交易付款
 * 作者：黄如辉  时间 2017/5/20.
 */

@EFragment(R.layout.fragment_business_detail)
public class BusinessDetailFragment extends BaseFragment{

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
    /*应收*/
    @ViewById(R.id.textView215)
    TextView textView215;
    /*手续费*/
    @ViewById(R.id.textView216)
    TextView textView216;
    /*实收*/
    @ViewById(R.id.textView217)
    TextView textView217;

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
        Subscriber subscriber = new PosetSubscriber<OrderDetailResponse>().getSubscriber(callback_refundorder);
        UserManager.getCarOrderdetail(String.valueOf(id), subscriber);
    }


    ResponseResultListener callback_refundorder = new ResponseResultListener<OrderDetailResponse>() {
        @Override
        public void success(OrderDetailResponse returnMsg) {
            txtMoney.setText("¥"+returnMsg.getAmount());
            txtSwitTimel.setText(returnMsg.getOrder_time());
            txtOrderid.setText(returnMsg.getOrder_no());
            txtRefundType.setText(returnMsg.getPay_info().getPay_name());
            txtRefundMoney.setText("¥"+returnMsg.getAmount());
            textView215.setText("");
            textView216.setText("");
            textView217.setText("");
            if (adapter != null){
                orderList.clear();
                for (OrderitemsModel model : returnMsg.getOrder_items()){
                    orderList.add(model.getOrderItemModel(model));
                }
                adapter.clear();
                adapter.addAll(orderList);
            }
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };
}
