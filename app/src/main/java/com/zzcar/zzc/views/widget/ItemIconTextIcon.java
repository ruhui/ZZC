package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


/**
 * Created by Administrator on 2017/2/27.
 */

@EViewGroup(R.layout.view_modle_icontexticon)
public class ItemIconTextIcon extends LinearLayout {

    @ViewById()
    RelativeLayout relaItem;

    @ViewById(R.id.txt_title)
    TextView txttitle;

    @ViewById(R.id.txtRight)
    TextView txtRight;

    public void setTitle(String title){
        txttitle.setText(title);
    }

    public ItemIconTextIcon(Context context) {
        super(context);
    }

    public ItemIconTextIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRightText(String rightTitle){
        txtRight.setText(rightTitle);
    }

    public String getRightText(){
        return txtRight.getText().toString();
    }

    public void setOnitemClickListener(OnClickListener listener){
        relaItem.setOnClickListener(listener);
    }
    public void setRightGravity(int gravity){
        txtRight.setGravity(gravity);
    }

    public void setRightTextColor(){
        txtRight.setTextColor(getResources().getColor(R.color.color_333333));
    }

    public void setRightHint(String hint){
        txtRight.setHint(hint);
    }

}
