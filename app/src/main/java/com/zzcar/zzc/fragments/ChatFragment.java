package com.zzcar.zzc.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.adapter.message.EMAImageMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
import com.hyphenate.easeui.ui.SendMessageListener;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.GoodDetailActivity_;
import com.zzcar.zzc.activities.MainActivity;
import com.zzcar.zzc.activities.MycarFromSelectActivity_;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.interfaces.ImageUploadListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.CarFromModel;
import com.zzcar.zzc.models.HomeCarGet;
import com.zzcar.zzc.models.MyfavcarModle;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.UploadFileWithoutLoding;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import rx.Subscriber;

public class ChatFragment extends EaseChatFragment implements EaseChatFragmentHelper, SendMessageListener{

	// constant start from 11 to avoid conflict with constant in base class
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;
    //车源
    private static final int ITEM_CARFORM = 5;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;
    

    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    //red packet code : 红包功能使用的常量
    private static final int MESSAGE_TYPE_RECV_RED_PACKET = 5;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET = 6;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET_ACK = 7;
    private static final int MESSAGE_TYPE_RECV_RED_PACKET_ACK = 8;
    private static final int MESSAGE_TYPE_RECV_RANDOM = 11;
    private static final int MESSAGE_TYPE_SEND_RANDOM = 12;
    private static final int ITEM_RED_PACKET = 16;
    //end of red packet code
    private String userheadImg;
    private String nick, userid;

    /*缓存的聊天记录*/
    private EMMessage storemessage;




    /**
     * if it is chatBot 
     */
    private boolean isRobot;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        hideTitleBar();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setUpView() {
        hideTitleBar();
        nick = fragmentArgs.getString("nick");
        userheadImg = fragmentArgs.getString(EaseConstant.EXTRA_USER_HEADIMG);
        userid = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);

        setSendMessageHelper(this);
        setChatFragmentHelper(this);
//        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
//            Map<String,RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
//            if(robotMap!=null && robotMap.containsKey(toChatUsername)){
//                isRobot = true;
//            }
//        }
        super.setUpView();
        // set click listener
        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                onBackPressed();
            }
        });
//        ((EaseEmojiconMenu)inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconExampleGroupData.getData());
//        if(chatType == EaseConstant.CHATTYPE_GROUP){
//            inputMenu.getPrimaryMenu().getEditText().addTextChangedListener(new TextWatcher() {
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
////                    if(count == 1 && "@".equals(String.valueOf(s.charAt(start)))){
////                        startActivityForResult(new Intent(getActivity(), PickAtUserActivity.class).
////                                putExtra("groupId", toChatUsername), REQUEST_CODE_SELECT_AT_USER);
////                    }
//                }
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });
//        }
    }
    
    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
        //extend menu items
//        inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
//        inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);
        inputMenu.registerExtendMenuItem(R.string.attach_voice_carform, R.drawable.nav_icon_car, ITEM_CARFORM, extendMenuItemClickListener);
//            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.ic_launcher, ITEM_VIDEO_CALL, extendMenuItemClickListener);
        //聊天室暂时不支持红包功能
        //red packet code : 注册红包菜单选项
//        if (chatType != EaseConstant.CHATTYPE_CHATROOM) {
//            inputMenu.registerExtendMenuItem(R.string.attach_red_packet, R.drawable.em_chat_red_packet_selector, ITEM_RED_PACKET, extendMenuItemClickListener);
//        }
        //end of red packet code
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //send the video
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //send the file
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_AT_USER:
                    if(data != null){
                        String username = data.getStringExtra("username");
                        inputAtUsername(username, false);
                    }
                    break;
                case ITEM_CARFORM:
                    //自定义车源
                    MyfavcarModle homeCarGet = (MyfavcarModle) data.getSerializableExtra("homeCarGet");
                    sendProject(homeCarGet);
                    break;
            default:
                break;
            }
        }
        
    }

    /**
     * 设置消息属性
     */
    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if(isRobot){
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }

//        //设置要发送扩展消息用户昵称
//        message.setAttribute(Constant.USER_NAME, nick);
//        //设置要发送扩展消息用户头像
//        message.setAttribute(Constant.HEAD_IMAGE_URL, userheadImg);
//        //设置要发送拓展消息用户id
//        message.setAttribute(Constant.USER_ID, userid);
    }
    
    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }

    /**
     * 进入聊天细节
     */
    @Override
    public void onEnterToChatDetails() {
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
//            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
//            if (group == null) {
//                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
//                return;
//            }
//            startActivityForResult(
//                    (new Intent(getActivity(), GroupDetailsActivity.class).putExtra("groupId", toChatUsername)),
//                    REQUEST_CODE_GROUP_DETAIL);
        }else if(chatType == EaseConstant.CHATTYPE_CHATROOM){
//        	startActivityForResult(new Intent(getActivity(), ChatRoomDetailsActivity.class).putExtra("roomId", toChatUsername), REQUEST_CODE_GROUP_DETAIL);
        }
    }

    /**
     * 点击头像
     */
    @Override
    public void onAvatarClick(String username) {
        //handling when user click avatar
//        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
//        intent.putExtra("username", username);
//        startActivity(intent);
    }


    /**
     * 长按头像
     */
    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }

    /**
     * 点击气泡
     */
    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        if (message.getStringAttribute("type", "").equals("project")){
            String productId = message.getStringAttribute("product_id", "");
            Intent intent = new Intent(getActivity(), GoodDetailActivity_.class);
            intent.putExtra("productId", productId);
            startActivity(intent);
        }
        return false;
    }


    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //red packet code : 处理红包回执透传消息
        for (EMMessage message : messages) {
            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            String action = cmdMsgBody.action();//获取自定义action
//            if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
//                RedPacketUtil.receiveRedPacketAckMessage(message);
//                messageList.refresh();
//            }
        }
        //end of red packet code
        super.onCmdMessageReceived(messages);
    }

    /**
     * 长按气泡
     */
    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
    	// no message forward when in chat room
