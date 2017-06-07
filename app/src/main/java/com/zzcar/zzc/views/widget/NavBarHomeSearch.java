package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzcar.zzc.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.view_nav_home_search)
public class NavBarHomeSearch extends Toolbar {

    @ViewById(R.id.searchEditText)
    SearchEditText searchEditText;
    @ViewById(R.id.textView)
    TextView searchText;
    @ViewById(R.id.imageView)
    ImageView searchImg;

    public NavBarHomeSearch(Context context) {
        super(context);
    }

    public NavBarHomeSearch(Context context, @Nullable AttributeSet attrs) {
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

    public void onSeacherListener(TextView.OnEditorActionListener listener){
        searchEditText.setOnEditorActionListener(listener);
    }
}
