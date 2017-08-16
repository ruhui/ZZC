package com.zzcar.zzc;

import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.umeng.analytics.MobclickAgent;
import com.zzcar.greendao.BrandListResponseDao;
import com.zzcar.greendao.MyEaseUserDao;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.models.MyEaseUser;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.Tool;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 *
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/18 11:42
 **/

public class MyApplication extends Application {

    private static MyApplication mInstance;
    private Context appContext;
    private EaseUI easeUI;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //集成测试，正式试用时要设为false
        MobclickAgent.setDebugMode(true);
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig( mInstance, Constant.UMENGAPPKEY, "yingyongbao");
        MobclickAgent. startWithConfigure(config);
        //环信初始化
//        EaseUI.getInstance().init(this, null);
//        EMClient.getInstance().setDebugMode(true);
//        initEMchat();
        initEaseUi(mInstance);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mInstance = null;
    }
//    private void initEMchat(){
//        EMOptions options = new EMOptions();
//        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
//        // options.setAppKey("lzan13#hxsdkdemo");
//        // 设置自动登录
//        options.setAutoLogin(true);
//        // 设置是否需要发送已读回执
//        options.setRequireAck(true);
//        // 设置是否需要发送回执，
//        options.setRequireDeliveryAck(true);
//        // 设置是否需要服务器收到消息确认
//        // options.setRequireServerAck(true);
//        // 设置是否根据服务器时间排序，默认是true
//        // options.setSortMessageByServerTime(false);
//        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
//        options.setAcceptInvitationAlways(false);
//        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
//        options.setAutoAcceptGroupInvitation(false);
//        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
//        options.setDeleteMessagesAsExitGroup(false);
//        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
//        options.allowChatroomOwnerLeave(true);
//        // 设置google GCM推送id，国内可以不用设置
//        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
//        // 设置集成小米推送的appid和appkey
//        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);
//        int pid = android.os.Process.myPid();
//        String processAppName = getAppName(pid);
//        // 如果APP启用了远程的service，此application:onCreate会被调用2次
//        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
//        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
//
//        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
//            // 则此application::onCreate 是被service 调用的，直接返回
//            return;
//        }
//        //初始化
////        EMClient.getInstance().init(this, options);
//        EaseUI.getInstance().init(this, options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
//    }
//
//    private String getAppName(int pID) {
//        String processName = null;
//        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
//        List l = am.getRunningAppProcesses();
//        if (l == null){
//            return "";
//        }
//        Iterator i = l.iterator();
//        PackageManager pm = this.getPackageManager();
//        while (i.hasNext()) {
//            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
//            try {
//                if (info.pid == pID) {
//                    processName = info.processName;
//                    return processName;
//                }
//            } catch (Exception e) {
//                // Log.d("Process", "Error>> :"+ e.toString());
//            }
//        }
//        return processName;
//    }

    public void initEaseUi(Context context) {
        EMOptions options = initChatOptions();
//        int pid = android.os.Process.myPid();
//        String processAppName = getAppName(pid);
//        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
//            // 则此application::onCreate 是被service 调用的，直接返回
//            return;
//        }
        //use default options if options is null
        if (EaseUI.getInstance().init(context, options)) {
            appContext = context;
            //获取easeui实例
            easeUI = EaseUI.getInstance();
            //初始化easeui
            easeUI.init(appContext,options);
            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//            EMClient.getInstance().setDebugMode(true);
            setEaseUIProviders();
            //设置全局监听
            setGlobalListeners();
//            broadcastManager = LocalBroadcastManager.getInstance(appContext);
//            initDbDao();
        }
    }
    private EMOptions initChatOptions(){
        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);
        return options;
    }

    protected void setEaseUIProviders() {
        // set profile provider if you want easeUI to handle avatar and nickname
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }
    private EaseUser getUserInfo(String username){
        //获取 EaseUser实例, 这里从内存中读取
        //如果你是从服务器中读读取到的，最好在本地进行缓存
        EaseUser user = null;
//        //如果用户是本人，就设置自己的头像
//        if(username.equals(EMClient.getInstance().getCurrentUser())){
//            user=new EaseUser(username);
//            user.setAvatar((String)SharedPreferencesUtils.getParam(appContext,APPConfig.USER_HEAD_IMG,""));
//            user.setNick((String)SharedPreferencesUtils.getParam(appContext, APPConfig.USER_NAME,"nike"));
//            return user;
//        }
////        if (user==null && getRobotList()!=null){
////            user=getRobotList().get(username);
////        }
//        //收到别人的消息，设置别人的头像
//        if (contactList!=null && contactList.containsKey(username)){
//            user=contactList.get(username);
//        }else { //如果内存中没有，则将本地数据库中的取出到内存中
//            contactList=getContactList();
//            user=contactList.get(username);
//        }
        //如果用户不是你的联系人，则进行初始化
        if(user == null){
            user = new EaseUser(username);
            MyEaseUserDao easeUserDao = GreenDaoUtils.getSingleTon().getmDaoSession().getMyEaseUserDao();
            List<MyEaseUser> userList = easeUserDao.queryBuilder().where(MyEaseUserDao.Properties.Id.like(username)).build().list();
            if (userList.size()>0){
                MyEaseUser myEaseUser = userList.get(0);
                user.setAvatar(Tool.getPicUrl(this, myEaseUser.getAvatar()));
                user.setNick(myEaseUser.getNick());
            }
            EaseCommonUtils.setUserInitialLetter(user);
        }else {
            if (TextUtils.isEmpty(user.getAvatar())){//如果名字为空，则显示环信号码
                user.setNick(user.getUsername());
            }
        }
        LogUtil.E("zcb","头像："+user.getAvatar());
        return user;
    }

