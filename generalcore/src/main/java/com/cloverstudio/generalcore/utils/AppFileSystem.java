package com.cloverstudio.generalcore.utils;

import android.content.Context;
import android.os.Environment;

import com.cloverstudio.generalcore.R;
import com.cloverstudio.utils.libs.FileTools;

import java.io.File;
import java.math.BigDecimal;

/**
 * 系统文件结构
 *
 * @author wlei 2014-11-4
 */
public class AppFileSystem {
    /**
     * 头像文件存放位置
     */
    private final static String FOLDER_4_IMG = "img";
    private final static String FOLDER_4_VIDEO = "video";
    private final static String FOLDER_4_UPLOAD = "upload";
    /**
     * 存放版本信息的文件夹
     */
    private final static String FOLDER_4_VERSION = "version";
    /**
     * 手机sd卡根目录
     */
    private final static String sdCardDirPath = FileTools
            .getExternalStorageDirectory();
    /**
     * 应用主文件夹名称
     */
    private static String appRootDirName = "cloverStudioGeneralCore";
    /**
     * 系统根文件夹的位置
     */
    private static String ROOT_DIR_PATH = "";
    /**
     * 系统图片文件存放位置
     */
    private static String IMAGE_SAVE_PATH = appRootDirName + "/image";
    /**
     * 系统视频文件存放位置
     */
    private static String VIDEO_SAVE_PATH = appRootDirName + "/video";
    /**
     * 断点续传文件存放位置
     */
    private static String UPLOAD_TMP_SAVE_PATH = appRootDirName + "/upload";
    /**
     * 系统下载文件夹
     */
    private static String APP_DEFAULT_DOWNLOAD_PATH = sdCardDirPath
            + "Download";

    /**
     * app版本文件存放位置
     */
    private static String APPVERSION_FILE_SAVE_PATH = "";

    /**
     * 是否被初始化
     */
    private static boolean isInit = false;

    public static String getAppRootDirName() {
        return appRootDirName;
    }

    /**
     * 初始化应用的文件系统
     *
     * @param rootDirName 自定义的文件系统根目录名称
     */
    public static void initFileSystem(String rootDirName) {
        if (rootDirName != null && !rootDirName.equals("")) {
            appRootDirName = rootDirName;
        }
        // 创建根文件夹
        ROOT_DIR_PATH = FileTools.createFolder(sdCardDirPath, appRootDirName);
        // 初始化文件系统
        initFolder(ROOT_DIR_PATH);
        // 清除下载文件夹中的内容,为了避免用户下载应用后，系统安装以前版本的apk
        // 标记为已经初始化
        isInit = true;
    }

    /**
     * 清空导出文件夹中的内容
     *
     * @param context
     */
    public static void deleteExpFolder(Context context) throws Exception {
        if (!isInit) {
            throw new Exception("AppFileSystem未初始化!");
        }
        if (context == null)
            return;
        try {
            // 清空导出文件夹中的内容
            String sdCardPath = FileTools.getExternalStorageDirectory()
                    + context.getString(R.string.app_name) + File.separator;
            FileTools.delAllFile(sdCardPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 清空文件系统中的缓存内容
     */
    public static void clearFileSystemCaches() throws Exception {
        if (!isInit) {
            throw new Exception("AppFileSystem未初始化!");
        }
        FileTools.delAllFile(IMAGE_SAVE_PATH);
        FileTools.delAllFile(APPVERSION_FILE_SAVE_PATH);
        FileTools.delAllFile(VIDEO_SAVE_PATH);
        FileTools.delAllFile(UPLOAD_TMP_SAVE_PATH);
        FileTools.delAllFile(ROOT_DIR_PATH + "/caches");
    }

    /**
     * 初始化文件系统
     *
     * @param rootFilePath
     */
    private static void initFolder(String rootFilePath) {
        // 图片文件存放位置
        IMAGE_SAVE_PATH = FileTools.createFolder(rootFilePath, FOLDER_4_IMG);
        // app版本文件存放位置
        APPVERSION_FILE_SAVE_PATH = FileTools.createFolder(
                rootFilePath,
                FOLDER_4_VERSION
        );
        // 视频文件存放位置
        VIDEO_SAVE_PATH = FileTools.createFolder(rootFilePath, FOLDER_4_VIDEO);
        // 断点续传文件存放位置
        UPLOAD_TMP_SAVE_PATH = FileTools.createFolder(
                rootFilePath,
                FOLDER_4_UPLOAD
        );
    }

    /**
     * 获取文件目录的大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            // return size + "Byte";
            return "0KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 获取 总缓存的大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getAPPFileSystemSize(Context context) throws Exception {
        if (!isInit) {
            throw new Exception("AppFileSystem未初始化!");
        }
        long cacheSize = getFolderSize(new File(ROOT_DIR_PATH));
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 返回图片文件存放位置
     *
     * @return
     */
    public static String getImageSavePath() throws Exception {
        if (!isInit) {
            throw new Exception("AppFileSystem未初始化!");
        }
        return IMAGE_SAVE_PATH;
    }

    /**
     * 返回视频文件存放位置
     *
     * @return
     */
    public static String getVideoSavePath() throws Exception {
        if (!isInit) {
            throw new Exception("AppFileSystem未初始化!");
        }
        return VIDEO_SAVE_PATH;
    }

    /**
     * 返回系统的默认文件下载路径
     *
     * @return
     */
    public static String getDownFolderPath() throws Exception {
        if (!isInit) {
            throw new Exception("AppFileSystem未初始化!");
        }
        return APP_DEFAULT_DOWNLOAD_PATH;
    }

    /**
     * 获取app版本信息的存放位置
     *
     * @return
     */
    public static String getAppVersionFileSavePath() throws Exception {
        if (!isInit) {
            throw new Exception("AppFileSystem未初始化!");
        }
        return APPVERSION_FILE_SAVE_PATH;
    }

    /**
     * 返回断点记录文件保存的文件夹位置
     *
     * @return
     */
    public static String getUploadTmpFileSavePath() throws Exception {
        if (!isInit) {
            throw new Exception("AppFileSystem未初始化!");
        }
        return UPLOAD_TMP_SAVE_PATH;
    }
}
