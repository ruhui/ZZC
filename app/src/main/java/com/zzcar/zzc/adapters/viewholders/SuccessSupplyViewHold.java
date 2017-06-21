package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.ImageStrandAdapter;
import com.zzcar.zzc.models.SupplyModel;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/21.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_success_supply)
public class SuccessSupplyViewHold extends LinearLayout {

    private Context mContext;

    @ViewById(R.id.imageView47)
    ImageView imgHead;
    @ViewById(R.id.textView202)
    TextView txtName;
    @ViewById(R.id.textView203)
    TextView txtContent;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.txtTime)
    TextView txtTime;


    public SuccessSupplyViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public SuccessSupplyViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(SupplyModel model){
        ImageLoader.loadCircleImage(Tool.getPicUrl(mContext, model.getMember().getPhoto(), 40, 40), imgHead, R.drawable.nav_icon_head_default);
        txtName.setText(model.getMember().getNick());
        txtContent.setText(model.getName());
        ImageStrandAdapter adapter = new ImageStrandAdapter(mContext, model.getImage_path());
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setAdapter(adapter);
        adapter.addAll(model.getImage_path());
        txtTime.setText(Tool.getTimeFormat(model.getStart_time()));
    }
}
