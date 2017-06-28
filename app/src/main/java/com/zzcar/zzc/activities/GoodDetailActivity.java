package com.zzcar.zzc.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zzcar.zzc.MyApplication;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.CommentAdapter;
import com.zzcar.zzc.adapters.PictureAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.constants.Permission;
import com.zzcar.zzc.fragments.MineBuyFragment_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.CommentListener;
import com.zzcar.zzc.interfaces.ImageUploadListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.interfaces.ShowOrHiddenListener;
import com.zzcar.zzc.manager.PermissonManager;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.CheckoutcartModel;
import com.zzcar.zzc.models.CommentModle;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.UploadFileWithoutLoding;
import com.zzcar.zzc.networks.responses.CarDetailRespose;
import com.zzcar.zzc.networks.responses.CheckoutcartResponse;
import com.zzcar.zzc.networks.responses.CommentResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.PermissionUtili;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.pulltorefresh.PullToRefreshBase;
import com.zzcar.zzc.views.pulltorefresh.PullToRefreshScrollView;
import com.zzcar.zzc.views.widget.NavBarDetail;
import com.zzcar.zzc.views.widget.dialogs.CommentDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.iwf.photopicker.PhotoPicker;
import rx.Subscriber;

/**
 * 商品详情
 */

@EActivity(R.layout.fragment_commoditydetail)
public class GoodDetailActivity extends BaseActivity {

    @ViewById(R.id.mRollPagerView)
    RollPagerView mRollViewPager;

    @ViewById(R.id.mToolbar)
    NavBarDetail mToolbar;
    @ViewById(R.id.line)
    View line;
    @ViewById(R.id.relaBottom)
    RelativeLayout relaBottom;
    @ViewById(R.id.imgRightsdfsd)
    ImageView imgRight;

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


    /*商品id*/
    private int productId;
    /*返回的详情数据*/
    CarDetailRespose cardetail;
    /*at的id*/
    private String atid = null;
    /*评论的内容*/
    private String commentContent;
    private CommentAdapter commentAdapter;
    private List<CommentModle> mCommentList = new ArrayList<>();

    private Context mContext;
    private List<String> picList = new ArrayList<>();
    PictureAdapter adapter;
    private int CURTUNPAGE = Constant.DEFAULTPAGE;
    private boolean isFavorate = false;
    /*保存返回的图片路径*/
    private List<String> successPath = new ArrayList<>();
    private CommentDialog dialog;

    private int REQ_CODE_CAMERA = 10125;
    /*照相机返回的路径*/
    private File tempfile;
    /*获取图片列表*/
    private ArrayList<String> photos = new ArrayList<>();

