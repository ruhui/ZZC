package com.zzcar.zzc.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.AuthenticationActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.constants.Permission;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ImageUploadListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.EnumSendUserType;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.UploadFileWithoutLoding;
import com.zzcar.zzc.networks.responses.VerifiedResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.KeyboardPatch;
import com.zzcar.zzc.utils.PermissionUtili;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.dialogs.ShowPhotoDialog;
import com.zzcar.zzc.views.widget.dialogs.ShowPhotoRenzhengDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;
import rx.Subscriber;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/12 16:50
 **/

@EFragment(R.layout.fragment_authenloadpic)
public class AuthenLoadPhotoFragment extends BaseFragment {

    @ViewById(R.id.imageView14)
    ImageView imgYingye;
    @ViewById(R.id.imageView15)
    ImageView shenfenzz;
    @ViewById(R.id.imageView16)
    ImageView shenfenzf;
    @ViewById(R.id.textView71)
    TextView txtSubmitCOde;
    @ViewById(R.id.textView72)
    TextView sendtoPhone;
    @ViewById(R.id.editText5)
    EditText edtCode;

    private boolean timerstart = false;
    private int SELECTPOSITION = 1;//1为营业执照 2为身份证正面照  3为身份证反面照
    private CountDownTimer myCount;
    private ShowPhotoRenzhengDialog dialog;
    private int REQ_CODE_CAMERA = 10125;

    /*照相机返回的路径*/
    private File tempfileYingye;
    private File tempfileShenfenzz;
    private File tempfileShenfenzf;
    /*获取图片列表*/
    private ArrayList<String> photosYingye = new ArrayList<>();
    private ArrayList<String> photosShenfenzz = new ArrayList<>();
    private ArrayList<String> photosShenfenzf = new ArrayList<>();

