package com.zzcar.zzc.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.models.SupplyPropsMiddleModel;
import com.zzcar.zzc.views.widget.ItemIconTextIcon;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.List;

/**
 * 创建时间： 2017/6/21.
 * 作者：黄如辉
 * 功能描述：
 */
@EActivity(R.layout.activity_search_supply)
public class SearchSupplyActivity extends BaseActivity {


    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    @ViewById(R.id.itemIconTextIcon1)
    ItemIconTextIcon itemIconTextIcon1;
    @ViewById(R.id.itemIconTextIcon2)
    ItemIconTextIcon itemIconTextIcon2;
    @ViewById(R.id.itemIconTextIcon4)
    ItemIconTextIcon itemIconTextIcon4;
    @ViewById(R.id.itemIconTextIcon5)
    ItemIconTextIcon itemIconTextIcon5;
    @ViewById(R.id.itemIconTextIcon6)
    ItemIconTextIcon itemIconTextIcon6;
    @ViewById(R.id.itemIconTextIcon7)
    ItemIconTextIcon itemIconTextIcon7;
    @ViewById(R.id.sureSubmit)
    TextView sureSubmit;
    
    private SupplyPropsMiddleModel searchRequest;

    @AfterViews
    void initView(){
        searchRequest = (SupplyPropsMiddleModel) getIntent().getSerializableExtra("supplypropsModel");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("高级筛选");
        mNavbar.setRightTxt("清空条件");
        mNavbar.setRightTxtColor(R.color.color_959595);

        itemIconTextIcon1.setTitle("所在地");itemIconTextIcon1.setRightGravity(Gravity.RIGHT);
        itemIconTextIcon2.setTitle("品牌车系");itemIconTextIcon2.setRightGravity(Gravity.RIGHT);
        itemIconTextIcon4.setTitle("颜色");itemIconTextIcon4.setRightGravity(Gravity.RIGHT);
        itemIconTextIcon5.setTitle("表显里程");itemIconTextIcon5.setRightGravity(Gravity.RIGHT);
        itemIconTextIcon6.setTitle("排放");itemIconTextIcon6.setRightGravity(Gravity.RIGHT);
        itemIconTextIcon7.setTitle("渠道");itemIconTextIcon7.setRightGravity(Gravity.RIGHT);

        if (searchRequest != null){
            itemIconTextIcon1.setRightText(searchRequest.getAddressdes());
            if (!searchRequest.getBland_iddes().equals("品牌")){
                itemIconTextIcon2.setRightText(searchRequest.getBland_iddes());
            }else{
                itemIconTextIcon2.setRightText("不限品牌");
            }
            
            itemIconTextIcon4.setRightText(searchRequest.getColorDes());
            itemIconTextIcon5.setRightText(searchRequest.getMileagedes());
            itemIconTextIcon6.setRightText(searchRequest.getEmission_des());
            if (!searchRequest.getChanneldes().equals("渠道")){
                itemIconTextIcon7.setRightText(searchRequest.getChanneldes());
            }else{
                itemIconTextIcon7.setRightText("不限渠道");
            }
        }

        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                searchRequest.resetData();
                resertView();
            }
        });

        /*颜色*/
        itemIconTextIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSupplyActivity.this, ColorSelectActivity_.class);
                startActivityForResult(intent, 10107);
            }
        });

        /*所在地*/
        itemIconTextIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSupplyActivity.this, SelectCityActivity_.class);
                startActivityForResult(intent, 10106);
            }
        });

        /*品牌*/
        itemIconTextIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchSupplyActivity.this, BrandCarActivity_.class);
                startActivityForResult(intent, 10105);
            }
        });

        /*表显里程*/
        itemIconTextIcon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchSupplyActivity.this, MileageActivity_.class);
                startActivityForResult(intent, 10103);
            }
        });

        /*排放*/
        itemIconTextIcon6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchSupplyActivity.this, EmissionActivity_.class);
                startActivityForResult(intent, 10102);
            }
        });

        /*渠道*/
        itemIconTextIcon7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchSupplyActivity.this, ChannelActivity_.class);
                startActivityForResult(intent, 10101);
            }
        });

        sureSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("searchRequest", (Serializable) searchRequest);
                setResult(10200, intent);
                finish();
            }
        });
    }

    /*重置界面*/
    private void resertView() {
        itemIconTextIcon7.setRightText(searchRequest.getChanneldes());
        itemIconTextIcon6.setRightText(searchRequest.getEmission_des());
        itemIconTextIcon5.setRightText(searchRequest.getMileagedes());
        itemIconTextIcon2.setRightText(searchRequest.getBland_iddes());
        itemIconTextIcon1.setRightText(searchRequest.getAddressdes());
        itemIconTextIcon4.setRightText(searchRequest.getColorDes());
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 10101){
                String channelid = data.getStringExtra("channelid");
                String channeldes = data.getStringExtra("channeldes");
                if (TextUtils.isEmpty(channelid)){
                    channelid = "0";
                }
                searchRequest.setChannel(Integer.valueOf(channelid));
                searchRequest.setChanneldes(channeldes);
                itemIconTextIcon7.setRightText(channeldes);
            }else if (requestCode == 10102){
                List<String> emissionids = (List<String>) data.getSerializableExtra("emissionids");
                String emissiones = data.getStringExtra("emissiones");

                searchRequest.setEmission_ids(emissionids);
                searchRequest.setEmission_des(emissiones);
                itemIconTextIcon6.setRightText(emissiones);
            }else if (requestCode == 10103){
                String mileage = data.getStringExtra("mileage");
                String min_mileage = data.getStringExtra("min_mileage");
                String max_mileage = data.getStringExtra("max_mileage");
                String mileagedes = data.getStringExtra("mileagedes");
                if (TextUtils.isEmpty(mileage)){
                    mileage = "0";
                }
                if (TextUtils.isEmpty(min_mileage)){
                    min_mileage = "0";
                }
                if (TextUtils.isEmpty(max_mileage)){
                    max_mileage = "0";
                }

                searchRequest.setMileage(Integer.valueOf(mileage));
                searchRequest.setMin_mileage(Double.valueOf(min_mileage));
                searchRequest.setMax_mileage(Double.valueOf(max_mileage));
                searchRequest.setMileagedes(mileagedes);
                itemIconTextIcon5.setRightText(mileagedes);
            }else if (requestCode == 10105){
                String brandid = data.getStringExtra("brandid");
                String seriesid = data.getStringExtra("seriesid");
                String typeid = data.getStringExtra("typeid");
                String branddes = data.getStringExtra("branddes");
                if (TextUtils.isEmpty(brandid)){
                    brandid = "0";
                }
                if (TextUtils.isEmpty(seriesid)){
                    seriesid = "0";
                }
                if (TextUtils.isEmpty(typeid)){
                    typeid = "0";
                }
                searchRequest.setBland_id(Integer.valueOf(brandid));
                searchRequest.setSeries_id(Integer.valueOf(seriesid));
                searchRequest.setSpec_id(Integer.valueOf(typeid));
                searchRequest.setBland_iddes(branddes);
                itemIconTextIcon2.setRightText(branddes);
            }else if (requestCode == 10106){
                String province_id = data.getStringExtra("province_id");
                String city_id = data.getStringExtra("city_id");
                String citydes = data.getStringExtra("citydes");
                if (TextUtils.isEmpty(province_id)){
                    province_id = "0";
                }
                if (TextUtils.isEmpty(city_id)){
                    city_id = "0";
                }

                searchRequest.setProvince_id(Integer.valueOf(province_id));
                searchRequest.setCity_id(Integer.valueOf(city_id));
                searchRequest.setAddressdes(citydes);
                itemIconTextIcon1.setRightText(citydes);
            }else if (requestCode == 10107){
                List<String> coloridlist = (List<String>) data.getSerializableExtra("colorids");
                String colordes = data.getStringExtra("colordes");
                searchRequest.setColor_ids(coloridlist);
                searchRequest.setColorDes(colordes);
                itemIconTextIcon4.setRightText(colordes);
            }
        }
    }
}
