package com.zzcar.zzc.activities;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.CitySortAdapter;
import com.zzcar.zzc.adapters.GroupMemberSortAdapter;
import com.zzcar.zzc.models.MemberModel;
import com.zzcar.zzc.models.ProvenceModel;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.SideBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 创建时间： 2017/6/27.
 * 作者：黄如辉
 * 功能描述：
 */
@EActivity(R.layout.activity_select_city)
public class GroupMemberActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.country_lvcountry)
    ListView countryListView;
    @ViewById(R.id.sidrbar)
    SideBar sideBar;

    private List<MemberModel> listMember = new ArrayList<>();

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        listMember = (List<MemberModel>) getIntent().getSerializableExtra("listMember");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("群成员");
        mNavbar.setMiddleTextColor(R.color.color_333333);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        PinyinComparator pinyinComparator = new PinyinComparator();
        Collections.sort(listMember, pinyinComparator);
        GroupMemberSortAdapter sortAdapter = new GroupMemberSortAdapter(getActivity(), listMember);
        countryListView.setAdapter(sortAdapter);

    }


    public class PinyinComparator implements Comparator<MemberModel> {

        public int compare(MemberModel o1, MemberModel o2) {
            if (o1.getFirst_letter().equals("@")
                    || o2.getFirst_letter().equals("#")) {
                return -1;
            } else if (o1.getFirst_letter().equals("#")
                    || o2.getFirst_letter().equals("@")) {
                return 1;
            } else {
                return o1.getFirst_letter().compareTo(o2.getFirst_letter());
            }
        }
    }

}
