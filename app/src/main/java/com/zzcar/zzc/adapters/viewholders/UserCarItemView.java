package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.HomeCarGet;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/24 18:15
 **/

@EViewGroup(R.layout.adapter_usercarfrom)
public class UserCarItemView extends LinearLayout {

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

    private Context mContext;


    public UserCarItemView(Context context) {
        super(context);
        mContext = context;
    }

    public UserCarItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(HomeCarGet homeCarGet, int position) {
        txtContent.setText(homeCarGet.getName());
        txtAddress.setText(homeCarGet.getCar_province_city());
        txtYear.setText(homeCarGet.getOn_number_year()+"");
        txtMilie.setText(""+homeCarGet.getMileage()+"万公里");
        txtPrice.setText(homeCarGet.getMarket_price()+"万");
        ImageLoader.loadImage(Tool.getPicUrl(mContext, homeCarGet.getFirst_image(), 92, 67), bgImage, R.drawable.pic_bg_default);

        if (homeCarGet.getStock() == 0){
            txtTuiguang.setVisibility(VISIBLE);
            txtTuiguang.setText("已售");
        }else{
            if (homeCarGet.isPromotion()){
                //是推广
                txtTuiguang.setVisibility(VISIBLE);
                txtTuiguang.setText("推广");
            }else{
                txtTuiguang.setVisibility(INVISIBLE);
                txtTuiguang.setText("");
            }
        }
    }
}
