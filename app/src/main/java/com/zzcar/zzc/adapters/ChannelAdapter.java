package com.zzcar.zzc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.responses.CarChanelResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/25 16:30
 **/
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private String selectPosition = "";
    private ArrayList<CarChanelResponse> mList = new ArrayList<>();
    private ItemClickListener clickListener;
    private Context mContext;

    public ChannelAdapter(Context mContext, List<CarChanelResponse> list, ItemClickListener clickListener){
        this.mContext = mContext;
        mList.addAll(list);
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_modle_icontexticon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CarChanelResponse carChanelResponse = mList.get(position);
        holder.txtTitle.setText(carChanelResponse.getText());
        holder.imgSelect.setVisibility(View.INVISIBLE);
        if (!TextUtils.isEmpty(selectPosition)){
            if (selectPosition == carChanelResponse.getValue()){
                holder.txtTitle.setTextColor(mContext.getResources().getColor(R.color.app_red));
            }else{
                holder.txtTitle.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.setOnItemClickListener(carChanelResponse.getText(), carChanelResponse.getValue(), position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<CarChanelResponse> mChannelList, SearchRequest searchRequest) {
        mList.addAll(mChannelList);
        if (searchRequest.getChannel().equals("")){
            selectPosition ="";
        }else{
            selectPosition = searchRequest.getChannel();
        }
        notifyDataSetChanged();
    }

    public void setData(SearchRequest searchRequest) {
        if (searchRequest.getChannel().equals("")){
            selectPosition ="";
        }else{
            selectPosition = searchRequest.getChannel();
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public ImageView imgSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            imgSelect = (ImageView) itemView.findViewById(R.id.mid_rightImg);
        }
    }

    public interface ItemClickListener{
        public void setOnItemClickListener(String text, String value, int position);
    }
}
