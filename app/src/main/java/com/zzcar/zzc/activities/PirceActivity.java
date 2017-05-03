package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.ChannelAdapter;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

@EActivity(R.layout.activity_mileage)
public class PirceActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.editText)
    EditText startPrice;
    @ViewById(R.id.editText2)
    EditText endPrice;
    @ViewById(R.id.txtRight)
    TextView sureSubmit;
    @ViewById(R.id.relaClearPrice)
    RelativeLayout relaClearPrice;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private ChannelAdapter adapter;
    private List<CarChanelResponse> mPricelList = new ArrayList<>();

    @AfterViews
    void initView(){
        getPriceBetween();
        mNavbar.setMiddleTitle("价格");
        mNavbar.setMiddleTextColor(R.color.color_333333);
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter = new ChannelAdapter(getActivity(), mPricelList, itemClickListener));

        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

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
                Intent intent = new Intent();
                intent.putExtra("price_type", "");
                intent.putExtra("min_price", minprice);
                intent.putExtra("max_price", maxprice);
                intent.putExtra("max_pricedes", minprice+"万-"+maxprice+"万");
                setResult(10104, intent);
                finish();
            }
        });

       /*清空价格数据*/
        relaClearPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("price_type", "");
                intent.putExtra("min_price", "");
                intent.putExtra("max_price", "");
                intent.putExtra("max_pricedes", "不限价格");
                setResult(10104, intent);
                finish();
            }
        });
    }


    ChannelAdapter.ItemClickListener itemClickListener = new ChannelAdapter.ItemClickListener() {
        @Override
        public void setOnItemClickListener(String text, String value, int position) {
            Intent intent = new Intent();
            intent.putExtra("price_type", value);
            intent.putExtra("min_price", "");
            intent.putExtra("max_price", "");
            intent.putExtra("max_pricedes", text);
            setResult(10104, intent);
            finish();
        }
    };

    @Override
    public void onNetChange(int netMobile) {

    }


    /**
     * 获取价格
     */
    private void getPriceBetween() {
        Subscriber subscriber = new PosetSubscriber<List<CarChanelResponse>>().getSubscriber(callback_pricebetween);
        UserManager.getPriceBwtween(subscriber);
    }

    /*价格区间回调*/
    ResponseResultListener callback_pricebetween = new ResponseResultListener<List<CarChanelResponse>>() {
        @Override
        public void success(List<CarChanelResponse> returnMsg) {
            LogUtil.E("success","success");
            mPricelList.clear();
            mPricelList.addAll(returnMsg);
            adapter.setData(mPricelList);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };
}
