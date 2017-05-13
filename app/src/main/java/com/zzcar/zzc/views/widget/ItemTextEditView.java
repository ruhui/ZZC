package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/12 15:13
 **/

@EViewGroup(R.layout.view_itemtextedit)
public class ItemTextEditView extends LinearLayout {

    @ViewById(R.id.textView68)
    TextView txtLeft;
    @ViewById(R.id.editText4)
    EditText edtRight;

    public ItemTextEditView(Context context) {
        super(context);
    }

    public ItemTextEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLeftValue(String value){
        txtLeft.setText(value);
    }

    public void setRightValue(String value){
        edtRight.setText(value);
    }

    public String getRightValue(){
        return edtRight.getText().toString();
    }

    public void setEdtRightHint(String hinttext){
        edtRight.setHint(hinttext);
    }
    public void setInputNumber(){
        edtRight.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
    }
}
