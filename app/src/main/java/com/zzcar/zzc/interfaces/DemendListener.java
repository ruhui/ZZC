package com.zzcar.zzc.interfaces;

import com.zzcar.zzc.models.MydemandModel;
import com.zzcar.zzc.models.MysupplyModel;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/15 15:14
 **/
public interface DemendListener {
    /*编辑*/
    public void editClickListener(MydemandModel model);
    /*删除*/
    public void cancleClickListener(MydemandModel model);
}
