package com.zzcar.zzc.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.CommentAdapter;
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
import org.greenrobot.eventbus.Subscribe;

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
 * 功能描述：求购详情
 */
@EActivity(R.layout.activity_detail_demend)
public class DemendDetailActivity extends BaseActivity {

    @ViewById(R.id.imageView45)
    ImageView imgHead;
    @ViewById(R.id.textView200)
    TextView txtName;
    @ViewById(R.id.textView201)
    TextView txtTime;
    @ViewById(R.id.textView194)
    TextView txtTittle;
    @ViewById(R.id.textView198)
    TextView txtContent;
    @ViewById(R.id.id_flowlayoutsize)
    TagFlowLayout flowlayout;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.scrollView)
    PullToRefreshScrollView myScrollView;
    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    private int CURTUNPAGE = Constant.DEFAULTPAGE;
    private int drawableD[] = new int[]{R.drawable.shpe_line_noradio_yellow, R.drawable.shape_line_grey, R.drawable.shape_line_blue};
    private int colorD[] = new int[]{R.color.color_yello, R.color.color_d5d5d5, R.color.colorPrimary};
    private CommentAdapter commentAdapter;
    private int infoId;
    /*at的id*/
    private String atid = null;
    private CommentDialog dialog;
    /*评论的内容*/
    private String commentContent;
    /*保存返回的图片路径*/
    private List<String> successPath = new ArrayList<>();
    /*获取图片列表*/
    private ArrayList<String> photos = new ArrayList<>();
    /*照相机返回的路径*/
    private File tempfile;
    private int REQ_CODE_CAMERA = 10125;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("求购详情");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });
//        myScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DemendDetailActivity.this,
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        commentAdapter = new CommentAdapter(adapterListener);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(commentAdapter);

        showProgress();
        infoId = getIntent().getIntExtra("info_id", 0);
        /*获取求购详情*/
        getDementDetail(infoId);
          /*获取评论*/
        getComments(infoId);

        myScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (refreshView.getMode() == PullToRefreshBase.Mode.PULL_FROM_END){
                    CURTUNPAGE++;
                    getComments(infoId);
                }else{
                    CURTUNPAGE = Constant.DEFAULTPAGE;
                    getComments(infoId);
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
            dialog = new CommentDialog(DemendDetailActivity.this, "@"+commentModle.getMember().getNick(), commentListener);
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
                Uri Imagefile = Uri.fromFile(tempfile);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Imagefile);
                startActivityForResult(cameraIntent, REQ_CODE_CAMERA);
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
                PermissonManager permissonManager = new PermissonManager(DemendDetailActivity.this);
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

    @Click(R.id.linearLayout3)
    void showCommentDialog(){
        dialog = new CommentDialog(DemendDetailActivity.this, "", commentListener);
        dialog.show();
    }

    private void resertView(DemendDetailResponse model) {
        ImageLoader.loadCircleImage(Tool.getPicUrl(DemendDetailActivity.this,
                model.getMember().getPhoto(), 20, 20), imgHead, R.drawable.nav_icon_head_default);
        txtName.setText(model.getMember().getNick());
        txtTime.setText(Tool.getTimeFormat(model.getStart_time()));
        txtTittle.setText(model.getName());
        txtContent.setText(model.getContent());

        String caroutColor = "";
        for (String str : model.getColor()){
            if (TextUtils.isEmpty(caroutColor)){
                caroutColor = str;
            }else{
                caroutColor += "、"+str;
            }
        }

        String carinColor = "";
        for (String str : model.getInside_color()){
            if (TextUtils.isEmpty(carinColor)){
                carinColor = str;
            }else{
                carinColor += str;
            }
        }

        ArrayList str_list = new ArrayList();
        if (model.getOn_number_min_year() == 0 || model.getOn_number_max_year() == 0){
            str_list.add("不限年龄");
        }else{
            str_list.add(model.getOn_number_min_year()+"-"+model.getOn_number_max_year()+"年");
        }
        if (model.getMin_price() == 0 || model.getMax_price() == 0){
            str_list.add("不限价格");
        }else{
            str_list.add(model.getMin_price()+"-"+model.getMax_price()+"万元");
        }
        if (!TextUtils.isEmpty(carinColor)){
            str_list.add(carinColor);
        }
        if (!TextUtils.isEmpty(caroutColor)){
            str_list.add(caroutColor);
        }

        flowlayout.setMaxSelectCount(0);
        flowlayout.setAdapter(new TagAdapter<String>(str_list)
        {
            @Override
            public View getView(FlowLayout parent, int position, String modle)
            {
                int number = new Random().nextInt(3);
                int color = colorD[number];
                int drawable = drawableD[number];
                TextView tv = (TextView) LayoutInflater.from(DemendDetailActivity.this).
                        inflate(R.layout.textview_tabflowlayout_color, flowlayout, false);
                tv.setBackgroundResource(drawable);
                tv.setTextColor(color);
                tv.setText(modle);
                return tv;
            }
        });
    }




    /*获取求购详情*/
    private void getDementDetail(int infoId) {
        Subscriber subscribe = new PosetSubscriber<DemendDetailResponse>().getSubscriber(callback_demenddetail);
        UserManager.getDemendDetail(infoId, subscribe);
    }

    /*获取评论*/
    private void getComments(int productId) {
        Subscriber subscriber = new PosetSubscriber<CarDetailRespose>().getSubscriber(callbak_comments);
        UserManager.getCommentList(productId, CURTUNPAGE, subscriber);
    }

    /*上传评论*/
    private void saveComment() {
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callbak_savecomment);
        UserManager.saveComment(infoId, atid, commentContent, successPath, subscriber);
    }


    ResponseResultListener callback_demenddetail = new ResponseResultListener<DemendDetailResponse>() {
        @Override
        public void success(DemendDetailResponse returnMsg) {
            closeProgress();
            resertView(returnMsg);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
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
                getComments(infoId);
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

}
