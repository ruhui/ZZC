package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.DemendDetailActivity_;
import com.zzcar.zzc.activities.GoodDetailActivity_;
import com.zzcar.zzc.activities.MemberMsgActivity_;
import com.zzcar.zzc.activities.SupplyDetailActivity_;
import com.zzcar.zzc.adapters.LiveMsgAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.HomeLiveMode;
import com.zzcar.zzc.models.ShouzhiItem;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeLivemsgResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/7.
 * 作者：黄如辉
 * 功能描述：实况
 */
@EFragment(R.layout.fragment_footprint)
public class LiveMsgFragment extends BasePullRecyclerFragment {

    private List<HomeLiveMode> dataList = new ArrayList<>();
    private int CURTURNPAGE = Constant.DEFAULTPAGE;

    private LiveMsgAdapter adapter;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("实况");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
        //获取实况
        getHomeLivemsg();
        adapter = new LiveMsgAdapter(getActivity(), adapterListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    /*点击监听*/
    AdapterListener adapterListener = new AdapterListener<ShouzhiItem>() {
        @Override
        public void setOnItemListener(ShouzhiItem o, int position) {
            if (o.getType() == 1){
                //用户信息
                Intent intent = new Intent(getActivity(), MemberMsgActivity_.class);
                intent.putExtra("userid", o.getObject_id());
                startActivity(intent);
            }else if (o.getType() == 2 || o.getType() == 5){
                //车源信息
                Intent intent = new Intent(getActivity(), GoodDetailActivity_.class);
                intent.putExtra("productId", o.getObject_id());
                startActivity(intent);
            }else if (o.getType() == 3){
                //求购详情
                Intent intent = new Intent(getActivity(), DemendDetailActivity_.class);
                intent.putExtra("info_id", o.getObject_id());
                startActivity(intent);
            }else if (o.getType() == 4){
                //询价
                Intent intent = new Intent(getActivity(), SupplyDetailActivity_.class);
                intent.putExtra("info_id", o.getObject_id());
                startActivity(intent);
            }
        }
    };


    /*获取实况*/
    private void getHomeLivemsg() {
        Subscriber subscriber = new PosetSubscriber<HomeLivemsgResponse>().getSubscriber(callback_shikuang);
        UserManager.getHomeLivemsg(CURTURNPAGE, subscriber);
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        dataList.clear();
        CURTURNPAGE = Constant.DEFAULTPAGE;
        getHomeLivemsg();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        showProgress();
        CURTURNPAGE++;
        getHomeLivemsg();
    }


    /*实况*/
    ResponseResultListener callback_shikuang = new ResponseResultListener<HomeLivemsgResponse>() {
        @Override
        public void success(HomeLivemsgResponse returnMsg) {
            closeProgress();
            dataList.clear();
            dataList.addAll(returnMsg.getRows());
            adapter.clear();
            adapter.addAll(dataList);
            if (CURTURNPAGE >= returnMsg.getTotal_pages()){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
            closeProgress();
        }
    };

}
