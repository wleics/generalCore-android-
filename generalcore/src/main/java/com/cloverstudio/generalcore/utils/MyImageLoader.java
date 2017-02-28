
package com.cloverstudio.generalcore.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

/**
 * 图片加载器
 *
 * @author wlei 2015-8-4
 */
public class MyImageLoader {
    /**
     * 图像缓存文件存放地址
     */
    private static String CACHEDIR_FILENAME = AppFileSystem.getAppRootDirName() + "/caches";

    // 是否初始化
    private static boolean IS_INIT = false;

    private static ImageLoader loader;

    public static void initMyImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, CACHEDIR_FILENAME);// 获取到缓存的目录地址
        // 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                // 线程池内加载的数量
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCache(new UnlimitedDiskCache(cacheDir))
                // 自定义缓存路径
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs()
                // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);// 全局初始化此配置
        IS_INIT = true;
    }

    /**
     * 显示网络图片
     *
     * @param url
     * @param imageView
     * @throws Exception
     */
    public static void loadImage(String url, final ImageView imageView) throws Exception {
        loadImage(url, imageView, false, null);
    }

    /**
     * 显示网络图片
     *
     * @param url
     * @param imageView
     * @param resetScaleType        是否需要重置imageview的图片缩放类型
     * @param myImageLoaderDelegate 回调
     * @throws Exception
     */
    public static void loadImage(String url, final ImageView imageView, boolean resetScaleType,
                                 final MyImageLoaderDelegate myImageLoaderDelegate)
            throws Exception {
        if (!IS_INIT) {
            throw new Exception("MyImageLoader not init");
        }
        if (url == null || imageView == null || url.trim().equals("")) {
            return;
        }
        if (loader == null) {
            loader = ImageLoader.getInstance();
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        if (resetScaleType) {
            final String key = SystemTools.hashKeyFormUrl(url.trim());
            Bitmap bitmap = LruImageCache.getBitmapFromMemCache(key);
            if (bitmap == null) {
                loader.displayImage(url.trim(), imageView, options, new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        imageView.setScaleType(ScaleType.CENTER_CROP);
                        LruImageCache.addBitmapToMemoryCache(key, loadedImage);
                        if (myImageLoaderDelegate != null) {
                            myImageLoaderDelegate.imageLoadComplete();
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            } else {
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ScaleType.CENTER_CROP);
                if (myImageLoaderDelegate != null) {
                    myImageLoaderDelegate.imageLoadComplete();
                }
            }

        } else {
            final String key = SystemTools.hashKeyFormUrl(url.trim());
            Bitmap bitmap = LruImageCache.getBitmapFromMemCache(key);
            if (bitmap == null) {
                loader.displayImage(url.trim(), imageView, options, new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        LruImageCache.addBitmapToMemoryCache(key, loadedImage);
                        if (myImageLoaderDelegate != null) {
                            myImageLoaderDelegate.imageLoadComplete();
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            } else {
                imageView.setImageBitmap(bitmap);
                if (myImageLoaderDelegate != null) {
                    myImageLoaderDelegate.imageLoadComplete();
                }
            }

        }
    }

    /**
     * 加载网络图片，并在图片加载完成后，回调
     *
     * @param url                   图片地址
     * @param imageView             用于显示图片的imageview
     * @param myImageLoaderDelegate 回调
     * @throws Exception
     */
    public static void loadImage(String url, final ImageView imageView,
                                 MyImageLoaderDelegate myImageLoaderDelegate) throws Exception {
        loadImage(url, imageView, false, myImageLoaderDelegate);
    }

    public static MyImageLoader getInstance() {
        return new MyImageLoader();
    }


    /**
     * 显示网络图片
     *
     * @param url
     * @param imageView
     * @param loadingView
     * @throws Exception
     */
    public static void loadImage(String url, ImageView imageView, final View loadingView)
            throws Exception {
        if (!IS_INIT) {
            throw new Exception("MyImageLoader not init");
        }
        if (url == null || imageView == null || url.trim().equals("")) {
            return;
        }
        if (loader == null) {
            loader = ImageLoader.getInstance();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();

        loader.displayImage(url.trim(), imageView, options, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadingView != null) {
                    loadingView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    /**
     * 加载图片，并执行与硬件加速先关的设置
     *
     * @param url         图片访问地址
     * @param imageView   显示图片用的imageview
     * @param loadingView 加载图片时需要用到的等待视图
     * @throws Exception
     */
    public static void loadImageBySecondaryInspection(String url, final ImageView imageView,
                                                      final View loadingView) throws Exception {
        if (!IS_INIT) {
            throw new Exception("MyImageLoader not init");
        }
        if (url == null || imageView == null || url.trim().equals("")) {
            return;
        }
        if (loader == null) {
            loader = ImageLoader.getInstance();
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        loader.loadImage(url.trim(), options, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                boolean b = isNeedCloseHardwareAcceleration(
                        loadedImage.getWidth(),
                        loadedImage.getHeight()
                );
                if (b) {
                    imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                    showImage(imageView, loadingView, imageUri);
                } else {
                    showImage(imageView, loadingView, imageUri);
                }
            }

            /**
             * 显示图片
             *
             * @param imageView
             * @param loadingView
             * @param imageUri
             */
            private void showImage(final ImageView imageView, final View loadingView,
                                   String imageUri) {
                loader.displayImage(imageUri, imageView);
                if (loadingView != null) {
                    loadingView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    /**
     * 通过检测图片的宽、高 来判断是否需要关闭硬件加速
     *
     * @param w 图片宽
     * @param h 图片高
     * @return
     */
    public static boolean isNeedCloseHardwareAcceleration(int w, int h) {
        int[] maxSize = new int[1];
        GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxSize, 0);

        if (maxSize[0] < h || maxSize[0] < w) {
            return true;
        }

        return false;
    }

    public interface MyImageLoaderDelegate {
        /**
         * 图片加载完成
         */
        public void imageLoadComplete();
    }

}
