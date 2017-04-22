package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
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


@EViewGroup(R.layout.view_nav_barsearch)
public class NavBarSearch extends Toolbar {

    @ViewById(R.id.searchEditText)
    SearchEditText searchEditText;
    @ViewById(R.id.textView)
    TextView searchText;
    @ViewById(R.id.imageView)
    ImageView searchImg;

    public NavBarSearch(Context context) {
        super(context);
    }

    public NavBarSearch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public String getSearchText(){
        return searchEditText.getText().toString();
    }

    public void setSearchTextListener(OnClickListener listener){
        searchText.setOnClickListener(listener);
    }

    public void setSearchImgListener(OnClickListener listener){
        searchImg.setOnClickListener(listener);
    }
}
