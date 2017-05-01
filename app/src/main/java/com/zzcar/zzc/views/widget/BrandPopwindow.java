package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zzcar.greendao.BrandListResponseDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.CarBrandAdapter;
import com.zzcar.zzc.adapters.SortAdapter;
import com.zzcar.zzc.models.PinyinComparator;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.ToastUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
   * 品牌
   * 创建作者： 黄如辉
   * 创建时间： 2017/4/24 14:10
  **/

public class BrandPopwindow {

    private BrandListener brandListener;
    private List<BrandListResponse> hotBrandList = new ArrayList<>();
    private List<BrandListResponse> mBrandList = new ArrayList<>();
    private CarBrandAdapter adapter;
    private ListView sortListView;
    private SortAdapter sortAdapter;
    private PinyinComparator pinyinComparator;
    private PopupWindow popupWindow;

    public PopupWindow showPopupWindow(Context mContext, Drawable bgdrawable, int bgcolor, BrandListener brandListener) {
       this.brandListener = brandListener;
       View contentView = initView(mContext);

       popupWindow = new PopupWindow(contentView,
               LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
       popupWindow.setTouchable(true);
       popupWindow.setFocusable(false);
       popupWindow.setAnimationStyle(R.style.popwin_anim_style);
       popupWindow.setTouchInterceptor(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               return false;
               // 这里如果返回true的话，touch事件将被拦截
               // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
           }
       });

       // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
       // 我觉得这里是API的一个bug
       // getResources().getDrawable(
       // R.drawable.select_main_item)
       // getActivity().getResources().getColor(R.color.mdtp_transparent_black)
       popupWindow.setBackgroundDrawable(bgdrawable);
       ColorDrawable dw = new ColorDrawable(bgcolor);
       popupWindow.setBackgroundDrawable(dw);
       // 设置好参数之后再show
//            popupWindow_paixu.showAsDropDown(view);
       return popupWindow;
   }

   private View initView(final Context mContext) {
       // 一个自定义的布局，作为显示的内容
       View contentView = LayoutInflater.from(mContext).inflate(
               R.layout.layout_popwindiw_brand, null);

       TextView textView5 = (TextView) contentView.findViewById(R.id.textView5);
       View vHead= View.inflate(mContext, R.layout.headview_brand, null);
       RelativeLayout relaClearPrice = (RelativeLayout) vHead.findViewById(R.id.relaClearPrice);
       RecyclerView mRecyclerView = (RecyclerView) vHead.findViewById(R.id.hotcityRecycleView);
       mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
       mRecyclerView.setAdapter(adapter = new CarBrandAdapter(brandadapterListener));
       adapter.addAll(hotBrandList);


       sortListView = (ListView) contentView.findViewById(R.id.country_lvcountry);
       SideBar sideBar = (SideBar) contentView.findViewById(R.id.sidrbar);
       sortListView.addHeaderView(vHead);

       pinyinComparator = new PinyinComparator();

       relaClearPrice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               brandListener.setSelect("品牌", "");
           }
       });


       sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

           @Override
           public void onTouchingLetterChanged(String s) {
               int position = sortAdapter.getPositionForSection(s.charAt(0));
               if(position != -1){
                   sortListView.setSelection(position);
               }
           }
       });

       sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> parent, View view,
                                   int position, long id) {
               ToastUtil.showToast("position"+position);
               int brandid = mBrandList.get(position-1).getId();
               brandListener.showSeriesPopwindow(brandid);
//               carseriespop.showAsDropDown(parentview);
//               carSeriesPopwindow.getDate(brandid);

           }
       });
       Collections.sort(mBrandList, pinyinComparator);
       sortAdapter = new SortAdapter(mContext, mBrandList);
       sortListView.setAdapter(sortAdapter);

       //取消
       textView5.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               brandListener.setDismiss();
           }
       });
       return contentView;
   }


    public void setData(){

//        showFragment(mContext, carSeriesFragment);
//        if (carSeriesPopwindow == null){
//            carSeriesPopwindow = new CarSeriesPopwindow();
//            carseriespop = carSeriesPopwindow.showPopupWindow(mContext, bgdrawable, bgcolor);
//        }
        if (hotBrandList.size() > 0){
            adapter.clear();
            adapter.addAll(hotBrandList);
            Collections.sort(mBrandList, pinyinComparator);
            sortAdapter.updateListView(mBrandList);
            return;
        }
        BrandListResponseDao brandDao = GreenDaoUtils.getSingleTon().getmDaoSession().getBrandListResponseDao();
        List<BrandListResponse> userList = brandDao.queryBuilder()
                .whereOr(BrandListResponseDao.Properties.Name.like("奥迪"), BrandListResponseDao.Properties.Name.like("宝马"),
                        BrandListResponseDao.Properties.Name.like("奔驰"),BrandListResponseDao.Properties.Name.like("本田"),
                        BrandListResponseDao.Properties.Name.like("别克"),BrandListResponseDao.Properties.Name.like("大众"),
                        BrandListResponseDao.Properties.Name.like("丰田"),BrandListResponseDao.Properties.Name.like("福特"),
                        BrandListResponseDao.Properties.Name.like("日产"),BrandListResponseDao.Properties.Name.like("现代"))
                .build().list();
        List<BrandListResponse> listbrand = brandDao.loadAll();
        hotBrandList.addAll(userList);
        mBrandList.addAll(listbrand);

        adapter.clear();
        adapter.addAll(hotBrandList);
        Collections.sort(mBrandList, pinyinComparator);
        sortAdapter.updateListView(mBrandList);
    }

    public interface BrandListener{
        public void setSelect(String title, String value);
        public void setDismiss();
        public void showSeriesPopwindow(int brandid);
    }

    CarBrandAdapter.BrandAdapterListener brandadapterListener = new CarBrandAdapter.BrandAdapterListener() {
        @Override
        public void setSelect(String title, String value) {
             brandListener.setSelect(title, value);
        }
    };
}
