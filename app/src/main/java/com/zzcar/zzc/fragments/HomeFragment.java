package com.zzcar.zzc.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.UserMsgResponse;
import com.zzcar.zzc.utils.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import rx.Subscriber;

/**
 * 描述：主页
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/18 15:30
 **/

@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {

    private int[] tabStr = new int[]{R.string.paixu, R.string.qudao, R.string.brand, R.string.price};

    @ViewById(R.id.mTab)
    TabLayout mTab;

    @AfterViews
    void initView(){
        initTab();

        getCarPush();
    }

    private void getCarPush() {
        Subscriber subscriber = new PosetSubscriber<List<HomeCarPushResponse>>().getSubscriber(callback_carpush);
        UserManager.getHomeCarpush(subscriber);
    }

    private void initTab() {
        for (int tab:tabStr) {
            mTab.addTab(mTab.newTab().setCustomView(createTabView(tab)));

        }

        LinearLayout linearLayout = (LinearLayout) mTab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.layout_divider_vertical));
        linearLayout.setPadding(0,20,0,20);
    }

    private View createTabView(int titelResId) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_cheyuan_tab, null);
        ((TextView) view.findViewById(R.id.homeTitle)).setText(titelResId);
        return view;
    }



    @Override
    public void onNetChange(int netMobile) {

    }


    ResponseResultListener callback_carpush = new ResponseResultListener<List<HomeCarPushResponse>>() {
        @Override
        public void success(List<HomeCarPushResponse> returnMsg) {
            LogUtil.E("success", "success");

        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };

}
