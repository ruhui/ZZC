package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.NetUtils;
import com.viewpagerindicator.IconPagerAdapter;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.CarFromFragment;
import com.zzcar.zzc.fragments.CarFromFragment_;
import com.zzcar.zzc.fragments.HomeFragment;
import com.zzcar.zzc.fragments.HomeFragment_;
import com.zzcar.zzc.fragments.MessageFragment_;
import com.zzcar.zzc.fragments.MineFragment_;
import com.zzcar.zzc.interfaces.FragmentClosePop;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.views.widget.NoScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.tab)
    TabLayout mTab;

    @ViewById(R.id.pager)
    NoScrollViewPager mPager;

    @ViewById(R.id.networkState)
    TextView networkState;




    public boolean popisShowing = false;

    @AfterViews
    void initView(){
         /*初始化环信消息监听‘*/
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        /*好友管理监听*/
        EMClient.getInstance().contactManager().setContactListener(friendListener);
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        setup();
        mPager.setNoScroll(true);
        if (getNetMobile() == -1){
            networkState.setVisibility(View.VISIBLE);
        }else{
            networkState.setVisibility(View.INVISIBLE);
        }

        // 判断sdk是否登录成功过，并没有退出和被踢，否则跳转到登陆界面
        if (!EMClient.getInstance().isLoggedInBefore()) {
            String emchatusername = SecurePreferences.getInstance().getString("EMChatUsername", "");
            loginEM(emchatusername, Constant.EMCHATPASSWORD);
        }
    }


    private void setup() {
        ArrayList<TabInfo> infos = new ArrayList<>();

        TabInfo homeTabInfo = new TabInfo(HomeFragment_.builder().build(), R.drawable.tab_home, R.string.tab_home);
        infos.add(homeTabInfo);

        TabInfo preheatTabInfo = new TabInfo(CarFromFragment_.builder().build(), R.drawable.tab_home_car, R.string.tab_carfrom);
        infos.add(preheatTabInfo);

        TabInfo shopingTabInfo = new TabInfo(HomeFragment_.builder().build(), R.drawable.tab_home_shangji, R.string.tab_shangji);
        infos.add(shopingTabInfo);

        TabInfo mineTabInfo = new TabInfo(MessageFragment_.builder().build(), R.drawable.tab_home_message, R.string.tab_message);
        infos.add(mineTabInfo);

        TabInfo mineTabInfos = new TabInfo(MineFragment_.builder().build(), R.drawable.tab_home_mine, R.string.tab_mine);
        infos.add(mineTabInfos);

        TabFragmentAdapter adapter = new TabFragmentAdapter(infos);
        mPager.setAdapter(adapter);

        for (int i = 0; i < adapter.getCount(); i++) {
            //i == 0设置为可点击
            mTab.addTab(mTab.newTab().setCustomView(createTabView(adapter.getIconResId(i), adapter.getTitleResId(i))), i == 0);
        }

        mTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mPager));
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTab.getTabAt(position).select();
            }
        });
    }

    private View createTabView(int iconResId, int titelResId) {
        View view = getLayoutInflater().inflate(R.layout.item_home, null);
        ((ImageView) view.findViewById(R.id.homeIcon)).setImageResource(iconResId);
        ((TextView) view.findViewById(R.id.homeTitle)).setText(titelResId);
        return view;
    }

    private class TabFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

        private ArrayList<TabInfo> tabInfos;

        public TabFragmentAdapter(ArrayList<TabInfo> tabInfos) {
            super(getSupportFragmentManager());
            this.tabInfos = tabInfos;
        }

        @Override
        public int getIconResId(int index) {
            return tabInfos.get(index).iconResId;
        }

        public int getTitleResId(int index) {
            return tabInfos.get(index).titleResId;
        }

        @Override
        public int getCount() {
            return tabInfos.size();
        }


        @Override
        public Fragment getItem(int position) {
            return tabInfos.get(position).fragment;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //取消销毁fragment,提高性能
            //super.destroyItem(container, position, object);
        }
    }

    private class TabInfo {
        private Fragment fragment;
        private int iconResId;
        private int titleResId;

        public TabInfo(Fragment fragment, int resId, int titleResId) {
            this.fragment = fragment;
            this.iconResId = resId;
            this.titleResId = titleResId;
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0){
            if (popisShowing){
                EventBus.getDefault().post(new FragmentClosePop(true));
            }else{
                moveTaskToBack(true);
            }
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public void onNetChange(int netMobile) {
        if (netMobile == -1){
            networkState.setVisibility(View.VISIBLE);
        }else{
            networkState.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.E("onActivityResult", "onActivityResult");
    }

    /*环信好友监听*/
    //好友状态请求
    EMContactListener friendListener = new EMContactListener() {

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请
            LogUtil.E("onContactInvited", "onContactInvited");
        }

        @Override
        public void onFriendRequestAccepted(String s) {
            //好友请求被同意
            LogUtil.E("onContactInvited", "onContactInvited");
        }

        @Override
        public void onFriendRequestDeclined(String s) {
            //好友请求被拒绝
            LogUtil.E("onContactInvited", "onContactInvited");
        }

        @Override
        public void onContactDeleted(String username) {
            //被删除时回调此方法
            LogUtil.E("onContactInvited", "onContactInvited");
        }


        @Override
        public void onContactAdded(String username) {
            //增加了联系人时回调此方法
            LogUtil.E("onContactInvited", "onContactInvited");
        }
    };

    /*环信消息监听*/
    /*消息监听*/
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息  收到消息，刷新列表，并且获取未读的条数
            LogUtil.E("onMessageReceived","onMessageReceived");
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            LogUtil.E("onCmdMessageReceived","onCmdMessageReceived");
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            LogUtil.E("onMessageRead","onMessageRead");
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            LogUtil.E("onMessageChanged","onMessageChanged");
        }
    };

    //实现ConnectionListener接口 链接状态
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }
        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(error == EMError.USER_REMOVED){
                        // 显示帐号已经被移除
                    }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        String emchatusername = SecurePreferences.getInstance().getString("EMChatUsername", "");
                        loginEM(emchatusername, Constant.EMCHATPASSWORD);
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)){
                            //连接不到聊天服务器
                        } else {
                            //当前网络不可用，请检查网络设置
                        }
                    }
                }
            });
        }
    }

    void loginEM(String userName, String password){
        EMClient.getInstance().login(userName, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         /*移除监听*/
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
}
