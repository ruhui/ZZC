package com.zzcar.zzc.constants;

import com.zzcar.zzc.R;

/**
 * Created by Administrator on 2017/2/21.
 */

public class Constant {
    /*默认第一页码*/
    public static final int DEFAULTPAGE = 1;
    /* 渠道使用的key,这里使用的是友盟的key */
    public static final String CHANNAL_KEY = "";
    /* 登录时倒计时*/
    public static final int TIMECOUNT = 60000;
    /* 图片保存路径 */
    public static final String FILEPATH = "/temp/"+System.currentTimeMillis() + ".jpg";
    /* 和后台接口对应的密钥 */
    public static final String PRIVATEKEY = "3BDB71CA-11A3-49";
    /* 友盟Appkey*/
    public static final String UMENGAPPKEY = "58ef20a6cae7e7474500182a";
    /* sharesdk的Appkey */
    public static final String SHARESDKAPPKEY = "1cf6a7b7657a2";
    /*公钥*/
    public static final String PARTNER = "a73b4d63648bbc98670d2823eb863fba";
    /*测试时图片查看地址*/
    public static final String PICLOOKURL = "http://47.89.48.28:5002";
    /*测试图片上传地址*/
    public static final String UPLOADURL = "http://47.89.48.28:5002/upload/";
    /*默认头像*/
    public static final int HEADIMG = R.drawable.nav_icon_head_default;
    /*上传头像图片路径*/
    public static final String UPLOADHEADURL = UPLOADURL + "profile";
    /*上传商品图片路径*/
    public static final String UPLOADGOODSURL = UPLOADURL + "goods";
    /*上传供应图片路径*/
    public static final String UPLOADINFOURL = UPLOADURL + "info";
    /*上传评论图片路径*/
    public static final String UPLOADCOMMENTURL = UPLOADURL + "comment";
    /*上传聊天图片路径*/
    public static final String UPLOADCHATURL = UPLOADURL + "chat";
    /*环信密码*/
    public static final String EMCHATPASSWORD = "car123456";

}
