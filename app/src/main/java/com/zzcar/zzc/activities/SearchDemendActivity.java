package com.zzcar.zzc.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.models.BlandModle;
import com.zzcar.zzc.models.DemendPropsModel;
import com.zzcar.zzc.models.MiddleDemendModel;
import com.zzcar.zzc.models.StartAndEndYear;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.ItemIconTextIcon;
import com.zzcar.zzc.views.widget.ItemOneView;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.wheel.view.TimePickerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

/**
 * 创建时间： 2017/6/20.
 * 作者：黄如辉
 * 功能描述：
 */
@EActivity(R.layout.activity_search_demend)
public class SearchDemendActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.itemIconTextIcon1)
    ItemIconTextIcon blandItem;
    @ViewById(R.id.itemIconTextIcon2)
    ItemIconTextIcon priceItem;
    @ViewById(R.id.itemIconTextIcon3)
    ItemIconTextIcon colorItem;
    @ViewById(R.id.outComTime)
    ItemOneView outComTime;

    /*求购缓存数据*/
    MiddleDemendModel propsModel;
    private TimePickerView pvTimecardTime;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){

        propsModel = (MiddleDemendModel) getIntent().getSerializableExtra("propsModel");

        mNavbar.setMiddleTitle("高级筛选");
        mNavbar.setRightTxt("清空条件");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setRightTxtColor(R.color.app_red);

        pvTimecardTime = new TimePickerView(SearchDemendActivity.this, TimePickerView.Type.YEAR_YEAR);

        blandItem.setTitle("品牌车系");blandItem.setRightHint("请选择品牌车系");blandItem.setRightGravity(Gravity.RIGHT);
        priceItem.setTitle("价格");priceItem.setRightHint("请选择价格");priceItem.setRightGravity(Gravity.RIGHT);
        outComTime.setTxtLeft("上牌时间");outComTime.setHint("请选择上牌时间");outComTime.setGravity(Gravity.RIGHT);
        colorItem.setTitle("颜色");colorItem.setRightHint("请选择颜色");colorItem.setRightGravity(Gravity.RIGHT);

        blandItem.setRightText(propsModel.getBranddes());
        priceItem.setRightText(propsModel.getPrice_typedes());
        outComTime.setTxtMiddle(propsModel.getTimeDes());
        colorItem.setRightText(propsModel.getColorDes());

        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                //清空数据
                propsModel.clearData();

            }
        });

        /*品牌车系*/
        blandItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDemendActivity.this, BrandCarActivity_.class);
                intent.putExtra("notspec", true);
                startActivityForResult(intent, 10105);
            }
        });

        /*价格*/
        priceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDemendActivity.this, PirceActivity_.class);
                startActivityForResult(intent, 10104);
            }
        });

        /*颜色*/
        colorItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchDemendActivity.this, ColorSelectActivity_.class);
                startActivityForResult(intent, 10107);
            }
        });

        outComTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTimecardTime.setTime(new Date());
                pvTimecardTime.setCyclic(false);
                pvTimecardTime.setCancelable(true);
                pvTimecardTime.show();
                Tool.hideInputMethod(SearchDemendActivity.this, outComTime);
            }
        });

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
                    propsModel.setStart_year(Integer.valueOf(startYear));
                    propsModel.setEnd_year(Integer.valueOf(endYear));
                    propsModel.setTimeDes(startYear +"-"+endYear+"年");
                }
                outComTime.setTxtMiddle(startYear +"-"+endYear+"年");
            }
        });
    }

    /*提交数据*/
    @Click(R.id.txtSubmit)
    void submitData(){
        Intent intent = new Intent();
        intent.putExtra("propsModel", propsModel);
        setResult(20171, intent);
        finish();
    }

    void resetView(){
        blandItem.setRightText(propsModel.getBranddes());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 10104){
                /*价格*/
                String price_type = data.getStringExtra("price_type");
                String min_price = data.getStringExtra("min_price");
                String max_price = data.getStringExtra("max_price");
                String pricedes = data.getStringExtra("max_pricedes");

                if (!TextUtils.isEmpty(price_type)){
                    propsModel.setPrice_typ(Integer.valueOf(price_type));
                }else{
                    propsModel.setPrice_typ(0);
                }
                if (!TextUtils.isEmpty(min_price)){
                    propsModel.setMin_price(Integer.valueOf(min_price));
                }else{
                    propsModel.setMin_price(0);
                }
                if (!TextUtils.isEmpty(max_price)){
                    propsModel.setMax_price(Integer.valueOf(max_price));
                }else{
                    propsModel.setMax_price(0);
                }
                propsModel.setPrice_typedes(pricedes);
                priceItem.setRightText(pricedes);
            }else if (requestCode == 10105){
                /*品牌车系*/
                String brandid = data.getStringExtra("brandid");
                String seriesid = data.getStringExtra("seriesid");
                //车型
//                String typeid = data.getStringExtra("typeid");
                String branddes = data.getStringExtra("branddes");

                if (!TextUtils.isEmpty(brandid)){
                    propsModel.setBland_id(Integer.valueOf(brandid));
                }else{
                    propsModel.setBland_id(0);
                }
                if (!TextUtils.isEmpty(seriesid)){
                    propsModel.setSeries_id(Integer.valueOf(seriesid));
                }else{
                    propsModel.setSeries_id(0);
                }
                propsModel.setBranddes(branddes);
            }else if (requestCode == 10107){
                /*车辆所属城市*/
                List<String> coloridlist = (List<String>) data.getSerializableExtra("colorids");
                String colordes = data.getStringExtra("colordes");
                propsModel.setColor_ids(coloridlist);
                propsModel.setColorDes(colordes);
                colorItem.setRightText(colordes);
            }
            resetView();
        }
    }
}
