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
import com.zzcar.zzc.models.MemberModel;
import com.zzcar.zzc.models.ProvenceModel;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import java.util.List;

public class GroupMemberSortAdapter extends BaseAdapter implements SectionIndexer{
	private List<MemberModel> list = null;
	private Context mContext;

	public GroupMemberSortAdapter(Context mContext, List<MemberModel> list) {
		this.mContext = mContext;
		this.list = list;
	}
	

	public void updateListView(List<MemberModel> list){
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
		final MemberModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.adapter_member_item, null);
			viewHolder.imgHead = (ImageView) view.findViewById(R.id.imageView26);
			viewHolder.txtTitle = (TextView) view.findViewById(R.id.textView134);
			viewHolder.txtSunTitle = (TextView) view.findViewById(R.id.textView135);
			viewHolder.txtRenzheng = (TextView) view.findViewById(R.id.textView136);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
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
		viewHolder.txtTitle.setText(this.list.get(position).getNick());
		viewHolder.txtSunTitle.setText(this.list.get(position).getShop_name());
		viewHolder.txtRenzheng.setText(this.list.get(position).getAuth_status_name());
		ImageLoader.loadCircleImage(Tool.getPicUrl(mContext, this.list.get(position).getPhoto(), 40, 40), viewHolder.imgHead, R.drawable.nav_icon_head_default);
		return view;

	}
	
	final static class ViewHolder {
		TextView tvLetter, txtTitle, txtSunTitle, txtRenzheng;
		ImageView imgHead;
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