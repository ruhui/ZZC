package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.AddressListener;
import com.zzcar.zzc.networks.responses.AddressResponse;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/7/11.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_address_edit)
public class EditListAddressViewHold extends LinearLayout {

    @ViewById(R.id.textView219)
    TextView txtName;
    @ViewById(R.id.textView220)
    TextView txtPhone;
    @ViewById(R.id.textView221)
    TextView txtAddress;
    @ViewById(R.id.imageView54)
    RelativeLayout imageView54;

    public EditListAddressViewHold(Context context) {
        super(context);
    }

    public EditListAddressViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(final AddressResponse model, final int position, final AddressListener adapterListener) {
        String htmlStr = "";
        txtName.setText(model.getShip_to());
        txtPhone.setText(model.getPhone());
        if (model.is_default()){
            htmlStr = "<font color='#ff4040'>【默认】</font> 收货地址：" + Tool.trim(model.getRegion_name());
        }else{
            htmlStr = "收货地址：" + Tool.trim(model.getRegion_name());
        }
        txtAddress.setText(Html.fromHtml(htmlStr));

        imageView54.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.cancleAddress(model, position);
            }
        });
    }
}
