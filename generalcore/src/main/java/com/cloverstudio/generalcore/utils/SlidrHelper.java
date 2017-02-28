package com.cloverstudio.generalcore.utils;

import android.app.Activity;
import android.graphics.Color;

import com.cloverstudio.slidr.Slidr;
import com.cloverstudio.slidr.model.SlidrConfig;
import com.cloverstudio.slidr.model.SlidrInterface;
import com.cloverstudio.slidr.model.SlidrListener;
import com.cloverstudio.slidr.model.SlidrPosition;

/**
 * 侧滑帮助
 * 
 * @author wlei 2015-10-14
 */
public class SlidrHelper {
	private Activity mActivity;
	private SlidrInterface mSlidrInterface;
	private SlidrDelegate mDelegate;

	public void setDelegate(SlidrDelegate mDelegate) {
		this.mDelegate = mDelegate;
	}

	public static SlidrHelper getInstance(Activity activity) {
		if (activity == null) {
			return null;
		}
		SlidrHelper slidrHelper = new SlidrHelper();
		slidrHelper.mActivity = activity;
		return slidrHelper;
	}

	private SlidrHelper() {
	}

	/**
	 * 锁定页面，不让页面滑动关闭
	 */
	public void lock() {
		mSlidrInterface.lock();
	}

	/**
	 * 取消页面锁定，使页面可以滑动
	 */
	public void unLock() {
		mSlidrInterface.unlock();
	}

	/**
	 * 初始化页面滑动
	 * 
	 */
	public void initSlidr() {
		SlidrConfig config = new SlidrConfig.Builder()
				.primaryColor(
						mActivity.getResources()
								.getColor(android.R.color.black))
				.secondaryColor(
						mActivity.getResources()
								.getColor(android.R.color.black))
				.position(SlidrPosition.LEFT).sensitivity(1f)
				.scrimColor(Color.BLACK).scrimStartAlpha(0.8f)
				.scrimEndAlpha(0f).velocityThreshold(2400)
				.distanceThreshold(0.25f).edge(true | false).edgeSize(0.18f)
				.listener(new SlidrListener() {

					@Override
					public void onSlideStateChanged(int state) {
					}

					@Override
					public void onSlideOpened() {
					}

					@Override
					public void onSlideClosed() {
						if (mDelegate != null) {
							mDelegate.onSlideClosed();
						}
					}

					@Override
					public void onSlideChange(float percent) {
					}
				}).build();

		mSlidrInterface = Slidr.attach(mActivity, config);
	}

	public interface SlidrDelegate {
		public void onSlideClosed();
	}
}
