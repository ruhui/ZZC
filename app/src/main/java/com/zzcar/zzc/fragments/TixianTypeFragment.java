package com.zzcar.zzc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.TixanTypeAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.EnumSendUserType;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.DepositResponse;
import com.zzcar.zzc.networks.responses.ValueTextResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.dialogs.RecycleViewDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/16 14:10
 **/

@EFragment(R.layout.fragment_tixiantype)
public class TixianTypeFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.textView87)
    TextView brankname;
    @ViewById(R.id.textView89)
    EditText kaihuName;
    @ViewById(R.id.editText6)
    EditText cardCode;
    @ViewById(R.id.telePhone)
    TextView txtPhone;
    @ViewById(R.id.editText7)
    EditText codeNum;
    @ViewById(R.id.textView91)
    TextView txtCode;

    private CountDownTimer myCount;
    /*银行列表*/
    private List<ValueTextResponse> listBrank = new ArrayList();
    private RecycleViewDialog dialog;
    private TixanTypeAdapter adapter;
    //是否去提现(如果是，当提交完申请调到提现界面)
    private boolean gotixian = false;
    private String bankcode = "";
    private boolean timerstart = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gotixian = getArguments().getBoolean("tixian");
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("提现账户");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
        adapter = new TixanTypeAdapter(adapterListener);
        adapter.addAll(listBrank);
        String phone = SecurePreferences.getInstance().getString("USERMOBILE","");
        txtPhone.setText(phone);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*获取提现方式*/
        getdeposit();
        /*获取可提现银行*/
        getBrank();
    }

    private void getBrank() {
        Subscriber subscriber = new PosetSubscriber<List<ValueTextResponse>>().getSubscriber(callback_bank);
        UserManager.getBank(subscriber);
    }

    /*选择银行*/
    @Click(R.id.relaSelebrank)
    void selectBrank(){
        dialog = new RecycleViewDialog(getActivity(),adapter);
        dialog.show();
    }

    /*点击选银行*/
    AdapterListener adapterListener = new AdapterListener<ValueTextResponse>() {
        @Override
        public void setOnItemListener(ValueTextResponse o, int position) {
            dialog.dismiss();
            brankname.setText(o.getText());
            bankcode = o.getValue();
        }
    };

    /*获取提现账号*/
    private void getdeposit(){
        Subscriber subscriber = new PosetSubscriber<DepositResponse>().getSubscriber(callback_usertixian);
        UserManager.getdeposit(subscriber);
    }

    @Click(R.id.txtSubmit)
    void submit(){

        String brankName = brankname.getText().toString();
        String cardnumber = cardCode.getText().toString();
        String accountname = kaihuName.getText().toString();
        String code = codeNum.getText().toString();
        if (TextUtils.isEmpty(bankcode)){
            ToastUtil.showToast("请选择银行");
            return;
        }
        if (TextUtils.isEmpty(cardnumber)){
            ToastUtil.showToast("请输入储值卡卡号");
            return;
        }
        if (TextUtils.isEmpty(accountname)){
            ToastUtil.showToast("请输入开户姓名");
            return;
        }
        if (TextUtils.isEmpty(code)){
            ToastUtil.showToast("请输入验证码");
            return;
        }


        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_savedeposi);
        UserManager.saveDeposit(bankcode, brankName,cardnumber, accountname, code, subscriber);
    }

    /*获取验证码*/
    @Click(R.id.textView91)
    void sendcode(){
        if (!timerstart){
            String mobile = SecurePreferences.getInstance().getString("USERMOBILE", "");
            timerstart = true;
            txtCode.setText(Constant.TIMECOUNT+"s");
            myCount = new MyCount(Constant.TIMECOUNT, 1000).start();
            Subscriber subscriber =  new PosetSubscriber<Boolean>().getSubscriber(callback_sendcode);
            UserManager.getRegsms(mobile, EnumSendUserType.DEPOSIT, subscriber);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (myCount != null){
            myCount.onFinish();
            myCount.cancel();
        }
    }


    /*定义一个倒计时的内部类*/
    class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            txtCode.setText( millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            timerstart = false;
            txtCode.setText(getString(R.string.app_login_getcode));
        }
    }


    /*獲得验证码*/
    ResponseResultListener callback_sendcode = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            if (returnMsg){
                ToastUtil.showToast("获取成功");
            }else{
                ToastUtil.showToast("获取失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("获取失败");
        }
    };

    ResponseResultListener callback_bank = new ResponseResultListener<List<ValueTextResponse>>() {
        @Override
        public void success(List<ValueTextResponse> returnMsg) {
            listBrank.clear();
            listBrank.addAll(returnMsg);
            adapter.clear();
            adapter.addAll(listBrank);
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };


    /*获取提现方式*/
    ResponseResultListener callback_usertixian = new ResponseResultListener<DepositResponse>() {
        @Override
        public void success(DepositResponse returnMsg) {
            LogUtil.E("success", "success");
            brankname.setText(returnMsg.getBank_name());
            kaihuName.setText(returnMsg.getAccount_name());
            cardCode.setText(returnMsg.getBank_card());
            bankcode = returnMsg.getBank();
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };

    /*保存提现方式*/
    ResponseResultListener callback_savedeposi = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            if (returnMsg){
                if (gotixian){
                    //跳转到提现界面
                    showFragment(getActivity(), TixianFragment_.builder().build());
                }else{
                    ToastUtil.showToast("保存成功");
                }
                finishFragment();
            }else{
                ToastUtil.showToast("保存失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("保存失败");
        }
    };
}
