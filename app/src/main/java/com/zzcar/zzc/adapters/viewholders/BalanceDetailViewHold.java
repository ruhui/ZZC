package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.OrderItemModle;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */

@EViewGroup(R.layout.adapter_balance_detail)
public class BalanceDetailViewHold extends LinearLayout {
    /*商品名称*/
    @ViewById(R.id.textView123)
    TextView txtProdctName;
    /*单价*/
    @ViewById(R.id.textView124)
    TextView txtSingelePrie;
    /*数量*/
    @ViewById(R.id.textView125)
    TextView txtQueity;
    /*金额*/
    @ViewById(R.id.textView126)
    TextView txtPrice;

    public BalanceDetailViewHold(Context context) {
        super(context);
    }

    public BalanceDetailViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(OrderItemModle itemModle){
        txtProdctName.setText(itemModle.getProduct_name());
        txtSingelePrie.setText("￥"+itemModle.getUnit_price());
        txtQueity.setText(itemModle.getQuantity());
        txtPrice.setText(itemModle.getMarket_price());
    }
}
