package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.DemendListener;
import com.zzcar.zzc.interfaces.SupplyListener;
import com.zzcar.zzc.models.MydemandModel;
import com.zzcar.zzc.models.MysupplyModel;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/15 14:56
 **/
@EViewGroup(R.layout.adapter_demend)
public class DemindViewHold extends LinearLayout {

    @ViewById(R.id.textView192)
    TextView txtTime;
    @ViewById(R.id.imageView43)
    ImageView imgCancle;
    @ViewById(R.id.imageView42)
    ImageView imgEdittext;
    @ViewById(R.id.textView194)
    TextView txtTittle;
    @ViewById(R.id.textView195)
    TextView txtPrice;
    @ViewById(R.id.textView196)
    TextView txtColor;
    @ViewById(R.id.textView197)
    TextView txtYear;
    @ViewById(R.id.textView198)
    TextView txtContent;

    private Context mContext;

    public DemindViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public DemindViewHold(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(final MydemandModel model, final DemendListener adapterListener){
        txtTime.setText(model.getEnd_time());
        txtTittle.setText(model.getName());
        txtContent.setText(model.getContent());
        txtColor.setText(model.getColor()+" "+model.getInside_color());
        txtPrice.setText(model.getMin_price() +"-"+model.getMax_price() );
        txtYear.setText(model.getOn_number_min_year()+"-"+model.getOn_number_max_year()+"年");

        imgCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.cancleClickListener(model);
            }
        });

        imgEdittext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.editClickListener(model);
            }
        });
    }
}
