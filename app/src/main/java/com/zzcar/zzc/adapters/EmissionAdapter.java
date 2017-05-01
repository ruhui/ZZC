package com.zzcar.zzc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.responses.CarChanelResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/25 16:30
 **/
public class EmissionAdapter extends RecyclerView.Adapter<EmissionAdapter.ViewHolder> {

    private ArrayList<CarChanelResponse> mList = new ArrayList<>();
    private ItemClickListener clickListener;
    private Context mContext;
    private HashMap<Integer, Boolean> hashMap = new HashMap<>();

    public EmissionAdapter(Context mContext, List<CarChanelResponse> list, ItemClickListener clickListener){
        this.mContext = mContext;
        mList.addAll(list);
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_modle_selectitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CarChanelResponse carChanelResponse = mList.get(position);
        holder.txtTitle.setText(carChanelResponse.getText());

        if (hashMap.containsKey(position)){
            if (hashMap.get(position)){
                holder.txtTitle.setTextColor(mContext.getResources().getColor(R.color.app_red));
                holder.checkBox.setImageResource(R.drawable.icon_red_checked);
            }else{
                holder.txtTitle.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                holder.checkBox.setImageResource(R.drawable.icon_red_unchecked);
            }
        }else{
            holder.txtTitle.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            holder.checkBox.setImageResource(R.drawable.icon_red_unchecked);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashMap.containsKey(position)){
                    boolean chech = hashMap.get(position);
                    if (chech){
                        holder.txtTitle.setTextColor(mContext.getResources().getColor(R.color.color_333333));
                        holder.checkBox.setImageResource(R.drawable.icon_red_unchecked);
                        hashMap.put(position, false);
                        clickListener.setOnItemClickListener(carChanelResponse.getText(), carChanelResponse.getValue(), false);
                    }else{
                        holder.txtTitle.setTextColor(mContext.getResources().getColor(R.color.app_red));
                        holder.checkBox.setImageResource(R.drawable.icon_red_checked);
                        hashMap.put(position, true);
                        clickListener.setOnItemClickListener(carChanelResponse.getText(), carChanelResponse.getValue(), true);
                    }
                }else{
                    holder.txtTitle.setTextColor(mContext.getResources().getColor(R.color.app_red));
                    holder.checkBox.setImageResource(R.drawable.icon_red_checked);
                    hashMap.put(position, true);
                    clickListener.setOnItemClickListener(carChanelResponse.getText(), carChanelResponse.getValue(), true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setData(List<CarChanelResponse> mChannelList) {
        mList.clear();
        mList.addAll(mChannelList);
        notifyDataSetChanged();
    }

    public void setDefault() {
        hashMap.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public ImageView checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            checkBox = (ImageView) itemView.findViewById(R.id.checkBox);
        }
    }

    public interface ItemClickListener{
        public void setOnItemClickListener(String text, String value, boolean checked);
    }
}
