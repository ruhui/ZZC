package com.zzcar.zzc.views.widget.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;


/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/8 19:01
 **/
public class RecycleViewDialog extends Dialog {

    private RecyclerView mRecycleView;
    private TextView tv_cancel;
    private Context mContext;
    private BaseRecyclerAdapter adapter;

    public RecycleViewDialog(Context context, BaseRecyclerAdapter adapter) {
        super(context, R.style.MyDialogTheme3);
        mContext = context;
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recycleview);
        setViewLocation();
        //外部点击取消
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.mRecycleView);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);

        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleView.setAdapter(adapter);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation(){
        DisplayMetrics dm = new DisplayMetrics();
        ((AppCompatActivity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }

}