package com.zzcar.zzc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.NetUtils;
import com.viewpagerindicator.IconPagerAdapter;
import com.zzcar.greendao.MyEaseUserDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.CarFromFragment;
import com.zzcar.zzc.fragments.CarFromFragment_;
import com.zzcar.zzc.fragments.HomeFragment;
import com.zzcar.zzc.fragments.HomeFragment_;
import com.zzcar.zzc.fragments.MessageFragment_;
import com.zzcar.zzc.fragments.MineFragment_;
import com.zzcar.zzc.interfaces.ActivityFinish;
import com.zzcar.zzc.interfaces.FragmentClosePop;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.MyEaseUser;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.views.widget.NoScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static com.hyphenate.easeui.EaseConstant.CHATTYPE_SINGLE;

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

        /*开启倒计时*/
        restart();
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
            mTab.addTab(mTab.newTab().setCustomView(createTabView(adapter.getIconResId(i), adapter.getTitleResId(i), i)), i == 0);
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

    private View createTabView(int iconResId, int titelResId, int i) {
        View view = getLayoutInflater().inflate(R.layout.item_home, null);
        ((ImageView) view.findViewById(R.id.homeIcon)).setImageResource(iconResId);
        ((TextView) view.findViewById(R.id.homeTitle)).setText(titelResId);
        view.findViewById(R.id.imageView29).setVisibility(View.GONE);
        if (i == 3){
            int unread = EMClient.getInstance().chatManager().getUnreadMessageCount();
            if (unread > 0 && mTab.getChildCount() > 0){
                view.findViewById(R.id.imageView29).setVisibility(View.VISIBLE);
            }else if ( mTab.getChildCount() > 0){
                view.findViewById(R.id.imageView29).setVisibility(View.GONE);
            }
        }

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
        public void onContactDeleted(final String username) {
            //被删除时回调此方法
            LogUtil.E("onContactInvited", "onContactInvited");
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
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
            if (messages.size()>0){
                EMMessage newMessage = messages.get(0);
                EaseUI.getInstance().getNotifier().onNewMsg(newMessage);

                EaseUI.getInstance().getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

                    @Override
                    public String getTitle(EMMessage message) {
                        //修改标题,这里使用默认
                        return message.getUserName();
                    }

                    @Override
                    public int getSmallIcon(EMMessage message) {
                        //设置小图标，这里为默认
                        return R.mipmap.ic_launcher;
                    }

                    @Override
                    public String getDisplayedText(EMMessage message) {
                        // 设置状态栏的消息提示，可以根据message的类型做相应提示
                        String ticker = EaseCommonUtils.getMessageDigest(message, MainActivity.this);
                        if(message.getType() == EMMessage.Type.TXT){
                            ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                        }
//                        EaseUser user = getUserInfo(message.getFrom());
//                        if(user != null){
//                            return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
//                        }else{
                        MyEaseUserDao easeUserDao = GreenDaoUtils.getSingleTon().getmDaoSession().getMyEaseUserDao();
                        List<MyEaseUser> listmyEaseUser = easeUserDao.queryBuilder().where(MyEaseUserDao.Properties.Id.eq(message.getFrom()+"")).list();
                        String nick = "";
                        if (listmyEaseUser.size() > 0){
                            nick = listmyEaseUser.get(0).getNick();
                        }

                        return nick + ": " + ticker;
//                        }
                    }

                    @Override
                    public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                        MyEaseUserDao easeUserDao = GreenDaoUtils.getSingleTon().getmDaoSession().getMyEaseUserDao();
                        List<MyEaseUser> listmyEaseUser = easeUserDao.queryBuilder().where(MyEaseUserDao.Properties.Id.eq(fromUsersNum+"")).list();
                        String nick = "";
                        if (listmyEaseUser.size() > 0){
                            nick = listmyEaseUser.get(0).getNick();
                        }
                        return nick + "发来了" + messageNum + "条消息";
                    }

                    @Override
                    public Intent getLaunchIntent(EMMessage message) {
                        //设置点击通知栏跳转事件
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        //有电话时优先跳转到通话页面
//                if(isVideoCalling){
//                    intent = new Intent(appContext, VideoCallActivity.class);
//                }else if(isVoiceCalling){
//                    intent = new Intent(appContext, VoiceCallActivity.class);
//                }else{
                        EMMessage.ChatType chatType = message.getChatType();
                        if (chatType == EMMessage.ChatType.Chat) { // 单聊信息
                            intent.putExtra("userId", message.getFrom());
                            intent.putExtra("chatType", CHATTYPE_SINGLE);
                        } else { // 群聊信息
                            // message.getTo()为群聊id
                            intent.putExtra("userId", message.getTo());
                            if(chatType == EMMessage.ChatType.GroupChat){
                                intent.putExtra("chatType", EaseConstant.CHATTYPE_GROUP);
                            }else{
                                intent.putExtra("chatType", EaseConstant.CHATTYPE_CHATROOM);
                            }
                        }
//                }
                        return intent;
                    }
                });

                //获取未读数，并设置tab
                int unread = EMClient.getInstance().chatManager().getUnreadMessageCount();
                if (unread > 0 && mTab.getChildCount() > 0){
                    View view = mTab.getChildAt(3);
                    view.findViewById(R.id.imageView29).setVisibility(View.VISIBLE);
                }else if ( mTab.getChildCount() > 0){
                    View view = mTab.getChildAt(3);
                    view.findViewById(R.id.imageView29).setVisibility(View.GONE);
                }

            }
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
        if (timer != null){
            timer.cancel();
        }
    }

    /*开启倒计时，当为8分钟的时候自动刷新token*/
    public void restart() {
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(480000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            //刷新数据
            refreshlogin();
        }
    };

    //刷新数据
    private void refreshlogin() {
        Subscriber subscriber = new PosetSubscriber<LoginResponse>().getSubscriber(callback_refhresh);
        UserManager.refreshLogin(subscriber);
    }

    //刷新数据回调
    ResponseResultListener callback_refhresh = new ResponseResultListener<LoginResponse>() {
        @Override
        public void success(LoginResponse returnMsg) {
            SecurePreferences.getInstance().edit().putString("Authorization", returnMsg.access_token).commit();
            SecurePreferences.getInstance().edit().putString("EXPIRESDATE", returnMsg.expires_date).commit();
            restart();
        }

        @Override
        public void fialed(String resCode, String message) {
            EventBus.getDefault().post(new ActivityFinish(true));
            Intent intent = new Intent(MainActivity.this, LoginAcitivty_.class);
            startActivity(intent);
            finish();
        }
    };



}
