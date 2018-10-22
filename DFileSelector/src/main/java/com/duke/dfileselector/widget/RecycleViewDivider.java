package com.duke.dfileselector.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author duke
 * @dateTime 2018-09-12 22:55
 * @description
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {

    private Paint mPaint;//如果需要用画笔手绘
    private Drawable mDrawableDivider;//如果需要绘制给定的drawable
    private int mPaintDividerLength = 2;//分割线宽度或高度PX
    private DrawType drawType;//用画笔绘制颜色，还是绘制特定的drawable
    private int margin;//分割线margin
    /**
     * 注意：列表的方向
     * LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
     */
    private int mOrientation;
    //系统默认的分割线
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public void setMargin(int margin) {
        this.margin = margin;
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation 列表方向
     * @param drawableId  分割线图片
     */
    public RecycleViewDivider(Context context, int orientation, int drawableId) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;
        if (drawableId == -100) {
            //获取系统的样式
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDrawableDivider = a.getDrawable(0);
            a.recycle();
        } else {
            mDrawableDivider = ContextCompat.getDrawable(context, drawableId);
        }
        //表明绘制drawable
        drawType = DrawType.USEDRAWABLE;
    }

    /**
     * @param context     上下文
     * @param orientation 列表方向
     */
    public RecycleViewDivider(Context context, int orientation) {
        this(context, orientation, -100);
    }

    /**
     * 自定义分割线
     *
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public RecycleViewDivider(int orientation, int dividerHeight, int dividerColor) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;
        if (dividerHeight != -100) {
            //分割线高度
            mPaintDividerLength = dividerHeight;
        }
        //创建特定画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
        //表明绘制用paint
        drawType = DrawType.USEPAINT;
    }

    /**
     * 自定义分割线
     *
     * @param orientation  列表方向
     * @param dividerColor 分割线颜色
     */
    public RecycleViewDivider(int orientation, int dividerColor) {
        this(orientation, -100, dividerColor);
    }


    /**
     * 看图说话：get Item Offsets,获得item的偏移量。此方法用来控制item的偏移
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        /**
         * 列表的方向为横向，画分割线就是纵向的，需要确定的是child的右边偏移值
         * 留出空间画分割线
         */
        if (this.mOrientation == LinearLayoutManager.HORIZONTAL)
            switch (drawType) {
                case USEPAINT:
                    outRect.set(0, 0, mPaintDividerLength, 0);
                    break;
                case USEDRAWABLE:
                    outRect.set(0, 0, mDrawableDivider.getIntrinsicWidth(), 0);
                    break;
            }
        /**
         * 列表的方向为纵向，画分割线就是横向的，需要确定的是child的下边偏移值
         * 留出空间画分割线
         */
        else if (this.mOrientation == LinearLayoutManager.VERTICAL)
            switch (drawType) {
                case USEPAINT:
                    outRect.set(0, 0, 0, mPaintDividerLength);
                    break;
                case USEDRAWABLE:
                    outRect.set(0, 0, 0, mDrawableDivider.getIntrinsicHeight());
                    break;
            }
    }

    /**
     * 绘制分割线
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            //列表是纵向的，需要绘制横向的分割线
            drawHorizontal(c, parent);
        } else {
            //列表是横向的，需要绘制纵向的分割线
            drawVertical(c, parent);
        }
    }

    /**
     * 绘制横向 item 分割线。左、上、右都是可计算的，下需要获取给定的高度值
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        //左边：到父容器的left内间距位置值
        final int left = parent.getPaddingLeft();
        //右边：到父容器的right内间距位置值
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        //循环绘制每条分割线
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            //上边：具体的某条分割线的左边以child的(bottom+bottomMargin)位置值
            final int top = child.getBottom() + layoutParams.bottomMargin;
            //下边：根据类型判断
            int bottom;
            switch (drawType) {
                case USEPAINT://构造方法声明使用画笔绘制
                    //下边：top加上指定的高度
                    bottom = top + mPaintDividerLength;
                    if (mPaint != null) {
                        canvas.drawRect(left + margin, top, right - margin, bottom, mPaint);
                    }
                    break;
                case USEDRAWABLE://构造方法声明使用drawable
                    if (mDrawableDivider != null) {
                        //下边：top加上指定的高度
                        bottom = top + mDrawableDivider.getIntrinsicHeight();
                        mDrawableDivider.setBounds(left + margin, top, right - margin, bottom);
                        mDrawableDivider.draw(canvas);
                    }
                    break;
            }
        }
    }

    /**
     * 绘制纵向 item 分割线。上、下、左都是可计算的，右侧需要获取给定的宽度值
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        //上边：到父容器的top内间距位置值
        final int top = parent.getPaddingTop();
        //下边：到父容器的bottom内间距位置值
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        //循环绘制每条分割线
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            //左边：具体的某条分割线的左边以child的(right+rightMargin)位置值
            final int left = child.getRight() + layoutParams.rightMargin;
            //右边：根据类型判断
            int right;
            switch (drawType) {
                case USEPAINT://构造方法声明使用画笔绘制
                    //右边：left加上指定的宽度
                    right = left + mPaintDividerLength;
                    if (mPaint != null) {
                        canvas.drawRect(left, top, right, bottom, mPaint);
                    }
                    break;
                case USEDRAWABLE://构造方法声明使用drawable
                    if (mDrawableDivider != null) {
                        //右边：left加上指定的宽度
                        right = left + mDrawableDivider.getIntrinsicWidth();
                        mDrawableDivider.setBounds(left, top, right, bottom);
                        mDrawableDivider.draw(canvas);
                    }
                    break;
            }
        }
    }

    public static enum DrawType {
        USEPAINT(1),//用画笔画
        USEDRAWABLE(2); //画特定的drawable
        private final int type;

        DrawType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

}
