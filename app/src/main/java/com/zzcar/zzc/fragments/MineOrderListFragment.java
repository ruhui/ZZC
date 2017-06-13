package com.zzcar.zzc.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.MyorderAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.RefreshListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.OrderRowsModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.OrderListResponse;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/13.
 * 作者：黄如辉
 * 功能描述：
 */

@EFragment(R.layout.fragment_pullrefresh)
public class MineOrderListFragment extends BasePullRecyclerFragment {


    private MyorderAdapter adapter;
    private int toolbarName;
    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private String status = "0";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarName = getArguments().getInt("toolbarName", 0);
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        //我买到的：待付款1,待卖家支付2,已完成3,待确认5
        switch (toolbarName){
            case 1:
                status = "0";
                break;
            case 2:
                status = "1";
                break;
            case 3:
                status = "2";
                break;
            case 4:
                status = "5";
                break;
            case 5:
                status = "3";
                break;
        }

        /*获取列表数据*/
        getOrderList();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new MyorderAdapter());
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
         /*获取列表数据*/
         CURTURNPAGE = Constant.DEFAULTPAGE;
         getOrderList();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        getOrderList();
    }

    void getOrderList(){
        Subscriber subscriber = new PosetSubscriber<OrderListResponse>().getSubscriber(callback_getorder);
        UserManager.buyCarorder(status, CURTURNPAGE, subscriber);
    }

    ResponseResultListener callback_getorder = new ResponseResultListener<OrderListResponse>() {
        @Override
        public void success(OrderListResponse returnMsg) {
            if (returnMsg.getTotal_pages() < CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            if (CURTURNPAGE == Constant.DEFAULTPAGE){
                adapter.clear();
                adapter.addAll(returnMsg.getRows());
            }else{
                adapter.addAll(returnMsg.getRows());
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            finishLoad(false);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().post(new RefreshListener("MINEBUYFRAGMENT"));
    }
}