//    private void initDbDao() {
//        inviteMessgeDao = new InviteMessgeDao(appContext);
//        userDao = new UserDao(appContext);
//    }
//    public Map<String, RobotUser> getRobotList() {
//        if (isLoggedIn() && robotList == null) {
//            robotList = demoModel.getRobotList();
//        }
//        return robotList;
//    }
//
//    /**
//     *获取所有的联系人信息
//     *
//     * @return
//     */
//    public Map<String, EaseUser> getContactList() {
//        if (isLoggedIn() && contactList == null) {
//            contactList = demoModel.getContactList();
//        }
//        // return a empty non-null object to avoid app crash
//        if(contactList == null){
//            return new Hashtable<String, EaseUser>();
//        }
//        return contactList;
//    }
//    /**
//     * if ever logged in
//     *
//     * @return
//     */
//    public boolean isLoggedIn() {
//        return EMClient.getInstance().isLoggedInBefore();
//    }
    /**
     * set global listener
     */
    protected void setGlobalListeners(){
        registerMessageListener();
    }
    /**
     * Global listener
     * If this event already handled by an activity, you don't need handle it again
     * activityList.size() <= 0 means all activities already in background or not in Activity Stack
     */
    protected void registerMessageListener() {
        EMMessageListener messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    //接收并处理扩展消息
//                    String userName=message.getStringAttribute(Constant.USER_NAME,"");
//                    String userPic=message.getStringAttribute(Constant.HEAD_IMAGE_URL,"");
//                    String userid=message.getStringAttribute(Constant.USER_ID,"");
//
//                    MyEaseUserDao easeUserDao = GreenDaoUtils.getSingleTon().getmDaoSession().getMyEaseUserDao();
//                    MyEaseUser easeUsernew = new MyEaseUser(userid+"", userPic, userName);
//                    long count = easeUserDao.queryBuilder().where(MyEaseUserDao.Properties.Id.eq(easeUsernew.getId())).count();
//                    if (count == 0){
//                        easeUserDao.insert(easeUsernew);
//                    }else{
//                        easeUserDao.update(easeUsernew);
//                    }
                    // in background, do not refresh UI, notify it in notification bar
//                    if(!easeUI.hasForegroundActivies()){
//                        getNotifier().onNewMsg(message);
//                    }
                }
            }
            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    //get message body
                    //end of red packet code
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                }
            }
            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }
            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }
            @Override
            public void onMessageChanged(EMMessage message, Object change) {
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }
}
