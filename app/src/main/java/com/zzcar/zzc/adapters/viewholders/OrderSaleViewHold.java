package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.CarfromItemAdapter;
import com.zzcar.zzc.adapters.MyorderSaleAdapter;
import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.models.OrderRowsModel;
import com.zzcar.zzc.models.OrderitemsModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/13.
 * 作者：黄如辉
 * 功能描述：
 */

@EViewGroup(R.layout.adapter_order)
public class OrderSaleViewHold extends LinearLayout {

    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.textView178)
    TextView txtDes;
    @ViewById(R.id.textView179)
    TextView txtPayAmount;
    @ViewById(R.id.textView180)
    TextView txtWaitPay;
    @ViewById(R.id.textView177)
    TextView txtMoney;

    private Context mContext;


    public OrderSaleViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public OrderSaleViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(final OrderRowsModel model, final MyorderSaleAdapter.OrderClickListener orderClickListener){
//        我买到的：,待卖家支付2,已完成3,取消4，待确认5，

        txtMoney.setText("合计  ¥"+model.getAmount());
        CarfromItemAdapter adapter = new CarfromItemAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
        adapter.addAll(model.getOrder_items());

        switch (model.getStatus()){
            case  1:
                //待买家付款1
                txtPayAmount.setVisibility(GONE);
                txtWaitPay.setVisibility(GONE);
                txtDes.setVisibility(VISIBLE);
                txtDes.setText("待买家支付订金");
                break;
            case 2:
                //待支付2
                txtPayAmount.setVisibility(VISIBLE);
                txtWaitPay.setVisibility(GONE);
                txtDes.setVisibility(GONE);
                txtPayAmount.setText("支付订金");
                break;
            case 3:
                //已完成3
                txtPayAmount.setVisibility(GONE);
                txtWaitPay.setVisibility(GONE);
                txtDes.setVisibility(VISIBLE);
                txtDes.setText("完成");
                break;
            case 4:
                //取消4
                txtPayAmount.setVisibility(GONE);
                txtWaitPay.setVisibility(GONE);
                txtDes.setVisibility(VISIBLE);
                txtDes.setText("已取消");
                break;
            case 5:
                //待确认5
                if (model.isSeller_confirm()){
                    //true是显示“已确认”
                    txtPayAmount.setVisibility(GONE);
                    txtWaitPay.setVisibility(GONE);
                    txtDes.setVisibility(VISIBLE);
                    txtDes.setText("交易成功");
                }else{
                    txtPayAmount.setVisibility(VISIBLE);
                    txtWaitPay.setVisibility(GONE);
                    txtDes.setVisibility(GONE);
                    txtPayAmount.setText("交易成功");
                }
                break;
        }

        adapter.setItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<OrderitemsModel>() {
            @Override
            public void onItemClick(View view, OrderitemsModel object, int position) {
                orderClickListener.itemClickListener(model);
            }
        });
    }
}
