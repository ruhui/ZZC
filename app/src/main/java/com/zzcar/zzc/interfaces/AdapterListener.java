package com.zzcar.zzc.interfaces;

import com.zzcar.zzc.models.ApplyFriendModel;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/11.
 */

public interface AdapterListener<T> {
    public void setOnItemListener(T t, int position);

}
