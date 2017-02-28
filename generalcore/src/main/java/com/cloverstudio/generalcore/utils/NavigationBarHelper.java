package com.cloverstudio.generalcore.utils;

import android.content.Context;

/**
 * 导航栏设置帮助类
 * Created by wlei on 2017/2/28.
 */

public class NavigationBarHelper {
    private static int navigationBarDefBackgroundColor = -1;

    /*设置导航栏的默认背景颜色*/
    public static void setNavigationBarDefBackgroundColor(Context context, int colorResId) {
        if (context == null)
            return;
        navigationBarDefBackgroundColor = context.getResources().getColor(colorResId);
    }

    /*返回导航栏的默认背景颜色*/
    public static int getNavigationBarDefBackgroundColor() {
        return navigationBarDefBackgroundColor;
    }
}
