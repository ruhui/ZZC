package com.zzcar.zzc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.BlandModle;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import java.util.HashMap;
import java.util.List;

public class SortSubscribeAdapter extends BaseAdapter implements SectionIndexer{
	private List<BrandListResponse> list = null;
	private Context mContext;
	private HashMap<Integer, BlandModle> hashBland = new HashMap<>();

	public SortSubscribeAdapter(Context mContext, List<BrandListResponse> list, HashMap<Integer, BlandModle> hashBland) {
		this.hashBland = hashBland;
		this.mContext = mContext;
		this.list = list;
	}
	

	public void updateListView(List<BrandListResponse> list){
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

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final BrandListResponse mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.imageview = (ImageView) view.findViewById(R.id.imageView3);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		int section = getSectionForPosition(position);
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getFirst_letter());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
	
		viewHolder.tvTitle.setText(this.list.get(position).getName());
		String imgPath = Tool.getPicUrl(mContext, mContent.getLogo());
		ImageLoader.loadImage(imgPath, viewHolder.imageview);
		if (hashBland.containsKey(mContent.getId())){
			viewHolder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.app_red));
		}else{
			viewHolder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.color_333333));
		}


		
		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
        ImageView imageview;
	}


	public int getSectionForPosition(int position) {
		return list.get(position).getFirst_letter().charAt(0);
	}

	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getFirst_letter();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}