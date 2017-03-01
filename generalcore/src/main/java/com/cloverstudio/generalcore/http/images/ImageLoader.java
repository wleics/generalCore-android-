package com.cloverstudio.generalcore.http.images;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.cloverstudio.generalcore.utils.AppFileSystem;
import com.cloverstudio.generalcore.utils.MyImageLoader;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;


/**
 * 网络图片加载帮助类
 */
public class ImageLoader {
    /**
     * 是否初始化过
     */
    private static boolean isInitialize = false;

    /**
     * 执行初始化
     *
     * @param context
     */
    public static void initialize(Context context) {

        File cacheDir = StorageUtils.getOwnCacheDirectory(
                context,
                AppFileSystem.getAppRootDirName() + "/caches"
        );// 获取到缓存的目录地址
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(cacheDir).setBaseDirectoryName("caches")
                .build();
        // 设置文件的缓存位置
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setMainDiskCacheConfig(diskCacheConfig).build();
        Fresco.initialize(context, config);
        //标记工具初始化完成
        isInitialize = true;
        //初始化MyImageLoader
        MyImageLoader.initMyImageLoader(context);

        //指定picasso的文件缓存位置
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(cacheDir)).build();
        Picasso.setSingletonInstance(picasso);
    }

    /**
     * 加载图片(使用fresco加载图片)
     *
     * @param imageUrl   图片地址
     * @param draweeView 图片展示视图
     * @param scaleType  图片缩放类型
     */
    public static void loadImage(String imageUrl, SimpleDraweeView draweeView,
                                 ScaleType scaleType) throws Exception {
        if (!isInitialize) {
            throw new Exception("ImageLoader未被初始化!");
        }
        if (null == imageUrl || null == draweeView) {
            return;
        }
        GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
        hierarchy.setActualImageScaleType(scaleType);
        draweeView.setHierarchy(hierarchy);
        Uri uri = Uri.parse(imageUrl);
        draweeView.setImageURI(uri);
    }

    /**
     * 显示新闻的默认缩率图(使用fresco加载图片)
     *
     * @param draweeView
     */
    public static void loadNewsSmallImage(SimpleDraweeView draweeView) throws Exception {
        if (!isInitialize) {
            throw new Exception("ImageLoader未被初始化!");
        }
        if (null == draweeView) {
            return;
        }
        try {
            Uri uri = Uri
                    .parse("android.resource://com.zeheng.abs.app/drawable/base_news_image_small_def.png");
            draweeView.setImageURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载网络图片(使用picasso加载图片)
     *
     * @param context          上下文
     * @param url              图片地址
     * @param placeholderImgId 占位图片资源编号
     * @param errorImgId       错误图片编号
     * @param imageView        待显示图片的imageview
     * @param scaleType        图片缩放类型
     */
    public static void loadImageBy(Context context, String url, int placeholderImgId,
                                   int errorImgId, ImageView imageView,
                                   ImageView.ScaleType scaleType) {

        if (context == null || url == null || url
                .equals("") || imageView == null || placeholderImgId == -1 || errorImgId == -1) {
            return;
        }

        RequestCreator requestCreator = Picasso.with(context)
                .load(url)
                .placeholder(placeholderImgId)
                .error(errorImgId)
                .tag(context);

        if (scaleType != null) {
            if (scaleType == ImageView.ScaleType.MATRIX) {
            } else if (scaleType == ImageView.ScaleType.CENTER) {
            } else if (scaleType == ImageView.ScaleType.FIT_XY) {
                requestCreator.fit();
            } else if (scaleType == ImageView.ScaleType.FIT_START) {
                requestCreator.fit();
            } else if (scaleType == ImageView.ScaleType.FIT_CENTER) {
                requestCreator.fit();
            } else if (scaleType == ImageView.ScaleType.FIT_END) {
                requestCreator.fit();
            } else if (scaleType == ImageView.ScaleType.CENTER_CROP) {
                requestCreator.centerCrop();
            } else if (scaleType == ImageView.ScaleType.CENTER_INSIDE) {
                requestCreator.centerInside();
            } else {
                requestCreator.fit();
            }
        }

        requestCreator.into(imageView);
    }

    /**
     * 加载网络图片(使用picasso加载图片)
     *
     * @param context          上下文
     * @param url              图片地址
     * @param placeholderImgId 占位图片资源编号
     * @param imageView        待显示图片的imageview
     */
    public static void loadImageBy(Context context, String url, int placeholderImgId,
                                   ImageView imageView) {
        loadImageBy(context, url, placeholderImgId, placeholderImgId, imageView, null);
    }

    /**
     * 取消imageloader的图片加载逻辑
     *
     * @param context
     */
    public static void cancelImageLoadByTag(Context context) throws Exception {
        if (!isInitialize) {
            throw new Exception("ImageLoader未被初始化!");
        }
        if (context != null)
            Picasso.with(context).cancelTag(context);
    }
    // TODO: 2017/3/1 添加用户获取缓存文件夹大小的方法
    // TODO: 2017/3/1 添加用于图片清理缓存文件的方法
}
