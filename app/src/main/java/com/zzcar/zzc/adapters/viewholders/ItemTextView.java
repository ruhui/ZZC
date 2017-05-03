package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.CityModel;
import com.zzcar.zzc.networks.requests.SearchRequest;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/2 16:10
 **/

@EViewGroup(R.layout.view_textview)
public class ItemTextView extends RelativeLayout {

    @ViewById(R.id.allcity)
    TextView allcity;

    public ItemTextView(Context context) {
        super(context);
    }

    public ItemTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(CityModel cityModel){
        allcity.setText(cityModel.getName());
    }
}
