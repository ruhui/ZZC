package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.OrderitemsModel;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 14:15
 **/
@EViewGroup(R.layout.adapter_sureorder)
public class SureOrderViewHold extends LinearLayout {

    @ViewById(R.id.textView8)
    TextView txtName;
    @ViewById(R.id.textView10)
    TextView txtAddress;
    @ViewById(R.id.textView11)
    TextView txtYear;
    @ViewById(R.id.textView12)
    TextView txtMilie;
    @ViewById(R.id.imageView8)
    ImageView imgProductimg;
    @ViewById(R.id.textView14)
    TextView txtPrice;

    private Context mContext;

    public SureOrderViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public SureOrderViewHold(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(OrderitemsModel model, int position){
        ImageLoader.loadImage(Tool.getPicUrl(mContext,model.getThumbnails_url(), 92, 67), imgProductimg, R.drawable.pic_bg_default);
        txtName.setText(model.getProduct_name());
        txtAddress.setText(model.getBody().getCar_province_city());
        txtYear.setText(model.getBody().getOn_number_year()+"");
        txtMilie.setText(""+model.getBody().getMileage()+"万公里");
        txtPrice.setText(model.getMarket_price()+"万");
    }
}
