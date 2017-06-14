package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.OrderViewHold;
import com.zzcar.zzc.adapters.viewholders.OrderViewHold_;
import com.zzcar.zzc.models.OrderRowsModel;

/**
 * 创建时间： 2017/6/13.
 * 作者：黄如辉
 * 功能描述：
 */

public class MyorderAdapter extends BaseRecyclerAdapter<OrderRowsModel, OrderViewHold> {

    private OrderClickListener orderClickListener;

    public MyorderAdapter(OrderClickListener orderClickListener){
        this.orderClickListener = orderClickListener;
    }

    @Override
    protected OrderViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return OrderViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(OrderViewHold itemView, final OrderRowsModel model, int position) {
        itemView.bind(model, orderClickListener);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderClickListener.itemClickListener(model);
            }
        });

        itemView.findViewById(R.id.textView179).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getStatus() == 1){
                    //支付订金
                    orderClickListener.payorderClickListener(model);
                }else if (model.getStatus() == 5){
                    //交易成功
                    if (!model.isBuyer_confirm()){
                        orderClickListener.successClickListener(model);
                    }
                }
            }
        });

        itemView.findViewById(R.id.textView180).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getStatus() == 1){
                    //取消
                    orderClickListener.cancleOrderClickListener(model);
                }
            }
        });
    }


    public interface OrderClickListener{
        public void itemClickListener( OrderRowsModel model);
        public void payorderClickListener(OrderRowsModel model);
        public void cancleOrderClickListener(OrderRowsModel model);
        public void successClickListener(OrderRowsModel model);
    }
}
