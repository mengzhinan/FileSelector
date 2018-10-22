package com.duke.dfileselector.helper;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;

import com.duke.dfileselector.util.SizeUtils;

/**
 * @author duke
 * @dateTime 2018-09-10 21:52
 * @description item layout 属性设置辅助类
 */
public class ItemParameter {
    private Context context;

    private boolean isSetLayoutPadding;
    private float layoutPaddingLeft;
    private float layoutPaddingTop;
    private float layoutPaddingRight;
    private float layoutPaddingBottom;

    private boolean isSetLayoutBackgroundResourceId;
    private int layoutBackgroundResourceId;

    private boolean isSetLayoutBackgroundColor;
    private int layoutBackgroundColor;

    private boolean isSetImageWidth;
    private float imageWidth;
    private boolean isSetImageHeight;
    private float imageHeight;
    private boolean isSetImageMarginRight;
    private float imageMarginRight;

    private boolean isSetTitleTextSize;
    private float titleTextSize;

    private boolean isSetTitleTextColor;
    private int titleTextColor;

    private boolean titleTextBold;

    private boolean isSetCountTextSize;
    private float countTextSize;

    private boolean isSetCountTextColor;
    private int countTextColor;

    private boolean countTextBold;

    private boolean isSetDateTextSize;
    private float dateTextSize;

    private boolean isSetDateTextColor;
    private int dateTextColor;

    private boolean dateTextBold;

    private boolean isSetSplitLineColor;
    private int splitLineColor;

    private boolean isSetSplitLineWidth;
    private float splitLineWidth;

    private boolean isSetSplitLineHeight;
    private float splitLineHeight;

    private boolean isSetSplitLineMarginLeft;
    private float splitLineMarginLeft;

    private boolean isSetSplitLineMarginRight;
    private float splitLineMarginRight;

    private ItemParameter(Context context) {
        this.context = context;
        if (this.context == null) {
            throw new IllegalArgumentException("context is null exception!");
        }
    }

    public static ItemParameter with(Context context) {
        return new ItemParameter(context);
    }

    private float dimenResToFloat(@DimenRes int resId) {
        return (SizeUtils.getDimenResToPx(context, resId) * 1.0F);
    }

    public ItemParameter setLayoutPadding(@DimenRes int leftResId, @DimenRes int topResId,
                                          @DimenRes int rightResId, @DimenRes int bottomResId) {
        setLayoutPadding(dimenResToFloat(leftResId),
                dimenResToFloat(topResId),
                dimenResToFloat(rightResId),
                dimenResToFloat(bottomResId));
        return this;
    }

    public ItemParameter setLayoutPadding(float leftPX, float topPX, float rightPX, float bottomPX) {
        layoutPaddingLeft = leftPX;
        layoutPaddingTop = topPX;
        layoutPaddingRight = rightPX;
        layoutPaddingBottom = bottomPX;
        isSetLayoutPadding = true;
        return this;
    }

    public boolean isSetLayoutPadding() {
        return isSetLayoutPadding;
    }

    public float getLayoutPaddingLeft() {
        return layoutPaddingLeft;
    }

    public float getLayoutPaddingTop() {
        return layoutPaddingTop;
    }

    public float getLayoutPaddingRight() {
        return layoutPaddingRight;
    }

    public float getLayoutPaddingBottom() {
        return layoutPaddingBottom;
    }

    public ItemParameter setLayoutBackgroundResourceId(@DrawableRes int resId) {
        this.layoutBackgroundResourceId = resId;
        isSetLayoutBackgroundResourceId = true;
        return this;
    }

    public boolean isSetLayoutBackgroundResourceId() {
        return isSetLayoutBackgroundResourceId;
    }

    public int getLayoutBackgroundResourceId() {
        return layoutBackgroundResourceId;
    }

    public ItemParameter setLayoutBackgroundColor(@ColorInt int color) {
        this.layoutBackgroundColor = color;
        isSetLayoutBackgroundColor = true;
        return this;
    }

    public boolean isSetLayoutBackgroundColor() {
        return isSetLayoutBackgroundColor;
    }

    public int getLayoutBackgroundColor() {
        return layoutBackgroundColor;
    }

    public ItemParameter setImageWidth(@DimenRes int resId) {
        setImageWidth(dimenResToFloat(resId));
        return this;
    }

    public ItemParameter setImageWidth(float imageWidth) {
        this.imageWidth = imageWidth;
        isSetImageWidth = true;
        return this;
    }

    public boolean isSetImageWidth() {
        return isSetImageWidth;
    }

    public float getImageWidth() {
        return imageWidth;
    }

    public ItemParameter setImageHeight(@DimenRes int resId) {
        setImageHeight(dimenResToFloat(resId));
        return this;
    }

    public ItemParameter setImageHeight(float imageHeight) {
        this.imageHeight = imageHeight;
        isSetImageHeight = true;
        return this;
    }

    public boolean isSetImageHeight() {
        return isSetImageHeight;
    }

    public float getImageHeight() {
        return imageHeight;
    }

    public ItemParameter setImageMarginRight(@DimenRes int resId) {
        setImageMarginRight(dimenResToFloat(resId));
        return this;
    }

    public ItemParameter setImageMarginRight(float imageMarginRight) {
        this.imageMarginRight = imageMarginRight;
        isSetImageMarginRight = true;
        return this;
    }

    public boolean isSetImageMarginRight() {
        return isSetImageMarginRight;
    }

