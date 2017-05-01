package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.ChannelAdapter;
import com.zzcar.zzc.adapters.EmissionAdapter;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

@EActivity(R.layout.activity_emission)
public class EmissionActivity extends BaseActivity {

    /*排放列表*/
    private List<CarChanelResponse> mChannelList = new ArrayList<>();
    private EmissionAdapter adapter;
    private List<String> emissionids = new ArrayList<>();
    private String emissionDes = "";
    private boolean imgchecked = false;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.checkBox)
    ImageView checkBox;
    @ViewById(R.id.relaItem)
    RelativeLayout relaItem;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTextColor(R.color.color_333333);
        mNavbar.setMiddleTitle("排放");
        mNavbar.setRightTxtColor(R.color.app_red);
        mNavbar.setRightTxt("确定");
        getCarChannel();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter = new EmissionAdapter(getActivity(), mChannelList, itemClickListener));
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                if (emissionids.size() == 5){
                    emissionDes = "不限排放";
                }else{
                    for (int i=0;i<emissionids.size(); i++){
                        for (int j=0;j<mChannelList.size();j++){
                            if (mChannelList.get(j).getValue().equals(emissionids.get(i))){
                                emissionDes += mChannelList.get(j).getText();
                            }
                        }
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("emissionids", (Serializable)emissionids);
                intent.putExtra("emissiones", emissionDes);
                setResult(10102, intent);
                finish();
            }
        });

        relaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgchecked){
                    emissionids.clear();
                    checkBox.setImageResource(R.drawable.icon_red_unchecked);
                    imgchecked = false;
                }else{
                    emissionids.clear();
//                    for (CarChanelResponse carcon : mChannelList){
//                        emissionids.add(carcon.getValue());
//                    }
                    adapter.setDefault();
                    checkBox.setImageResource(R.drawable.icon_red_checked);
                    imgchecked = true;
                }
            }
        });
    }

    EmissionAdapter.ItemClickListener itemClickListener = new EmissionAdapter.ItemClickListener() {
        @Override
        public void setOnItemClickListener(String text, String value, boolean checked) {
            if (imgchecked){
                emissionids.clear();
                checkBox.setImageResource(R.drawable.icon_red_unchecked);
                imgchecked = false;
            }
            if (emissionids.contains(value)){
                emissionids.remove(value);
            }
            if (checked){
                emissionids.add(value);
            }
        }

//        @Override
//        public void setOnItemClickListener(String text, String value, int position) {
//            Intent intent = new Intent();
//            intent.putExtra("emissionid", value);
//            intent.putExtra("emissiones", text);
//            setResult(10102, intent);
//            finish();
//        }
    };

    /**
     * 获取排放
     */
    private void getCarChannel() {
        Subscriber subscriber = new PosetSubscriber<List<CarChanelResponse>>().getSubscriber(callback_carchannel);
        UserManager.getEmission(subscriber);
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
