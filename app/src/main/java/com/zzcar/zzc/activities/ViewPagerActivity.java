/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.zzcar.zzc.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zzcar.zzc.R;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class ViewPagerActivity extends AppCompatActivity {

	private Context mContext;
	private List<String> imagepathList = new ArrayList<>();
	private int position;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设定为竖屏
		setContentView(R.layout.activity_view_pager);
		ViewPager mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
		setContentView(mViewPager);

		imagepathList = (List<String>) getIntent().getSerializableExtra("imagepathList");
		position = getIntent().getIntExtra("position",0);

		mContext = this;

		mViewPager.setAdapter(new SamplePagerAdapter());
		mViewPager.setCurrentItem(position);
	}

	class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imagepathList.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());

			Picasso.with(mContext)
					.load(Tool.getPicUrl(mContext, imagepathList.get(position)))
					.into(photoView, new Callback() {
						@Override
						public void onSuccess() {

						}

						@Override
						public void onError() {
						}
					});
//			photoView.setImageResource(sDrawables[position]);

			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}
}
