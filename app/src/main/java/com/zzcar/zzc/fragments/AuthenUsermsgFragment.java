package com.zzcar.zzc.fragments;

import android.content.Context;
import android.view.Gravity;

import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.networks.responses.VerifiedResponse;
import com.zzcar.zzc.views.widget.ItemIconTextIcon;
import com.zzcar.zzc.views.widget.ItemTextEditView;

import org.androidannotations.annotations.AfterViews;
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
    ItemIconTextIcon carcomDetailAddress;

    private  VerifiedResponse verifiedResponse;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        verifiedResponse = (VerifiedResponse) getArguments().getSerializable("verifiedResponse");
    }

    @AfterViews
    void initView(){
        carcomName.setTitle("车行名称");carcomName.setRightText(verifiedResponse.getAccount_name());carcomName.setRightGravity(Gravity.RIGHT);
        carcomType.setTitle("车行类型");carcomType.setRightText(verifiedResponse.getCarType());carcomType.setRightGravity(Gravity.RIGHT);
        legalPerson.setLeftValue("法人姓名");legalPerson.setRightValue(verifiedResponse.getLegal_person());
        personCard.setLeftValue("身份证号");personCard.setRightValue(verifiedResponse.getId_card());
        BusinesLicense.setLeftValue("营业执照");BusinesLicense.setRightValue(verifiedResponse.getLicense_no());
        carcomAddress.setTitle("所在地");carcomAddress.setRightText(verifiedResponse.getRegion_name());carcomAddress.setRightGravity(Gravity.RIGHT);
        carcomDetailAddress.setTitle("详细地址");carcomDetailAddress.setRightText(verifiedResponse.getAddress());carcomDetailAddress.setRightGravity(Gravity.RIGHT);
    }


    void resetView(){

    }

    @Override
    public void onNetChange(int netMobile) {

    }



}
