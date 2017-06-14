package com.zzcar.zzc.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.CarfromItemAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.OrderitemsModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.OrderDetailResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/14 14:13
 **/
@EFragment(R.layout.fragment_orderdetiao)
public class OrderDetailFragment extends BaseFragment {

    private CarfromItemAdapter adapter;

    @ViewById(R.id.textView182)
    TextView txtOrderNo;
    @ViewById(R.id.textView185)
    TextView txtOrderLiushuiNo;
    @ViewById(R.id.textView186)
    TextView txtPayType;
    @ViewById(R.id.textView183)
    TextView txtPayStatu;
    @ViewById(R.id.textView187)
    TextView txtCreattime;
    @ViewById(R.id.textView188)
    TextView txtPhone;
    @ViewById(R.id.textView170)
    TextView txtRevicer;
    @ViewById(R.id.textView171)
    TextView txtAddress;
    @ViewById(R.id.textView192)
    TextView txtPay;
    @ViewById(R.id.textView193)
    TextView txtPayamount;
    @ViewById(R.id.textView190)
    TextView txtAmount;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;

    private String productID = "", type = "";//type 0为买家  1为卖家

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        productID = getArguments().getString("id");
        type = getArguments().getString("type");
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("订单详情");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        adapter = new CarfromItemAdapter();
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter);

        getOrderDetail();

        /*去支付*/
        txtPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /*获取详细订单数据*/
    private void getOrderDetail() {
        if (TextUtils.isEmpty(productID)){
            ToastUtil.showToast("数据异常");
            return;
        }
        showProgress();
        Subscriber subscriber = new PosetSubscriber<OrderDetailResponse>().getSubscriber(callback_orderdetail);
        UserManager.getCarOrderdetail(productID, subscriber);
    }

    ResponseResultListener callback_orderdetail = new ResponseResultListener<OrderDetailResponse>() {
        @Override
        public void success(OrderDetailResponse returnMsg) {
            closeProgress();
            adapter.addAll(returnMsg.getOrder_items());
            txtOrderNo.setText(returnMsg.getOrder_no());
            txtOrderLiushuiNo.setText(returnMsg.getPay_info().getTransaction_no());
            txtPayType.setText(returnMsg.getPay_info().getPay_name());
            if (type.equals("0")){
                //买家
                String statu = "";
                switch (returnMsg.getStatus()){
                    case 1:
                        //待付款
                        statu = "待付款";
                        break;
                    case 2:
                        //待卖家支付
                        statu = "待卖家支付";
                        break;
                    case 3:
                        //已完成
                        statu = "已完成";
                        break;
                    case 4:
                        //取消
                        statu = "订单已取消";
                        break;
                    case 5:
                        //待确认
                        statu = "待确认";
                        break;
                }
                txtPayStatu.setText(statu);
            }else{
                //卖家
                String statu = "";
                switch (returnMsg.getStatus()){
                    case 1:
                        //待买家付款
                        statu = "待买家付款";
                        break;
                    case 2:
                        //待支付
                        statu = "待支付";
                        break;
                    case 3:
                        //已完成
                        statu = "已完成";
                        break;
                    case 4:
                        //取消
                        statu = "订单已取消";
                        break;
                    case 5:
                        //待确认
                        statu = "待确认";
                        break;
                }
                txtPayStatu.setText(statu);
            }

            txtCreattime.setText(returnMsg.getOrder_time());
            txtPayamount.setText("合计  ¥"+returnMsg.getAmount()+"元");
            txtAmount.setText(returnMsg.getAmount()+"元");
            txtPhone.setText(returnMsg.getShipping().getPhone());
            txtRevicer.setText(returnMsg.getShipping().getShip_to());
            txtAddress.setText(returnMsg.getShipping().getAddress());
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
