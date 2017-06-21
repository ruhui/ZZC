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
public class MileageActivity extends BaseActivity {

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
    @ViewById(R.id.txtNolimit)
    TextView txtNolimit;

    private ChannelAdapter adapter;
    private List<CarChanelResponse> mChannelList = new ArrayList<>();

    @AfterViews
    void initView(){
        getMileage();

        txtNolimit.setText("不限里程");
        mNavbar.setMiddleTitle("里程");
        mNavbar.setMiddleTextColor(R.color.color_333333);
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter = new ChannelAdapter(getActivity(), mChannelList, itemClickListener));

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
                    ToastUtil.showToast("请输入里程上限");
                    return;
                }
                if (TextUtils.isEmpty(maxprice)){
                    ToastUtil.showToast("请输入里程下限");
                    return;
                }
                if (Double.valueOf(minprice) > Double.valueOf(maxprice)){
                    ToastUtil.showToast("请输入正确的里程区间");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("mileage", "");
                intent.putExtra("min_mileage", minprice);
                intent.putExtra("max_mileage", maxprice);
                intent.putExtra("mileagedes", minprice+"万公里-"+maxprice+"万公里");
                setResult(10103, intent);
                finish();
            }
        });

       /*清空价格数据*/
        relaClearPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("mileage", "");
                intent.putExtra("min_mileage", "");
                intent.putExtra("max_mileage", "");
                intent.putExtra("mileagedes", "不限里程");
                setResult(10103, intent);
                finish();
            }
        });
    }


    ChannelAdapter.ItemClickListener itemClickListener = new ChannelAdapter.ItemClickListener() {
        @Override
        public void setOnItemClickListener(String text, String value, int position) {
            Intent intent = new Intent();
            intent.putExtra("mileage", value);
            intent.putExtra("min_mileage", "");
            intent.putExtra("max_mileage", "");
            intent.putExtra("mileagedes", text);
            setResult(10103, intent);
            finish();
        }
    };

    @Override
    public void onNetChange(int netMobile) {

    }


    /**
     * 获取里程
     */
    private void getMileage() {
        Subscriber subscriber = new PosetSubscriber<List<CarChanelResponse>>().getSubscriber(callback_carchannel);
        UserManager.getMileage(subscriber);
    }

    /*渠道回调*/
    ResponseResultListener callback_carchannel = new ResponseResultListener<List<CarChanelResponse>>() {
        @Override
        public void success(List<CarChanelResponse> returnMsg) {
            LogUtil.E("success","success");
            mChannelList.clear();
            mChannelList.addAll(returnMsg);
            adapter.setData(mChannelList);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };
}
