package com.zzcar.zzc.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.zzcar.zzc.MyApplication;
import com.zzcar.zzc.constants.Constant;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统工具类
 *
 * @author ruhui
 * @time 2017/2/21 16:34
 **/

public class Tool {

    /**
     * 获取友盟渠道名称
     * @param ctx
     * @return
     */
    public static String getChannelName(Context ctx) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {}

                    channelName = applicationInfo.metaData.getString(Constant.CHANNAL_KEY);
                }
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    /**
     * 获取手机版本号
     * @param context
     * @param isVersionCode 是否是数字
     * @return
     */
    public static String getAppVersion(Context context, boolean isVersionCode) {
        String resoult = "";
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        resoult = packInfo.versionName;
        if (isVersionCode) {
            resoult = String.valueOf(packInfo.versionCode);
        }
        return resoult;
    }

    /**
     * 判断两对象是否相同
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    /**
     * 隐藏键盘
     * @param context
     * @param view
     */
    public static void hideInputMethod(Context context, View view) {
        try {
            InputMethodManager manager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示键盘
     */
    public static void showInputMethod() {
        InputMethodManager imm = (InputMethodManager) MyApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        //这里给它设置了弹出的时间，
        imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
    }



    /**
     * @param date
     * @return 格式化 yyyy-MM-dd
     */
    public static String formatSimpleDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 格式化 2位小数
     *
     * @param number
     * @return
     */
    public static String formatPrice(double number) {
        BigDecimal b = new BigDecimal(number);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1 + "";
    }

    /**
     * 格式化 2位小数
     *
     * @param number
     * @return
     */
    public static String formatPrice(String number) {
        String res;
        try {
            BigDecimal b = new BigDecimal(number);
            res = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
        } catch (Exception e) {
            e.printStackTrace();
            res = number;
        }
        return res;
    }

    public static String getGson(Object t){
        Gson gson = new Gson();
        return gson.toJson(t);
    }

    //将Json数据解析成相应的映射对象
    public static <T>T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }






    //md5加密
    public static String getMd5(String plainText) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            md.update(plainText.getBytes());
            // 获得密文
            byte b[] = md.digest();
            // 把密文转换成十六进制的字符串形式
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取手机型号
     */
    public static String getMODEL(){
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     */
    public static String getBrand(){
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取ip
     * @return
     */
    public static String getIP(Context context){
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return  ip;
    }

    /**
     * 是否是手机号码
     * @param mobiles
     * @return
     */
    public static boolean checkPhoneNum(String mobiles) {
        Pattern p = Pattern.compile("^((1[0-9]))\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }



    private static String intToIp(int i) {
        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }



//    public static void showShare(Context context,String title,String url,String content,String comment,String pictureUrl) {
//        //初始化ShareSDK
//        ShareSDK.initSDK(context);
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
//        oks.setTitle(title);
//        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
//        oks.setTitleUrl(url);
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText(content);
//        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl(pictureUrl);
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl(url);
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment(comment);
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(context.getString(R.string.app_name));
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(url);
//        // 启动分享GUI
//        oks.show(context);
//    }

    /**
     * 车源的时间格式
     */
    public static String getTimeFormat(String date){
        String newyear="",mouth="",day="",hour="",minute="";
        if (date.indexOf("-") > 0){
            newyear = date.substring(0, date.indexOf("-"));
            date = date.substring(date.indexOf("-")+1);
            if (date.indexOf("-")>0){
                mouth = date.substring(0, date.indexOf( "-"));
                date = date.substring(date.indexOf("-")+1);
                if (date.indexOf(" ")>0){
                    day = date.substring(0, date.indexOf(" "));
                    date = date.substring(date.indexOf(" ")+1);
                    if (date.indexOf(":")>0){
                        hour = date.substring(0, date.indexOf(":"));
                        date = date.substring(date.indexOf(":")+1);
                        if (date.indexOf(":") > 0){
                            minute = date.substring(0, date.indexOf(":"));
                        }
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(newyear) ){
            if ((Integer.valueOf(newyear) < getYear())){
                return newyear+"年"+mouth+"月";
            }else if (Integer.valueOf(newyear) == getYear()){
                if (!TextUtils.isEmpty(day) && !TextUtils.isEmpty(mouth) && !TextUtils.isEmpty(day) ){
                    int m = Integer.valueOf(mouth);
                    int h = getMonth();
                    if (Integer.valueOf(mouth) < getMonth()){
                        return mouth+"月"+day+"日";
                    }else if ((Integer.valueOf(mouth) == getMonth()) ){
                        if (Integer.valueOf(day) < getDay()){
                            return mouth+"月"+day+"日";
                        }else if (Integer.valueOf(day) == getDay()){
                            int hout =getHour();
                            int houtp = getHour()-Integer.valueOf(hour);
                            if (houtp > 1 && houtp < 24){
                                return hour+" : "+minute;
                            }else{
                                return "刚刚";
                            }
                        }else{
                            return "";
                        }
                    }else{
                        return  "";
                    }
                }else{
                    return newyear +"年";
                }
            }else{
                return  "";
            }
        }else{
            return  "";
        }
    }


    public void getTimeByCalendar(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH);//获取月份
        int day=cal.get(Calendar.DATE);//获取日
        int hour=cal.get(Calendar.HOUR);//小时
        int minute=cal.get(Calendar.MINUTE);//分
        int second=cal.get(Calendar.SECOND);//秒
        int WeekOfYear = cal.get(Calendar.DAY_OF_WEEK);//一周的第几天
    }

    /**
     * 获取年
     */
    public static int getYear(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        return year;
    }
    /**
     * 获取月
     */
    public static int getMonth(){
        Calendar cal = Calendar.getInstance();
        int month=cal.get(Calendar.MONTH);//获取月份
        return month+1;
    }

    /**
     * 获取日
     */
    public static int getDay(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DATE);//获取日
    }

    /**
     * 获取时
     */
    public static int getHour(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);//小时
    }

    /**
     * 获取分
     */
    public static int getMinute(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MINUTE);//分
    }

    /**
     * 获取图片路径
     */
    public static String getPicUrl(Context mContext, String url, int width, int heigh){
        int weidth = Utils.dip2px(mContext, width);
        int height = Utils.dip2px(mContext, heigh);
        if (url.contains(".jpg")){
            return Constant.PICLOOKURL + url + "_1_" + weidth + "_" + height + "_0.jpg";
        }else  if (url.contains(".png")){
            return Constant.PICLOOKURL + url + "_1_" + weidth + "_" + height + "_0.png";
        }else{
            return "";
        }
    }
}