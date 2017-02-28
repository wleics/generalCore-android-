package com.cloverstudio.generalcore.utils;

/**
 * @author slma
 * @version 创建时间：2015年4月30日 下午3:04:38
 * @Log.java
 */
public class LogM {
    private static boolean flag = true;
    private static String TAG = "com.cloverstudio.generalcore";

    public static void show(String tag, String msg) {
        if (flag) {
            android.util.Log.i(tag, msg);
        }
    }

    /*打印日志*/
    public static void showLog(String msg) {
        if (msg != null) {
            show(TAG, msg);
        }
    }
}
