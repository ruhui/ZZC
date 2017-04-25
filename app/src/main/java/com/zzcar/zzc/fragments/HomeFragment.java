package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.MainActivity;
import com.zzcar.zzc.activities.SearchActivity_;
import com.zzcar.zzc.adapters.HomeCarAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.FragmentClosePop;
import com.zzcar.zzc.interfaces.PopcloseListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.HomeCarGet;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.PaixuPopwindow;
import com.zzcar.zzc.views.widget.NavBarSearch;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;

/**
 * 描述：主页
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/18 15:30
 **/

@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {

    @Override
    public void onNetChange(int netMobile) {

    }
}
