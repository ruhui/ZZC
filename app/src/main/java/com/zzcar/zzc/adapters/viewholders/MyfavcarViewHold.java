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
import com.zzcar.zzc.models.HomeCarGet;
import com.zzcar.zzc.models.MyfavcarModle;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */

@EViewGroup(R.layout.adapter_myfavcar)
public class MyfavcarViewHold extends LinearLayout{

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
    @ViewById(R.id.imgCancle)
    ImageView imgCancle;

    private Context mContext;

    public MyfavcarViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public MyfavcarViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(final MyfavcarModle homeCarGet, final int position, final AdapterListener adapterListener) {
        if (homeCarGet.getStock() == 0){
            bgImage.setImageResource(R.drawable.pic_bg_default);
            txtContent.setText("");
            relativeLayout.setVisibility(INVISIBLE);
            txtTuiguang.setText("已售");
            txtPrice.setText("");
        }else{
            ImageLoader.loadImage(Tool.getPicUrl(mContext, homeCarGet.getFirst_image(), 92, 67), bgImage, R.drawable.pic_bg_default);
            relativeLayout.setVisibility(VISIBLE);
            txtContent.setText(homeCarGet.getName());
            txtAddress.setText(homeCarGet.getCar_province_city());
            txtYear.setText(homeCarGet.getOn_number_year()+"");
            txtMilie.setText(""+homeCarGet.getMileage()+"万公里");
            txtPrice.setText("推广："+homeCarGet.getMarket_price()+"万");
            if (homeCarGet.getPromotion()){
                //是推广
                txtTuiguang.setVisibility(VISIBLE);
                txtTuiguang.setText("推广");
            }else{
                txtTuiguang.setVisibility(INVISIBLE);
                txtTuiguang.setText("");
            }
        }

        imgCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.setOnItemListener(homeCarGet, position);
            }
        });
    }
}
