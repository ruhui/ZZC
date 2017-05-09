package com.zzcar.zzc.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.CommentAdapter;
import com.zzcar.zzc.adapters.PictureAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.CommentModle;
import com.zzcar.zzc.models.MemberModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CarDetailRespose;
import com.zzcar.zzc.networks.responses.CommentResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.pulltorefresh.PullToRefreshScrollView;
import com.zzcar.zzc.views.widget.MyscrollerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 商品详情
 */

@EActivity(R.layout.fragment_commoditydetail)
public class GoodDetailActivity extends BaseActivity {

    @ViewById(R.id.mRollPagerView)
    RollPagerView mRollViewPager;

    @ViewById(R.id.scrollView)
    PullToRefreshScrollView myScrollView;
    @ViewById(R.id.textView17)
    TextView content;
    @ViewById(R.id.textView20)
    TextView marketPrice;
    @ViewById(R.id.priceDingjin)
    TextView priceDingjin;
    @ViewById(R.id.imageView4)
    ImageView headMember;
    @ViewById(R.id.textView21)
    TextView memNick;
    @ViewById(R.id.textView22)
    TextView carCompany;
    @ViewById(R.id.textView15)
    TextView isCheck;
    @ViewById(R.id.textView44)
    TextView descripe;
    @ViewById(R.id.textView23)
    TextView onnumberdes;
    @ViewById(R.id.textView26)
    TextView outFactorydes;
    @ViewById(R.id.textView28)
    TextView carprovincecity;
    @ViewById(R.id.textView30)
    TextView onnumberprovincecity;
    @ViewById(R.id.textView32)
    TextView carColor;
    @ViewById(R.id.textView34)
    TextView newcarprice;
    @ViewById(R.id.textView36)
    TextView miledes;
    @ViewById(R.id.textView38)
    TextView emmidbiao;
    @ViewById(R.id.textView40)
    TextView usertypedes;
    @ViewById(R.id.textView42)
    TextView safeDes;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private CommentAdapter commentAdapter;
    private List<CommentModle> mCommentList = new ArrayList<>();

    private Context mContext;
    private List<String> picList = new ArrayList<>();
    PictureAdapter adapter;
    private int CURTUNPAGE = Constant.DEFAULTPAGE;

    @AfterViews
    void initView(){
        mContext = this;
        int productId = getIntent().getIntExtra("productId", 0);
        initRollView();
        getCarDetail(productId);
        /*获取评论*/
        getComments(productId);

        commentAdapter = new CommentAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(GoodDetailActivity.this));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(commentAdapter);
        commentAdapter.addAll(mCommentList);
    }




    void resertView(CarDetailRespose returnMsg){
        picList.clear();
        picList.addAll(returnMsg.getImage_path());
        adapter.setPicture(picList);
        content.setText(returnMsg.getName());
        marketPrice.setText("￥"+returnMsg.getMarket_price()+"万");
        priceDingjin.setText("订金￥" + returnMsg.getPrice());
        ImageLoader.loadImage(Tool.getPicUrl(GoodDetailActivity.this,returnMsg.getMember().getPhoto(), 30, 30), headMember, Constant.HEADIMG);
        memNick.setText(returnMsg.getMember().getNick());
        carCompany.setText(returnMsg.getMember().getShop_name());
        isCheck.setText(returnMsg.getMember().getAuth_status());
        descripe.setText(returnMsg.getContent());
        onnumberdes.setText(returnMsg.getOn_number_year()+"年" +returnMsg.getOn_number_month()+"月");
        outFactorydes.setText(returnMsg.getOut_factory_year()+"年"+returnMsg.getOut_factory_month()+"月");
        carprovincecity.setText(returnMsg.getCar_province_city());
        onnumberprovincecity.setText(returnMsg.getOn_number_province_city());
        carColor.setText(returnMsg.getColor());
        newcarprice.setText(returnMsg.getNew_car_price()+"万元");
        miledes.setText(returnMsg.getMileage()+"万公里");
        emmidbiao.setText(returnMsg.getEmission());
        usertypedes.setText(returnMsg.getUse_type());
        safeDes.setText(returnMsg.getExp_safe_year()+"年"+returnMsg.getExp_safe_month()+"月");
    }

    private void initRollView() {
        //获取屏幕宽度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(2000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        adapter = new PictureAdapter(mContext, dm.widthPixels, picList);
        //设置适配器
        mRollViewPager.setAdapter(adapter);
        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW, Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);
    }

    @Override
    public void onNetChange(int netMobile) {

    }


    public void setAlpha(float alpha){
//            mToolbar.setAlpha(alpha);
    }

    /*获取商品详情*/
    private void getCarDetail(int productId) {
        Subscriber subscriber = new PosetSubscriber<CarDetailRespose>().getSubscriber(callbak_cardetail);
        UserManager.getCarDetail(productId, subscriber);
    }

    /*获取评论*/
    private void getComments(int productId) {
        Subscriber subscriber = new PosetSubscriber<CarDetailRespose>().getSubscriber(callbak_comments);
        UserManager.getCommentList(productId, CURTUNPAGE, subscriber);
    }

    ResponseResultListener callbak_cardetail = new ResponseResultListener<CarDetailRespose>() {
        @Override
        public void success(CarDetailRespose returnMsg) {
            LogUtil.E("success","success");
            resertView(returnMsg);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };


    ResponseResultListener callbak_comments = new ResponseResultListener<CommentResponse>() {
        @Override
        public void success(CommentResponse returnMsg) {
            LogUtil.E("success", "success");
            mCommentList.addAll(returnMsg.getRows());
            commentAdapter.clear();
            commentAdapter.addAll(mCommentList);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };

}
