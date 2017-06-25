package com.zzcar.zzc.fragments;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.BulletinResponse;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/25.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_publicdetail)
public class PublicDetailFragment extends BaseFragment {

    @ViewById(R.id.textView204)
    TextView txtTitle;
    @ViewById(R.id.textView205)
    TextView txtContent;
    @ViewById(R.id.textView206)
    TextView txtTime;
    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    private long objectid;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        objectid = getArguments().getLong("objectid", 0);
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        getBulletin();

        mNavbar.setMiddleTitle("公告详情");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
    }

    private void getBulletin() {
        Subscriber subscriber = new PosetSubscriber<BulletinResponse>().getSubscriber(callback_bulletin);
        UserManager.getBulletin(objectid, subscriber);
    }

    ResponseResultListener callback_bulletin = new ResponseResultListener<BulletinResponse>() {
        @Override
        public void success(BulletinResponse returnMsg) {
            if (returnMsg.is_read()){
                txtTitle.setTextColor(getResources().getColor(R.color.gray));
                txtContent.setTextColor(getResources().getColor(R.color.gray));
                txtTime.setTextColor(getResources().getColor(R.color.gray));
            }else{
                txtTitle.setTextColor(getResources().getColor(R.color.color_090909));
                txtContent.setTextColor(getResources().getColor(R.color.color_090909));
                txtTime.setTextColor(getResources().getColor(R.color.color_090909));
            }
            txtTitle.setText(returnMsg.getTitle());
            txtContent.setText(returnMsg.getContent());
            txtTime.setText(returnMsg.getCreate_time());
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };
}
