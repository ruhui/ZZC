package com.zzcar.zzc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.ChannelActivity_;
import com.zzcar.zzc.activities.NickChangeAcitivity_;
import com.zzcar.zzc.activities.SearchActivity;
import com.zzcar.zzc.activities.SelectCityActivity_;
import com.zzcar.zzc.activities.SelectCountryActivity_;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.networks.responses.VerifiedResponse;
import com.zzcar.zzc.utils.KeyboardPatch;
import com.zzcar.zzc.views.widget.ItemIconTextIcon;
import com.zzcar.zzc.views.widget.ItemTextEditView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：车行认证-用户信息
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/12 15:34
 **/

@EFragment(R.layout.fragment_authen_usermsg)
public class AuthenUsermsgFragment extends BaseFragment {

    /*名称*/
    @ViewById(R.id.carcomName)
    ItemIconTextIcon carcomName;
    /*类型*/
    @ViewById(R.id.carcomType)
    ItemIconTextIcon carcomType;
    /*法人*/
    @ViewById(R.id.legalPerson)
    ItemTextEditView legalPerson;
    /*身份证*/
    @ViewById(R.id.personCard)
    ItemTextEditView personCard;
     /*营业执照*/
    @ViewById(R.id.BusinesLicense)
    ItemTextEditView BusinesLicense;
    /*车行地址*/
    @ViewById(R.id.carcomAddress)
    ItemIconTextIcon carcomAddress;
    /*详细地址*/
    @ViewById(R.id.carcomDetailAddress)
    ItemTextEditView carcomDetailAddress;
    //做完删掉
    private  VerifiedResponse verifiedResponse;
    private KeyboardPatch keyboard;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        verifiedResponse = (VerifiedResponse) getArguments().getSerializable("verifiedResponse");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("verifiedResponse", verifiedResponse);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null){
            verifiedResponse = (VerifiedResponse) savedInstanceState.getSerializable("verifiedResponse");
        }
    }

    @AfterViews
    void initView(){
        //设置键盘弹起
        keyboard = new KeyboardPatch(getActivity(), getView());
        keyboard.enable();
        carcomName.setTitle("车行名称");carcomName.setRightGravity(Gravity.RIGHT);carcomName.setRightTextColor();carcomName.setRightHint("请输入车行名称");
        carcomType.setTitle("车行类型");carcomType.setRightGravity(Gravity.RIGHT);carcomType.setRightTextColor();carcomType.setRightHint("请选择车行类型");
        legalPerson.setLeftValue("法人姓名");legalPerson.setEdtRightHint("请填写营业执照上的法人姓名");
        personCard.setLeftValue("身份证号");personCard.setEdtRightHint("身份证上的15位或18位号码");personCard.setInputNumber();
        BusinesLicense.setLeftValue("营业执照");BusinesLicense.setEdtRightHint("请填写营业执照号码");
        carcomAddress.setTitle("所在地");carcomAddress.setRightGravity(Gravity.RIGHT);carcomAddress.setRightTextColor();carcomAddress.setRightHint("请选择车辆所在地");
        carcomDetailAddress.setLeftValue("详细地址");carcomDetailAddress.setEdtRightHint("请填输入详细地址");
        resetView();
    }

    void resetView(){
        if (verifiedResponse != null){
            carcomName.setRightText(verifiedResponse.getAccount_name());
            carcomType.setRightText(verifiedResponse.getCarType());
            carcomAddress.setRightText(verifiedResponse.getRegion_name());
            if (!TextUtils.isEmpty(verifiedResponse.getLegal_person())){
                legalPerson.setRightValue(verifiedResponse.getLegal_person());
            }
            if (!TextUtils.isEmpty(verifiedResponse.getId_card())){
                personCard.setRightValue(verifiedResponse.getId_card());
            }
            if (!TextUtils.isEmpty(verifiedResponse.getLicense_no())){
                BusinesLicense.setRightValue(verifiedResponse.getLicense_no());
            }
            if (!TextUtils.isEmpty(verifiedResponse.getAddress())){
                carcomDetailAddress.setRightValue(verifiedResponse.getAddress());
            }
        }
    }

    /*车行名称*/
    @Click(R.id.carcomName)
    void setcarName(){
        Intent intent = new Intent(getActivity(), NickChangeAcitivity_.class);
        intent.putExtra("titleBar", "车行名称");
        intent.putExtra("value", verifiedResponse.getAccount_name());
        startActivityForResult(intent, 20171);
    }

    /*渠道*/
    @Click(R.id.carcomType)
    void setaCarType(){
        Intent intent = new Intent(getActivity(), ChannelActivity_.class);
        intent.putExtra("actionTitle", "车行类型");
        intent.putExtra("dismisbuxian", true);
        startActivityForResult(intent, 20172);
    }

    @Click(R.id.carcomAddress)
    void selectCity(){
        Intent intent = new Intent(getActivity(), SelectCountryActivity_.class);
        startActivityForResult(intent, 20173);
    }


    /*获取填写的相关信息*/
    public VerifiedResponse getBasicData(){
        String legal_person = legalPerson.getRightValue();
        verifiedResponse.setLegal_person(legal_person);
        String idcard = personCard.getRightValue();
        verifiedResponse.setId_card(idcard);
        String license_no = BusinesLicense.getRightValue();
        verifiedResponse.setLicense_no(license_no);
        String addressdetail = carcomDetailAddress.getRightValue();
        verifiedResponse.setAddress(addressdetail);
        return verifiedResponse;
    }

    @Override
    public void onNetChange(int netMobile) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 20171){
                String value = data.getStringExtra("value");
                verifiedResponse.setAccount_name(value);
            }else  if (requestCode == 20172){
                String channelid = data.getStringExtra("channelid");
                String channeldes = data.getStringExtra("channeldes");
                verifiedResponse.setType(Integer.valueOf(channelid));
                carcomType.setRightText(channeldes);
            }else if(requestCode == 20173){
                int province_id = data.getIntExtra("province_id", 0);
                int city_id = data.getIntExtra("city_id", 0);
                int contryId = data.getIntExtra("contryId", 0);
                String region_name = data.getStringExtra("region_name");
                verifiedResponse.setProvince_id(province_id);
                verifiedResponse.setCity_id(city_id);
                verifiedResponse.setArea_id(contryId);
                verifiedResponse.setRegion_name(region_name);
            }
        }
        resetView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (keyboard != null){
            keyboard.disable();

        }
    }
}
