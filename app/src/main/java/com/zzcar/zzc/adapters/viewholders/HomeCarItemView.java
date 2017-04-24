package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.HomeCarGet;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/24 18:15
 **/

@EViewGroup(R.layout.adapter_homecarfrom)
public class HomeCarItemView extends LinearLayout {

    @ViewById(R.id.imageView8)
    ImageView bgImage;
    @ViewById(R.id.textView8)
    TextView txtContent;
    @ViewById(R.id.textView10)
    TextView txtAddress;
    @ViewById(R.id.textView11)
    TextView txtYear;
    @ViewById(R.id.textView12)
    TextView txtMilie;
    @ViewById(R.id.textView13)
    TextView txtPifa;
    @ViewById(R.id.textView14)
    TextView txtPrice;
    @ViewById(R.id.textView15)
    TextView txtRenzheng;
    @ViewById(R.id.textView16)
    TextView txtCompany;
    @ViewById(R.id.textView18)
    TextView txtTime;
    @ViewById(R.id.textView19)
    TextView txtTuiguang;
    @ViewById(R.id.textView20)
    TextView txtHaspay;
    @ViewById(R.id.relativeLayout)
    RelativeLayout relativeLayout;


    public HomeCarItemView(Context context) {
        super(context);
    }

    public HomeCarItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(HomeCarGet homeCarGet, int position) {
        if (homeCarGet.getStock() == 0){
            bgImage.setImageResource(R.drawable.pic_bg_default);
            txtContent.setText("");
            txtAddress.setText("");
            relativeLayout.setVisibility(INVISIBLE);
            txtRenzheng.setVisibility(INVISIBLE);
            txtCompany.setText("");
            txtTime.setText("");
            txtTime.setText("");
            txtTuiguang.setVisibility(INVISIBLE);
            txtHaspay.setVisibility(VISIBLE);
            txtPrice.setText("");
            txtPifa.setText("");
        }else{
            if (homeCarGet.isPromotion()){
                //是推广
                txtTuiguang.setVisibility(VISIBLE);
            }else{
                txtTuiguang.setVisibility(INVISIBLE);
            }
            txtHaspay.setVisibility(INVISIBLE);
            txtContent.setText(homeCarGet.getName());
            txtAddress.setText(homeCarGet.getCar_province_city());
            txtYear.setText(homeCarGet.getOn_number_year());
            txtMilie.setText(""+homeCarGet.getMileage());
            txtPrice.setText(homeCarGet.getMarket_price()+"万");
        }
    }
}
