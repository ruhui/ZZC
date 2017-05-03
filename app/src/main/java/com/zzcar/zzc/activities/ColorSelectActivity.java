package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.ColorSelectAdapter;
import com.zzcar.zzc.adapters.EmissionAdapter;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.ColorResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

@EActivity(R.layout.activity_color_select)
public class ColorSelectActivity extends BaseActivity {

    private List<ColorResponse> mColorList = new ArrayList<>();
    private ColorSelectAdapter adapter;
    private List<String> emissionids = new ArrayList<>();
    private String emissionDes = "";
    /*是否选择了列表*/
    private boolean imgchecked = false;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.checkBox)
    ImageView checkBox;
    @ViewById(R.id.relaItem)
    RelativeLayout relaItem;
    @ViewById(R.id.allTxt)
    TextView allTxt;

    boolean singleselect = false;

    @AfterViews
    void initView(){
        getColorData();

        singleselect = getIntent().getBooleanExtra("singleselect",false);
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTextColor(R.color.color_333333);
        mNavbar.setMiddleTitle("颜色");

        if (singleselect){
            relaItem.setVisibility(View.GONE);
            allTxt.setVisibility(View.GONE);
        }else{
            relaItem.setVisibility(View.VISIBLE);
            allTxt.setVisibility(View.VISIBLE);
            mNavbar.setRightTxtColor(R.color.app_red);
            mNavbar.setRightTxt("确定");
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter = new ColorSelectAdapter(getActivity(), mColorList,
                itemClickListener, singleselect));
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                if (singleselect){
                    return;
                }
                if (emissionids.size() == 0){
                    emissionDes = "不限颜色";
                }else{
                    for (int i=0;i<emissionids.size(); i++){
                        for (int j=0;j<mColorList.size();j++){
                            if (mColorList.get(j).getValue().equals(emissionids.get(i))){
                                emissionDes += " "+mColorList.get(j).getText();
                            }
                        }
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("colorids", (Serializable)emissionids);
                intent.putExtra("colordes", emissionDes);
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
                    adapter.setDefault();
                    checkBox.setImageResource(R.drawable.icon_red_checked);
                    imgchecked = true;
                }
            }
        });
    }


    ColorSelectAdapter.ItemClickListener itemClickListener = new ColorSelectAdapter.ItemClickListener() {
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
            if (singleselect){
                if (emissionids.size() == 0){
                    emissionDes = "不限颜色";
                }else{
                    for (int i=0;i<emissionids.size(); i++){
                        for (int j=0;j<mColorList.size();j++){
                            if (mColorList.get(j).getValue().equals(emissionids.get(i))){
                                emissionDes += " "+mColorList.get(j).getText();
                            }
                        }
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("colorids", (Serializable)emissionids);
                intent.putExtra("colordes", emissionDes);
                setResult(10102, intent);
                finish();
            }
        }
    };


    private void getColorData() {
        Subscriber subscriber = new PosetSubscriber<List<ColorResponse>>().getSubscriber(callback_carchannel);
        UserManager.getColor(subscriber);
    }

    /*颜色回调*/
    ResponseResultListener callback_carchannel = new ResponseResultListener<List<ColorResponse>>() {
        @Override
        public void success(List<ColorResponse> returnMsg) {
            LogUtil.E("success","success");
            mColorList.clear();
            mColorList.addAll(returnMsg);
            adapter.setData(mColorList);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };

    @Override
    public void onNetChange(int netMobile) {

    }
}
