package com.duke.dfileselector.helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.duke.dfileselector.provide.dateFormat.FileDateProvide;
import com.duke.dfileselector.provide.icon.FileIconProvide;
import com.duke.dfileselector.provide.order.FileOrderProvide;
import com.duke.dfileselector.provide.size.FileSizeProvide;
import com.duke.dfileselector.util.FileSelectorUtils;
import com.duke.dfileselector.util.SizeUtils;
import com.duke.dfileselector.widget.FileSelectorLayout;
import com.duke.dfileselector.widget.RecycleViewDivider;

import java.io.FileFilter;
import java.util.ArrayList;

/**
 * @author duke
 * @dateTime 2018-09-09 21:24
 * @description 文件选择器代理类
 */
public class FileSelector {

    public interface OnFileSelectListener {
        void onSelected(ArrayList<String> list);
    }

    //文件选择器核心控件
    private FileSelectorLayout selectorLayout;
    private Context context;

    private FileSelector(FileSelectorLayout selectorLayout) {
        this.selectorLayout = selectorLayout;
        if (this.selectorLayout == null) {
            throw new IllegalArgumentException("The parameter of FileSelectorLayout is null exception.");
        }
        this.context = selectorLayout.getContext();
    }

    public static FileSelector with(FileSelectorLayout selectorLayout) {
        return new FileSelector(selectorLayout);
    }

    private float dimenToPXFloat(@DimenRes int resId) {
        return SizeUtils.getDimenResToPx(context, resId) * 1.0F;
    }

    private float dpToPXFloat(float dp) {
        return SizeUtils.dp2px(context, dp) * 1.0F;
    }

    /**
     * 文件选择回调
     *
     * @param l
     * @return
     */
    public FileSelector listen(OnFileSelectListener l) {
        selectorLayout.setOnFileSelectListener(l);
        return this;
    }

    /**
     * 设置recyclerView的item布局样式
     *
     * @param itemParameter
     * @return
     */
    public FileSelector setItemParameter(ItemParameter itemParameter) {
        selectorLayout.setItemViewHelper(itemParameter);
        return this;
    }

    /**
     * 设置是否为多选模式
     *
     * @param isMultiSelectionModel
     * @return
     */
    public FileSelector setMultiSelectionModel(boolean isMultiSelectionModel) {
        selectorLayout.setMultiSelectionModel(isMultiSelectionModel);
        return this;
    }

    /**
     * 设置多选模式下最多可选择文件的数量
     *
     * @param maxSize
     * @return
     */
    public FileSelector setMultiModelMaxSize(int maxSize) {
        selectorLayout.setMultiModelMaxSize(maxSize);
        return this;
    }

    /**
     * 设置多选模式下超出数量时的toast提示
     *
     * @param isNeedToastIfOutOfMaxSize 是否需要toast
     * @param toastStringIfOutOfMaxSize toast的文本
     * @return
     */
    public FileSelector setMultiModelToast(boolean isNeedToastIfOutOfMaxSize, @NonNull String toastStringIfOutOfMaxSize) {
        selectorLayout.setMultiModelToast(isNeedToastIfOutOfMaxSize, toastStringIfOutOfMaxSize);
        return this;
    }

    /**
     * 设置列表item的文件大小或子目录中文件个数的数据显示样式
     *
     * @param fileSizeProvide
     * @return
     */
    public FileSelector setFileSizeProvide(FileSizeProvide fileSizeProvide) {
        if (fileSizeProvide == null) {
            return this;
        }
        selectorLayout.setFileSizeProvide(fileSizeProvide);
        return this;
    }

    /**
     * 设置列表文件类型对应的图标
     *
     * @param fileIconProvide
     * @return
     */
    public FileSelector setFileIconProvide(FileIconProvide fileIconProvide) {
        if (fileIconProvide == null) {
            return this;
        }
        selectorLayout.setFileIconProvide(fileIconProvide);
        return this;
    }

    /**
     * 设置列表item的时间显示格式
     *
     * @param fileDateProvide
     * @return
     */
    public FileSelector setFileDateProvide(FileDateProvide fileDateProvide) {
        if (fileDateProvide == null) {
            return this;
        }
        selectorLayout.setFileDateProvide(fileDateProvide);
        return this;
    }

