package com.zzcar.zzc.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.EmptyResponse;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.dialogs.BuyTpyeDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/12.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_jiaoyidanbao)
public class JiaoyiFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.textView163)
    TextView txtNick;
    @ViewById(R.id.textView165)
    TextView txtCompanyName;
    @ViewById(R.id.imgSelt)
    ImageView imgSelt;
    @ViewById(R.id.textView102)
    TextView txtXieyi;
    @ViewById(R.id.txtSecuery)
    TextView txtSecuery;
    @ViewById(R.id.relaSelect)
    RelativeLayout relaSelect;
    @ViewById(R.id.imageView39)
    ImageView imageView39;
    @ViewById(R.id.textView166)
    TextView textView166;
    @ViewById(R.id.txtSubmit)
    TextView txtSubmit;


    private boolean selectxieyi = false;
    private BuyTpyeDialog dialog;
    private boolean isSecurity = false;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("交易担保店铺");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);

        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        getUserMsg();
    }


    @Click(R.id.relaSelect)
    void selectXieyi(){
        if (selectxieyi){
            selectxieyi = false;
            imgSelt.setImageResource(R.drawable.nav_icon_default);
        }else{
            selectxieyi = true;
            imgSelt.setImageResource(R.drawable.nav_icon_selected);
        }
    }

    /*提交申请*/
    @Click(R.id.txtSubmit)
    void submitApply(){
        if (isSecurity){
            //是否是担保商铺，取消申请
            refundSecurity();
        }else{
            if (!selectxieyi){
                ToastUtil.showToast("请勾选协议");
                return;
            }

            dialog = new BuyTpyeDialog(getActivity(), adapterListener);
            dialog.show();
        }
    }



    AdapterListener adapterListener = new AdapterListener<String>() {
        @Override
        public void setOnItemListener(String o, int position) {
            buySecurity(o);
            dialog.dismiss();
        }
    };


    /*获取用户信息*/
    public void getUserMsg() {
        Subscriber subscriber = new PosetSubscriber<MineMsgResponse>().getSubscriber(callback_usermsg);
        UserManager.getUserMsg(subscriber);
    }

    /*退交易保证金*/
    private void refundSecurity() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_refund);
        UserManager.refundSecurity(subscriber);
    }

    /*提交申请*/
    public void buySecurity(String pay_code) {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<String>().getSubscriber(callback_buyecurity);
        UserManager.buySecurity(pay_code, subscriber);
    }

    ResponseResultListener callback_usermsg = new ResponseResultListener<MineMsgResponse>() {
        @Override
        public void success(MineMsgResponse returnMsg) {
            txtNick.setText(returnMsg.getNick());
            txtCompanyName.setText(returnMsg.getShop_name());
            isSecurity = returnMsg.isSecurity();
            if (returnMsg.isSecurity()){
                txtSecuery.setText("交易担保店铺");
                relaSelect.setVisibility(View.INVISIBLE);
                imageView39.setVisibility(View.VISIBLE);
                textView166.setText("保证金在钱包中冻结");
                txtSubmit.setText("取消交易担保");
            }else{
                txtSecuery.setText("3000元");
                relaSelect.setVisibility(View.VISIBLE);
                imageView39.setVisibility(View.INVISIBLE);
                textView166.setText("保证金缴纳后将在钱包中冻结");
                txtSubmit.setText("申请开通");
            }
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };

    ResponseResultListener callback_buyecurity = new ResponseResultListener<String>() {
        @Override
        public void success(String returnMsg) {
            closeProgress();
            ToastUtil.showToast("申请成功");
            getUserMsg();
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            ToastUtil.showToast("申请失败");
        }
    };

    ResponseResultListener callback_refund = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("申请成功");
                getUserMsg();
            }else{
                ToastUtil.showToast("申请失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("申请失败");
            closeProgress();
        }
    };
}
