package com.zzcar.zzc.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.IntegralAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.IntegralDetail;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.IntegralDetailResponse;
import com.zzcar.zzc.networks.responses.MybillResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：积分
 * 作者：黄如辉  时间 2017/5/15.
 */

@EFragment(R.layout.fragment_integral)
public class IntegralFragment extends BasePullRecyclerFragment{

    private int CURTURNPAGE = Constant.DEFAULTPAGE;

    private List<IntegralDetail> mList = new ArrayList<>();
    private IntegralAdapter adapter;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.textView75)
    TextView point;

    @Override
    public void onNetChange(int netMobile) {

    }


    /*获取积分明细*/
    private void getIntegralDetail() {
        Subscriber subscriber = new PosetSubscriber<IntegralDetailResponse>().getSubscriber(callback_intefraldetail);
        UserManager.getIntegraldetail(CURTURNPAGE, subscriber);
    }

    /*获取用户财务*/
    public void getUserBill() {
        Subscriber subscriber = new PosetSubscriber<MybillResponse>().getSubscriber(callback_userbill);
        UserManager.getMyBill(subscriber);
    }



    @Override
    protected void initView(PullRecyclerView recyclerView) {
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("积分");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
        getUserBill();
        //获取积分明细
        getIntegralDetail();
        recyclerView.enableRefresh(true);
        recyclerView.enableLoadMore(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new IntegralAdapter());
        adapter.addAll(mList);
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        mList.clear();
        getIntegralDetail();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        getIntegralDetail();
        showProgress();
    }



    ResponseResultListener callback_userbill = new ResponseResultListener<MybillResponse>() {
        @Override
        public void success(MybillResponse returnMsg) {
            point.setText(returnMsg.getIntegral()+"");
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };


    /*获取详情*/
    ResponseResultListener callback_intefraldetail = new ResponseResultListener<IntegralDetailResponse>() {
        @Override
        public void success(IntegralDetailResponse returnMsg) {
            closeProgress();
            if (returnMsg.getTotal_pages() <= CURTURNPAGE){
                finishLoad(true);
            }else{
                finishLoad(false);
            }
            List<IntegralDetail> list = returnMsg.getRows();
            mList.addAll(list);
            adapter.clear();
            adapter.addAll(mList);
        }


        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            finishLoad(true);
        }
    };
}
