package com.zzcar.zzc.views.widget.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.zzcar.zzc.R;

/**
 * 描述：售后弹出框
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/8 15:11
 **/
public class ShowPhotoDialog extends Dialog {

    private Context mContext;
    private ListListener mListener;

    public ShowPhotoDialog(Context context, ListListener mListener) {
        super(context, R.style.MyDialogTheme3);
        mContext = context;
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_showphoto);
        setViewLocation();
        //外部点击取消
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        TextView takephoto = (TextView) findViewById(R.id.txtTuikuan);
        TextView showphoto = (TextView) findViewById(R.id.txtTuihuo);
        TextView tv_cancel = (TextView) findViewById(R.id.tv_cancel);

        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
                mListener.setOnItemClickListener(1);
                dismiss();
            }
        });

        showphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                mListener.setOnItemClickListener(2);
                dismiss();
            }
        });

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

    public interface ListListener{
        public void setOnItemClickListener(int type);
    }
//type 1为拍照  2为相册
}
