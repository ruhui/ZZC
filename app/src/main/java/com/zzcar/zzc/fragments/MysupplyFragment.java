package com.zzcar.zzc.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;

/**
 * 创建时间： 2017/6/15.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_pullrefresh)
public class MysupplyFragment extends BasePullRecyclerFragment {

    private String Tag = "";
    private int CURTURNPAGE = Constant.DEFAULTPAGE;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Tag = getArguments().getString("Tag");
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        showProgress();
        CURTURNPAGE++;
    }
}
