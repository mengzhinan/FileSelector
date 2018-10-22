package com.duke.dfileselector.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Author: duke
 * @DateTime: 2017-05-27 15:05
 * @Description:
 */
public class SizeUtils {

    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (context == null) {
            return null;
        }
        return context.getResources().getDisplayMetrics();
    }

    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        if (activity == null) {
            return null;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    public static int getScreenWidthPX(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        if (metrics == null) {
            return 0;
        }
        return metrics.widthPixels;
    }

    public static int getScreenHeigthPX(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        if (metrics == null) {
            return 0;
        }
        return metrics.heightPixels;
    }

    public static float getScreenWidthDP(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        if (metrics == null) {
            return 0;
        }
        return metrics.widthPixels / metrics.density;
    }

    public static float getScreenHeigthDP(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        if (metrics == null) {
            return 0;
        }
        return metrics.heightPixels / metrics.density;
    }

    public static int getDimenResToPx(Context context, int dimenResId) {
        if (context == null) {
            return -1;
        }
        return context.getResources().getDimensionPixelSize(dimenResId);
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2pxOfTypedValue(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static int dp2pxOfTypedValue(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static Rect getViewRectInParent(View view, ViewGroup parent) {
        Rect rect = new Rect();
        view.getDrawingRect(rect);
        parent.offsetDescendantRectToMyCoords(view, rect);
        return rect;
    }

    public static Rect getWindowTopTaskRect(Activity context) {
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect;
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
