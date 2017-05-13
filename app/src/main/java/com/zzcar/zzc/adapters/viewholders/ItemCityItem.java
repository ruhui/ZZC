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
 * 创建时间： 2017/5/2 11:16
 **/

@EViewGroup(R.layout.view_viewhold_city)
public class ItemCityItem extends RelativeLayout {

    @ViewById(R.id.txtCity)
    TextView txtCity;

    public ItemCityItem(Context context) {
        super(context);
    }

    public ItemCityItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(String cityModel){
        txtCity.setText(cityModel);
    }
}