    @AfterViews
    void initView(){
        myScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        setAlpha(0f);
        mToolbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mToolbar.setTitleName("商品详情");

        mContext = this;
        productId = getIntent().getIntExtra("productId", 0);
        initRollView();
        getCarDetail(productId);
        /*获取评论*/
        getComments(productId);

        commentAdapter = new CommentAdapter(adapterListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GoodDetailActivity.this,
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(commentAdapter);
        commentAdapter.addAll(mCommentList);

        myScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (refreshView.getMode() == PullToRefreshBase.Mode.PULL_FROM_END){
                    CURTUNPAGE++;
                    getComments(productId);
                }
            }
        });
    }

    /*评论的点击行*/
    AdapterListener adapterListener = new AdapterListener<CommentModle>() {
        @Override
        public void setOnItemListener(CommentModle commentModle, int position) {
            //如果是自己的评论，则删除
            atid = commentModle.getUser_id() + "";
            dialog = new CommentDialog(GoodDetailActivity.this, "@"+commentModle.getMember().getNick(), commentListener);
            dialog.show();
        }
    };


    @Subscribe
    public void onMessageEvent(ShowOrHiddenListener event){
        int height =mToolbar.getMeasuredHeight();
        float scrollY = event.height;
        if (scrollY <= 0){
            setAlpha(0);
        }else if(scrollY >= height){
            setAlpha(1);
        }else{
            setAlpha((float) scrollY/height);
        }
    }

    void resertView(CarDetailRespose returnMsg){
        isFavorate = returnMsg.is_favorte();
        setImageFavo();
        picList.clear();
        picList.addAll(returnMsg.getImage_path());
        adapter.setPicture(picList);
        content.setText(returnMsg.getName());
        marketPrice.setText("¥"+returnMsg.getMarket_price()+"万");
        priceDingjin.setText("订金¥" + returnMsg.getPrice());
        ImageLoader.loadImage(Tool.getPicUrl(GoodDetailActivity.this,returnMsg.getMember().getPhoto(), 30, 30), headMember, Constant.HEADIMG);
        memNick.setText(returnMsg.getMember().getNick());
        carCompany.setText(returnMsg.getMember().getShop_name());
        isCheck.setText(returnMsg.getMember().getAuth_status_name());
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

    //设置收藏按钮
    void setImageFavo(){
        if (isFavorate){
            mToolbar.setRightMenuIcon(R.drawable.nav_button_shoucang_default);
            imgRight.setImageResource(R.drawable.nav_button_shoucang_default);
        }else{
            mToolbar.setRightMenuIcon(R.drawable.nav_button_shoucang_default_unselect);
            imgRight.setImageResource(R.drawable.nav_button_shoucang_default_unselect);
        }
    }

    /*立即购买*/
    @Click(R.id.linearLayout)
    void buyOrder(){
        showProgress();
        List<CheckoutcartModel> listmodel = new ArrayList<>();
        CheckoutcartModel model = new CheckoutcartModel(productId, 1);
        listmodel.add(model);
        Subscriber subscriber = new PosetSubscriber<>().getSubscriber(callback_buyorder);
        UserManager.getSureorder(listmodel, 2, subscriber);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (resultCode == getActivity().RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE){
                showProgress();
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
        }else  if (requestCode==REQ_CODE_CAMERA) {
            showProgress();
            String imgPath = tempfile.getPath();
            photos.add(imgPath);
        }
        if (photos.size() > 0){
            for (String imgpath : photos){
                new UploadFileWithoutLoding(uploadListener).execute(imgpath, Constant.UPLOADCOMMENTURL);
            }
        }
    }

    ImageUploadListener uploadListener = new ImageUploadListener(){

        @Override
        public void finishLoading(String imgPath, int position) {
            successPath.add(imgPath);
            if (photos.size() <= successPath.size()){
                //上传评论
                CURTUNPAGE = Constant.DEFAULTPAGE;
                mCommentList.clear();
                saveComment();
            }
        }
    };


    @Override
    public void onNetChange(int netMobile) {

    }

    @Click(R.id.userMember)
    void intentMenberMsg(){
        //跳转到用户界面
        if (cardetail != null){
            Intent intent = new Intent(GoodDetailActivity.this,MemberMsgActivity_.class);
            intent.putExtra("userid", cardetail.getUser_id());
            intent.putExtra("nick", cardetail.getMember().getNick());
            intent.putExtra("photo", cardetail.getMember().getPhoto());
            intent.putExtra("shopname", cardetail.getMember().getShop_name());
            intent.putExtra("statuname", cardetail.getMember().getAuth_status_name());
            startActivity(intent);
        }

    }

    public void setAlpha(float alpha){
        mToolbar.setAlpha(alpha);
        line.setAlpha(alpha);
        if (mToolbar.getAlpha() >= 0.8){
            relaBottom.setVisibility(View.INVISIBLE);
        }else{
            relaBottom.setVisibility(View.VISIBLE);
        }
    }

    @Click(R.id.ivMenuLeft)
    void leftBtn(){
        finish();
    }

    @Click(R.id.editTextlog)
    void showCommentDialog(){
        dialog = new CommentDialog(GoodDetailActivity.this, "", commentListener);
        dialog.show();
    }


    CommentListener commentListener = new CommentListener() {
        @Override
        public void send(String content) {
            //发送消息
            commentContent = content;
            CURTUNPAGE = Constant.DEFAULTPAGE;
            mCommentList.clear();
            saveComment();
            if (dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }

        }

        @Override
        public void camera(String content) {
            //拍照
            commentContent = content;
            if (dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            String[] permission = new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE};
            boolean checked =  PermissionUtili.checkPermission(getActivity(), permission, "需要设置手机权限",
                    "需要使用相机和读取相册权限，请到设置中设置应用权限。");
            if (checked){
                tempfile = getFilePath();

                Uri mOriginUri;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mOriginUri = FileProvider.getUriForFile(MyApplication.getInstance(), MyApplication.getInstance().getPackageName() + ".FileProvider", tempfile);
                } else {
                    mOriginUri = Uri.fromFile(tempfile);
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mOriginUri);
                startActivityForResult(intent, REQ_CODE_CAMERA);

//                Uri Imagefile = Uri.fromFile(tempfile);
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Imagefile);
//                startActivityForResult(cameraIntent, REQ_CODE_CAMERA);
            }
        }

        @Override
        public void photo(String content) {
            commentContent = content;
            //获取相册
            if (dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            String[] permission = new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE};
            boolean checked =  PermissionUtili.checkPermission(getActivity(), permission, "需要设置手机权限",
                    "需要使用相机和读取相册权限，请到设置中设置应用权限。");
            if (checked){
                //相册
                PermissonManager permissonManager = new PermissonManager(GoodDetailActivity.this);
                permissonManager.lacksPermissions();
                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setShowCamera(false)
                        .setShowGif(false)
                        .setPreviewEnabled(false)
                        .setSelected(photos)
                        .start(getActivity(), PhotoPicker.REQUEST_CODE);
            }
        }
    };

    /*返回定义的相册路径*/
    private File getFilePath() {
        File DatalDir = Environment.getExternalStorageDirectory();
        File myDir = new File(DatalDir, "/DCIM/Camera");
        myDir.mkdirs();
        String mDirectoryname = DatalDir.toString() + "/DCIM/Camera";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hhmmss", Locale.SIMPLIFIED_CHINESE);
        File tempfile = new File(mDirectoryname, sdf.format(new Date()) + ".jpg");
        if (tempfile.isFile())
            tempfile.delete();
        return tempfile;
    }

    /*收藏*/
    @Click(R.id.imgRight)
    void setFaverate(){
        if (isFavorate){
            //已收藏设置为未收藏
            isFavorate = false;
        }else{
            isFavorate = true;
        }
        saveFavorite();
        setImageFavo();
    }

    @Click(R.id.btnBuy)
    void bottomBuy(){
        showProgress();
        List<CheckoutcartModel> listmodel = new ArrayList<>();
        CheckoutcartModel model = new CheckoutcartModel(productId, 1);
        listmodel.add(model);
        Subscriber subscriber = new PosetSubscriber<>().getSubscriber(callback_buyorder);
        UserManager.getSureorder(listmodel, 2, subscriber);
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

    /*取消或者添加收藏*/
    private void saveFavorite() {
        Subscriber subscriber = new PosetSubscriber<Integer>().getSubscriber(callbak_savefavorte);
        UserManager.savefavorte(productId, subscriber);
    }

    /*上传评论*/
    private void saveComment() {
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callbak_savecomment);
        UserManager.saveComment(productId, atid, commentContent, successPath, subscriber);
    }

    ResponseResultListener callbak_cardetail = new ResponseResultListener<CarDetailRespose>() {
        @Override
        public void success(CarDetailRespose returnMsg) {
            LogUtil.E("success","success");
            resertView(returnMsg);
            cardetail = returnMsg;
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
            closeProgress();
            myScrollView.onRefreshComplete();
            mCommentList.addAll(returnMsg.getRows());
            commentAdapter.clear();
            commentAdapter.addAll(mCommentList);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            LogUtil.E("fialed", "fialed");
        }
    };

    /*新增评论*/
    ResponseResultListener callbak_savecomment = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            commentContent = "";
            successPath.clear();
            atid = null;
            if (returnMsg){
                ToastUtil.showToast("评论成功");
                showProgress();
                 /*获取评论*/
                getComments(productId);
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            commentContent = "";
            successPath.clear();
            atid = null;
            closeProgress();
            ToastUtil.showToast("评论失败");
        }
    };

    /*收藏*/
    ResponseResultListener callbak_savefavorte = new ResponseResultListener<Integer>() {
        @Override
        public void success(Integer returnMsg) {
            LogUtil.E("success", "success");
            if (isFavorate){
                ToastUtil.showToast("收藏成功");
            }else{
                ToastUtil.showToast("取消成功");
            }

        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("success", "success");
            if (isFavorate){
                ToastUtil.showToast("收藏失败");
            }else{
                ToastUtil.showToast("取消失败");
            }

        }
    };

    /*确认订单回调*/
    ResponseResultListener callback_buyorder = new ResponseResultListener<CheckoutcartResponse>() {
        @Override
        public void success(CheckoutcartResponse returnMsg) {
            closeProgress();
            Intent intent = new Intent(GoodDetailActivity.this, SureOrderActivity_.class);
            intent.putExtra("checkoutcart", returnMsg);
            startActivity(intent);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };



}
