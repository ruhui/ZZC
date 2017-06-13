package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.OrderitemsModel;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/14.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_carfrom_item)
public class CarfromViewHold extends LinearLayout {

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

    private Context mContext;

    public CarfromViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public CarfromViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(OrderitemsModel model){
        txtContent.setText(model.getProduct_name());
        txtAddress.setText(model.getBody().getCar_province_city());
        txtYear.setText(model.getBody().getOn_number_year()+"");
        txtMilie.setText(""+model.getBody().getMileage()+"万公里");
        txtPrice.setText(model.getMarket_price()+"万");
        ImageLoader.loadImage(Tool.getPicUrl(mContext, model.getThumbnails_url(), 92, 67), bgImage, R.drawable.pic_bg_default);
    }
}
