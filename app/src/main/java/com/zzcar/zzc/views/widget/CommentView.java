package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.GoodDetailActivity;
import com.zzcar.zzc.adapters.ImageStrandAdapter;
import com.zzcar.zzc.adapters.PictureAdapter;
import com.zzcar.zzc.models.CommentModle;
import com.zzcar.zzc.networks.responses.CommentResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/8.
 */

@EViewGroup(R.layout.adapter_item_comments)
public class CommentView extends LinearLayout {

    @ViewById(R.id.imageView4)
    ImageView headImg;
    @ViewById(R.id.txtNamt)
    TextView userName;
    @ViewById(R.id.textView55)
    TextView txtTime;
    @ViewById(R.id.textView57)
    TextView txtContent;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private Context mContext;

    public CommentView(Context context) {
        super(context);
        mContext = context;
    }

    public CommentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(CommentModle commentModle){
        ImageLoader.loadImage(Tool.getPicUrl(mContext, commentModle.getMember().getPhoto(), 30, 30),headImg, R.drawable.nav_icon_head_default);
        userName.setText(commentModle.getMember().getNick());
        String content = commentModle.getContent();
        if (commentModle.getAt_member() != null){
            String atname = "@" +commentModle.getAt_member().getNick()+":";
            SpannableString styledText = new SpannableString(atname + content);
            styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style_comment_atmember), 0, atname.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txtContent.setText(styledText, TextView.BufferType.SPANNABLE);
        }else{
            txtContent.setText(content);
        }

        txtTime.setText(Tool.getTimeFormat(commentModle.getCreate_time()));

        ImageStrandAdapter adapter = new ImageStrandAdapter(mContext);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setAdapter(adapter);
        adapter.addAll(commentModle.getImage_path());
    }
}
