package com.zzcar.zzc.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.zzcar.zzc.MyApplication;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.interfaces.DownLoadListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.networks.DownloadTask;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.networks.responses.UserMsgResponse;
import com.zzcar.zzc.networks.responses.VersionResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.dialogs.MyAlertDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.List;

import rx.Subscriber;

@EActivity(R.layout.activity_login_acitivty)
public class LoginAcitivty extends BaseActivity {

    @ViewById(R.id.editText8)
    EditText edtPhone;
    @ViewById(R.id.editText9)
    EditText edtPassword;

    private boolean isLoadingUpdate = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
    }

    @AfterViews
    void initView(){
        String phonenumber = SecurePreferences.getInstance().getString("USERMOBILE", "");
        String password = SecurePreferences.getInstance().getString("USERPASSWORD", "");
        edtPhone.setText(phonenumber);
        edtPassword.setText(password);

        getAppNewVersion();
    }

    @Click(R.id.button2)
    void buttonsecond(){
        String phonenum = edtPhone.getText().toString();
        String password = edtPassword.getText().toString();

        if (TextUtils.isEmpty(phonenum)){
            ToastUtil.showToast("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtil.showToast("请输入密码");
            return;
        }

        showProgress();
        getData(phonenum, password);
    }

    @Click(R.id.button3)
    void regist(){
        //注册
        Intent intent = new Intent(LoginAcitivty.this, RegistActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.textView101)
    void forgetpassword(){
        //忘记密码
        Intent intent = new Intent(LoginAcitivty.this, ForgetPasswordActivity_.class);
        startActivity(intent);
    }

    @Override
    public void onNetChange(int netMobile) {

    }


    private void getData(String phonenum, String password){
        Subscriber subscriber = new PosetSubscriber<LoginResponse>().getSubscriber(callback);
        UserManager.loginzzc(phonenum, password, subscriber);
    }

    ResponseResultListener callback = new ResponseResultListener<LoginResponse>() {
        @Override
        public void success(LoginResponse returnMsg) {
            String phonenum = edtPhone.getText().toString();
            String password = edtPassword.getText().toString();
            SecurePreferences.getInstance().edit().putString("Authorization", returnMsg.access_token).commit();
            SecurePreferences.getInstance().edit().putString("USERMOBILE", phonenum).commit();
            SecurePreferences.getInstance().edit().putString("USERPASSWORD", password).commit();
            SecurePreferences.getInstance().edit().putString("EXPIRESDATE", returnMsg.expires_date).commit();


            /*查看用户是否认证，未认证则跳转到认证界面*/
            getUserMsg();

        }

        @Override
        public void fialed(String resCode, String message) {
            Log.e("fialed","fialed");
            closeProgress();
        }
    };

    /*获取用户信息*/
    public void getUserMsg() {
        Subscriber subscriber = new PosetSubscriber<MineMsgResponse>().getSubscriber(callback_usermsg);
        UserManager.getUserMsg(subscriber);
    }


    ResponseResultListener callback_usermsg = new ResponseResultListener<MineMsgResponse>() {
        @Override
        public void success(MineMsgResponse returnMsg) {
            closeProgress();
            if (returnMsg.getAuth_status() == 3){
                SecurePreferences.getInstance().edit().putInt("Auth_Status", returnMsg.getAuth_status()).commit();
                //登录环信
                SecurePreferences.getInstance().edit().putString("EMChatUsername", String.valueOf(returnMsg.getId())).commit();
                loginEM(String.valueOf(returnMsg.getId()), Constant.EMCHATPASSWORD);
                Intent intent = new Intent(LoginAcitivty.this, MainActivity_.class);
                startActivity(intent);
                finish();
            }else{
                //未认证
                Intent intent = new Intent(LoginAcitivty.this, AuthenticationActivity_.class);
                intent.putExtra("Auth_status", returnMsg.getAuth_status());
                startActivity(intent);
            }
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };


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
                switch (code) {
                    // 网络异常 2
                    case EMError.NETWORK_ERROR:
                        LogUtil.E("网络错误 code: " + code , " message:" + message);
                        break;
                    // 无效的用户名 101
                    case EMError.INVALID_USER_NAME:
                        LogUtil.E("无效的用户名 code: " + code , " message:" + message);
                        break;
                    // 无效的密码 102
                    case EMError.INVALID_PASSWORD:
                        LogUtil.E("无效的密码  code: " + code , " message:" + message);
                        break;
                    // 用户认证失败，用户名或密码错误 202
                    case EMError.USER_AUTHENTICATION_FAILED:
                        LogUtil.E("用户认证失败，用户名或密码错误  code: " + code , " message:" + message);
                        break;
                    // 用户不存在 204
                    case EMError.USER_NOT_FOUND:
                        LogUtil.E("用户不存在  code: " + code , " message:" + message);
                        break;
                    // 无法访问到服务器 300
                    case EMError.SERVER_NOT_REACHABLE:
                        LogUtil.E("无法访问到服务器  code: " + code , " message:" + message);
                        break;
                    // 等待服务器响应超时 301
                    case EMError.SERVER_TIMEOUT:
                        LogUtil.E("等待服务器响应超时  code: " + code , " message:" + message);
                        break;
                    // 服务器繁忙 302
                    case EMError.SERVER_BUSY:
                        LogUtil.E("服务器繁忙 code: " + code , " message:" + message);
                        break;
                    // 未知 Server 异常 303 一般断网会出现这个错误
                    case EMError.SERVER_UNKNOWN_ERROR:
                        LogUtil.E("未知的服务器异常 code: " + code , " message:" + message);
                        break;
                    default:
                        LogUtil.E("ml_sign_in_failed code code: " + code , " message:" + message);
                        break;
                }
            }
        });
    }

    /*版本升级提醒*/
    private void getAppNewVersion() {
        Subscriber subscriber = new PosetSubscriber<VersionResponse>().getSubscriber(callback_version);
        UserManager.getAppNewVersion(subscriber);
    }

    ResponseResultListener callback_version = new ResponseResultListener<VersionResponse>() {
        @Override
        public void success(VersionResponse returnMsg) {
            LogUtil.E("VersionResponse", "VersionResponse");
            try {
                PackageManager pm = getPackageManager();
                PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
                int curtentVersion = packageInfo.versionCode;
                int serverVersion = returnMsg.getAndroid_version_num();
                if (curtentVersion < serverVersion) {
                    //判断是否需要强制升级
                    if (returnMsg.is_force_update() ) {
                        //直接升级
                        if ( !isLoadingUpdate){
                            isLoadingUpdate = true;
                            UpdateCorrect(returnMsg);
                        }
                    } else {
                        //提示升级
                        AlertUpdate(returnMsg);
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("VersionResponse", "VersionResponse");
        }
    };

    private MyAlertDialog myAlertDialog;
    private DownloadTask downloadTask;

    private void AlertUpdate(final VersionResponse returnMsg) {
        myAlertDialog = new MyAlertDialog(LoginAcitivty.this, true);
        myAlertDialog.show();
        myAlertDialog.setTitle("更新");
        myAlertDialog.setContent(Html.fromHtml(returnMsg.getAndroid_update_content()));
        myAlertDialog.setPosiButtion("更新");
        myAlertDialog.setNegButtion("以后更新");
        myAlertDialog.setTopTitlColor(R.color.colorPrimary);
        myAlertDialog.setLeftColor(R.color.colorPrimary);
        myAlertDialog.setRightColor(R.color.color_333333);
        myAlertDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新
                myAlertDialog.dismiss();
                UpdateNow(returnMsg.getAndroid_update_url());
            }
        });
    }

    private void UpdateCorrect(final VersionResponse returnMsg) {
        myAlertDialog = new MyAlertDialog(LoginAcitivty.this, false);
        myAlertDialog.show();
        myAlertDialog.setCancelable(false);
        myAlertDialog.setTitle("更新");
        myAlertDialog.setContent(Html.fromHtml(returnMsg.getAndroid_update_content()));
        myAlertDialog.setPosiButtion("更新");
        myAlertDialog.setTopTitlColor(R.color.app_red);
        myAlertDialog.setLeftColor(R.color.app_red);
        myAlertDialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新
                UpdateNow(returnMsg.getAndroid_update_url());
            }
        });
    }

    /*开始升级*/
    public void UpdateNow(String downloadUrl) {
        /*测试地址*/
//        downloadUrl = "http://orzrcdvjo.bkt.clouddn.com/XGW.apk";
        //开始升级
        Integer filelen = Integer.valueOf(9625927);
        downloadTask = new DownloadTask(filelen, downLoadListener);
        downloadTask.execute(downloadUrl);
    }

    /*下载更新包回调*/
    DownLoadListener downLoadListener = new DownLoadListener() {
        @Override
        public void finishNotify() {
            finishNotifyV();
        }

        @Override
        public void shownotifi() {
            shownotifiV();
        }

        @Override
        public void installApk() {
            installApkV();
        }

        @Override
        public void refreshView(int filelen, int nowlenth) {
            views.setProgressBar(R.id.progressBar1, filelen, nowlenth, false);
            views.setTextViewText(R.id.textView1, nowlenth * 100/filelen + "%");
            mNotification.notify(123, build);
        }

        @Override
        public void dismisNotification() {
            build.flags = Notification.FLAG_AUTO_CANCEL;
        }

        @Override
        public void cancleNotification() {
            mNotification.cancel(123);
        }
    };


    private NotificationManager mNotification;
    private RemoteViews views;
    private Notification build;


    public void finishNotifyV() {
        views.setViewVisibility(R.id.progressBar1, View.INVISIBLE);
        views.setTextViewText(R.id.textView1, "下载完成,点击升级");
        views.setTextViewText(R.id.textView2, "");
        mNotification.cancel(123);
    }

    public void shownotifiV() {
        mNotification = (NotificationManager) MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        views = new RemoteViews(MyApplication.getInstance().getPackageName(), R.layout.notification);
        Intent intent = new Intent();
        PendingIntent ic = PendingIntent.getActivity(this, 0, intent, 0);
        build = new NotificationCompat.Builder(this)
                .setContent(views)
                .setContentTitle("升级")
                .setTicker("开始升级")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.default_login)
                .setContentIntent(ic)
                .build();
        if (Build.VERSION.SDK_INT <= 10) {
            build.contentView = views;
        }
        mNotification.notify(123, build);
    }

    /**
     * 安装APK文件
     */
    private void installApkV() {
        File apkfile = new File(Constant.SAVEAPPFILEPATH);
        if (!apkfile.exists()) {
            return;
        }
//        // 通过Intent安装APK文件
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//                "application/vnd.android.package-archive");
//        startActivity(i);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(LoginAcitivty.this, "com.zzcar.zzc", apkfile);    //第二个参数是manifest中定义的`authorities`
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
//		android.os.Process.killProcess(android.os.Process.myPid());
    }

}
