/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui.utils;

import android.content.Context;
import android.text.TextUtils;

import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil;

public class EaseImageUtils extends com.hyphenate.util.ImageUtils{
	
	public static String getImagePath(String remoteUrl)
	{
		String imageName= remoteUrl.substring(remoteUrl.lastIndexOf("/") + 1, remoteUrl.length());
		String path =PathUtil.getInstance().getImagePath()+"/"+ imageName;
        EMLog.d("msg", "image path:" + path);
        return path;
		
	}
	
	public static String getThumbnailImagePath(String thumbRemoteUrl) {
		String thumbImageName= thumbRemoteUrl.substring(thumbRemoteUrl.lastIndexOf("/") + 1, thumbRemoteUrl.length());
		String path =PathUtil.getInstance().getImagePath()+"/"+ "th"+thumbImageName;
        EMLog.d("msg", "thum image path:" + path);
        return path;
    }

	/**
	 * 获取图片路径
	 */
	public static String getPicUrl(Context mContext, String url, int width, int heigh){
		if (TextUtils.isEmpty(url)){
			return "";
		}
		int weidth = dip2px(mContext, width);
		int height = dip2px(mContext, heigh);
		if (url.contains(".jpg")){
			return "http://47.89.48.28:5002" + url + "_1_" + weidth + "_" + height + "_0.jpg";
		}else  if (url.contains(".png")){
			return "http://47.89.48.28:5002" + url + "_1_" + weidth + "_" + height + "_0.png";
		}else{
			return "";
		}
	}

	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}


}