    /**
     * 设置列表可现实的文件过滤条件
     *
     * @param fileFilter
     * @return
     */
    public FileSelector setFileFilter(FileFilter fileFilter) {
        if (fileFilter == null) {
            return this;
        }
        selectorLayout.setFileFilter(fileFilter);
        return this;
    }

    /**
     * 设置文件列表的排序比较器
     *
     * @param fileOrderProvide
     * @return
     */
    public FileSelector setFileOrderProvide(FileOrderProvide fileOrderProvide) {
        if (fileOrderProvide == null) {
            return this;
        }
        selectorLayout.setFileOrderProvide(fileOrderProvide);
        return this;
    }

    /**
     * 显示head顶部的横线
     *
     * @param visible
     * @return
     */
    public FileSelector setHeadTopLineVisibility(int visible) {
        selectorLayout.getHeadTopLine().setVisibility(visible);
        return this;
    }

    /**
     * 设置head顶部横线颜色
     *
     * @param color
     * @return
     */
    public FileSelector setHeadTopLineColor(@ColorInt int color) {
        selectorLayout.getHeadTopLine().setBackgroundColor(color);
        setHeadTopLineVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置head顶部横线高度
     *
     * @param resId
     * @return
     */
    public FileSelector setHeadTopLineHeight(@DimenRes int resId) {
        return setHeadTopLineHeightPX(dimenToPXFloat(resId));
    }

    /**
     * 设置head顶部横线高度
     *
     * @param dpHeight
     * @return
     */
    public FileSelector setHeadTopLineHeightDP(float dpHeight) {
        return setHeadTopLineHeightPX(dpToPXFloat(dpHeight));
    }

    /**
     * 设置head顶部横线高度
     *
     * @param pxHeight
     * @return
     */
    public FileSelector setHeadTopLineHeightPX(float pxHeight) {
        ViewGroup.LayoutParams layoutParams = selectorLayout.getHeadTopLine().getLayoutParams();
        if (layoutParams == null) {
            return this;
        }
        setHeadTopLineVisibility(View.VISIBLE);
        layoutParams.height = (int) pxHeight;
        selectorLayout.getHeadTopLine().setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置head顶部横线的边距
     *
     * @param leftDimenRes
     * @param topDimenRes
     * @param rightDimenRes
     * @param bottomDimenRes
     * @return
     */
    public FileSelector setHeadTopLineMargin(@DimenRes int leftDimenRes, @DimenRes int topDimenRes, @DimenRes int rightDimenRes, @DimenRes int bottomDimenRes) {
        return setHeadTopLineMarginPX(
                dimenToPXFloat(leftDimenRes),
                dimenToPXFloat(topDimenRes),
                dimenToPXFloat(rightDimenRes),
                dimenToPXFloat(bottomDimenRes));
    }

    /**
     * 设置head顶部横线的边距
     *
     * @param leftDP
     * @param topDP
     * @param rightDP
     * @param bottomDP
     * @return
     */
    public FileSelector setHeadTopLineMarginDP(float leftDP, float topDP, float rightDP, float bottomDP) {
        return setHeadTopLineMarginPX(
                dpToPXFloat(leftDP),
                dpToPXFloat(topDP),
                dpToPXFloat(rightDP),
                dpToPXFloat(bottomDP));
    }

    /**
     * 设置head顶部横线的边距
     *
     * @param leftPX
     * @param topPX
     * @param rightPX
     * @param bottomPX
     * @return
     */
    public FileSelector setHeadTopLineMarginPX(float leftPX, float topPX, float rightPX, float bottomPX) {
        ViewGroup.LayoutParams layoutParams = selectorLayout.getHeadTopLine().getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return this;
        }
        setHeadTopLineVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginLayoutParams.setMargins((int) leftPX, (int) topPX, (int) rightPX, (int) bottomPX);
        selectorLayout.getHeadTopLine().setLayoutParams(marginLayoutParams);
        return this;
    }

    /**
     * 显示head底部的横线
     *
     * @param visible
     * @return
     */
    public FileSelector setHeadBottomLineVisibility(int visible) {
        selectorLayout.getHeadBottomLine().setVisibility(visible);
        return this;
    }


    /**
     * 设置head底部横线颜色
     *
     * @param color
     * @return
     */
    public FileSelector setHeadBottomLineColor(@ColorInt int color) {
        selectorLayout.getHeadBottomLine().setBackgroundColor(color);
        setHeadBottomLineVisibility(View.VISIBLE);
        return this;
    }


    /**
     * 设置head顶部横线高度
     *
     * @param resId
     * @return
     */
    public FileSelector setHeadBottomLineHeight(@DimenRes int resId) {
        return setHeadBottomLineHeightPX(dimenToPXFloat(resId));
    }

    /**
     * 设置head底部横线高度
     *
     * @param dpHeight
     * @return
     */
    public FileSelector setHeadBottomLineHeightDP(float dpHeight) {
        return setHeadBottomLineHeightPX(dpToPXFloat(dpHeight));
    }

    /**
     * 设置head底部横线高度
     *
     * @param pxHeight
     * @return
     */
    public FileSelector setHeadBottomLineHeightPX(float pxHeight) {
        ViewGroup.LayoutParams layoutParams = selectorLayout.getHeadBottomLine().getLayoutParams();
        if (layoutParams == null) {
            return this;
        }
        setHeadBottomLineVisibility(View.VISIBLE);
        layoutParams.height = (int) pxHeight;
        selectorLayout.getHeadBottomLine().setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置head底部横线的边距
     *
     * @param leftDimenRes
     * @param topDimenRes
     * @param rightDimenRes
     * @param bottomDimenRes
     * @return
     */
    public FileSelector setHeadBottomLineMargin(@DimenRes int leftDimenRes, @DimenRes int topDimenRes, @DimenRes int rightDimenRes, @DimenRes int bottomDimenRes) {
        return setHeadBottomLineMarginPX(
                dimenToPXFloat(leftDimenRes),
                dimenToPXFloat(topDimenRes),
                dimenToPXFloat(rightDimenRes),
                dimenToPXFloat(bottomDimenRes));
    }

    /**
     * 设置head底部横线的边距
     *
     * @param leftDP
     * @param topDP
     * @param rightDP
     * @param bottomDP
     * @return
     */
    public FileSelector setHeadBottomLineMarginDP(float leftDP, float topDP, float rightDP, float bottomDP) {
        return setHeadBottomLineMarginPX(
                dpToPXFloat(leftDP),
                dpToPXFloat(topDP),
                dpToPXFloat(rightDP),
                dpToPXFloat(bottomDP));
    }

    /**
     * 设置head底部横线的边距
     *
     * @param leftPX
     * @param topPX
     * @param rightPX
     * @param bottomPX
     * @return
     */
    public FileSelector setHeadBottomLineMarginPX(float leftPX, float topPX, float rightPX, float bottomPX) {
        ViewGroup.LayoutParams layoutParams = selectorLayout.getHeadBottomLine().getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return this;
        }
        setHeadBottomLineVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginLayoutParams.setMargins((int) leftPX, (int) topPX, (int) rightPX, (int) bottomPX);
        selectorLayout.getHeadBottomLine().setLayoutParams(marginLayoutParams);
        return this;
    }

    /**
     * 设置选择器头部容器高度
     *
     * @param resId
     * @return
     */
    public FileSelector setHeadRootHeight(@DimenRes int resId) {
        return setHeadRootHeightPX(dimenToPXFloat(resId));
    }

    /**
     * 设置选择器头部容器高度
     *
     * @param dpHeight
     * @return
     */
    public FileSelector setHeadRootHeightDP(float dpHeight) {
        return setHeadRootHeightPX(dpToPXFloat(dpHeight));
    }

    /**
     * 设置选择器头部容器高度
     *
     * @param pxHeight
     * @return
     */
    public FileSelector setHeadRootHeightPX(float pxHeight) {
        ViewGroup.LayoutParams layoutParams = selectorLayout.getHeadRoot().getLayoutParams();
        if (layoutParams == null) {
            return this;
        }
        layoutParams.height = (int) pxHeight;
        selectorLayout.getHeadRoot().setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置head内容的边距
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public FileSelector setHeadRootPadding(@DimenRes int left, @DimenRes int top, @DimenRes int right, @DimenRes int bottom) {
        return setHeadRootPaddingPX(
                dimenToPXFloat(left),
                dimenToPXFloat(top),
                dimenToPXFloat(right),
                dimenToPXFloat(bottom));
    }

    /**
     * 设置head内容的边距
     *
     * @param leftDP
     * @param topDP
     * @param rightDP
     * @param bottomDP
     * @return
     */
    public FileSelector setHeadRootPaddingDP(float leftDP, float topDP, float rightDP, float bottomDP) {
        return setHeadRootPaddingPX(
                dpToPXFloat(leftDP),
                dpToPXFloat(topDP),
                dpToPXFloat(rightDP),
                dpToPXFloat(bottomDP));
    }

    /**
     * 设置head内容的边距
     *
     * @param leftPX
     * @param topPX
     * @param rightPX
     * @param bottomPX
     * @return
     */
    public FileSelector setHeadRootPaddingPX(float leftPX, float topPX, float rightPX, float bottomPX) {
        selectorLayout.getHeadRoot().setPadding((int) leftPX, (int) topPX, (int) rightPX, (int) bottomPX);
        return this;
    }

    /**
     * 设置head背景颜色
     *
     * @param color
     * @return
     */
    public FileSelector setHeadRootBackgroundColor(@ColorInt int color) {
        selectorLayout.getHeadRoot().setBackgroundColor(color);
        return this;
    }

    /**
     * 设置head背景颜色
     *
     * @param resId
     * @return
     */
    public FileSelector setHeadRootBackgroundResource(@DrawableRes int resId) {
        selectorLayout.getHeadRoot().setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置选择器头部左边当前路径文本的字体大小
     *
     * @param resId
     * @return
     */
    public FileSelector setHeadLeftTextSize(@DimenRes int resId) {
        return setHeadLeftTextSizePX(dimenToPXFloat(resId));
    }

    /**
     * 设置选择器头部左边当前路径文本的字体大小
     *
     * @param px
     * @return
     */
    public FileSelector setHeadLeftTextSizePX(float px) {
        selectorLayout.getHeadPathTextView().getPaint().setTextSize(px);
        return this;
    }

    /**
     * 设置选择器头部左边当前路径文本的字体大小
     *
     * @param sp
     * @return
     */
    public FileSelector setHeadLeftTextSizeSP(float sp) {
        selectorLayout.getHeadPathTextView().setTextSize(sp);
        return this;
    }

    /**
     * 设置选择器头部左边当前路径文本的字体颜色
     *
     * @param color
     * @return
     */
    public FileSelector setHeadLeftTextColor(@ColorInt int color) {
        selectorLayout.getHeadPathTextView().setTextColor(color);
        return this;
    }

    /**
     * 设置选择器头部左边当前路径文本的字体颜色
     *
     * @param colors
     * @return
     */
    public FileSelector setHeadLeftTextColor(ColorStateList colors) {
        if (colors == null) {
            return this;
        }
        selectorLayout.getHeadPathTextView().setTextColor(colors);
        return this;
    }

    /**
     * 设置选择器头部左边当前路径文本的字体为粗体
     *
     * @param isBold
     * @return
     */
    public FileSelector setHeadLeftTextBold(boolean isBold) {
        selectorLayout.getHeadPathTextView().setTypeface(FileSelectorUtils.getTypeface(isBold));
        return this;
    }

    /**
     * 设置选择器头部左边路径文本的省略方式
     *
     * @param textEllipsize
     * @return
     */
    public FileSelector setHeadLeftTextEllipsize(TextUtils.TruncateAt textEllipsize) {
        if (textEllipsize == null) {
            return this;
        }
        selectorLayout.getHeadPathTextView().setEllipsize(textEllipsize);
        return this;
    }

    /**
     * 设置选择器头部右边返回按钮的left返回图标
     *
     * @param leftDrawable
     * @return
     */
    public FileSelector setHeadRightImage(Drawable leftDrawable) {
        return setHeadRightImagePX(leftDrawable, 0, 0);
    }

    /**
     * 设置选择器头部右边返回按钮的left返回图标
     *
     * @param leftDrawable
     * @return
     */
    public FileSelector setHeadRightImageDP(Drawable leftDrawable, float widthDP, float heightDP) {
        return setHeadRightImagePX(leftDrawable, dpToPXFloat(widthDP), dpToPXFloat(heightDP));
    }

    /**
     * 设置选择器头部右边返回按钮的left返回图标
     *
     * @param leftDrawable
     * @return
     */
    public FileSelector setHeadRightImagePX(Drawable leftDrawable, float widthPX, float heightPX) {
        if (leftDrawable == null) {
            return this;
        }
        if (widthPX <= 0) {
            widthPX = leftDrawable.getMinimumWidth();
        }
        if (heightPX <= 0) {
            heightPX = leftDrawable.getMinimumHeight();
        }
        leftDrawable.setBounds(0, 0, (int) widthPX, (int) heightPX);
        selectorLayout.getHeadBackTextView().setCompoundDrawables(leftDrawable, null, null, null);
        return this;
    }

    /**
     * 设置选择器头部右边返回按钮的文本
     *
     * @param resId
     * @return
     */
    public FileSelector setHeadRightText(@StringRes int resId) {
        selectorLayout.getHeadBackTextView().setText(resId);
        return this;
    }

    /**
     * 设置选择器头部右边返回按钮的文本
     *
     * @param text
     * @return
     */
    public FileSelector setHeadRightText(String text) {
        selectorLayout.getHeadBackTextView().setText(text);
        return this;
    }

    /**
     * 设置选择器头部右边返回按钮的字体大小
     *
     * @param resId
     * @return
     */
    public FileSelector setHeadRightTextSize(@DimenRes int resId) {
        return setHeadRightTextSizePX(dimenToPXFloat(resId));
    }

    /**
     * 设置选择器头部右边返回按钮的字体大小
     *
     * @param px
     * @return
     */
    public FileSelector setHeadRightTextSizePX(float px) {
        selectorLayout.getHeadBackTextView().getPaint().setTextSize(px);
        return this;
    }

    /**
     * 设置选择器头部右边返回按钮的字体大小
     *
     * @param sp
     * @return
     */
    public FileSelector setHeadRightTextSizeSP(float sp) {
        selectorLayout.getHeadBackTextView().setTextSize(sp);
        return this;
    }

    /**
     * 设置选择器头部右边返回按钮的字体颜色
     *
     * @param color
     * @return
     */
    public FileSelector setHeadRightTextColor(@ColorInt int color) {
        selectorLayout.getHeadBackTextView().setTextColor(color);
        return this;
    }

    /**
     * 设置选择器头部右边返回按钮的字体颜色
     *
     * @param colors
     * @return
     */
    public FileSelector setHeadRightTextColor(ColorStateList colors) {
        if (colors == null) {
            return this;
        }
        selectorLayout.getHeadBackTextView().setTextColor(colors);
        return this;
    }

    /**
     * 设置选择器头部右边返回按钮的字体粗体
     *
     * @param isBold
     * @return
     */
    public FileSelector setHeadRightTextBold(boolean isBold) {
        selectorLayout.getHeadBackTextView().setTypeface(FileSelectorUtils.getTypeface(isBold));
        return this;
    }

    /**
     * 设置head右边返回按钮的背景颜色
     *
     * @param color
     * @return
     */
    public FileSelector setHeadRightTextBackgroundColor(@ColorInt int color) {
        selectorLayout.getHeadBackTextView().setBackgroundColor(color);
        return this;
    }

    /**
     * 设置head右边返回按钮的背景资源
     *
     * @param resId
     * @return
     */
    public FileSelector setHeadRightTextBackgroundResource(@DrawableRes int resId) {
        selectorLayout.getHeadBackTextView().setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置recyclerView自定义的分割线
     *
     * @param itemDecoration
     * @return
     */
    public FileSelector setRecyclerViewLineDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (itemDecoration == null) {
            return this;
        }
        selectorLayout.getRecyclerView().addItemDecoration(itemDecoration);
        return this;
    }

    /**
     * 设置recyclerView分割线颜色和颜色
     *
     * @param color
     * @return
     */
    public FileSelector setRecyclerViewLineColorHeight(@ColorInt int color, @DimenRes int heightResId, @DimenRes int marginResId) {
        return setRecyclerViewLineColorHeightPX(color, dimenToPXFloat(heightResId), dimenToPXFloat(marginResId));
    }

    /**
     * 设置recyclerView分割线颜色和颜色
     *
     * @param color
     * @return
     */
    public FileSelector setRecyclerViewLineColorHeightDP(@ColorInt int color, float heightDP, float marginDP) {
        return setRecyclerViewLineColorHeightPX(color, dpToPXFloat(heightDP), dpToPXFloat(marginDP));
    }

    /**
     * 设置recyclerView分割线颜色和颜色
     *
     * @param color
     * @return
     */
    public FileSelector setRecyclerViewLineColorHeightPX(@ColorInt int color, float heightPX, float marginPX) {
        RecycleViewDivider divider = new RecycleViewDivider(LinearLayoutManager.VERTICAL, (int) heightPX, color);
        divider.setMargin((int) marginPX);
        selectorLayout.getRecyclerView().addItemDecoration(divider);
        return this;
    }

    /**
     * 设置recyclerView背景颜色
     *
     * @param color
     * @return
     */
    public FileSelector setRecyclerViewBackgroundColor(@ColorInt int color) {
        selectorLayout.getRecyclerView().setBackgroundColor(color);
        return this;
    }

    /**
     * 设置recyclerView背景资源
     *
     * @param resId
     * @return
     */
    public FileSelector setRecyclerViewBackgroundResource(@DrawableRes int resId) {
        selectorLayout.getRecyclerView().setBackgroundResource(resId);
        return this;
    }

    /**
     * 设置选择器无内容界面的提示文本
     *
     * @param resId
     * @return
     */
    public FileSelector setEmptyText(@StringRes int resId) {
        selectorLayout.getEmptyTextView().setText(resId);
        return this;
    }

    /**
     * 设置选择器无内容界面的提示文本
     *
     * @param text
     * @return
     */
    public FileSelector setEmptyText(String text) {
        selectorLayout.getEmptyTextView().setText(text);
        return this;
    }

    /**
     * 设置选择器无内容界面的提示字体颜色
     *
     * @param color
     * @return
     */
    public FileSelector setEmptyTextColor(@ColorInt int color) {
        selectorLayout.getEmptyTextView().setTextColor(color);
        return this;
    }

    /**
     * 设置选择器无内容界面的提示字体颜色
     *
     * @param colors
     * @return
     */
    public FileSelector setEmptyTextColor(ColorStateList colors) {
        if (colors == null) {
            return this;
        }
        selectorLayout.getEmptyTextView().setTextColor(colors);
        return this;
    }

    /**
     * 设置选择器无内容界面的提示字体大小
     *
     * @param resId
     * @return
     */
    public FileSelector setEmptyTextSize(@DimenRes int resId) {
        return setEmptyTextSizePX(dimenToPXFloat(resId));
    }

    /**
     * 设置选择器无内容界面的提示字体大小
     *
     * @param px
     * @return
     */
    public FileSelector setEmptyTextSizePX(float px) {
        selectorLayout.getEmptyTextView().getPaint().setTextSize(px);
        return this;
    }

    /**
     * 设置选择器无内容界面的提示字体大小
     *
     * @param sp
     * @return
     */
    public FileSelector setEmptyTextSizeSP(float sp) {
        selectorLayout.getEmptyTextView().setTextSize(sp);
        return this;
    }

    /**
     * 设置选择器无内容界面的图片
     *
     * @param topDrawableResId
     * @return
     */
    public FileSelector setEmptyTopImage(@DrawableRes int topDrawableResId) {
        return setEmptyTopImage(new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(), topDrawableResId)));
    }

    /**
     * 设置选择器无内容界面的图片
     *
     * @param topDrawable
     * @return
     */
    public FileSelector setEmptyTopImage(Drawable topDrawable) {
        return setEmptyTopImagePX(topDrawable, 0, 0);
    }

    /**
     * 设置选择器无内容界面的图片
     *
     * @param topDrawableResId
     * @param widthResId
     * @param heightResId
     * @return
     */
    public FileSelector setEmptyTopImage(@DrawableRes int topDrawableResId, @DimenRes int widthResId, @DimenRes int heightResId) {
        return setEmptyTopImagePX(new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(), topDrawableResId)),
                dimenToPXFloat(widthResId),
                dimenToPXFloat(heightResId));
    }

    /**
     * 设置选择器无内容界面的图片
     *
     * @param topDrawable
     * @param widthDP
     * @param heightDP
     * @return
     */
    public FileSelector setEmptyTopImageDP(Drawable topDrawable, float widthDP, float heightDP) {
        return setEmptyTopImagePX(topDrawable, dpToPXFloat(widthDP), dpToPXFloat(heightDP));
    }

    /**
     * 设置选择器无内容界面的图片
     *
     * @param topDrawable
     * @param widthPX
     * @param heightPX
     * @return
     */
    public FileSelector setEmptyTopImagePX(Drawable topDrawable, float widthPX, float heightPX) {
        if (topDrawable == null) {
            return this;
        }
        if (widthPX <= 0) {
            widthPX = topDrawable.getMinimumWidth();
        }
        if (heightPX <= 0) {
            heightPX = topDrawable.getMinimumHeight();
        }
        topDrawable.setBounds(0, 0, (int) widthPX, (int) heightPX);
        selectorLayout.getEmptyTextView().setCompoundDrawables(null, topDrawable, null, null);
        return this;
    }

    /**
     * 设置底部选择按钮的高度
     *
     * @param resId
     * @return
     */
    public FileSelector setSubmitTextViewHeight(@DimenRes int resId) {
        return setSubmitTextViewHeightPX(dimenToPXFloat(resId));
    }

    /**
     * 设置底部选择按钮的高度
     *
     * @param dpHeight
     * @return
     */
    public FileSelector setSubmitTextViewHeightDP(float dpHeight) {
        return setSubmitTextViewHeightPX(dpToPXFloat(dpHeight));
    }

    /**
     * 设置底部选择按钮的高度
     *
     * @param pxHeight
     * @return
     */
    public FileSelector setSubmitTextViewHeightPX(float pxHeight) {
        ViewGroup.LayoutParams layoutParams = selectorLayout.getSubmitTextView().getLayoutParams();
        if (layoutParams == null) {
            return this;
        }
        layoutParams.height = (int) pxHeight;
        selectorLayout.getSubmitTextView().setLayoutParams(layoutParams);
        return this;
    }

    /**
     * 设置底部提交按钮字体文本
     *
     * @param resId
     * @return
     */
    public FileSelector setSubmitText(@StringRes int resId) {
        selectorLayout.getSubmitTextView().setText(resId);
        return this;
    }

    /**
     * 设置底部提交按钮字体文本
     *
     * @param text
     * @return
     */
    public FileSelector setSubmitText(String text) {
        selectorLayout.getSubmitTextView().setText(text);
        return this;
    }

    /**
     * 设置底部提交按钮字体颜色
     *
     * @param color
     * @return
     */
    public FileSelector setSubmitTextColor(@ColorInt int color) {
        selectorLayout.getSubmitTextView().setTextColor(color);
        return this;
    }

    /**
     * 设置底部提交按钮字体颜色
     *
     * @param colors
     * @return
     */
    public FileSelector setSubmitTextColor(ColorStateList colors) {
        if (colors == null) {
            return this;
        }
        selectorLayout.getSubmitTextView().setTextColor(colors);
        return this;
    }

    /**
     * 设置底部提交按钮字体大小
     *
     * @param resId
     * @return
     */
    public FileSelector setSubmitTextSize(@DimenRes int resId) {
        return setSubmitTextSizePX(dimenToPXFloat(resId));
    }

    /**
     * 设置底部提交按钮字体大小
     *
     * @param px
     * @return
     */
    public FileSelector setSubmitTextSizePX(float px) {
        selectorLayout.getSubmitTextView().getPaint().setTextSize(px);
        return this;
    }

    /**
     * 设置底部提交按钮字体大小
     *
     * @param sp
     * @return
     */
    public FileSelector setSubmitTextSizeSP(float sp) {
        selectorLayout.getSubmitTextView().setTextSize(sp);
        return this;
    }

    /**
     * 设置底部提交按钮字体粗细
     *
     * @param isBold
     * @return
     */
    public FileSelector setSubmitTextBold(boolean isBold) {
        selectorLayout.getSubmitTextView().setTypeface(FileSelectorUtils.getTypeface(isBold));
        return this;
    }

    /**
     * 设置底部提交按钮背景颜色
     *
     * @param color
     * @return
     */
    public FileSelector setSubmitViewBackgroundColor(@ColorInt int color) {
        selectorLayout.getSubmitTextView().setBackgroundColor(color);
        return this;
    }

    /**
     * 设置底部提交按钮背景颜色
     *
     * @param resId
     * @return
     */
    public FileSelector setSubmitViewBackgroundResource(@DrawableRes int resId) {
        selectorLayout.getSubmitTextView().setBackgroundResource(resId);
        return this;
    }

    public void setup() {
        selectorLayout.setup();
    }
}
