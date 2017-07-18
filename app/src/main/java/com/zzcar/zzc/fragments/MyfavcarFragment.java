package com.zzcar.zzc.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.MyfavCarAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.MyfavcarModle;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.MyfavcarResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：我的收藏
 * 作者：黄如辉  时间 2017/5/21.
 */

@EFragment(R.layout.fragment_footprint)
public class MyfavcarFragment extends BasePullRecyclerFragment{

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    private boolean isFavorate = false;
    private MyfavCarAdapter adapter;
    private List<MyfavcarModle> favList = new ArrayList<>();
    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private int CURTURNPOSITION;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("我的收藏");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        adapter = new MyfavCarAdapter(adapterListener, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.addAll(favList);
        getFavcar();
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        favList.clear();
        getFavcar();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        getFavcar();
        showProgress();
    }

    AdapterListener adapterListener = new AdapterListener<MyfavcarModle>() {
        @Override
        public void setOnItemListener(MyfavcarModle o, int position) {
            //删除收藏
            CURTURNPOSITION = position;
            Subscriber subscriber = new PosetSubscriber<>().getSubscriber(callback_cancle);
            UserManager.savefavorte(o.getProduct_id(), subscriber);
        }
    };

    /*获取收藏列表*/
    void getFavcar(){
        Subscriber subscriber = new PosetSubscriber<MyfavcarResponse>().getSubscriber(callback_favcar);
        UserManager.getMyfavcar(CURTURNPAGE, subscriber);
    }

    ResponseResultListener callback_favcar = new ResponseResultListener<MyfavcarResponse>() {
        @Override
        public void success(MyfavcarResponse returnMsg) {
            closeProgress();
            if (returnMsg.getTotal_pages() <= CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            List<MyfavcarModle> myfavcarList = returnMsg.getRows();
            favList.addAll(myfavcarList);
            adapter.clear();
            adapter.addAll(favList);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    ResponseResultListener callback_cancle = new ResponseResultListener() {
        @Override
        public void success(Object returnMsg) {
            ToastUtil.showToast("取消成功");
            adapter.remove(CURTURNPOSITION);
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("取消失败");
        }
    };
}
