package com.hitek.zhangxin.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author zzj
 * @date 2017/9/26
 */

public class ScreenUtil {
    /**
     * Get the width of the screen.
     *获得屏幕宽度
     * @param context
     *            The context to use. Usually your
     *            {@link android.app.Application} or
     *            {@link Activity} object.
     * @return Return the width of the screen.
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * Get the height of the screen.
     *获得屏幕高度
     * @param context
     *            The context to use. Usually your
     *            {@link android.app.Application} or
     *            {@link Activity} object.
     * @return Return the height of the screen.
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}
