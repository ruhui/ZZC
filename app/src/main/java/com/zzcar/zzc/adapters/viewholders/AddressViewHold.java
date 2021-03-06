package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.networks.responses.AddressResponse;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/29.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_address_list)
public class AddressViewHold extends LinearLayout {

    @ViewById(R.id.textView219)
    TextView txtName;
    @ViewById(R.id.textView220)
    TextView txtPhone;
    @ViewById(R.id.textView221)
    TextView txtAddress;
    @ViewById(R.id.imageView49)
    ImageView imageView49;

    public AddressViewHold(Context context) {
        super(context);
    }

    public AddressViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(AddressResponse model){
        String htmlStr = "";
        txtName.setText(model.getShip_to());
        txtPhone.setText(model.getPhone());
        if (model.is_default()){
            imageView49.setImageResource(R.drawable.nav_icon_selected);
            htmlStr = "<font color='#ff4040'>【默认】</font> 收货地址：" + Tool.trim(model.getRegion_name());
        }else{
            imageView49.setImageResource(R.drawable.nav_icon_default);
            htmlStr = "收货地址：" + Tool.trim(model.getRegion_name());
        }
        txtAddress.setText(Html.fromHtml(htmlStr));
    }
}
