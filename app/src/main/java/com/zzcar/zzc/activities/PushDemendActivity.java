package com.zzcar.zzc.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.PhotoAdapte;
import com.zzcar.zzc.constants.Permission;
import com.zzcar.zzc.fragments.BuyIntegralFragment_;
import com.zzcar.zzc.interfaces.RefreshFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.PermissonManager;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.AddCarMiddleModle;
import com.zzcar.zzc.models.SavedemandModel;
import com.zzcar.zzc.models.StartAndEndYear;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.PublishintegralResponse;
import com.zzcar.zzc.networks.responses.SavedemandResponse;
import com.zzcar.zzc.networks.responses.SingleSupplyResponse;
import com.zzcar.zzc.utils.KeyboardPatch;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.PermissionUtili;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.ItemOneView;
import com.zzcar.zzc.views.widget.ItemSecondView;
import com.zzcar.zzc.views.widget.NavBar3;
import com.zzcar.zzc.views.widget.dialogs.MyAlertDialog2;
import com.zzcar.zzc.views.widget.dialogs.TakePhotoDialog;
import com.zzcar.zzc.wheel.view.TimePickerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.iwf.photopicker.PhotoPicker;
import rx.Subscriber;

@EActivity(R.layout.activity_push_demend)
public class PushDemendActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar3 mNavbar;

    @ViewById(R.id.blandcar)
    ItemOneView blandcar;

    @ViewById(R.id.cardTime)
    ItemOneView cardTime;

    @ViewById(R.id.carinColor)
    ItemOneView carColor;

    @ViewById(R.id.caroutColor)
    ItemOneView carinColor;

    @ViewById(R.id.editText3)
    EditText carDes;

    @ViewById(R.id.editText21)
    EditText edtMinPrice;

    @ViewById(R.id.editText20)
    EditText edtMaxPrice;

    /*提交*/
    @ViewById(R.id.relaBottom)
    RelativeLayout txtSubmit;

    @ViewById(R.id.textView199)
    TextView txtAlert;

    private  KeyboardPatch keyboard;
    private TimePickerView pvTimecardTime;
    private boolean isPublish = false;
    /*缓存的数据*/
    SavedemandModel savedemandModel = new SavedemandModel();

    @AfterViews
    void initView(){
        /*获取积分*/
        getPublishintegral();

        initpvTime();
        //车辆id为空是新增
        String productid =getIntent().getStringExtra("product_id");
        savedemandModel.setInfo_id(productid);

        if (!TextUtils.isEmpty(productid)){
            //获取询价信息
            showProgress();
            getSingeleSupply(productid);
        }
        //设置键盘弹起
        keyboard = new KeyboardPatch(this, getView());
        keyboard.enable();
        mNavbar.setLeftMenuText("取消");
        mNavbar.setMiddleTitle("发布求购");

        mNavbar.setOnMenuClickListener(new NavBar3.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                final MyAlertDialog2 dialog = new MyAlertDialog2(PushDemendActivity.this, true);
                dialog.show();
                dialog.setTitle("取消发布");
                dialog.setContent("是否放弃发布求购");
                dialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        finish();
                    }
                });
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                /*清空条件*/
            }
        });

        blandcar.setTxtLeft("品牌车系");blandcar.setHint("必填");
        cardTime.setTxtLeft("上牌时间");cardTime.setHint("必填");
        carColor.setTxtLeft("车身颜色(多选)");carColor.setHint("必填");
        carinColor.setTxtLeft("内饰颜色(多选)");carinColor.setHint("必填");

        cardTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTimecardTime.setTime(new Date());
                pvTimecardTime.setCyclic(false);
                pvTimecardTime.setCancelable(true);
                pvTimecardTime.show();
                Tool.hideInputMethod(PushDemendActivity.this,cardTime);
            }
        });

        // 上牌时间回调
        pvTimecardTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener(){
            @Override
            public void onTimeSelect(Date date)
            {}

            @Override
            public void onTimeSelectStartEndYear(StartAndEndYear startAndEndYear) {
                String startYear = startAndEndYear.getStartYear();
                String endYear = startAndEndYear.getEndYear();
                if (Integer.valueOf(startYear) > Integer.valueOf(endYear)){
                    ToastUtil.showToast("开始时间应小于结束时间");
                }else{
                    savedemandModel.setOn_number_min_year(Integer.valueOf(startYear));
                    savedemandModel.setOn_number_max_year(Integer.valueOf(endYear));
                }
                cardTime.setTxtMiddle(startYear +"-"+endYear+"年");
            }
        });


        blandcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushDemendActivity.this, BrandCarActivity_.class);
                startActivityForResult(intent, 10101);
            }
        });


        carColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushDemendActivity.this, ColorSelectActivity_.class);
                startActivityForResult(intent, 10102);
            }
        });

        carinColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushDemendActivity.this, ColorSelectActivity_.class);
                startActivityForResult(intent, 10103);
            }
        });


        /*提交*/
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subMitCar();
            }
        });
    }

    /*判断是否可以发布*/
    private void getPublishintegral() {
        Subscriber subscriber = new PosetSubscriber<PublishintegralResponse>().getSubscriber(callback_integra);
        UserManager.getPublishintegral("1", subscriber);
    }


    @Subscribe
    public void refreshIntegra(RefreshFragment refreshFragment){
        if (refreshFragment.refresh){
            getPublishintegral();
        }
    }

    /*获取车源信息*/
    void getSingeleSupply(String productid){
        Subscriber subscriber = new PosetSubscriber<SingleSupplyResponse>().getSubscriber(callback_singlecar);
        UserManager.getSingelmydemand(productid, subscriber);
    }

    private void subMitCar() {
        String content = carDes.getText().toString();

        if (TextUtils.isEmpty(content)){
            ToastUtil.showToast("请输入您的其他要求");
            return;
        }

        if (!isPublish){
            //不可发布,跳转到购买积分界面
            showFragment(BuyIntegralFragment_.builder().build());
            return;
        }
        String minprice = edtMinPrice.getText().toString();
        String maxprice = edtMaxPrice.getText().toString();

        minprice = Tool.formatPrice(minprice);
        maxprice = Tool.formatPrice(maxprice);

        if (TextUtils.isEmpty(minprice) || TextUtils.isEmpty(maxprice) || Double.valueOf(minprice) > Double.valueOf(maxprice)){
            ToastUtil.showToast("请输入正确的金额范围");
            return;
        }

        savedemandModel.setMin_price( Double.valueOf(minprice));
        savedemandModel.setMax_price( Double.valueOf(maxprice));
        savedemandModel.setContent(content);

        saveCarFrom();
    }


    @Override
    public void onBackPressed() {
        final MyAlertDialog2 dialog = new MyAlertDialog2(PushDemendActivity.this, true);
        dialog.show();
        dialog.setTitle("取消发布");
        dialog.setContent("是否放弃发车");
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
    }


    private void initpvTime() {
        pvTimecardTime = new TimePickerView(PushDemendActivity.this, TimePickerView.Type.YEAR_YEAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 10101){
                /*品牌车系*/
                String brandid = data.getStringExtra("brandid");
                String seriesid = data.getStringExtra("seriesid");
                String typeid = data.getStringExtra("typeid");
                String branddes = data.getStringExtra("branddes");

                savedemandModel.setBland_id(brandid);
                savedemandModel.setSeries_id(seriesid);
                savedemandModel.setBlandDes(branddes);

            }else if(requestCode == 10102){
                /*车身颜色*/
                List<String> colorids = (List<String>) data.getSerializableExtra("colorids");
                String colordes = data.getStringExtra("colordes");

                savedemandModel.setColor(colorids);
                savedemandModel.setCarColorDes(colordes);
            }else if (requestCode == 10103){
                /*内饰颜色*/
                List<String> colorids = (List<String>) data.getSerializableExtra("colorids");
                String colordes = data.getStringExtra("colordes");
                savedemandModel.setInside_color(colorids);
                savedemandModel.setCarInColorDes(colordes);
            }
            resetView();
        }
    }

    void resetView(){
        blandcar.setTxtMiddle(savedemandModel.getBlandDes());
        carColor.setTxtMiddle(savedemandModel.getCarColorDes());
        carinColor.setTxtMiddle(savedemandModel.getCarInColorDes());
    }

    @Override
    public void onNetChange(int netMobile) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (keyboard != null){
            keyboard.disable();
        }
    }

    /*发布车源*/
    private void saveCarFrom() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_carfrom);
        UserManager.saveDemand(savedemandModel, subscriber);
    }

    ResponseResultListener callback_carfrom = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            ToastUtil.showToast("发布成功");
            EventBus.getDefault().post(new RefreshFragment(true, "MyDemend"));
            Intent intent = new Intent();
            intent.putExtra("isrefreshdata", true);
            setResult(10201, intent);
            finish();
        }

        @Override
        public void fialed(String resCode, String message) {closeProgress();}
    };


    /*获取单条车源*/
    ResponseResultListener callback_singlecar = new ResponseResultListener<SavedemandResponse>() {
        @Override
        public void success(SavedemandResponse returnMsg) {
            closeProgress();
            List<String> carcolor = returnMsg.getProps_name().getColor();
            String carColor = "";
            for (String str : carcolor){
                if (TextUtils.isEmpty(carColor)){
                    carColor = str;
                }else{
                    carColor += "、"+str;
                }
            }

            List<String> carintcolor = returnMsg.getProps_name().getInside_color();
            String carInColor = "";
            for (String str : carintcolor){
                if (TextUtils.isEmpty(carColor)){
                    carInColor = str;
                }else{
                    carInColor += "、"+str;
                }
            }

            savedemandModel.setBlandDes(returnMsg.getProps_name().getName());
            savedemandModel.setCarColorDes(carColor);
            savedemandModel.setCarInColorDes(carInColor);
            savedemandModel.setTimeDes(returnMsg.getOn_number_min_year()+"—"+returnMsg.getOn_number_max_year()+"年");

            savedemandModel.setBland_id(returnMsg.getBland_id());
            savedemandModel.setColor(returnMsg.getColor());
            savedemandModel.setContent(returnMsg.getContent());
            savedemandModel.setInside_color(returnMsg.getInside_color());
            savedemandModel.setMax_price(returnMsg.getMax_price());
            savedemandModel.setMin_price(returnMsg.getMin_price());
            savedemandModel.setOn_number_max_year(returnMsg.getOn_number_max_year());
            savedemandModel.setOn_number_min_year(returnMsg.getOn_number_min_year());
            savedemandModel.setSeries_id(returnMsg.getSeries_id());

            resetView();
            cardTime.setTxtMiddle(savedemandModel.getTimeDes());
            edtMinPrice.setText(savedemandModel.getMin_price()+"");
            edtMaxPrice.setText(savedemandModel.getMax_price()+"");
            carDes.setText(savedemandModel.getContent());
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            LogUtil.E("fialed", "fialed");
        }
    };


    /*是否允许发布*/
    ResponseResultListener callback_integra = new ResponseResultListener<PublishintegralResponse>() {
        @Override
        public void success(PublishintegralResponse returnMsg) {
            isPublish = returnMsg.is_pubilsh();
            if (returnMsg.is_pubilsh()){
                //可以发布
                txtAlert.setText("发布求购/需要"+returnMsg.getIntegral()+"积分");
            }else{
                txtAlert.setText("积分不足，请购买");
            }
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };

}
