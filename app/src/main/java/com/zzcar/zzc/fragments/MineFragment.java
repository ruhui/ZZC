package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.greendao.MyEaseUserDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.*;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.RefreshFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.MyEaseUser;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.FridendListResponse;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.networks.responses.MybillResponse;
import com.zzcar.zzc.networks.responses.UserMsgResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.ItemViewThird;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;

import rx.Subscriber;

/**
 * 描述：我的
 * 作者：黄如辉  时间 2017/5/7.
 */

@EFragment(R.layout.fragment_mine)
public class MineFragment extends BaseFragment {

    @ViewById(R.id.imageView5)
    ImageView myheadImg;
    @ViewById(R.id.textView45)
    TextView txtNick;
    @ViewById(R.id.textView46)
    TextView txtProfessal;
    @ViewById(R.id.textView47)
    TextView txtRenzheng;
    @ViewById(R.id.textView48)
    TextView txtMineMoney;
    @ViewById(R.id.textView50)
    TextView txtPoin;
    @ViewById(R.id.relaMineMoney)
    RelativeLayout relaMineMoney;
    @ViewById(R.id.relaMinePoin)
    RelativeLayout relaMinePoin;
    @ViewById(R.id.relaMineMark)
    RelativeLayout relaMineMark;

    @ViewById(R.id.mybuyItem)
    ItemViewThird mybuyItem;
    @ViewById(R.id.mysaleItem)
    ItemViewThird mysaleItem;
    @ViewById(R.id.mycarItem)
    ItemViewThird mycarItem;
    @ViewById(R.id.myapplybuyItem)
    ItemViewThird myapplybuyItem;
    @ViewById(R.id.myaskItem)
    ItemViewThird myaskItem;
    @ViewById(R.id.orderingItem)
    ItemViewThird orderingItem;
    @ViewById(R.id.mysaveItem)
    ItemViewThird mysaveItem;
    @ViewById(R.id.settingItem)
    ItemViewThird settingItem;

    private MineMsgResponse mineMsgResponse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @AfterViews
    void initView(){
        getUserMsg();
        getUserBill();

        mybuyItem.setNameText("我买到的");mybuyItem.setImgResouse(R.drawable.nav_icon_maichu);
        mysaleItem.setNameText("我卖出的");mysaleItem.setImgResouse(R.drawable.nav_icon_maidao);
        mycarItem.setNameText("我的车源");mycarItem.setImgResouse(R.drawable.nav_icon_cheyuan);
        myapplybuyItem.setNameText("我的求购");myapplybuyItem.setImgResouse(R.drawable.nav_icon_qiugou);
        myaskItem.setNameText("我的询价");myaskItem.setImgResouse(R.drawable.nav_icon_head_xunjia);
        orderingItem.setNameText("订阅");orderingItem.setImgResouse(R.drawable.nav_icon_dingyue);
        mysaveItem.setNameText("我的收藏");mysaveItem.setImgResouse(R.drawable.nav_icon_head_shoucang);
        settingItem.setNameText("设置");settingItem.setImgResouse(R.drawable.nav_icon_shezhi);

        /*我的积分*/
        relaMinePoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(getActivity(), IntegralFragment_.builder().build());
            }
        });

        /*二维码*/
        relaMineMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mineMsgResponse == null){
                    return;
                }
                String qrcode = mineMsgResponse.getQrcode();
                QrcodeFragment qrcodeFragment = QrcodeFragment_.builder().build();
                Bundle bundle = new Bundle();
                bundle.putString("qrcode",qrcode);
                qrcodeFragment.setArguments(bundle);
                showFragment(getActivity(), qrcodeFragment);
            }
        });

        /*钱包*/
        relaMineMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(getActivity(), BalanceFragment_.builder().build());
            }

        });

        /*我的收藏*/
        mysaveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(getActivity(), MyfavcarFragment_.builder().build());
            }
        });

        /*我的车源*/
        mycarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MycarFromActivity_.class);
                startActivity(intent);
            }
        });

        settingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(getActivity(), SettingFragment_.builder().build());
            }
        });

        //订阅
        orderingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubscriberSettingActivity_.class);
                startActivity(intent);
            }
        });
    }

    @Subscribe
    public void refreshData(RefreshFragment refresh){
        if (refresh.refresh){
            //刷新数据
            getUserMsg();
            getUserBill();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /*我的资料*/
    @Click(R.id.relativeLayout2)
    void myMessage(){
        if (mineMsgResponse == null){
            getUserMsg();
            getUserBill();
        }else{
            Intent intent = new Intent(getActivity(), MineInfoActivity_.class);
            intent.putExtra("mineMsgResponse", (Serializable) mineMsgResponse);
            startActivityForResult(intent, 10212);
        }
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    /*获取用户信息*/
    public void getUserMsg() {
        Subscriber subscriber = new PosetSubscriber<MineMsgResponse>().getSubscriber(callback_usermsg);
        UserManager.getUserMsg(subscriber);
    }


    /*获取用户财务*/
    public void getUserBill() {
        Subscriber subscriber = new PosetSubscriber<MybillResponse>().getSubscriber(callback_userbill);
        UserManager.getMyBill(subscriber);
    }


    ResponseResultListener callback_usermsg = new ResponseResultListener<MineMsgResponse>() {
        @Override
        public void success(MineMsgResponse returnMsg) {
            mineMsgResponse = returnMsg;
            resetMineData();

            MyEaseUserDao easeUserDao = GreenDaoUtils.getSingleTon().getmDaoSession().getMyEaseUserDao();
            MyEaseUser easeUser = new MyEaseUser(returnMsg.getId()+"", returnMsg.getPhoto(), returnMsg.getNick());
            long count = easeUserDao.queryBuilder().where(MyEaseUserDao.Properties.Id.eq(easeUser.getId())).count();
            if (count == 0){
                easeUserDao.insert(easeUser);
            }else{
                easeUserDao.update(easeUser);
            }
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };



    ResponseResultListener callback_userbill = new ResponseResultListener<MybillResponse>() {
        @Override
        public void success(MybillResponse returnMsg) {
            txtMineMoney.setText(returnMsg.getBalance()+"");
            txtPoin.setText(returnMsg.getIntegral()+"");
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };

    /*重置我的资料*/
    public void resetMineData(){
        ImageLoader.loadCircleImage(Tool.getPicUrl(getActivity(), mineMsgResponse.getPhoto(), 40, 40), myheadImg,R.drawable.nav_icon_head_default);
        txtNick.setText(mineMsgResponse.getNick());
        txtProfessal.setText(mineMsgResponse.getEmp_name());
        txtRenzheng.setText(mineMsgResponse.getAuth_status_name());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            MineMsgResponse mineMsgResponse = (MineMsgResponse) data.getSerializableExtra("mineMsgResponse");
            this.mineMsgResponse = mineMsgResponse;
            resetMineData();
        }
    }
}
