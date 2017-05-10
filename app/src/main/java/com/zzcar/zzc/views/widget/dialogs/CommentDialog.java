package com.zzcar.zzc.views.widget.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.GoodDetailActivity;
import com.zzcar.zzc.interfaces.CommentListener;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/10.
 */

public class CommentDialog extends Dialog {


    public TextView btnBuy;
    private ImageView imageView12,selectphoto, takecamera;
    private EditText editText;
    private RelativeLayout selectPhoto;
    private Context mContext;
    private CommentListener commentListener;
    private boolean selectPhotoshow = false;


    public CommentDialog(Context context, CommentListener commentListener) {
        super(context,  R.style.MyDialogTheme3);
        mContext = context;
        this.commentListener = commentListener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comment);
        selectphoto = (ImageView) findViewById(R.id.imageView10);
        takecamera = (ImageView) findViewById(R.id.imageView11);
        imageView12 = (ImageView) findViewById(R.id.imageView12);
        btnBuy = (TextView) findViewById(R.id.btnBuy);
        editText = (EditText) findViewById(R.id.editText);
        selectPhoto = (RelativeLayout) findViewById(R.id.selectPhoto);
        selectPhoto.setVisibility(View.GONE);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectPhotoshow){
                    selectPhotoshow = false;
                    selectPhoto.setVisibility(View.GONE);
                }
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_NONE:
                        break;
                    case EditorInfo.IME_ACTION_GO:
                        break;
                    case EditorInfo.IME_ACTION_SEARCH:
                        break;
                    case EditorInfo.IME_ACTION_SEND:
                        //发送
                        String content = editText.getText().toString();
                        if (TextUtils.isEmpty(content)){
                            ToastUtil.showToast("请输入内容");
                        }else{
                            commentListener.send(content);
                            Tool.hideInputMethod(mContext, editText);
                            dismiss();
                        }
                        break;
                    case EditorInfo.IME_ACTION_NEXT:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        //相册
        selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                commentListener.photo(content);
            }
        });
        //拍照
        takecamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                commentListener.camera(content);
            }
        });

        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhotoshow = true;
                Tool.hideInputMethod(mContext, editText);
                selectPhoto.setVisibility(View.VISIBLE);
            }
        });

    }

}
