package com.zzcar.zzc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.viewpagerindicator.IconPagerAdapter;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.fragments.MycarfromFragment;
import com.zzcar.zzc.fragments.MycarfromFragment_;
import com.zzcar.zzc.fragments.UserCarFromFragment;
import com.zzcar.zzc.fragments.UserCarFromFragment_;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.interfaces.TablayoutTitle;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.networks.responses.UserMessageResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.NavBar;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.NoScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Transactional;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import rx.Subscriber;

import static com.hyphenate.easeui.EaseConstant.CHATTYPE_SINGLE;

/**
 * 描述：用户资料
 * 作者：黄如辉  时间 2017/5/25.
 */
@EActivity(R.layout.activity_membermsg)
public class MemberMsgActivity extends BaseActivity {

    @ViewById(R.id.imageView26)
    ImageView imgHeadView;
    @ViewById(R.id.mNavbar)
    NavBar mNavbar;
    @ViewById(R.id.mTab)
    TabLayout mTab;
    @ViewById(R.id.mViewPager)
    NoScrollViewPager mPager;
    @ViewById(R.id.textView134)
    TextView txtName;
    @ViewById(R.id.textView135)
    TextView txtshopName;
    @ViewById(R.id.textView136)
    TextView txtStatus;
    @ViewById(R.id.linearLayout4)
    LinearLayout linearLayout4;
    @ViewById(R.id.txtAddFriend)
    TextView txtAddFriend;

    private int userid;
    private UserMessageResponse usermessage;

    @Override
    public void onNetChange(int netMobile) {}


    @AfterViews
    void initView(){
        /*获取用户信息*/
        userid = getIntent().getIntExtra("userid", 0);
        getUserMessage();
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setTitle("众众车");
        mNavbar.setRightMenuIcon(R.drawable.nav_icon_more);
        mNavbar.setOnMenuClickListener(new NavBar.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
            }
        });


    }

    private void getUserMessage() {
        Subscriber subscribe = new PosetSubscriber<UserMessageResponse>().getSubscriber(callback_usermsg);
        UserManager.getUserMessage(userid,subscribe);
    }

    /*发送消息*/
    @Click(R.id.relaSendmsg)
    void sendmsg(){
//        Intent intent = new Intent(this, ECChatActivity.class);
//        intent.putExtra("ec_chat_id", userid+"");
//        startActivity(intent);
        //new出EaseChatFragment或其子类的实例

        if (usermessage == null){
            return;
        }
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("userId", userid+"");
        intent.putExtra("isfriend", usermessage.is_friend());
        intent.putExtra("headImg", usermessage.getPhoto());
        intent.putExtra("chatType", CHATTYPE_SINGLE);
        intent.putExtra("nick", usermessage.getNick());

        startActivity(intent);
//        EaseChatFragment chatFragment = new EaseChatFragment();
//        //传入参数
//        Bundle args = new Bundle();
//        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
//        args.putString(EaseConstant.EXTRA_USER_ID, userid+"");
//        chatFragment.setArguments(args);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.container, chatFragment, chatFragment.getClass().getName());
//        transaction.addToBackStack(chatFragment.getClass().getName());
//        transaction.commitAllowingStateLoss();
    }

    /*添加好友*/
    @Click(R.id.addFriend)
    void addFriend(){
        if (!usermessage.is_friend()){
            Intent intent = new Intent(MemberMsgActivity.this, SendFriendActivity_.class);
            intent.putExtra("userid", userid);
            startActivity(intent);
        }
    }


    private void initTab() {

        ArrayList<MemberMsgActivity.TabInfo> infos = new ArrayList<>();

        UserCarFromFragment fragment_zaishou = UserCarFromFragment_.builder().build();
        Bundle bundle = new Bundle();
        bundle.putString("userid", userid+"");
        fragment_zaishou.setArguments(bundle);

        MycarfromFragment fragment_yishou = MycarfromFragment_.builder().build();
        Bundle bundle1 = new Bundle();
        bundle1.putString("Tag", "1");
        fragment_yishou.setArguments(bundle1);

        MycarfromFragment fragment_weishangjai = MycarfromFragment_.builder().build();
        Bundle bundle2 = new Bundle();
        bundle2.putString("Tag", "2");
        fragment_weishangjai.setArguments(bundle2);

        MemberMsgActivity.TabInfo homeTabInfo = new MemberMsgActivity.TabInfo(fragment_zaishou, "在售("+usermessage.getSell_count()+")");
        infos.add(homeTabInfo);

        MemberMsgActivity.TabInfo preheatTabInfo = new MemberMsgActivity.TabInfo(fragment_yishou, "求购("+usermessage.getDemand_count()+")");
        infos.add(preheatTabInfo);

        MemberMsgActivity.TabInfo shopingTabInfo = new MemberMsgActivity.TabInfo(fragment_weishangjai, "询价("+usermessage.getSupply_count()+")");
        infos.add(shopingTabInfo);

        MemberMsgActivity.TabFragmentAdapter adapter = new MemberMsgActivity.TabFragmentAdapter(infos);
        mPager.setAdapter(adapter);

        for (int i = 0; i < adapter.getCount(); i++) {
            //i == 0设置为可点击
            mTab.addTab(mTab.newTab().setCustomView(createTabView(adapter.getTitleResId(i))), i == 0);
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

    private class TabFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

        private ArrayList<MemberMsgActivity.TabInfo> tabInfos;

        public TabFragmentAdapter(ArrayList<MemberMsgActivity.TabInfo> tabInfos) {
            super(getSupportFragmentManager());
            this.tabInfos = tabInfos;
        }

        public String getTitleResId(int index) {
            return tabInfos.get(index).titleResId;
        }

        @Override
        public int getIconResId(int index) {
            return 0;
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

    private View createTabView(String titelResId) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_cheyuan_tab, null);
        ((TextView) view.findViewById(R.id.homeTitle)).setText(titelResId);
        view.findViewById(R.id.homeIcon).setVisibility(View.INVISIBLE);
        return view;
    }

    private class TabInfo {
        private Fragment fragment;
        private String titleResId;

        public TabInfo(Fragment fragment, String titleResId) {
            this.fragment = fragment;
            this.titleResId = titleResId;
        }
    }


    /*获取用户数据*/
    ResponseResultListener callback_usermsg = new ResponseResultListener<UserMessageResponse>() {
        @Override
        public void success(UserMessageResponse returnMsg) {
            usermessage = returnMsg;
            ImageLoader.loadImage(Tool.getPicUrl(MemberMsgActivity.this, returnMsg.getPhoto(), 40, 40), imgHeadView);
            txtName.setText(returnMsg.getNick());
            txtshopName.setText(returnMsg.getShop_name());
            txtStatus.setText(returnMsg.getAuth_status_name());
            if (returnMsg.is_self()){
                //自己
                linearLayout4.setVisibility(View.GONE);
                txtAddFriend.setBackgroundResource(R.color.app_red);
            }
            if (returnMsg.is_friend()){
                txtAddFriend.setText("已是好友");
                txtAddFriend.setBackgroundResource(R.color.gray);
            }


            initTab();
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };
}
