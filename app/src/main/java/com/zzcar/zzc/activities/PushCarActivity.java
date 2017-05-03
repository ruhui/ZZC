package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.models.AddCarMiddleModle;
import com.zzcar.zzc.utils.KeyboardPatch;
import com.zzcar.zzc.views.widget.ItemOneView;
import com.zzcar.zzc.views.widget.ItemSecondView;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_push_car)
public class PushCarActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.blandcar)
    ItemOneView blandcar;
    @ViewById(R.id.selectCity)
    ItemOneView selectCity;
    @ViewById(R.id.priceItem)
    ItemSecondView priceItem;
    @ViewById(R.id.cardTime)
    ItemOneView cardTime;
    @ViewById(R.id.cardBelong)
    ItemOneView cardBelong;
    @ViewById(R.id.mileData)
    ItemSecondView mileData;
    @ViewById(R.id.carColor)
    ItemOneView carColor;
    @ViewById(R.id.outComTime)
    ItemOneView outComTime;
    @ViewById(R.id.emissionSta)
    ItemOneView emissionSta;
    @ViewById(R.id.newcarPrice)
    ItemSecondView newcarPrice;
    @ViewById(R.id.limitTime)
    ItemOneView limitTime;
    @ViewById(R.id.useto)
    ItemOneView useto;
    @ViewById(R.id.editText3)
    EditText carDes;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    /*提交*/
    @ViewById(R.id.txtSubmit)
    TextView txtSubmit;

    private  KeyboardPatch keyboard;
    private AddCarMiddleModle carMiddle;

    @AfterViews
    void initView(){
        //车辆id为空是新增
        String productid =getIntent().getStringExtra("product_id");

        carMiddle = new AddCarMiddleModle();
        if (TextUtils.isEmpty(productid)){
            carMiddle.setProduct_id("0");
        }
        //设置键盘弹起
        keyboard = new KeyboardPatch(this, getView());
        keyboard.enable();
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("发布车源");
        mNavbar.setRightTxt("清空条件");

        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                /*清空条件*/
            }
        });

        blandcar.setTxtLeft("品牌车系");blandcar.setHint("必填");
        selectCity.setTxtLeft("所在地");selectCity.setHint("必填");
        priceItem.setTxtLeft("价格");priceItem.setTxtRight("万元");
        cardTime.setTxtLeft("上牌时间");cardTime.setHint("必填");
        cardBelong.setTxtLeft("牌照归属");cardBelong.setHint("必填");
        mileData.setTxtLeft("表显里程");mileData.setTxtRight("万公里");
        carColor.setTxtLeft("车身颜色");carColor.setHint("必填");
        outComTime.setTxtLeft("出厂时间");outComTime.setHint("必填");
        emissionSta.setTxtLeft("排放标准");emissionSta.setHint("必填");
        newcarPrice.setTxtLeft("新车指导价");newcarPrice.setTxtRight("万元");
        limitTime.setTxtLeft("强制险到期");limitTime.setHint("必填");
        useto.setTxtLeft("用途");useto.setHint("必填");

        blandcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, BrandCarActivity_.class);
                startActivityForResult(intent, 10105);
            }
        });

        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, SelectCityActivity_.class);
                startActivityForResult(intent, 10106);
            }
        });

        cardBelong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, SelectCityActivity_.class);
                startActivityForResult(intent, 10107);
            }
        });

        carColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, ColorSelectActivity_.class);
                intent.putExtra("singleselect", true);
                startActivityForResult(intent, 10108);
            }
        });

        emissionSta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, EmissionActivity_.class);
                intent.putExtra("singleselect", true);
                startActivityForResult(intent, 10102);
            }
        });

        useto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, UserTypeActivity_.class);
                startActivityForResult(intent, 10109);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 10102){
                /*排放*/
                List<String> emissionids = (List<String>) data.getSerializableExtra("emissionids");
                String emissiones = data.getStringExtra("emissiones");
                if (emissionids.size() > 0){
                    String emissionid = emissionids.get(0);
                    carMiddle.setEmission(emissionid);
                }
                carMiddle.setEmissionDes(emissiones);
            }else if (requestCode == 10105){
                /*品牌车系*/
                String brandid = data.getStringExtra("brandid");
                String seriesid = data.getStringExtra("seriesid");
                String typeid = data.getStringExtra("typeid");
                String branddes = data.getStringExtra("branddes");
                carMiddle.setBland_id(brandid);
                carMiddle.setSeries_id(seriesid);
                carMiddle.setSpec_id(typeid);
                carMiddle.setBladseriesdes(branddes);
            }else if (requestCode == 10106){
                /*车辆所属城市*/
                String province_id = data.getStringExtra("province_id");
                String city_id = data.getStringExtra("city_id");
                String citydes = data.getStringExtra("citydes");
                carMiddle.setCar_province_id(province_id);
                carMiddle.setCar_city_id(city_id);
                carMiddle.setBelongCityDes(citydes);
            }else if (requestCode == 10107){
                /*牌照归属*/
                String province_id = data.getStringExtra("province_id");
                String city_id = data.getStringExtra("city_id");
                String citydes = data.getStringExtra("citydes");
                carMiddle.setOn_number_province_id(province_id);
                carMiddle.setOn_number_city_id(city_id);
                carMiddle.setNumberBelongDes(citydes);
            }else if(requestCode == 10108){
                /*车身颜色*/
                List<String> colorids = (List<String>) data.getSerializableExtra("colorids");
                String colordes = data.getStringExtra("colordes");
                if (colorids.size() > 0){
                    String colorid = colorids.get(0);
                    carMiddle.setColor(colorid);
                }
                carMiddle.setColorDes(colordes);

            }else if (requestCode == 10109){
                String usertypeid = data.getStringExtra("usertypeid");
                String usertypeDes = data.getStringExtra("usertypeDes");
                carMiddle.setUse_type(usertypeid);
                carMiddle.setUsertypeDes(usertypeDes);
            }
            resetView();
        }
    }

    void resetView(){
        blandcar.setTxtMiddle(carMiddle.getBladseriesdes());
        selectCity.setTxtMiddle(carMiddle.getBelongCityDes());
        cardTime.setTxtMiddle("上牌时间");
        cardBelong.setTxtMiddle(carMiddle.getNumberBelongDes());
        carColor.setTxtMiddle(carMiddle.getColorDes());
        outComTime.setTxtMiddle("出厂时间");
        emissionSta.setTxtMiddle(carMiddle.getEmissionDes());
        limitTime.setTxtMiddle("强制险到期");
        useto.setTxtMiddle(carMiddle.getUsertypeDes());
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
}
