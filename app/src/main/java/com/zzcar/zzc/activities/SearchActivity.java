package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.views.widget.ItemIconTextIcon;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.NavBar2_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.List;

@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    @ViewById(R.id.itemIconTextIcon1)
    ItemIconTextIcon itemIconTextIcon1;
    @ViewById(R.id.itemIconTextIcon2)
    ItemIconTextIcon itemIconTextIcon2;
    @ViewById(R.id.itemIconTextIcon3)
    ItemIconTextIcon itemIconTextIcon3;
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


    private SearchRequest searchRequest;
    private boolean iscleardata = false;

    @AfterViews
    void initView(){
        SearchRequest parentreques = (SearchRequest) getIntent().getSerializableExtra("searchRequest");
        searchRequest = new SearchRequest();
        searchRequest.copyData(parentreques);
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("高级筛选");
        mNavbar.setRightTxt("清空条件");
        mNavbar.setRightTxtColor(R.color.color_959595);

        itemIconTextIcon1.setTitle("所在地");
        itemIconTextIcon2.setTitle("品牌车系");
        itemIconTextIcon3.setTitle("价格");
        itemIconTextIcon4.setTitle("颜色");
        itemIconTextIcon5.setTitle("表显里程");
        itemIconTextIcon6.setTitle("排放");
        itemIconTextIcon7.setTitle("渠道");

        if (parentreques != null){
            itemIconTextIcon1.setRightText(parentreques.getCitydes());
            if (!parentreques.getBland_iddes().equals("品牌")){
                itemIconTextIcon2.setRightText(parentreques.getBland_iddes());
            }else{
                itemIconTextIcon2.setRightText("不限品牌");
            }
            if (!parentreques.getPrice_typedes().equals("价格")){
                itemIconTextIcon3.setRightText(parentreques.getPrice_typedes());
            }else{
                itemIconTextIcon3.setRightText("不限价格");
            }
            itemIconTextIcon4.setRightText(parentreques.getColorDes());
            itemIconTextIcon5.setRightText(parentreques.getMileagedes());
            itemIconTextIcon6.setRightText(parentreques.getEmission_des());
            if (!parentreques.getChanneldes().equals("渠道")){
                itemIconTextIcon7.setRightText(parentreques.getChanneldes());
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
                iscleardata = true;
                resertView();
            }
        });

        /*颜色*/
        itemIconTextIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ColorSelectActivity_.class);
                startActivityForResult(intent, 10107);
            }
        });

        /*所在地*/
        itemIconTextIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SelectCityActivity_.class);
                startActivityForResult(intent, 10106);
            }
        });

        /*品牌*/
        itemIconTextIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, BrandCarActivity_.class);
                startActivityForResult(intent, 10105);
            }
        });

        /*价格*/
        itemIconTextIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, PirceActivity_.class);
                startActivityForResult(intent, 10104);
            }
        });

        /*表显里程*/
        itemIconTextIcon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, MileageActivity_.class);
                startActivityForResult(intent, 10103);
            }
        });

        /*排放*/
        itemIconTextIcon6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, EmissionActivity_.class);
                startActivityForResult(intent, 10102);
            }
        });

        /*渠道*/
        itemIconTextIcon7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, ChannelActivity_.class);
                startActivityForResult(intent, 10101);
            }
        });

        sureSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("searchRequest", (Serializable) searchRequest);
                intent.putExtra("iscleardata", iscleardata);
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
        itemIconTextIcon3.setRightText(searchRequest.getPrice_typedes());
        itemIconTextIcon2.setRightText(searchRequest.getBland_iddes());
        itemIconTextIcon1.setRightText(searchRequest.getCitydes());
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
                searchRequest.setChannel(channelid);
                searchRequest.setChanneldes(channeldes);
                itemIconTextIcon7.setRightText(channeldes);
                iscleardata = false;
            }else if (requestCode == 10102){
                List<String> emissionids = (List<String>) data.getSerializableExtra("emissionids");
                String emissiones = data.getStringExtra("emissiones");
                searchRequest.setEmission_ids(emissionids);
                searchRequest.setEmission_des(emissiones);
                itemIconTextIcon6.setRightText(emissiones);
                iscleardata = false;
            }else if (requestCode == 10103){
                String mileage = data.getStringExtra("mileage");
                String min_mileage = data.getStringExtra("min_mileage");
                String max_mileage = data.getStringExtra("max_mileage");
                String mileagedes = data.getStringExtra("mileagedes");
                searchRequest.setMileage(mileage);
                searchRequest.setMin_mileage(min_mileage);
                searchRequest.setMax_mileage(max_mileage);
                searchRequest.setMileagedes(mileagedes);
                itemIconTextIcon5.setRightText(mileagedes);
                iscleardata = false;
            }else if (requestCode == 10104){
                String price_type = data.getStringExtra("price_type");
                String min_price = data.getStringExtra("min_price");
                String max_price = data.getStringExtra("max_price");
                String pricedes = data.getStringExtra("max_pricedes");
                searchRequest.setPrice_type(price_type);
                searchRequest.setMin_price(min_price);
                searchRequest.setMax_price(max_price);
                searchRequest.setPrice_typedes(pricedes);
                itemIconTextIcon3.setRightText(pricedes);
                iscleardata = false;
            }else if (requestCode == 10105){
                String brandid = data.getStringExtra("brandid");
                String seriesid = data.getStringExtra("seriesid");
                String typeid = data.getStringExtra("typeid");
                String branddes = data.getStringExtra("branddes");
                searchRequest.setBland_id(brandid);
                searchRequest.setSeries_id(seriesid);
                searchRequest.setSpec_id(typeid);
                searchRequest.setBland_iddes(branddes);
                itemIconTextIcon2.setRightText(branddes);
                iscleardata = false;
            }else if (requestCode == 10106){
                String province_id = data.getStringExtra("province_id");
                String city_id = data.getStringExtra("city_id");
                String citydes = data.getStringExtra("citydes");
                searchRequest.setProvince_id(province_id);
                searchRequest.setCity_id(city_id);
                searchRequest.setCitydes(citydes);
                itemIconTextIcon1.setRightText(citydes);
                iscleardata = false;
            }else if (requestCode == 10107){
                List<String> colorids = (List<String>) data.getSerializableExtra("colorids");
                String colordes = data.getStringExtra("colordes");
                searchRequest.setColor_ids(colorids);
                searchRequest.setColorDes(colordes);
                itemIconTextIcon4.setRightText(colordes);
                iscleardata = false;
            }
        }
    }
}
