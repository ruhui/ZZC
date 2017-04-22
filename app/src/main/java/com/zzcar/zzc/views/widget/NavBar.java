package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzcar.zzc.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.view_nav_bar)
public class NavBar extends Toolbar {

    @ViewById
    ImageView ivMenuLeft;
    @ViewById
    ImageView ivMenuRight;
    @ViewById
    ImageView ivMenuRightSecond;

    @ViewById
    View root;

    @ViewById(R.id.tv_title)
    TextView mTitleTextView;


    private int menuLeftIconRes;

    private int menuRightIconRes;
    private int menuRightSecondIconRes;

    private int titleRes;

    private OnMenuClickListener mOnMenuClickListener;

    public NavBar(Context context) {
        super(context);
    }

    public NavBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }


    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NavBar);
        menuLeftIconRes = ta.getResourceId(R.styleable.NavBar_menu_icon_left, -1);
        menuRightIconRes = ta.getResourceId(R.styleable.NavBar_menu_icon_right, -1);
        menuRightSecondIconRes = ta.getResourceId(R.styleable.NavBar_menu_icon_right_second, -1);
        titleRes = ta.getResourceId(R.styleable.NavBar_title_res, -1);
        ta.recycle();
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

    @Click(R.id.ivMenuRight)
    void onRightMenuClick(View view) {
        if (mOnMenuClickListener != null) {
            mOnMenuClickListener.onRightMenuClick(view);
        }

    }

    @Click(R.id.ivMenuRightSecond)
    void onRightSecondMenuCLick(View view) {
        if (mOnMenuClickListener != null) {
            mOnMenuClickListener.onRightMenuSecondClick(view);
        }
    }

    @AfterViews
    void init() {
        setLeftMenuIcon(menuLeftIconRes);
        setRightMenuIcon(menuRightIconRes);
        setRightSecondMenuIcon(menuRightSecondIconRes);
        setTitle(titleRes);
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

    public void setRightSecondMenuIcon(@DrawableRes int drawableRes) {
        if (drawableRes == -1) {
            return;
        }
        ivMenuRightSecond.setImageResource(drawableRes);
        setDisplayRightSecondMenu(true);
    }

    public void setDisplayLeftMenu(boolean enable) {
        ivMenuLeft.setVisibility(enable ? VISIBLE : GONE);
    }

    public void setDisplayRightMenu(boolean enable) {
        ivMenuRight.setVisibility(enable ? VISIBLE : GONE);
    }

    public void setDisplayRightSecondMenu(boolean enable) {
        ivMenuRightSecond.setVisibility(enable ? VISIBLE : GONE);
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


    public void setTitle(@StringRes int titleRes) {
        if (titleRes <= 0) {
            return;
        }
        mTitleTextView.setText(titleRes);
    }

    public ImageView getImageview(){
        return ivMenuRight;
    }

    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }


    public static abstract class OnMenuClickListener {
        public void onLeftMenuClick(View view) {
        }

        public void onRightMenuClick(View view) {
        }

        public void onRightMenuSecondClick(View view) {

        }
    }

}
