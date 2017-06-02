package com.zzcar.zzc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;

import com.hyphenate.EMContactListener;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.EBaseActivity;
import com.zzcar.zzc.fragments.ChatFragment;
import com.zzcar.zzc.interfaces.PermissionsManager;
import com.zzcar.zzc.views.widget.NavBar;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.NavBar3;

/**
 * chat activity，EaseChatFragment was used {@link # EaseChatFragment}
 *
 */
public class ChatActivity extends EBaseActivity {
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;
    private NavBar mNavbar;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        String nick = getIntent().getStringExtra("nick");
        boolean isfriend = getIntent().getBooleanExtra("isfriend", false);

        RelativeLayout addFriend = (RelativeLayout) findViewById(R.id.addFriend);
        mNavbar = (NavBar) findViewById(R.id.mNavbar);
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setRightMenuIcon(R.drawable.nav_icon_shezhihaoyou);
        mNavbar.setTitle(nick);
        mNavbar.setOnMenuClickListener(new NavBar.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                finish();
            }
        });

        if (isfriend){
            addFriend.setVisibility(View.GONE);
        }else{
            addFriend.setVisibility(View.VISIBLE);
        }

        activityInstance = this;
        //get user id or group id
        toChatUsername = getIntent().getExtras().getString("userId");
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加朋友
                Intent intent = new Intent(ChatActivity.this, SendFriendActivity_.class);
                intent.putExtra("userid", toChatUsername);
                startActivity(intent);
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
    	// make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}