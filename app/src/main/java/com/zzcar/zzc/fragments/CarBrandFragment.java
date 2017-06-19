package com.zzcar.zzc.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.greendao.BrandListResponseDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.CarBrandAdapter;
import com.zzcar.zzc.adapters.SortAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.BrandListener;
import com.zzcar.zzc.models.PinyinComparator;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.BrandPopwindow;
import com.zzcar.zzc.views.widget.SideBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by asus-pc on 2017/4/30.
 */

@EFragment(R.layout.layout_popwindiw_brand)
public class CarBrandFragment extends BaseFragment {

    private List<BrandListResponse> hotBrandList = new ArrayList<>();
    private List<BrandListResponse> mBrandList = new ArrayList<>();
    private CarBrandAdapter adapter;

    private CarseriesFragment carseriesFragment = CarseriesFragment_.builder().build();
    private SortAdapter sortAdapter;
    private PinyinComparator pinyinComparator;

    @ViewById(R.id.country_lvcountry)
    ListView sortListView;
    @ViewById(R.id.textView5)
    TextView textView5;
    @ViewById(R.id.sidrbar)
    SideBar sideBar;

    //listView中第一项的索引
    private int mListViewFirstItem = 0;
    //listView中第一项的在屏幕中的位置
    private int mScreenY = 0;


    @AfterViews
    void initView(){
        View vHead= View.inflate(getActivity(), R.layout.headview_brand, null);
        RelativeLayout relaClearPrice = (RelativeLayout) vHead.findViewById(R.id.relaClearPrice);
        RecyclerView mRecyclerView = (RecyclerView) vHead.findViewById(R.id.hotcityRecycleView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        mRecyclerView.setAdapter(adapter = new CarBrandAdapter(brandadapterListener));
        adapter.addAll(hotBrandList);


        sortListView.addHeaderView(vHead);
        pinyinComparator = new PinyinComparator();

        relaClearPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new BrandListener("品牌", ""));
            }
        });


        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = sortAdapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    sortListView.setSelection(position);
                }
                if (carseriesFragment.isAdded()){
                    carseriesFragment.closeSpecFragment();
                }
            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int brandid = mBrandList.get(position-1).getId();
                String branddes = mBrandList.get(position-1).getName();
                showCarSeriesfragment(brandid, branddes);
                if (carseriesFragment.isAdded()){
                    carseriesFragment.closeSpecFragment();
                }
//                closefragment();
//                brandListener.showSeriesPopwindow(brandid);
//               carseriespop.showAsDropDown(parentview);
//               carSeriesPopwindow.getDate(brandid);

            }
        });
        Collections.sort(mBrandList, pinyinComparator);
        sortAdapter = new SortAdapter(getActivity(), mBrandList);
        sortListView.setAdapter(sortAdapter);

        //取消
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                brandListener.setDismiss();
                closefragment();
            }
        });


        sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if(sortListView.getChildCount()>0)
                {
                    View childAt = sortListView.getChildAt(i);
                    int[] location = new int[2];
                    if (childAt != null){
                        childAt.getLocationOnScreen(location);
                    }
                    if(i!=mListViewFirstItem) {
                        if (carseriesFragment.isAdded()){
                            carseriesFragment.closefragment();
                        }
                        mListViewFirstItem = i;
                        mScreenY = location[1];
                    }else{
                        if(mScreenY>location[1]) {
                            LogUtil.E("--->", "->向上滑动");
                            if (carseriesFragment.isAdded()){
                                carseriesFragment.closefragment();
                            }
                        } else if(mScreenY<location[1]) {
                            if (carseriesFragment.isAdded()){
                                carseriesFragment.closefragment();
                            }
                            LogUtil.E("--->", "->向下滑动");
                        }
                        mScreenY = location[1];
                    }
                }
            }
        });
        setData();
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    CarBrandAdapter.BrandAdapterListener brandadapterListener = new CarBrandAdapter.BrandAdapterListener() {
        @Override
        public void setSelect(String title, String value) {
            showCarSeriesfragment(Integer.valueOf(value), title);
        }
    };


    public void setData(){
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

    public void closefragment() {
        getFragmentManager().popBackStackImmediate("CarBrandFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /*显示车系*/
    void showCarSeriesfragment(int brandid, String branddes){
        if (carseriesFragment==null || !carseriesFragment.isAdded()){
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, carseriesFragment, CarBrandFragment.class.getName());
            transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_right_exit);
            transaction.addToBackStack("CarseriesFragment");
            transaction.commit();
        }
        carseriesFragment.setBrand(brandid, branddes);
    }
}
