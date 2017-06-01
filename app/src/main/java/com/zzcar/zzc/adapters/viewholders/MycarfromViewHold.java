package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.MyfavcarModle;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */
@EViewGroup(R.layout.viewhold_mycarfrom)
public class MycarfromViewHold extends LinearLayout {

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
    @ViewById(R.id.textView14)
    TextView txtPrice;
    @ViewById(R.id.textView19)
    TextView txtTuiguang;
    @ViewById(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @ViewById(R.id.imgShangjia)
    ImageView imgShangjia;
    @ViewById(R.id.imgEdit)
    ImageView imgEdit;

    private Context mContext;


    public MycarfromViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public MycarfromViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(final MyfavcarModle homeCarGet, final int position, final AdapterListener adapterListener, String tag) {
        txtContent.setText(homeCarGet.getName());
        txtAddress.setText(homeCarGet.getCar_province_city());
        txtYear.setText(homeCarGet.getOn_number_year()+"");
        txtMilie.setText(""+homeCarGet.getMileage()+"万公里");
        txtPrice.setText(homeCarGet.getMarket_price()+"万");
        ImageLoader.loadImage(Tool.getPicUrl(mContext, homeCarGet.getFirst_image(), 92, 67), bgImage, R.drawable.pic_bg_default);



        //聊天选择我的车源
        if (tag.equals("011")){
            imgShangjia.setVisibility(GONE);
            imgEdit.setVisibility(GONE);
        }else{
            imgShangjia.setVisibility(VISIBLE);
            imgEdit.setVisibility(VISIBLE);
            if (tag.equals("2")){
                //未上架
                imgShangjia.setImageResource(R.drawable.nav_icon_xiajia_up);
            }else{
                imgShangjia.setImageResource(R.drawable.nav_icon_xiajia_down);
            }
        }

        if (homeCarGet.getStock() == 0){
            txtTuiguang.setText("已售");
            txtTuiguang.setVisibility(VISIBLE);
        }else{
            if (homeCarGet.getPromotion()){
                //是推广
                txtTuiguang.setVisibility(VISIBLE);
                txtTuiguang.setText("推广");
            }else{
                txtTuiguang.setVisibility(INVISIBLE);
                txtTuiguang.setText("");
            }
        }

        /*上下架*/
        imgShangjia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.setOnItemListener(homeCarGet, 0);
            }
        });

        /*编辑*/
        imgEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.setOnItemListener(homeCarGet, 1);
            }
        });
    }
}
