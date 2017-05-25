package com.zzcar.zzc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.GoodDetailActivity_;
import com.zzcar.zzc.activities.MemberMsgActivity;
import com.zzcar.zzc.adapters.HomeCarAdapter;
import com.zzcar.zzc.adapters.UserCarAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.HomeAdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.interfaces.TablayoutTitle;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.HomeCarGet;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/25.
 */

@EFragment(R.layout.fragment_tablayout_pullrefresh)
public class UserCarFromFragment extends BasePullRecyclerFragment {

    @ViewById(R.id.mTab)
    TabLayout mTab;

    private String[] tabList = new String[]{"在售", "已售"};
    private UserCarAdapter carfromAdapter;
    private String userid;
    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private String sallout = "false";
    List<HomeCarGet> mList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userid = getArguments().getString("userid");
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        initTab();
        recyclerView.enableRefresh(true);
        recyclerView.enableLoadMore(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        carfromAdapter = new UserCarAdapter(adapterListener);
        recyclerView.setAdapter(carfromAdapter);
        carfromAdapter.addAll(mList);

        /*加载数据*/
        getCarsData();
    }


    private void initTab() {

        for (int i = 0; i < tabList.length; i++) {
            //i == 0设置为可点击
            mTab.addTab(mTab.newTab().setCustomView(createTabView(tabList[i])), i == 0);
        }

        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CURTURNPAGE = Constant.DEFAULTPAGE;
                mList.clear();
                int position = tab.getPosition();
                if (position == 0){
                    sallout = "false";
                }else{
                    sallout = "true";
                }
                getCarsData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private View createTabView(String titelResId) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_cheyuan_tab, null);
        ((TextView) view.findViewById(R.id.homeTitle)).setText(titelResId);
        view.findViewById(R.id.homeIcon).setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        mList.clear();
        getCarsData();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        getCarsData();
    }

    /*adapter行点击监听*/
    HomeAdapterListener adapterListener = new HomeAdapterListener() {
        @Override
        public void setOnItemClckListener(int position, int productId) {
            Intent intent = new Intent(getActivity(), GoodDetailActivity_.class);
            intent.putExtra("productId", productId);
            startActivity(intent);
        }
    };

    /**
     * 车源列表
     */
    private void getCarsData(){
        Subscriber subscriber = new PosetSubscriber<HomeCarGetResponse>().getSubscriber(callback_cardata);
        UserManager.getUserCarFrom(userid+"", CURTURNPAGE, sallout, subscriber);
    }

    /*帅选回调*/
    ResponseResultListener callback_cardata = new ResponseResultListener<HomeCarGetResponse>() {
        @Override
        public void success(HomeCarGetResponse returnMsg) {
            EventBus.getDefault().post(new TablayoutTitle(returnMsg.getTotal_result()+"", 0));
            if (returnMsg.getRows().size() == 10){
                finishLoad(true);
            }else{
                finishLoad(false);
            }
            closeProgress();
            mList.addAll(returnMsg.getRows());
            if (carfromAdapter != null){
                carfromAdapter.clear();
                carfromAdapter.addAll(mList);
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            finishLoad(false);
            closeProgress();
            LogUtil.E("fialed","fialed");
        }
    };


}
