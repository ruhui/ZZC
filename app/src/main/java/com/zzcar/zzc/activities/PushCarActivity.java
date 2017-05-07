package com.zzcar.zzc.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.PhotoAdapte;
import com.zzcar.zzc.constants.Permission;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.PermissonManager;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.AddCarMiddleModle;
import com.zzcar.zzc.models.ImageList;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CheckSuccessResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.KeyboardPatch;
import com.zzcar.zzc.utils.PermissionUtili;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.ItemOneView;
import com.zzcar.zzc.views.widget.ItemSecondView;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.NavBar3;
import com.zzcar.zzc.views.widget.dialogs.MyAlertDialog;
import com.zzcar.zzc.views.widget.dialogs.MyAlertDialog2;
import com.zzcar.zzc.views.widget.dialogs.TakePhotoDialog;
import com.zzcar.zzc.wheel.view.TimePickerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.iwf.photopicker.PhotoPicker;
import rx.Subscriber;

@EActivity(R.layout.activity_push_car)
public class PushCarActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar3 mNavbar;
    @ViewById(R.id.blandcar)
    ItemOneView blandcar;
    @ViewById(R.id.selectCity)
    ItemOneView selectCity;
    @ViewById(R.id.priceItem)
    ItemSecondView priceItem;
    @ViewById(R.id.cardTime)
    ItemOneView cardTime;
    @ViewById(R.id.cardBelong)
    ItemOneView cardBelong;
    @ViewById(R.id.mileData)
    ItemSecondView mileData;
    @ViewById(R.id.carColor)
    ItemOneView carColor;
    @ViewById(R.id.outComTime)
    ItemOneView outComTime;
    @ViewById(R.id.emissionSta)
    ItemOneView emissionSta;
    @ViewById(R.id.newcarPrice)
    ItemSecondView newcarPrice;
    @ViewById(R.id.limitTime)
    ItemOneView limitTime;
    @ViewById(R.id.useto)
    ItemOneView useto;
    @ViewById(R.id.editText3)
    EditText carDes;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    /*提交*/
    @ViewById(R.id.txtSubmit)
    TextView txtSubmit;

    private PhotoAdapte adapter;

    private  KeyboardPatch keyboard;
    private AddCarMiddleModle carMiddle;
    private TakePhotoDialog photodialog;
    /*获取图片列表*/
    private ArrayList<String> photos = new ArrayList<>();
    private TimePickerView pvTimecardTime;
    private TimePickerView pvTimeoutComTime;
    private TimePickerView pvTimelimitTime;
    private int REQ_CODE_CAMERA = 10125;
    /*照相机返回的路径*/
    private  File tempfile;

    @AfterViews
    void initView(){
        initpvTime();
        //车辆id为空是新增
        String productid =getIntent().getStringExtra("product_id");

        carMiddle = new AddCarMiddleModle();
        if (TextUtils.isEmpty(productid)){
            carMiddle.setProduct_id("0");
        }
        //设置键盘弹起
        keyboard = new KeyboardPatch(this, getView());
        keyboard.enable();
        mNavbar.setLeftMenuText("取消");
        mNavbar.setMiddleTitle("发布车源");
        mNavbar.setRightTxt("清空条件");

        mNavbar.setOnMenuClickListener(new NavBar3.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                final MyAlertDialog2 dialog = new MyAlertDialog2(PushCarActivity.this, true);
                dialog.show();
                dialog.setTitle("取消发布");
                dialog.setContent("是否放弃发车");
                dialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        finish();
                    }
                });
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                /*清空条件*/
            }
        });

        blandcar.setTxtLeft("品牌车系");blandcar.setHint("必填");
        selectCity.setTxtLeft("所在地");selectCity.setHint("必填");
        priceItem.setTxtLeft("价格");priceItem.setTxtRight("万元");
        cardTime.setTxtLeft("上牌时间");cardTime.setHint("必填");
        cardBelong.setTxtLeft("牌照归属");cardBelong.setHint("必填");
        mileData.setTxtLeft("表显里程");mileData.setTxtRight("万公里");
        carColor.setTxtLeft("车身颜色");carColor.setHint("必填");
        outComTime.setTxtLeft("出厂时间");outComTime.setHint("必填");
        emissionSta.setTxtLeft("排放标准");emissionSta.setHint("必填");
        newcarPrice.setTxtLeft("新车指导价");newcarPrice.setTxtRight("万元");
        limitTime.setTxtLeft("强制险到期");limitTime.setHint("必填");
        useto.setTxtLeft("用途");useto.setHint("必填");

        outComTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTimeoutComTime.setTime(new Date());
                pvTimeoutComTime.setCyclic(false);
                pvTimeoutComTime.setCancelable(true);
                pvTimeoutComTime.show();
                Tool.hideInputMethod(PushCarActivity.this,outComTime);
            }
        });

        cardTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTimecardTime.setTime(new Date());
                pvTimecardTime.setCyclic(false);
                pvTimecardTime.setCancelable(true);
                pvTimecardTime.show();
                Tool.hideInputMethod(PushCarActivity.this,cardTime);
            }
        });

        limitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTimelimitTime.setTime(new Date());
                pvTimelimitTime.setCyclic(false);
                pvTimelimitTime.setCancelable(true);
                pvTimelimitTime.show();
                Tool.hideInputMethod(PushCarActivity.this,limitTime);
            }
        });

        // 强制险到期回调
        pvTimelimitTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener(){
            @Override
            public void onTimeSelect(Date date)
            {
                String time = Tool.formatSimpleDate1(date);
                carMiddle.setSafeDes(time);
                String birthday = Tool.formatSimpleDate(date);
                if (!TextUtils.isEmpty(birthday)){
                    if (birthday.indexOf("-") >= 1){
                        String year = birthday.substring(0, birthday.indexOf("-"));
                        carMiddle.setExp_safe_year(year);
                        String str = birthday.substring(birthday.indexOf("-") + 1);
                        if (str.indexOf("-") >= 1){
                            String month = str.substring(0, str.indexOf("-"));
                            carMiddle.setExp_safe_month(month);
                        }
                    }
                }
                resetView();
            }
        });


        // 出厂时间回调
        pvTimeoutComTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener(){
            @Override
            public void onTimeSelect(Date date)
            {
                String time = Tool.formatSimpleDate1(date);
                carMiddle.setOutfactoryDes(time);
                String birthday = Tool.formatSimpleDate(date);
                if (!TextUtils.isEmpty(birthday)){
                    if (birthday.indexOf("-") >= 1){
                        String year = birthday.substring(0, birthday.indexOf("-"));
                        carMiddle.setOut_factory_year(year);
                        String str = birthday.substring(birthday.indexOf("-") + 1);
                        if (str.indexOf("-") >= 1){
                            String month = str.substring(0, str.indexOf("-"));
                            carMiddle.setOut_factory_month(month);
                        }
                    }
                }
                resetView();
            }
        });

        // 上牌时间回调
        pvTimecardTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener(){
            @Override
            public void onTimeSelect(Date date)
            {
                String time = Tool.formatSimpleDate1(date);
                carMiddle.setCardTimeDes(time);
                String birthday = Tool.formatSimpleDate(date);
                if (!TextUtils.isEmpty(birthday)){
                    if (birthday.indexOf("-") >= 1){
                        String year = birthday.substring(0, birthday.indexOf("-"));
                        carMiddle.setOn_number_year(year);
                        String str = birthday.substring(birthday.indexOf("-") + 1);
                        if (str.indexOf("-") >= 1){
                            String month = str.substring(0, str.indexOf("-"));
                            carMiddle.setOn_number_month(month);
                        }
                    }
                }
                resetView();
            }
        });


        blandcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, BrandCarActivity_.class);
                startActivityForResult(intent, 10105);
            }
        });

        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, SelectCityActivity_.class);
                startActivityForResult(intent, 10106);
            }
        });

        cardBelong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, SelectCityActivity_.class);
                startActivityForResult(intent, 10107);
            }
        });

        carColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, ColorSelectActivity_.class);
                intent.putExtra("singleselect", true);
                startActivityForResult(intent, 10108);
            }
        });

        emissionSta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, EmissionActivity_.class);
                intent.putExtra("singleselect", true);
                startActivityForResult(intent, 10102);
            }
        });

        useto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PushCarActivity.this, UserTypeActivity_.class);
                startActivityForResult(intent, 10109);
            }
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(PushCarActivity.this, 3));
        mRecyclerView.setAdapter(adapter = new PhotoAdapte(PushCarActivity.this, photos, itemClickListener));


        /*提交*/
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subMitCar();
            }
        });
    }


    private void subMitCar() {
        String priceNum = priceItem.getTxtMiddle();
        String mile = mileData.getTxtMiddle();
        String newcarPri = newcarPrice.getTxtMiddle();
        String cardes = carDes.getText().toString();
        carMiddle.setContent(cardes);
        carMiddle.setNew_car_price(newcarPri);
        carMiddle.setMileage(mile);
        carMiddle.setMarket_price(priceNum);
        String alertmsg = carMiddle.alertMsg(carMiddle);
        if (TextUtils.isEmpty(alertmsg)){
            //添加车源
            saveCarFrom(carMiddle);
        }else{
            ToastUtil.showToast(alertmsg);
        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        final MyAlertDialog2 dialog = new MyAlertDialog2(PushCarActivity.this, true);
        dialog.show();
        dialog.setTitle("取消发布");
        dialog.setContent("是否放弃发车");
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
    }

    PhotoAdapte.ItemClickListener itemClickListener = new PhotoAdapte.ItemClickListener() {
        @Override
        public void itemListener() {
            selectImage();
        }

        @Override
        public void imgbackListener(List<String> imgList) {
            //拿到图片，并设置图
            List<String> listPath = new ArrayList<>();
            for (String imgPath : imgList){
                ImageList imageList = new ImageList(imgPath);
                listPath.add(imageList.getPath());
            }
            carMiddle.setImage_path(listPath);
        }
    };

    private void initpvTime() {
        pvTimecardTime = new TimePickerView(PushCarActivity.this, TimePickerView.Type.YEAR_MONTH);
        pvTimeoutComTime = new TimePickerView(PushCarActivity.this, TimePickerView.Type.YEAR_MONTH);
        pvTimelimitTime = new TimePickerView(PushCarActivity.this, TimePickerView.Type.YEAR_MONTH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 10102){
                /*排放*/
                List<String> emissionids = (List<String>) data.getSerializableExtra("emissionids");
                String emissiones = data.getStringExtra("emissiones");
                if (emissionids.size() > 0){
                    String emissionid = emissionids.get(0);
                    carMiddle.setEmission(emissionid);
                }
                carMiddle.setEmissionDes(emissiones);
            }else if (requestCode == 10105){
                /*品牌车系*/
                String brandid = data.getStringExtra("brandid");
                String seriesid = data.getStringExtra("seriesid");
                String typeid = data.getStringExtra("typeid");
                String branddes = data.getStringExtra("branddes");
                carMiddle.setBland_id(brandid);
                carMiddle.setSeries_id(seriesid);
                carMiddle.setSpec_id(typeid);
                carMiddle.setBladseriesdes(branddes);
            }else if (requestCode == 10106){
                /*车辆所属城市*/
                String province_id = data.getStringExtra("province_id");
                String city_id = data.getStringExtra("city_id");
                String citydes = data.getStringExtra("citydes");
                carMiddle.setCar_province_id(province_id);
                carMiddle.setCar_city_id(city_id);
                carMiddle.setBelongCityDes(citydes);
            }else if (requestCode == 10107){
                /*牌照归属*/
                String province_id = data.getStringExtra("province_id");
                String city_id = data.getStringExtra("city_id");
                String citydes = data.getStringExtra("citydes");
                carMiddle.setOn_number_province_id(province_id);
                carMiddle.setOn_number_city_id(city_id);
                carMiddle.setNumberBelongDes(citydes);
            }else if(requestCode == 10108){
                /*车身颜色*/
                List<String> colorids = (List<String>) data.getSerializableExtra("colorids");
                String colordes = data.getStringExtra("colordes");
                if (colorids.size() > 0){
                    String colorid = colorids.get(0);
                    carMiddle.setColor(colorid);
                }
                carMiddle.setColorDes(colordes);
            }else if (requestCode == 10109){
                String usertypeid = data.getStringExtra("usertypeid");
                String usertypeDes = data.getStringExtra("usertypeDes");
                carMiddle.setUse_type(usertypeid);
                carMiddle.setUsertypeDes(usertypeDes);
            }else if (resultCode == getActivity().RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE){
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                adapter.setData(photos);
            }
            resetView();
        }else  if (requestCode==REQ_CODE_CAMERA) {
            String imgPath = tempfile.getPath();
            photos.add(imgPath);
            adapter.setData(photos);
        }
    }

    void resetView(){
        blandcar.setTxtMiddle(carMiddle.getBladseriesdes());
        selectCity.setTxtMiddle(carMiddle.getBelongCityDes());
        cardTime.setTxtMiddle(carMiddle.getCardTimeDes());
        cardBelong.setTxtMiddle(carMiddle.getNumberBelongDes());
        carColor.setTxtMiddle(carMiddle.getColorDes());
        outComTime.setTxtMiddle(carMiddle.getOutfactoryDes());
        emissionSta.setTxtMiddle(carMiddle.getEmissionDes());
        limitTime.setTxtMiddle(carMiddle.getSafeDes());
        useto.setTxtMiddle(carMiddle.getUsertypeDes());
    }

    @Override
    public void onNetChange(int netMobile) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (keyboard != null){
            keyboard.disable();
        }
    }

    private void selectImage() {
        String[] permission = new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE};
        boolean checked =  PermissionUtili.checkPermission(getActivity(), permission, "需要设置手机权限",
                "需要使用相机和读取相册权限，请到设置中设置应用权限。");

        if (checked ) {
            //弹出弹框
            photodialog = new TakePhotoDialog(PushCarActivity.this, photoListener);
            photodialog.show();
        }
    }

    TakePhotoDialog.TakePhotoListener photoListener = new TakePhotoDialog.TakePhotoListener() {
        @Override
        public void selectCamera() {
            //相机
            tempfile = getFilePath();
            Uri Imagefile = Uri.fromFile(tempfile);
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Imagefile);
            startActivityForResult(cameraIntent, REQ_CODE_CAMERA);
            photodialog.closedialog();
        }

        @Override
        public void selectPhoto() {
            photodialog.closedialog();
            //相册
            PermissonManager permissonManager = new PermissonManager(PushCarActivity.this);
            permissonManager.lacksPermissions();
            PhotoPicker.builder()
                    .setPhotoCount(9)
                    .setShowCamera(false)
                    .setShowGif(false)
                    .setPreviewEnabled(false)
                    .setSelected(photos)
                    .start(getActivity(), PhotoPicker.REQUEST_CODE);
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

    /*发布车源*/
    private void saveCarFrom(AddCarMiddleModle carMiddle) {
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_carfrom);
        UserManager.savecar(carMiddle, subscriber);
    }

    ResponseResultListener callback_carfrom = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            ToastUtil.showToast("发布成功");
            Intent intent = new Intent();
            intent.putExtra("isrefreshdata", true);
            setResult(10201, intent);
            finish();
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("发布失败");
        }
    };


}
