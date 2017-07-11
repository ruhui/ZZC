package com.zzcar.zzc.interfaces;

import com.zzcar.zzc.networks.responses.AddressResponse;

/**
 * 创建时间： 2017/7/11.
 * 作者：黄如辉
 * 功能描述：
 */

public interface AddressListener {
    /*删除地址*/
    public void cancleAddress(AddressResponse model, int pisiton);
    /*行点击*/
    public void setOnItemClickListener(AddressResponse model);
}
