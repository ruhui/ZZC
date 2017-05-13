package com.zzcar.zzc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.interfaces.ImageUploadListener;
import com.zzcar.zzc.networks.UploadFile;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.views.widget.LoadingProgressImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/4.
 */

public class PhotoAdapte extends RecyclerView.Adapter<PhotoAdapte.ViewHold> {

    private Context mContext;
    private ItemClickListener itemClickListener;
    private List<String> mList = new ArrayList<>();
    /*保存返回的图片路径*/
    private List<String> successPath = new ArrayList<>();

    public PhotoAdapte(Context context, List<String> list, ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.mList.addAll(list);
        mContext = context;
    }

    public void setData(List<String> list){
        mList.clear();
        successPath.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_photo_loadout, null);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(ViewHold holder, int position) {
        if (mList.size() == 0){
            holder.progressView.setVisibility(View.GONE);
            holder.picImg.setImageResource(R.drawable.ic_image);
        }else{
            if (mList.size() >= 9){
                String imgpath = mList.get(position);
                holder.progressView.setVisibility(View.VISIBLE);
                ImageLoader.loadImage(imgpath, holder.picImg);
                UploadFile uploadimg = new UploadFile(holder.progressView, successPath, uploadListener);
                uploadimg.execute(imgpath, Constant.UPLOADGOODSURL);
            }else{
                if (position == mList.size()){
                    holder.picImg.setImageResource(R.drawable.ic_image);
                    holder.progressView.setVisibility(View.GONE);
                }else{
                    String imgpath = mList.get(position);
                    ImageLoader.loadImage(imgpath, holder.picImg);
                    holder.progressView.setVisibility(View.VISIBLE);
                    UploadFile uploadimg = new UploadFile(holder.progressView, successPath, uploadListener);
                    uploadimg.execute(imgpath, Constant.UPLOADGOODSURL);
                }
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.itemListener();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList.size() == 0) {
            return 1;
        } else if (mList.size() < 9){
            return mList.size() + 1;
        }else{
            return mList.size();
        }

    }

    public class ViewHold extends RecyclerView.ViewHolder {
        public ImageView picImg;
        public LoadingProgressImageView progressView;

        public ViewHold(View itemView) {
            super(itemView);
            picImg = (ImageView) itemView.findViewById(R.id.picImg);
            progressView = (LoadingProgressImageView) itemView.findViewById(R.id.customView);
        }
    }

    public interface ItemClickListener{
        public void itemListener();
        public void imgbackListener(List<String> imgList);
    }

    ImageUploadListener uploadListener = new ImageUploadListener(){

        @Override
        public void finishLoading(String imgPath) {
            successPath.add(imgPath);
            if (mList.size() <= successPath.size()){
                //把值传给activity
                itemClickListener.imgbackListener(successPath);
            }
        }
    };
}
