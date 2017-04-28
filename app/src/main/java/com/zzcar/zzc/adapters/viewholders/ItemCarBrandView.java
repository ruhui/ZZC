package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by asus-pc on 2017/4/27.
 */

@EViewGroup(R.layout.viewitempictext)
public class ItemCarBrandView extends RelativeLayout {
    private Context mContext;

    @ViewById(R.id.imageView3)
    ImageView imageView3;
    @ViewById(R.id.textView3)
    TextView textView3;

    public ItemCarBrandView(Context context) {
        super(context);
        mContext = context;
    }

    public ItemCarBrandView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void setImageView(String imgPath){
        imgPath = Tool.getPicUrl(mContext, imgPath);
        ImageLoader.loadImage(imgPath, imageView3);
    }

    public void setTitle(String title){
        textView3.setText(title);
    }

    public void bind(BrandListResponse brandListResponse){
        setTitle(brandListResponse.getName());
        setImageView(brandListResponse.getLogo());
    }
}