    private VerifiedResponse verifiedResponse;
    private KeyboardPatch keyboard;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        verifiedResponse = (VerifiedResponse) getArguments().getSerializable("verifiedResponse");
    }

    @AfterViews
    void initView(){
        //设置键盘弹起
        keyboard = new KeyboardPatch(getActivity(), getView());
        keyboard.enable();
        String mobile = SecurePreferences.getInstance().getString("USERMOBILE", "");
        sendtoPhone.setText("短信验证码将会发送到"+ mobile.substring(0, 4)+"****"+mobile.substring(mobile.length()-4, mobile.length()));
    }

    @Override
    public void onNetChange(int netMobile) {

    }


    /*获取验证码*/
    @Click(R.id.textView71)
    void sendcode(){
        if (!timerstart){
            String mobile = SecurePreferences.getInstance().getString("USERMOBILE", "");
            timerstart = true;
            txtSubmitCOde.setText(Constant.TIMECOUNT+"s");
            myCount = new MyCount(Constant.TIMECOUNT, 1000).start();
            Subscriber subscriber =  new PosetSubscriber<Boolean>().getSubscriber(callback_sendcode);
            UserManager.getRegsms(mobile, EnumSendUserType.VERIFIED, subscriber);
        }
    }

    /*获取营业执照*/
    @Click(R.id.imageView14)
    void yingyePicture(){
        SELECTPOSITION = 1;
        if (dialog != null){
            dialog.show();
        }else{
            dialog = new ShowPhotoRenzhengDialog(getActivity(), mListener);
            dialog.show();
        }
        dialog.setDemoPic(R.drawable.bg_demo_yingye);
    }

    /*身份证正面*/
    @Click(R.id.imageView15)
    void shenfenzhengZM(){
        SELECTPOSITION = 2;
        if (dialog != null){
            dialog.show();
        }else{
            dialog = new ShowPhotoRenzhengDialog(getActivity(), mListener);
            dialog.show();
        }
        dialog.setDemoPic(R.drawable.bg_demo_shenfenz);
    }

    /*身份证反面*/
    @Click(R.id.imageView16)
    void shenfenzhengFM(){
        SELECTPOSITION = 3;
        if (dialog != null){
            dialog.show();
        }else{
            dialog = new ShowPhotoRenzhengDialog(getActivity(), mListener);
            dialog.show();
        }
        dialog.setDemoPic(R.drawable.bg_demo_shenfenf);
    }

    /*保存*/
    @Click(R.id.txtSave)
    void saveVertifi(){
        String code = edtCode.getText().toString();
        verifiedResponse.setCode(code);
        ((AuthenticationActivity)getActivity()).saveVerified(verifiedResponse);
    }


    ShowPhotoRenzhengDialog.ListListener mListener = new ShowPhotoRenzhengDialog.ListListener() {
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
                        Uri Imagefile= null;
                        switch (SELECTPOSITION){
                            case 1:
                                tempfileYingye = Tool.getFilePath();
                                Imagefile = Uri.fromFile(tempfileYingye);
                                break;
                            case 2:
                                tempfileShenfenzz = Tool.getFilePath();
                                Imagefile = Uri.fromFile(tempfileShenfenzz);
                                break;
                            case 3:
                                tempfileShenfenzf = Tool.getFilePath();
                                Imagefile = Uri.fromFile(tempfileShenfenzf);
                                break;
                        }

                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Imagefile);
                        startActivityForResult(cameraIntent, REQ_CODE_CAMERA);
                    }
                    break;
                case 2:
                    //相册
                    if (checked){
                        switch (SELECTPOSITION){
                            case 1:
                                PhotoPicker.builder()
                                        .setPhotoCount(1)
                                        .setShowCamera(false)
                                        .setShowGif(false)
                                        .setPreviewEnabled(false)
                                        .setSelected(photosYingye)
                                        .start(getActivity(), PhotoPicker.REQUEST_CODE);
                                break;
                            case 2:
                                PhotoPicker.builder()
                                        .setPhotoCount(1)
                                        .setShowCamera(false)
                                        .setShowGif(false)
                                        .setPreviewEnabled(false)
                                        .setSelected(photosShenfenzz)
                                        .start(getActivity(), PhotoPicker.REQUEST_CODE);;
                                break;
                            case 3:
                                PhotoPicker.builder()
                                        .setPhotoCount(1)
                                        .setShowCamera(false)
                                        .setShowGif(false)
                                        .setPreviewEnabled(false)
                                        .setSelected(photosShenfenzf)
                                        .start(getActivity(), PhotoPicker.REQUEST_CODE);
                                break;
                        }
                    }
                    break;
            }
        }
    };




    /*定义一个倒计时的内部类*/
    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            txtSubmitCOde.setText( millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            timerstart = false;
            txtSubmitCOde.setText(getString(R.string.app_login_getcode));
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (resultCode == getActivity().RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE){
                showProgress();
                switch (SELECTPOSITION){
                    case 1:
                        photosYingye = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                        new UploadFileWithoutLoding(uploadListener).execute(photosYingye.get(0), Constant.UPLOADHEADURL);
                        break;
                    case 2:
                        photosShenfenzz = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                        new UploadFileWithoutLoding(uploadListener).execute(photosShenfenzz.get(0), Constant.UPLOADHEADURL);
                        break;
                    case 3:
                        photosShenfenzf = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                        new UploadFileWithoutLoding(uploadListener).execute(photosShenfenzf.get(0), Constant.UPLOADHEADURL);
                        break;
                }
            }
        }else  if (requestCode==REQ_CODE_CAMERA) {
            showProgress();
            switch (SELECTPOSITION){
                case 1:
                    String imgPathYing = tempfileYingye.getPath();
                    new UploadFileWithoutLoding(uploadListener).execute(imgPathYing, Constant.UPLOADHEADURL);
                    photosYingye.add(imgPathYing);
                    break;
                case 2:
                    String imgPathShenfenzz = tempfileShenfenzz.getPath();
                    photosShenfenzz.add(imgPathShenfenzz);
                    new UploadFileWithoutLoding(uploadListener).execute(imgPathShenfenzz, Constant.UPLOADHEADURL);
                    break;
                case 3:
                    String imgPathShenfenzf = tempfileShenfenzf.getPath();
                    photosShenfenzf.add(imgPathShenfenzf);
                    new UploadFileWithoutLoding(uploadListener).execute(imgPathShenfenzf, Constant.UPLOADHEADURL);
                    break;
            }
        }
    }

    ImageUploadListener uploadListener = new ImageUploadListener(){

        @Override
        public void finishLoading(String imgPath) {
            closeProgress();
            //去上传头像
            switch (SELECTPOSITION){
                case 1:
                    verifiedResponse.setLicense(imgPath);
                    ImageLoader.loadImage(Tool.getPicUrl(getActivity(), imgPath, 154, 100), imgYingye);
                    break;
                case 2:
                    verifiedResponse.setCard_positive(imgPath);
                    ImageLoader.loadImage(Tool.getPicUrl(getActivity(), imgPath, 154, 100), shenfenzz);
                    break;
                case 3:
                    verifiedResponse.setCard_negative(imgPath);
                    ImageLoader.loadImage(Tool.getPicUrl(getActivity(), imgPath, 154, 100), shenfenzf);
                    break;
            }
        }
    };

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (keyboard != null){
            keyboard.disable();
        }
        if (myCount != null){
            myCount.onFinish();
            myCount.cancel();
        }
    }
}