    public float getImageMarginRight() {
        return imageMarginRight;
    }

    public ItemParameter setTitleTextSize(@DimenRes int resId) {
        setTitleTextSize(dimenResToFloat(resId));
        return this;
    }

    public ItemParameter setTitleTextSize(float sizePX) {
        this.titleTextSize = sizePX;
        isSetTitleTextSize = true;
        return this;
    }

    public boolean isSetTitleTextSize() {
        return isSetTitleTextSize;
    }

    public float getTitleTextSize() {
        return titleTextSize;
    }

    public ItemParameter setTitleTextColor(@ColorInt int color) {
        this.titleTextColor = color;
        isSetTitleTextColor = true;
        return this;
    }

    public boolean isSetTitleTextColor() {
        return isSetTitleTextColor;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public ItemParameter setTitleTextBold(boolean titleTextBold) {
        this.titleTextBold = titleTextBold;
        return this;
    }

    public boolean isTitleTextBold() {
        return titleTextBold;
    }

    public ItemParameter setCountTextSize(@DimenRes int resId) {
        setCountTextSize(dimenResToFloat(resId));
        return this;
    }

    public ItemParameter setCountTextSize(float sizePX) {
        this.countTextSize = sizePX;
        isSetCountTextSize = true;
        return this;
    }

    public boolean isSetCountTextSize() {
        return isSetCountTextSize;
    }

    public float getCountTextSize() {
        return countTextSize;
    }

    public ItemParameter setCountTextColor(@ColorInt int color) {
        this.countTextColor = color;
        isSetCountTextColor = true;
        return this;
    }

    public boolean isSetCountTextColor() {
        return isSetCountTextColor;
    }

    public int getCountTextColor() {
        return countTextColor;
    }

    public ItemParameter setCountTextBold(boolean countTextBold) {
        this.countTextBold = countTextBold;
        return this;
    }

    public boolean isCountTextBold() {
        return countTextBold;
    }

    public ItemParameter setDateTextSize(@DimenRes int resId) {
        setDateTextSize(dimenResToFloat(resId));
        return this;
    }

    public ItemParameter setDateTextSize(float sizePX) {
        this.dateTextSize = sizePX;
        isSetDateTextSize = true;
        return this;
    }

    public boolean isSetDateTextSize() {
        return isSetDateTextSize;
    }

    public float getDateTextSize() {
        return dateTextSize;
    }

    public ItemParameter setDateTextColor(@ColorInt int color) {
        this.dateTextColor = color;
        isSetDateTextColor = true;
        return this;
    }

    public boolean isSetDateTextColor() {
        return isSetDateTextColor;
    }

    public int getDateTextColor() {
        return dateTextColor;
    }

    public ItemParameter setDateTextBold(boolean dateTextBold) {
        this.dateTextBold = dateTextBold;
        return this;
    }

    public boolean isDateTextBold() {
        return dateTextBold;
    }

    public ItemParameter setSplitLineColor(@ColorInt int color) {
        this.splitLineColor = color;
        isSetSplitLineColor = true;
        return this;
    }

    public boolean isSetSplitLineColor() {
        return isSetSplitLineColor;
    }

    public int getSplitLineColor() {
        return splitLineColor;
    }

    public ItemParameter setSplitLineWidth(@DimenRes int resId) {
        setSplitLineWidth(dimenResToFloat(resId));
        return this;
    }

    public ItemParameter setSplitLineWidth(float widthPX) {
        this.splitLineWidth = widthPX;
        isSetSplitLineWidth = true;
        return this;
    }

    public boolean isSetSplitLineWidth() {
        return isSetSplitLineWidth;
    }

    public float getSplitLineWidth() {
        return splitLineWidth;
    }

    public ItemParameter setSplitLineHeight(@DimenRes int resId) {
        setSplitLineHeight(dimenResToFloat(resId));
        return this;
    }

    public ItemParameter setSplitLineHeight(float heightPX) {
        this.splitLineHeight = heightPX;
        isSetSplitLineHeight = true;
        return this;
    }

    public boolean isSetSplitLineHeight() {
        return isSetSplitLineHeight;
    }

    public float getSplitLineHeight() {
        return splitLineHeight;
    }

    public ItemParameter setSplitLineMarginLeft(@DimenRes int resId) {
        setSplitLineMarginLeft(dimenResToFloat(resId));
        return this;
    }

    public ItemParameter setSplitLineMarginLeft(float splitLineMarginLeftPX) {
        this.splitLineMarginLeft = splitLineMarginLeftPX;
        this.isSetSplitLineMarginLeft = true;
        return this;
    }

    public boolean isSetSplitLineMarginLeft() {
        return isSetSplitLineMarginLeft;
    }

    public float getSplitLineMarginLeft() {
        return splitLineMarginLeft;
    }

    public ItemParameter setSplitLineMarginRight(@DimenRes int resId) {
        setSplitLineMarginRight(dimenResToFloat(resId));
        return this;
    }

    public ItemParameter setSplitLineMarginRight(float splitLineMarginRightPX) {
        this.splitLineMarginRight = splitLineMarginRightPX;
        this.isSetSplitLineMarginRight = true;
        return this;
    }

    public boolean isSetSplitLineMarginRight() {
        return isSetSplitLineMarginRight;
    }

    public float getSplitLineMarginRight() {
        return splitLineMarginRight;
    }
}
