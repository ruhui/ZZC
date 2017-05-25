package com.zzcar.zzc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.MycarFromActivity;
import com.zzcar.zzc.activities.PushCarActivity_;
import com.zzcar.zzc.adapters.MycarfromAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.RefreshFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.interfaces.TablayoutTitle;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.MyfavcarModle;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.MyfavcarResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */
@EFragment(R.layout.fragment_pullrefresh)
public class MycarfromFragment extends BasePullRecyclerFragment {

    private MycarfromAdapter adapter;
    private List<MyfavcarModle> mList = new ArrayList<>();
    private String Tag = "";
    private int CURTURNPAGE = Constant.DEFAULTPAGE;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Tag = getArguments().getString("Tag");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        adapter = new MycarfromAdapter(adapterListener, Tag);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.addAll(mList);
        getMycar();
    }

    /*获取我的车源*/
    private void getMycar() {
        Subscriber subscriber = new PosetSubscriber<MyfavcarResponse>().getSubscriber(callback_mycar);
        UserManager.getMycar(CURTURNPAGE, Tag, subscriber );
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        mList.clear();
        getMycar();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        showProgress();
        CURTURNPAGE++;
        getMycar();
    }


    //刷新界面
    @Subscribe
    public void refresh(RefreshFragment refresh){
        if(refresh.TAG.equals("Mycar") && refresh.refresh){
            CURTURNPAGE = Constant.DEFAULTPAGE;
            mList.clear();
            getMycar();
        }
    }

    AdapterListener adapterListener = new AdapterListener<MyfavcarModle>() {
        @Override
        public void setOnItemListener(MyfavcarModle o, int position) {
            switch (position){
                case 0:
                    //上下架
                    upandDown(o.getProduct_id());
                    break;
                case 1:
                    //编辑
                    Intent intent = new Intent(getActivity(), PushCarActivity_.class);
                    intent.putExtra("product_id", o.getProduct_id()+"");
                    startActivity(intent);
                    break;
            }
        }
    };

    /*上下架*/
    void upandDown(int productid){
        Subscriber subscriber = new PosetSubscriber<Integer>().getSubscriber(callback_upanddown);
        UserManager.updown(productid, subscriber );
    }


    ResponseResultListener callback_mycar = new ResponseResultListener<MyfavcarResponse>() {
        @Override
        public void success(MyfavcarResponse returnMsg) {
            closeProgress();
            if (returnMsg.getTotal_pages() <= CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            List<MyfavcarModle>  listdata = returnMsg.getRows();
            mList.addAll(listdata);
            adapter.clear();
            adapter.addAll(mList);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    /*上下架回调*/
    ResponseResultListener callback_upanddown = new ResponseResultListener<Integer>() {
        @Override
        public void success(Integer returnMsg) {
            ToastUtil.showToast("操作成功");
            showProgress();
            CURTURNPAGE =Constant.DEFAULTPAGE;
            mList.clear();
            getMycar();
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("操作失败");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
