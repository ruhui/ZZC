package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.zzcar.zzc.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.view_nav_bardetail)
public class NavBarDetail extends Toolbar {

    private boolean isselect = false;

    @ViewById(R.id.ivMenuLeft)
    ImageView ivMenuLeft;
    @ViewById(R.id.imgRight)
    ImageView ivMenuRight;
    @ViewById(R.id.tv_title)
    TextView mTitleTextView;

    private OnMenuClickListener mOnMenuClickListener;

    public NavBarDetail(Context context) {
        super(context);
    }

    public NavBarDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setOnMenuClickListener(OnMenuClickListener listener) {
        mOnMenuClickListener = listener;
    }

    @Click(R.id.ivMenuLeft)
    void onLeftMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            mOnMenuClickListener.onLeftMenuClick(view);
        }

    }

    @Click(R.id.imgRight)
    void onRightMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            mOnMenuClickListener.onRightMenuClick(view);
        }
    }

    public void setLeftMenuIcon(@DrawableRes int drawableRes) {
        if (drawableRes == -1) {
            return;
        }
        ivMenuLeft.setImageResource(drawableRes);
        setDisplayLeftMenu(true);
    }


    public void setRightMenuIcon(@DrawableRes int drawableRes) {
        if (drawableRes == -1) {
            return;
        }
        ivMenuRight.setImageResource(drawableRes);
        setDisplayRightMenu(true);
    }

    public void setDisplayLeftMenu(boolean enable) {
        ivMenuLeft.setVisibility(enable ? VISIBLE : GONE);
    }

    public void setDisplayRightMenu(boolean enable) {
        ivMenuRight.setVisibility(enable ? VISIBLE : GONE);
    }

    public void setTitleEnabled(boolean isShow) {
        mTitleTextView.setVisibility(isShow ? VISIBLE : GONE);
    }

    public void setRightMenuEnabled(boolean isShow) {
        ivMenuRight.setVisibility(isShow ? VISIBLE : GONE);
    }

    public void setLeftMenuEnabled(boolean isShow) {
        ivMenuLeft.setVisibility(isShow ? VISIBLE : GONE);
    }


    @Override
    public void setTitle(@StringRes int titleRes) {
        if (titleRes <= 0) {
            return;
        }
        mTitleTextView.setText(titleRes);
    }

    public void setTitleName(CharSequence title) {
        mTitleTextView.setText(title);
    }


    public static abstract class OnMenuClickListener {
        public void onLeftMenuClick(View view) {
        }

        public void onRightMenuClick(View view) {
        }

    }

    public boolean isselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
    }
}
