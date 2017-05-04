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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.manager.PermissonManager;

import me.iwf.photopicker.PhotoPicker;

/**
 * Created by Administrator on 2017/3/15.
 */

public class TakePhotoDialog extends Dialog {

    private TakePhotoListener photoListener;
    private ImageView cameraImg,photoImg;
    private TextView cancleTxt;
    private Context mContext;


    public TakePhotoDialog(Context context, TakePhotoListener photoListener) {
        super(context, R.style.MyDialogTheme);
        this.photoListener = photoListener;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_takephoto);
        setViewLocation();
        //外部点击取消
        setCanceledOnTouchOutside(true);

        initView();
    }

    private void initView() {
        cameraImg = (ImageView) findViewById(R.id.cameraImg);
        photoImg = (ImageView) findViewById(R.id.photoImg);
        cancleTxt = (TextView) findViewById(R.id.cancleTxt);

        cancleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //拍照
        cameraImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoListener.selectCamera();
            }
        });

        //相册
        photoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoListener.selectPhoto();
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

    public interface TakePhotoListener{
        public void selectCamera();
        public void selectPhoto();
    }

    public void closedialog(){
        this.dismiss();
    }
}
