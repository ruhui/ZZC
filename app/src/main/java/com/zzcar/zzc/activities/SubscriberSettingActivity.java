package com.zzcar.zzc.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.BlandMiddelModel;
import com.zzcar.zzc.models.BlandModle;
import com.zzcar.zzc.models.SeriesItemsModel;
import com.zzcar.zzc.models.StartAndEndYear;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.MysubscribeResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.ItemOneView;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.wheel.view.TimePickerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/10.
 * 作者：黄如辉
 * 功能描述：订阅设置
 */

@EActivity(R.layout.fragment_subscribe_setting)
public class SubscriberSettingActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.txtBland)
    ItemOneView txtBland;
    @ViewById(R.id.txtShangpai)
    ItemOneView txtShangpai;
    @ViewById(R.id.txtPaifang)
    ItemOneView txtPaifang;
    @ViewById(R.id.editText18)
    EditText edtStarprice;
    @ViewById(R.id.editText17)
    EditText edtEndprice;
    @ViewById(R.id.txtSaveSub)
    TextView txtSaveSub;

    /*缓存记录品牌车系*/
    private HashMap<Integer, BlandModle> hashBland = new HashMap<>();

    private TimePickerView shangpaiStartTime;

    /*保存数据缓存*/
    private MysubscribeResponse mysubscriber = new MysubscribeResponse();
    /*品牌描述*/
    private String bland_series_text = "";

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        initpvTime();
        mNavbar.setMiddleTitle("订阅");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        txtBland.setTxtLeft("品牌");
        txtShangpai.setTxtLeft("上牌时间");
        txtPaifang.setTxtLeft("排放");

        getMysubscribe();

        /*品牌*/
        txtBland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubscriberSettingActivity.this, SubscriberBrandCarActivity_.class);
                intent.putExtra("hashBland", hashBland);
                startActivityForResult(intent, 10103);
            }
        });

        txtPaifang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmissionActivity_.class);
                startActivityForResult(intent, 10102);
            }
        });

        txtShangpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shangpaiStartTime.setTime(new Date());
                shangpaiStartTime.setCyclic(false);
                shangpaiStartTime.setCancelable(true);
                shangpaiStartTime.setTitle("");
                shangpaiStartTime.show();

                Tool.hideInputMethod(SubscriberSettingActivity.this,  edtEndprice);
            }
        });


        // 上牌时间回调
        shangpaiStartTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener(){
            @Override
            public void onTimeSelect(Date date) {}

            @Override
            public void onTimeSelectStartEndYear(StartAndEndYear startAndEndYear) {
                String startYear = startAndEndYear.getStartYear();
                String endYear = startAndEndYear.getEndYear();
                if (Integer.valueOf(startYear) > Integer.valueOf(endYear)){
                    ToastUtil.showToast("开始时间应小于结束时间");
                }else{
                    mysubscriber.setStart_year(Integer.valueOf(startYear));
                    mysubscriber.setEnd_year(Integer.valueOf(endYear));
                }
                txtShangpai.setTxtMiddle(startYear +"-"+endYear+"年");
            }
        });

        txtSaveSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSubscriber();
            }
        });

    }

    private void initpvTime() {
        shangpaiStartTime = new TimePickerView(SubscriberSettingActivity.this, TimePickerView.Type.YEAR_YEAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 10102){
                List<String> emissionids = (List<String>) data.getSerializableExtra("emissionids");
                String emissiones = data.getStringExtra("emissiones");
                mysubscriber.setEmission(emissionids);
                mysubscriber.setEmission_text(emissiones);
                txtPaifang.setTxtMiddle(emissiones);
            }else if(requestCode == 10103){
                hashBland = (HashMap<Integer, BlandModle>) data.getSerializableExtra("hashBland");
                Iterator iterator = hashBland.entrySet().iterator();
                if (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry) iterator.next();
                    BlandModle val = (BlandModle) entry.getValue();
                    for (SeriesItemsModel modle : val.getSeries_items()){
                        bland_series_text += modle.getName();
                    }
                }
                txtBland.setTxtMiddle(bland_series_text);
            }
        }
    }

    /*获取我的订阅*/
    private void getMysubscribe() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<MysubscribeResponse>().getSubscriber(callback_mysubscriber);
        UserManager.getMysubscribe(subscriber);
    }

    /*保存订阅*/
    private void saveSubscriber(){
        List<BlandModle> bland = new ArrayList<>();

        showProgress();
        Iterator iterator = hashBland.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            Integer key = (Integer) entry.getKey();
            BlandModle val = (BlandModle) entry.getValue();
            bland.add(val);
        }

        String startPrice = edtStarprice.getText().toString();
        String endPrice = edtEndprice.getText().toString();
        if (TextUtils.isEmpty(startPrice)){
            startPrice = "0";
        }
        if (TextUtils.isEmpty(endPrice)){
            endPrice = "0";
        }

        mysubscriber.setBland(bland);
        mysubscriber.setBland_series_text(bland_series_text);
        mysubscriber.setStart_price(Double.valueOf(startPrice));
        mysubscriber.setEnd_price(Double.valueOf(endPrice));

        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_saveSubscribe);
        UserManager.saveSubscribe(mysubscriber, subscriber);

    }


    /* 我的订阅回调*/
    ResponseResultListener callback_mysubscriber = new ResponseResultListener<MysubscribeResponse>() {
        @Override
        public void success(MysubscribeResponse returnMsg) {
            closeProgress();
            txtBland.setTxtMiddle(returnMsg.getBland_series_text());
            txtPaifang.setTxtMiddle(returnMsg.getEmission_text());
            if (returnMsg.getStart_year() != 0) {
                txtShangpai.setTxtMiddle(returnMsg.getStart_year() + "-" + returnMsg.getEnd_year() + "年");
            }
            if (returnMsg.getStart_price() != 0) {
                edtStarprice.setText(returnMsg.getStart_price() + "");
            }
            if (returnMsg.getEnd_price() != 0){
                edtEndprice.setText(returnMsg.getEnd_price() + "");
            }
            hashBland.clear();
            for (BlandModle modle : returnMsg.getBland()){
                hashBland.put(modle.getId(), modle);
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    ResponseResultListener callback_saveSubscribe = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            getMysubscribe();
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
