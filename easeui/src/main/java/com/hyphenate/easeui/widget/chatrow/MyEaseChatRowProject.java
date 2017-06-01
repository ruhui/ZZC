package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseImageUtils;
import com.hyphenate.exceptions.HyphenateException;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/31 15:13
 **/

public class MyEaseChatRowProject extends EaseChatRow {

    private ImageView imgProduct;
    private TextView txtProductName;
    private TextView txtMoeny;

    public MyEaseChatRowProject(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    /*注入布局*/
    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? R.layout.ease_row_carfrom_receive
                : R.layout.ease_row_carfrom_send, this);
    }

    /*把控件的id初始化出来*/
    @Override
    protected void onFindViewById() {
        imgProduct = (ImageView) findViewById(R.id.imgProduct);
        txtProductName = (TextView) findViewById(R.id.textView140);
        txtMoeny = (TextView) findViewById(R.id.textView141);
    }

    /*刷新列表视图状态更改时消息*/
    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    /*添加显示消息和位置等属性*/
    @Override
    protected void onSetUpView() {
        // 设置内容,通过扩展自文本获取消息内容，填充到相应的位置
//        imgProduct.setText("测试的项目");
//        message.setAttribute("product_id", homeCarGet.getProduct_id()+"");
//        message.setAttribute("first_image", homeCarGet.getFirst_image()+"");
//        message.setAttribute("market_price", homeCarGet.getMarket_price()+"");
//        message.setAttribute("name", homeCarGet.getName());

        String imageurl = message.getStringAttribute("first_image", "");
        String marketprice = message.getStringAttribute("market_price", "");
        String productname = message.getStringAttribute("name", "");

        Glide.with(imgProduct.getContext()).load(EaseImageUtils.getPicUrl(imgProduct.getContext(), imageurl, 100, 70)).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(imgProduct);
        txtProductName.setText(productname);
        txtMoeny.setText("￥"+marketprice+"万元");
        handleTextMessage();
    }

    /*添加气泡的点击事件*/
    @Override
    protected void onBubbleClick() {

    }


    protected void handleTextMessage() {
        if (message.direct() == EMMessage.Direct.SEND) {
            setMessageSendCallback();
            switch (message.status()) {
                case CREATE:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.GONE);
                    break;
                case FAIL:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case INPROGRESS:
                    progressBar.setVisibility(View.VISIBLE);
                    statusView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }else{
            if(!message.isAcked() && message.getChatType() == EMMessage.ChatType.Chat){
                try {
                    EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
