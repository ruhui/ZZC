package com.zzcar.zzc.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/18.
 * 作者：黄如辉
 * 功能描述：
 */

@EFragment(R.layout.fragment_msgf)
public class BusinessDemendFragment extends BasePullRecyclerFragment{

    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

//    private BusinessDemendAdapter adapter;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        getDemend();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(adapter = new BusinessDemendAdapter());
    }

    private void getDemend() {

    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {

    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {

    }
}
