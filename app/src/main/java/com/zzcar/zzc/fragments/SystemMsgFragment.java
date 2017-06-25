package com.zzcar.zzc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.DemendDetailActivity_;
import com.zzcar.zzc.activities.GoodDetailActivity;
import com.zzcar.zzc.activities.GoodDetailActivity_;
import com.zzcar.zzc.activities.MemberMsgActivity_;
import com.zzcar.zzc.activities.SupplyDetailActivity_;
import com.zzcar.zzc.adapters.SystemAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.SystemModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.SystemMsgResponse;
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
 * 创建时间： 2017/6/25.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_footprint)
public class SystemMsgFragment extends BasePullRecyclerFragment {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    private int CURTTURNPAGE = Constant.DEFAULTPAGE;
    private long objectId;
    private SystemAdapter adapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        objectId = getArguments().getLong("objectId", 0);
    }

    @Override
    public void onNetChange(int netMobile) {

    }



    @Override
    protected void initView(PullRecyclerView recyclerView) {
        mNavbar.setMiddleTitle("系统消息");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setRightTxt("全部已读");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                List<String> listId = new ArrayList<>();
                setRead(listId);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new SystemAdapter(adapterListener));
        getSystemMsg();
    }

    AdapterListener adapterListener = new AdapterListener<SystemModel>() {
        @Override
        public void setOnItemListener(SystemModel o, int position) {
            /*设置已读*/
            List<String> listId = new ArrayList<>();
            listId.clear();
            listId.add(o.getId()+"");
            setRead(listId);

            switch (o.getType()) {
                case 1:
                    //公告
                    PublicDetailFragment fragment = PublicDetailFragment_.builder().build();
                    Bundle bundle = new Bundle();
                    bundle.putLong("objectid", o.getObject_id());
                    fragment.setArguments(bundle);
                    showFragment(getActivity(), fragment);
                    break;
                case 2:
                    // 收款2：跳到收款详情账单页;1
                    BalanceDetailDetailFragment fragment_shoukuan = BalanceDetailDetailFragment_.builder().build();
                    Bundle bundle_shoukuan = new Bundle();
                    bundle_shoukuan.putInt("id", o.getObject_id());
                    bundle_shoukuan.putString("title", o.getTitle());
                    fragment_shoukuan.setArguments(bundle_shoukuan);
                    showFragment(getActivity(), fragment_shoukuan);
                    break;
                case 3:
                    //提现3：跳到提现申请或成功详情;1
                    BalanceDetailDetailFragment fragment_tixian = BalanceDetailDetailFragment_.builder().build();
                    Bundle bundle_tixian = new Bundle();
                    bundle_tixian.putInt("id", o.getObject_id());
                    bundle_tixian.putString("title", o.getTitle());
                    fragment_tixian.setArguments(bundle_tixian);
                    showFragment(getActivity(), fragment_tixian);
                    break;
                case 4:
                    //订单4：跳到订单详情页1
                    OrderDetailFragment fragment_order_detail = OrderDetailFragment_.builder().build();
                    Bundle bundle_order_detail = new Bundle();
                    bundle_order_detail.putString("type", "0");//买家
                    bundle_order_detail.putString("id", o.getOrder_no());
                    fragment_order_detail.setArguments(bundle_order_detail);
                    showFragment(getActivity(), fragment_order_detail);
                    break;
                case 5:
                    //加好友，不跳转0
                    break;
                case 6:
                    //被关注0
                    break;
                case 7:
                    //求购信息被评论-详情1
                    Intent intent_demend = new Intent(getActivity(), DemendDetailActivity_.class);
                    intent_demend.putExtra("info_id", o.getObject_id());
                    startActivity(intent_demend);
                    break;
                case 8:
                    //询价信息被评论详情1
                    Intent intent_supply = new Intent(getActivity(), SupplyDetailActivity_.class);
                    intent_supply.putExtra("info_id", o.getObject_id());
                    startActivity(intent_supply);
                    break;
                case 9:
                    //退款订单1
                    BalanceDetailDetailFragment fragment_refund = BalanceDetailDetailFragment_.builder().build();
                    Bundle bundle_refund = new Bundle();
                    bundle_refund.putInt("id", o.getObject_id());
                    bundle_refund.putString("title", o.getTitle());
                    fragment_refund.setArguments(bundle_refund);
                    showFragment(getActivity(), fragment_refund);
                    break;
                case 10:
                    //积分10：不用跳页面了;
                    break;
                case 11://0
                    break;
                case 12://0
                    //群聊
                    break;
                case 13:
                    //推荐注册推荐注册13：跳页个人主页;1
                    Intent intent_member = new Intent(getActivity(), MemberMsgActivity_.class);
                    intent_member.putExtra("userid", o.getObject_id());
                    startActivity(intent_member);
                    break;
                case 14:
                    //商品（车源）评论-车源详情1
                    Intent intent_14 = new Intent(getActivity(), GoodDetailActivity_.class);
                    intent_14.putExtra("productId", o.getObject_id());
                    startActivity(intent_14);
                    break;
                case 15:
                    //订阅15：车源详情;1
                    Intent intent_15 = new Intent(getActivity(), GoodDetailActivity_.class);
                    intent_15.putExtra("productId", o.getObject_id());
                    startActivity(intent_15);
                    break;
            }
        }
    };

    private void setRead(List<String> ids) {
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_read);
        UserManager.setread(ids, subscriber);
    }


    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTTURNPAGE = Constant.DEFAULTPAGE;
        getSystemMsg();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        showProgress();
        CURTTURNPAGE ++;
        getSystemMsg();
    }


    private void getSystemMsg() {
        Subscriber subscriber = new PosetSubscriber<SystemMsgResponse>().getSubscriber(callback_systemmsg);
        UserManager.getSystemMsg(objectId, CURTTURNPAGE, subscriber);
    }

    ResponseResultListener callback_systemmsg = new ResponseResultListener<SystemMsgResponse>() {
        @Override
        public void success(SystemMsgResponse returnMsg) {
            closeProgress();
            if (returnMsg.getTotal_pages() <= CURTTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }

            if (CURTTURNPAGE == Constant.DEFAULTPAGE){
                adapter.clear();
            }

            adapter.addAll(returnMsg.getRows());

        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    ResponseResultListener callback_read = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            LogUtil.E("success", "success");
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };
}
