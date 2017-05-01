package com.zzcar.zzc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.CarTypeSpecs;
import com.zzcar.zzc.models.CarfactoryDto;

import java.util.List;

public class CarTypeAdapter extends BaseAdapter{
	private List<CarTypeSpecs> list = null;
	private Context mContext;

	public CarTypeAdapter(Context mContext, List<CarTypeSpecs> list) {
		this.mContext = mContext;
		this.list = list;
	}
	

	public void updateListView(List<CarTypeSpecs> list){
		this.list = list;
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
		if (list.get(position).getYear_id() == 0){
			return false;
		}else{
			return true;
		}
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final CarTypeSpecs mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item_series, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.imageview = (ImageView) view.findViewById(R.id.imageView3);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		if(mContent.getYear_id() == 0){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvTitle.setVisibility(View.GONE);
			viewHolder.tvLetter.setText(mContent.getName());
		}else{
			viewHolder.tvTitle.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setVisibility(View.GONE);
		}

		viewHolder.tvTitle.setText(this.list.get(position).getName());
		return view;
	}



	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
        ImageView imageview;
	}

}