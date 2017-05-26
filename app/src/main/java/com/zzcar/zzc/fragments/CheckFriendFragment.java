package com.zzcar.zzc.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.CheckFriendAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.ApplyFriendModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.ApplyFriendResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：好友申请
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/26 16:52
 **/
@EFragment(R.layout.fragment_footprint)
public class CheckFriendFragment extends BasePullRecyclerFragment{

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private CheckFriendAdapter adapter;
    private List<ApplyFriendModel> mList = new ArrayList<>();
    private int userid = 0;

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        getFriendList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new CheckFriendAdapter(adapterListener));
        adapter.addAll(mList);
    }

    AdapterListener adapterListener = new AdapterListener<ApplyFriendModel>() {
        @Override
        public void setOnItemListener(ApplyFriendModel o, int position) {
            //添加好有
            addFriend(o.getUser_id());
            userid = o.getUser_id();
        }
    };

    private void addFriend(int user_id) {
        Subscriber subscirbe = new PosetSubscriber<Boolean>().getSubscriber(callback_addfriend);
        UserManager.addFriend(user_id, 1, subscirbe);
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {

    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {

    }

    @Override
    public void onNetChange(int netMobile) {

    }

    /*获取请求的好有列表*/
    private void getFriendList() {
        Subscriber subscriber = new PosetSubscriber<ApplyFriendResponse>().getSubscriber(callback_friend);
        UserManager.getApplyFriend("", CURTURNPAGE, subscriber);
    }

    ResponseResultListener callback_friend = new ResponseResultListener<ApplyFriendResponse>() {
        @Override
        public void success(ApplyFriendResponse returnMsg) {
            if (returnMsg.getTotal_pages() <= CURTURNPAGE ){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            mList.clear();
            mList.addAll(returnMsg.getRows());
            adapter.clear();
            adapter.addAll(mList);
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };

    /*添加好友回调*/
    ResponseResultListener callback_addfriend = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            if (returnMsg){
                ToastUtil.showToast("添加成功");
                for (ApplyFriendModel friend : mList){
                    if (userid == friend.getUser_id()){
                        friend.setIs_friend(true);
                        break;
                    }
                }
                adapter.replaceWith(mList);
            }else{
                ToastUtil.showToast("添加失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("添加失败");
        }
    };
}
