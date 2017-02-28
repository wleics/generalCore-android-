package com.cloverstudio.generalcore.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class LruImageCache {
	private static LruCache<String, Bitmap> mMemoryCache;

	/**
	 * 将图片缓存进行初始化
	 */
	public static void initCache() {
		// 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
		// LruCache通过构造函数传入缓存值，以KB为单位。
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// 使用最大可用内存值的1/8作为缓存的大小。
		int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// 重写此方法来衡量每张图片的大小，默认返回图片数量。
				return bitmap.getByteCount() / 1024;
			}
		};
	}

	/**
	 * 清空缓存
	 */
	public static void clear() {
		if (mMemoryCache != null) {
			mMemoryCache.evictAll();
		}
	}

	/**
	 * 添加图像到缓存
	 * 
	 * @param key
	 * @param bitmap
	 */
	public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * 获取缓存中的图像
	 * 
	 * @param key
	 * @return
	 */
	public static Bitmap getBitmapFromMemCache(String key) {
		if (mMemoryCache == null) {
			initCache();
		}
		return mMemoryCache.get(key);
	}
}
