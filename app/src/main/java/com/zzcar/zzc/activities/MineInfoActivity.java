package com.zzcar.zzc.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzcar.zzc.MyApplication;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.constants.Permission;
import com.zzcar.zzc.interfaces.ImageUploadListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.UploadFileWithoutLoding;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.PermissionUtili;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.dialogs.ShowPhotoDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import rx.Subscriber;


/**
 * 描述：我的资料
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/12 9:42
 **/

@EActivity(R.layout.fragment_myinfor)
public class MineInfoActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    @ViewById(R.id.textView62)
    TextView txtRenzheng;
    @ViewById(R.id.imageView13)
    ImageView headViw;
    @ViewById(R.id.textView65)
    TextView txtNick;
    @ViewById(R.id.textView67)
    TextView txtTelePhone;

    private  MineMsgResponse minemessage;
    private  ShowPhotoDialog dialog;

    private int REQ_CODE_CAMERA = 10125;
    /*照相机返回的路径*/
    private File tempfile;
    /*获取图片列表*/
    private ArrayList<String> photos = new ArrayList<>();
    /*缓存的头像*/
    private String imgpathMemery;
    /*昵称*/
    private String nickName = "";

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        minemessage = (MineMsgResponse) getIntent().getSerializableExtra("mineMsgResponse");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("我的资料");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                Intent intent = new Intent();
                intent.putExtra("mineMsgResponse", minemessage);
                setResult(20120, intent);
                finish();
            }
        });

        resetView();
    }

    private void resetView() {
        txtRenzheng.setText(minemessage.getAuth_status_name());
        ImageLoader.loadCircleImage(Tool.getPicUrl(getActivity(), minemessage.getPhoto(), 38, 38), headViw, R.drawable.nav_icon_head_default);
        txtNick.setText(minemessage.getNick());
        txtTelePhone.setText(minemessage.getMobile());
    }

    @Click(R.id.relaRenzheng)
    void renzheng(){
        int auth_status = SecurePreferences.getInstance().getInt("Auth_Status", 0);
        Intent intent = new Intent(MineInfoActivity.this, AuthenticationActivity_.class);
        intent.putExtra("Auth_status", auth_status);
        startActivity(intent);
    }

    @Click(R.id.relaHeagimg)
    void changeHeadimg(){
        dialog = new ShowPhotoDialog(getActivity(), mListener);
        dialog.show();
    }

    /*昵称*/
    @Click(R.id.relaNick)
    void setNick(){
        Intent intent = new Intent(MineInfoActivity.this, NickChangeAcitivity_.class);
        intent.putExtra("titleBar", "修改昵称");
        intent.putExtra("value", minemessage.getNick());
        startActivityForResult(intent, 20171);
    }

    ShowPhotoDialog.ListListener mListener = new ShowPhotoDialog.ListListener() {
        @Override
        public void setOnItemClickListener(int type) {
            dialog.dismiss();
            String[] permission = new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE};
            boolean checked =  PermissionUtili.checkPermission(getActivity(), permission, "需要设置手机权限",
                    "需要使用相机和读取相册权限，请到设置中设置应用权限。");
            switch (type){
                case 1:
                    //拍照
                    if (checked){
                        tempfile = Tool.getFilePath();
                        Uri mOriginUri;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            mOriginUri = FileProvider.getUriForFile(MyApplication.getInstance(), "com.zzcar.zzc.provider", tempfile);
                        } else {
                            mOriginUri = Uri.fromFile(tempfile);
                        }
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOriginUri);
                        startActivityForResult(intent, REQ_CODE_CAMERA);

//                        Uri Imagefile = Uri.fromFile(tempfile);
//                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Imagefile);
//                        startActivityForResult(cameraIntent, REQ_CODE_CAMERA);
                    }
                    break;
                case 2:
                    //相册
                    if (checked){
                        //相册
                        PhotoPicker.builder()
                                .setPhotoCount(1)
                                .setShowCamera(false)
                                .setShowGif(false)
                                .setPreviewEnabled(false)
                                .setSelected(photos)
                                .start(getActivity(), PhotoPicker.REQUEST_CODE);
                    }
                    break;
            }
        }
    };


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("mineMsgResponse", minemessage);
        setResult(20120, intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showProgress();
        if (data != null){
            if (resultCode == getActivity().RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE){
                showProgress();
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }else if (requestCode == 20171){
                String nick = data.getStringExtra("value");
                nickName = nick;
                minemessage.setNick(nick);
                resetView();
                //调用修改昵称接口
                Subscriber subscriber =  new PosetSubscriber<Boolean>().getSubscriber(callback_nick);
                UserManager.saveNick(nick, subscriber);
            }
        }else  if (requestCode==REQ_CODE_CAMERA) {
            showProgress();
            String imgPath = tempfile.getPath();
            photos.add(imgPath);
        }
        if (photos.size() > 0){
            new UploadFileWithoutLoding(uploadListener).execute(photos.get(0), Constant.UPLOADHEADURL);
        }else{
            closeProgress();
        }
    }

    ImageUploadListener uploadListener = new ImageUploadListener(){

        @Override
        public void finishLoading(String imgPath, int position) {
            //去上传头像
            imgpathMemery = imgPath;
            Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_headphoto);
            UserManager.savePhoto(imgPath, subscriber);
        }
    };

    /*头像保存回调*/
    ResponseResultListener callback_headphoto = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("保存成功");
            }else{
                ToastUtil.showToast("保存失败");
            }
            minemessage.setPhoto(imgpathMemery);
            resetView();
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            ToastUtil.showToast("保存失败");
        }
    };


    /*修改昵称*/
    ResponseResultListener callback_nick = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            nickName = "";
            if (returnMsg){
                ToastUtil.showToast("修改成功");
            }else{
                ToastUtil.showToast("修改失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            nickName = "";
            ToastUtil.showToast("修改失败");
        }
    };
}
