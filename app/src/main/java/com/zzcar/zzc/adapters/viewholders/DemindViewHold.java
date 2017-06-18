package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.DemendListener;
import com.zzcar.zzc.models.MydemandModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Random;

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
    @ViewById(R.id.textView198)
    TextView txtContent;
    @ViewById(R.id.id_flowlayoutsize)
    TagFlowLayout flowlayout;

    private Context mContext;
    private int drawableD[] = new int[]{R.drawable.shpe_line_noradio_yellow, R.drawable.shape_line_grey, R.drawable.shape_line_blue};
    private int colorD[] = new int[]{R.color.color_yello, R.color.color_d5d5d5, R.color.colorPrimary};

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

        String caroutColor = "";
        for (String str : model.getColor()){
            if (TextUtils.isEmpty(caroutColor)){
                caroutColor = str;
            }else{
                caroutColor += "、"+str;
            }
        }

        String carinColor = "";
        for (String str : model.getInside_color()){
            if (TextUtils.isEmpty(carinColor)){
                carinColor = str;
            }else{
                carinColor += str;
            }
        }

        ArrayList str_list = new ArrayList();
        if (model.getOn_number_min_year() == 0 || model.getOn_number_max_year() == 0){
            str_list.add("不限年龄");
        }else{
            str_list.add(model.getOn_number_min_year()+"-"+model.getOn_number_max_year()+"年");
        }
        if (model.getMin_price() == 0 || model.getMax_price() == 0){
            str_list.add("不限价格");
        }else{
            str_list.add(model.getMin_price()+"-"+model.getMax_price()+"万元");
        }
        if (!TextUtils.isEmpty(carinColor)){
            str_list.add(carinColor);
        }
        if (!TextUtils.isEmpty(caroutColor)){
            str_list.add(caroutColor);
        }

        flowlayout.setMaxSelectCount(0);
        flowlayout.setAdapter(new TagAdapter<String>(str_list)
        {
            @Override
            public View getView(FlowLayout parent, int position, String modle)
            {
                int number = new Random().nextInt(3);
                int color = colorD[number];
                int drawable = drawableD[number];
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.textview_tabflowlayout_color, flowlayout, false);
                tv.setBackgroundResource(drawable);
                tv.setTextColor(color);
                tv.setText(modle);
                return tv;
            }
        });

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