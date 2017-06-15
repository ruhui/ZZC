package com.zzcar.zzc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.MysupplyActivity;
import com.zzcar.zzc.activities.PushSupplyActivity_;
import com.zzcar.zzc.adapters.SupplyAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.RefreshFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.interfaces.SupplyListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.MysupplyModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.MysupplyResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.dialogs.MyAlertDialog;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.greendao.annotation.Id;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/15.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_pullrefresh)
public class MysupplyFragment extends BasePullRecyclerFragment {

    private String Tag = "";
    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private SupplyAdapter dapter_supply;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Tag = getArguments().getString("Tag");
    }

    @Override
    public void onNetChange(int netMobile) {}

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        dapter_supply = new SupplyAdapter(supplyListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(dapter_supply);

        getSupply();
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        getSupply();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        showProgress();
        CURTURNPAGE++;
        getSupply();
    }

    SupplyListener supplyListener = new SupplyListener() {
        @Override
        public void editClickListener(MysupplyModel model) {
            Intent intent = new Intent(getActivity(), PushSupplyActivity_.class);
            intent.putExtra("product_id", model.getInfo_id()+"");
            startActivity(intent);
        }

        @Override
        public void cancleClickListener(final MysupplyModel model) {
            //删除我的询价
            final MyAlertDialog dialog = new MyAlertDialog(getActivity(), true);
            dialog.show();
            dialog.setTitle("删除询价");
            dialog.setContent("是否删除该条询价");
            dialog.setOnPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancleSupply(model);
                    dialog.dismiss();
                }
            });
        }
    };

    @Subscribe
    public void refreshFragment(RefreshFragment refreshFragment){
        if (refreshFragment.refresh &&refreshFragment.TAG.equals("MySupply")){
            CURTURNPAGE = Constant.DEFAULTPAGE;
            getSupply();
        }
    }

    /*获取询价*/
    private void getSupply() {
        Subscriber subscriber = new PosetSubscriber<MysupplyResponse>().getSubscriber(callback_mysupply);
        UserManager.getMysupply(Tag, CURTURNPAGE, subscriber);
    }

    /*删除询价*/
    private void cancleSupply(MysupplyModel model){
        Subscriber subscriber = new PosetSubscriber<MysupplyResponse>().getSubscriber(callback_deletesupply);
        UserManager.deleteSupplyinfo(model.getInfo_id(), subscriber);
    }

    ResponseResultListener callback_mysupply = new ResponseResultListener<MysupplyResponse>() {
        @Override
        public void success(MysupplyResponse returnMsg) {
            closeProgress();
            if (returnMsg.getTotal_pages() < CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            if (CURTURNPAGE == Constant.DEFAULTPAGE){
                dapter_supply.clear();
                dapter_supply.addAll(returnMsg.getRows());
            }else{
                dapter_supply.addAll(returnMsg.getRows());
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    ResponseResultListener callback_deletesupply = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            if (returnMsg){
                ToastUtil.showToast("删除成功");
                CURTURNPAGE = Constant.DEFAULTPAGE;
                getSupply();
            }else{
                ToastUtil.showToast("删除失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("删除失败");
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
