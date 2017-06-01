package com.zzcar.zzc.interfaces;

import com.zzcar.zzc.models.HomeCarGet;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/6.
 */

public interface HomeAdapterListener {
    public void setOnItemClckListener(int position, int productId, HomeCarGet homeCarGet);
}
