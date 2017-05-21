package com.zzcar.zzc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.MoneyDetailHeadModel;
import com.zzcar.zzc.models.ShouzhiItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/20.
 */

public class BalanceDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Object> dataList = new ArrayList<>();
    private AdapterListener adapterListener;

    public BalanceDetailAdapter(Context mContext, List<Object> dataList, AdapterListener adapterListener){
        this.mContext = mContext;
        dataList.addAll(dataList);
        this.adapterListener = adapterListener;
    }

    public void setDataList(List<Object> datalist){
        dataList.clear();
        dataList.addAll(datalist);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1){
            return new MyHeadViewHold(LayoutInflater.from(mContext)
                    .inflate(R.layout.adapter_balancedetail_headview, parent, false));
        }else{
            return new ItemViewHold(LayoutInflater.from(mContext)
                    .inflate(R.layout.adapter_balancedetail_itemview, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (dataList.get(position) instanceof MoneyDetailHeadModel){
            MoneyDetailHeadModel headModel = (MoneyDetailHeadModel) dataList.get(position);
            ((MyHeadViewHold)holder).txtTime.setText(headModel.date);
            ((MyHeadViewHold)holder).txtShouru.setText("收入"+ headModel.total);
        }else{
            final ShouzhiItem itemdata = (ShouzhiItem) dataList.get(position);
            ((ItemViewHold)holder).txtName.setText(itemdata.getTypeDes());
            ((ItemViewHold)holder).txtMoney.setText(""+itemdata.getAmmount());
            ((ItemViewHold)holder).txtStatu.setText(itemdata.getStatus_name());
            ((ItemViewHold)holder).txtTime.setText(itemdata.getTime());
            ((ItemViewHold)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterListener.setOnItemListener(itemdata, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof MoneyDetailHeadModel){
            //头部
            return 1;
        }else{
            return 2;
        }
    }

    /*头部的布局*/
    class MyHeadViewHold extends RecyclerView.ViewHolder{
        TextView txtTime,txtShouru;
        public MyHeadViewHold(View itemView) {
            super(itemView);
            txtTime = (TextView) itemView.findViewById(R.id.textView106);
            txtShouru = (TextView) itemView.findViewById(R.id.textView107);
        }
    }

    /*列表的布局*/
    class ItemViewHold extends RecyclerView.ViewHolder{

        TextView txtName,txtTime,txtMoney, txtStatu;
        public ItemViewHold(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.textView108);
            txtTime = (TextView) itemView.findViewById(R.id.textView109);
            txtMoney = (TextView) itemView.findViewById(R.id.textView110);
            txtStatu = (TextView) itemView.findViewById(R.id.textView111);
        }
    }
}
