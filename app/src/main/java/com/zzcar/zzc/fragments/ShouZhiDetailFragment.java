package com.zzcar.zzc.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.ShouzhiDEtailAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：收支明细
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/17 19:43
 **/

@EFragment(R.layout.view_recycleview)
public class ShouZhiDetailFragment extends BasePullRecyclerFragment{

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    private ShouzhiDEtailAdapter adapter;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        adapter = new ShouzhiDEtailAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {

    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {

    }
}
