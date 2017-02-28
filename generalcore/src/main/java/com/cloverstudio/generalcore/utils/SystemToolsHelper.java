package com.cloverstudio.generalcore.utils;

/**
 * 系统设置帮助类，该类最好在应用的application中进行设置
 * Created by wlei on 2017/2/28.
 */

public class SystemToolsHelper {
    /*是否支持日志打印输出*/
    private static boolean logData = true;
    /*支持日志打印时，打印日志的tag*/
    private static String logTag = "com.cloverstudio.generalcore";

    /**
     * 禁用日志答应功能
     */
    public static void disableLogData() {
        logData = false;
    }

    public static boolean isLogData() {
        return logData;
    }

    public static String getLogTag() {
        return logTag;
    }

    /**
     * 设置打印日志的tag
     *
     * @param logTag
     */
    public static void setLogTag(String logTag) {
        if (logTag != null && !logTag.equals("")) {
            SystemToolsHelper.logTag = logTag;
        }
    }
}
