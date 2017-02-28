package com.cloverstudio.generalcore.http.images;

import android.content.Context;
import android.net.Uri;

import com.cloverstudio.generalcore.utils.AppFileSystem;
import com.cloverstudio.generalcore.utils.MyImageLoader;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.nostra13.universalimageloader.utils.StorageUtils;

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
    }

    /**
     * 加载图片
     *
     * @param imageUrl   图片地址
     * @param draweeView 图片展示视图
     * @param scaleType  图片缩放类型
     */
    public static void loadImage(String imageUrl, SimpleDraweeView draweeView,
                                 ScaleType scaleType) throws Exception {
        if (!isInitialize) {
            throw new Exception("FrescoImageLoader未被初始化!");
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
     * 显示新闻的默认缩率图
     *
     * @param draweeView
     */
    public static void loadNewsSmallImage(SimpleDraweeView draweeView) throws Exception {
        if (!isInitialize) {
            throw new Exception("FrescoImageLoader未被初始化!");
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
}
