package com.zzcar.zzc.views.widget.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;

/**
   * 得先show，否则会出空指针
   * 创建作者： 黄如辉
   * 创建时间： 2017/4/22 14:25
  **/

public class MyAlertDialog extends AlertDialog {

   public TextView dialogTitle,txtContent,btnSure;
   public RelativeLayout relaSure,relaCancle;
   private boolean bothbutton = false;



   public MyAlertDialog(Context context, boolean bothbutton) {
       super(context,  R.style.MyDialogTheme);
       this.bothbutton = bothbutton;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.dialog_alert);
       dialogTitle = (TextView) findViewById(R.id.textView59);
       txtContent = (TextView) findViewById(R.id.textView60);
       relaSure = (RelativeLayout) findViewById(R.id.relaSure);
       relaCancle = (RelativeLayout) findViewById(R.id.relaCancle);
       if (bothbutton){
           relaCancle.setVisibility(View.VISIBLE);
       }
       relaCancle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dismiss();
           }
       });
   }

   public void setTitle(String title){
       dialogTitle.setText(title);
   }

   public void setContent(String content){
       txtContent.setText(content);
   }

   public void setOnPositiveListener(View.OnClickListener listener){
       relaSure.setOnClickListener(listener);
   }
}
