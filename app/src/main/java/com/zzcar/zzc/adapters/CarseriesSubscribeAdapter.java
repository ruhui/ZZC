package com.zzcar.zzc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.BlandModle;
import com.zzcar.zzc.models.CarfactoryDto;
import com.zzcar.zzc.models.SeriesItemsModel;

import java.util.HashMap;
import java.util.List;

public class CarseriesSubscribeAdapter extends BaseAdapter{
	private List<CarfactoryDto> list = null;
	private Context mContext;
	private BlandModle blandModle;

	public CarseriesSubscribeAdapter(Context mContext, List<CarfactoryDto> list, BlandModle blandModle) {
		this.mContext = mContext;
		this.list = list;
		this.blandModle = blandModle;
	}
	

	public void updateListView(List<CarfactoryDto> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public void updateView( BlandModle blandModle){
		this.blandModle = blandModle;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean isEnabled(int position) {
		if (list.get(position).getFactory_id() == 0){
			return false;
		}else{
			return true;
		}
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final CarfactoryDto mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item_subscribe_series, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.imageview = (ImageView) view.findViewById(R.id.imageView3);
			viewHolder.imageView38 = (ImageView) view.findViewById(R.id.imageView38);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		if(mContent.getFactory_id() == 0){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvTitle.setVisibility(View.GONE);
			viewHolder.imageView38.setVisibility(View.GONE);
			viewHolder.tvLetter.setText(mContent.getName());
		}else{
			viewHolder.tvTitle.setVisibility(View.VISIBLE);
			viewHolder.imageView38.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setVisibility(View.GONE);

			if (blandModle != null){
				boolean hascontain = false;
				List<SeriesItemsModel> listmodel = blandModle.getSeries_items();
				for (SeriesItemsModel model : listmodel){
					if (model.getId() == mContent.getId()){
						hascontain = true;
						break;
					}
				}
				if (hascontain){
					viewHolder.imageView38.setImageResource(R.drawable.icon_red_checked);
				}else{
					viewHolder.imageView38.setImageResource(R.drawable.icon_red_unchecked);
				}
			}else{
				viewHolder.imageView38.setImageResource(R.drawable.icon_red_unchecked);
			}

		}

		viewHolder.tvTitle.setText(this.list.get(position).getName());
		return view;
	}



	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
        ImageView imageview;
		ImageView imageView38;
	}

}