//        startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message",message)
//                .putExtra("ischatroom", chatType == EaseConstant.CHATTYPE_CHATROOM),
//                REQUEST_CODE_CONTEXT_MENU);
    }

    /**
     * 扩展菜单项点击,如果你想覆盖,返回true
     */
    //2.设置拓展消息点击事件
    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO:
    //            Intent intent = new Intent(getActivity(), ImageGridActivity.class);
    //            startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                break;
            case ITEM_FILE: //file
    //            selectFileFromLocal();
                break;
            case ITEM_VOICE_CALL:
                startVoiceCall();
                break;
            case ITEM_VIDEO_CALL:
    //            startVideoCall();
                break;
            case ITEM_RED_PACKET:
                break;
            case ITEM_CARFORM:
                //自定义车源
                Intent intent = new Intent(getActivity(), MycarFromSelectActivity_.class);
                intent.putExtra("userid", toChatUsername);
                startActivityForResult(intent, ITEM_CARFORM);
                break;
            //end of red packet code
            default:
                break;
            }
            //keep exist extend menu
        return false;
    }


    /**
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }
    
    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
//            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
//                    .putExtra("isComingCall", false));
//            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }
    
    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
//            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
//                    .putExtra("isComingCall", false));
//            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }



    /**
     * chat row provider 
     *
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
        	//which is used to count the number of different chat row
            return 10;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if(message.getType() == EMMessage.Type.TXT){
                //voice call
                if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                }else if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {

            if(message.getType() == EMMessage.Type.TXT){
                // voice call or video call
                if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                    message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
//                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
                }
//                //red packet code : 红包消息、红包回执消息以及转账消息的chat row
//                else if (RedPacketUtil.isRandomRedPacket(message)) {//小额随机红包
//                    return new ChatRowRandomPacket(getActivity(), message, position, adapter);
//                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {//红包消息
//                    return new ChatRowRedPacket(getActivity(), message, position, adapter);
//                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {//红包回执消息
//                    return new ChatRowRedPacketAck(getActivity(), message, position, adapter);
//                }
                //end of red packet code
            }
            return null;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //调用该方法可防止红包SDK引起的内存泄漏
//        RPRedPacketUtil.getInstance().detachView();
    }



    /*自定义消息的发送*/
    private void sendProject(MyfavcarModle homeCarGet) {
        // TODO Auto-generated method stub
        EMMessage message = EMMessage.createTxtSendMessage("project", toChatUsername);
        // 增加自己特定的属性
        message.setAttribute("type", "project");
        message.setAttribute("product_id", homeCarGet.getProduct_id()+"");
        message.setAttribute("first_image", homeCarGet.getFirst_image()+"");
        message.setAttribute("market_price", homeCarGet.getMarket_price()+"");
        message.setAttribute("name", homeCarGet.getName());
        sendMessage(message);
    }

    /**
     * 每当有信息的时候回调
     * @param message
     */
    @Override
    public void setOnSendMessageListener(EMMessage message) {
        if (message.getType() == EMMessage.Type.TXT){
            if (message.getStringAttribute("type","").equals("project")){
                //自定义
                String type = message.getStringAttribute("type", "");
                String product_id = message.getStringAttribute("product_id", "");
                String first_image = message.getStringAttribute("first_image", "");
                String market_price = message.getStringAttribute("market_price", "");
                String name = message.getStringAttribute("name", "");
                CarFromModel carFromModel = new CarFromModel(product_id, market_price);
                sendMessageToServer1(message.getTo(), "", first_image, carFromModel);
            }else{
                EMTextMessageBody textMessageBody = (EMTextMessageBody) message.getBody();
                String content = textMessageBody.getMessage().toString();
                sendMessageToServer(message.getTo(), content, "");
            }
        }else if (message.getType() == EMMessage.Type.IMAGE){
            this.storemessage = message;
            EMImageMessageBody imageMessageBody = (EMImageMessageBody) message.getBody();
            String localurl = imageMessageBody.getLocalUrl();
            //先上传图片
            new UploadFileWithoutLoding(uploadListener).execute(localurl, Constant.UPLOADCHATURL);
        }
    }

    ImageUploadListener uploadListener = new ImageUploadListener() {
        @Override
        public void finishLoading(String imgPath, int position) {
            sendMessageToServer(storemessage.getTo(), "", imgPath);
        }
    };


    void sendMessageToServer(String toid, String content, String imagepath){
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_save);
        content = content.replace("[:'(]","");
        content = content.replace("[<o)]", "");
        UserManager.addChatMessage(toid, content, imagepath, null, subscriber);
    }

    void sendMessageToServer1(String toid, String content, String imagepath, CarFromModel carFromModel){
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_save);
        UserManager.addChatMessage(toid, content, imagepath, carFromModel, subscriber);
    }

    ResponseResultListener callback_save = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            LogUtil.E("returnMsg", "dsf");
        }

        @Override
        public void fialed(String resCode, String message) {
        }
    };
}
