package com.zzcar.zzc.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zzcar.zzc.MyApplication;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.CommentAdapter;
import com.zzcar.zzc.adapters.ImageStrandAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.constants.Permission;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.CommentListener;
import com.zzcar.zzc.interfaces.ImageUploadListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.PermissonManager;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.CommentModle;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.UploadFileWithoutLoding;
import com.zzcar.zzc.networks.responses.CarDetailRespose;
import com.zzcar.zzc.networks.responses.CommentResponse;
import com.zzcar.zzc.networks.responses.DemendDetailResponse;
import com.zzcar.zzc.networks.responses.SupplyDetailResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.PermissionUtili;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.pulltorefresh.PullToRefreshBase;
import com.zzcar.zzc.views.pulltorefresh.PullToRefreshScrollView;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.dialogs.CommentDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import me.iwf.photopicker.PhotoPicker;
import rx.Subscriber;

/**
 * 创建时间： 2017/6/22.
 * 作者：黄如辉
 * 功能描述：询价详情
 */
@EActivity(R.layout.activity_detail_supply)
public class SupplyDetailActivity extends BaseActivity {

    @ViewById(R.id.imageView47)
    ImageView imgHead;
    @ViewById(R.id.textView202)
    TextView txtName;
    @ViewById(R.id.textView203)
    TextView txtContent;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.txtTime)
    TextView txtTime;

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
    /*车况*/
    @ViewById(R.id.textView44)
    TextView descripe;
    /*评论*/
    @ViewById(R.id.mRecyclerViewMement)
    RecyclerView mRecyclerViewMement;
    @ViewById(R.id.scrollView)
    PullToRefreshScrollView myScrollView;
    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    private int REQ_CODE_CAMERA = 10125;
    /*照相机返回的路径*/
    private File tempfile;
    private CommentDialog dialog;
    private String atid = null;
    private int CURTUNPAGE = Constant.DEFAULTPAGE;
    /*评论的内容*/
    private String commentContent;
    private CommentAdapter commentAdapter;
    /*保存返回的图片路径*/
    private List<String> successPath = new ArrayList<>();
    /*获取图片列表*/
    private ArrayList<String> photos = new ArrayList<>();

    private int infoId;
    private Context mContext;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mContext = this;

        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("询价详情");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        infoId = getIntent().getIntExtra("info_id", 0);
        /*获取询价*/
        getSupply();
        /*获取评论*/
        getComments();

        commentAdapter = new CommentAdapter(adapterListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SupplyDetailActivity.this,
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerViewMement.setLayoutManager(linearLayoutManager);
        mRecyclerViewMement.setAdapter(commentAdapter);

        myScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (refreshView.getMode() == PullToRefreshBase.Mode.PULL_FROM_END){
                    CURTUNPAGE++;
                    getComments();
                }else{
                    CURTUNPAGE = Constant.DEFAULTPAGE;
                    getComments();
                }
            }
        });
    }

    void resertView(SupplyDetailResponse model){
        ImageLoader.loadCircleImage(Tool.getPicUrl(mContext, model.getMember().getPhoto(), 40, 40), imgHead, R.drawable.nav_icon_head_default);
        txtName.setText(model.getMember().getNick());
        txtContent.setText(model.getName());
        ImageStrandAdapter adapter = new ImageStrandAdapter(mContext, model.getImage_path());
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setAdapter(adapter);
        adapter.addAll(model.getImage_path());
        txtTime.setText(Tool.getTimeFormat(model.getStart_time()));

        onnumberdes.setText(model.getOn_number_year()+"年" +model.getOn_number_month()+"月");
        outFactorydes.setText(model.getOut_factory_year()+"年"+model.getOut_factory_month()+"月");
        carprovincecity.setText(model.getCar_province_city());
        onnumberprovincecity.setText(model.getOn_number_province_city());
        carColor.setText(model.getColor());
        newcarprice.setText(model.getNew_car_price()+"万元");
        miledes.setText(model.getMileage()+"万公里");
        emmidbiao.setText(model.getEmission());
        usertypedes.setText(model.getUse_type());
        safeDes.setText(model.getExp_safe_year()+"年"+model.getExp_safe_month()+"月");
        descripe.setText(model.getContent());
    }


    @Click(R.id.linearLayout3)
    void showCommentDialog(){
        dialog = new CommentDialog(SupplyDetailActivity.this, "", commentListener);
        dialog.show();
    }


    /*评论的点击行*/
    AdapterListener adapterListener = new AdapterListener<CommentModle>() {
        @Override
        public void setOnItemListener(CommentModle commentModle, int position) {
            //如果是自己的评论，则删除
            atid = commentModle.getUser_id() + "";
            dialog = new CommentDialog(SupplyDetailActivity.this, "@"+commentModle.getMember().getNick(), commentListener);
            dialog.show();
        }
    };


    CommentListener commentListener = new CommentListener() {
        @Override
        public void send(String content) {
            //发送消息
            commentContent = content;
            CURTUNPAGE = Constant.DEFAULTPAGE;
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
                PermissonManager permissonManager = new PermissonManager(SupplyDetailActivity.this);
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
                saveComment();
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

    private void getSupply() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<SupplyDetailResponse>().getSubscriber(callback_getsupply);
        UserManager.getSupplyDetail(infoId, subscriber);
    }

    /*上传评论*/
    private void saveComment() {
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callbak_savecomment);
        UserManager.saveInfocomment(infoId, atid, commentContent, successPath, subscriber);
    }

    /*获取评论*/
    private void getComments() {
        Subscriber subscriber = new PosetSubscriber<CarDetailRespose>().getSubscriber(callbak_comments);
        UserManager.getInfocomments(infoId, CURTUNPAGE, subscriber);
    }

    ResponseResultListener callback_getsupply = new ResponseResultListener<SupplyDetailResponse>() {
        @Override
        public void success(SupplyDetailResponse returnMsg) {
            closeProgress();
            resertView(returnMsg);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
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
                getComments();
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

    ResponseResultListener callbak_comments = new ResponseResultListener<CommentResponse>() {
        @Override
        public void success(CommentResponse returnMsg) {
            LogUtil.E("success", "success");
            closeProgress();
            myScrollView.onRefreshComplete();
            if (CURTUNPAGE == Constant.DEFAULTPAGE){
                commentAdapter.clear();
                commentAdapter.addAll(returnMsg.getRows());
            }else{
                commentAdapter.addAll(returnMsg.getRows());
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            myScrollView.onRefreshComplete();
        }
    };
}
