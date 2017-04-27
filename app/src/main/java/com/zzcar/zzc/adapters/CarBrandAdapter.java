package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.ItemCarBrandView_;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.adapters.viewholders.ItemCarBrandView;

/**
 * Created by asus-pc on 2017/4/28.
 */

public class CarBrandAdapter extends BaseRecyclerAdapter<BrandListResponse, ItemCarBrandView> {

    private BrandAdapterListener carbrandListener;

    public CarBrandAdapter(BrandAdapterListener carbrandListener){
        this.carbrandListener = carbrandListener;
    }

    @Override
    protected ItemCarBrandView onCreateItemView(ViewGroup parent, int viewType) {
        return ItemCarBrandView_.build(parent.getContext());
    }

    @Override
    protected void onBindView(ItemCarBrandView itemView, final BrandListResponse brandListResponse, int position) {
        itemView.bind(brandListResponse);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carbrandListener.setSelect(brandListResponse.getName(), brandListResponse.getId()+"");
            }
        });
    }

    public interface BrandAdapterListener{
        public void setSelect(String title, String value);
    }
}
