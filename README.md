## [我的博客地址](https://blog.csdn.net/fesdgasdgasdg "点击进入")
# FileSelector    文件选择器
    * 什么是文件选择器？
  简单来说就是运行于 Android 手机上的一个第三方库，它提供手机外部存储中的文件、目录的检索、选择功能，提供便捷可视化的界面给用户选择文件。
  
    * 这个代码库有什么优势呢？
  确实，其功能对于修养多年的大神来说太简单了。其核心代码就是一个 RecyclerView 和一个 Adapter，已扩展存储中的文件作为数据。但是我提供了高度可定制化 UI 效果的功能，好戏后面见。
  
    * 使用简单。需要引入 DFileSelector 代码库。没有上传到 maven 等依赖库。
  
    * 此代码库需要读取扩展存储的权限
  
    * 本例是使用模拟器运行的效果，更好的效果请使用真机运行查看

## 先来看效果
    * 单选模式
![](https://github.com/mengzhinan/FileSelector/blob/master/snapshot/a1.gif "单选模式效果")

    * 多选模式
![](https://github.com/mengzhinan/FileSelector/blob/master/snapshot/a2.gif "多选模式效果")

偷个懒，给你指个路 -> <br/>
--> [使用示例代码1](https://github.com/mengzhinan/FileSelector/blob/master/app/src/main/java/com/duke/fileselector/Demo1Activity.java "点击进入")

--> [使用示例代码1](https://github.com/mengzhinan/FileSelector/blob/master/app/src/main/java/com/duke/fileselector/Demo2Activity.java "点击进入")

--> [使用示例代码1](https://github.com/mengzhinan/FileSelector/blob/master/app/src/main/java/com/duke/fileselector/Demo3Activity.java "点击进入")

## 怎么更好的定制UI和功能呢？
    可以设置文件选中后的回调
    可以定制每一个 item 的UI效果，参考 setItemParameter() 方法
    可以设置单选模式和多选模式
    可以定制文件的 icon 类型
    可以定制列表数据排序方式
    可以定制文件过滤规则
    可以定制任意一处的文字颜色、字体大小、图片等细节
    
    等等
    
    具体细节可以参考下面的代码简报

FileSelector.with(fileSelectorLayout)
                .listen(onFileSelectListener)
                .setItemParameter(ItemParameter.with(fileSelectorLayout.getContext())
                                .setTitleTextSize(R.dimen.sp_20)
//                                .setTitleTextColor(Color.RED)
//                                .setTitleTextBold(true)
//                                .setImageWidth(R.dimen.dp_50)
//                                .setImageHeight(R.dimen.dp_50)
//                                .setImageMarginRight(R.dimen.dp_50)
//                                .setCountTextColor(Color.RED)
//                                .setCountTextSize(R.dimen.sp_20)
//                                .setCountTextBold(true)
//                                .setDateTextSize(R.dimen.sp_20)
//                                .setDateTextColor(Color.RED)
//                                .setDateTextBold(true)
//                                .setLayoutBackgroundColor(Color.RED)
//                                .setLayoutPadding(R.dimen.dp_1, R.dimen.dp_1, R.dimen.dp_1, R.dimen.dp_1)
//                                .setSplitLineColor(Color.RED)
//                                .setSplitLineHeight(R.dimen.dp_10)
//                                .setSplitLineWidth(R.dimen.dp_10)
//                                .setSplitLineMarginLeft(R.dimen.dp_50)
//                                .setSplitLineMarginRight(R.dimen.dp_50)
                )
                .setMultiSelectionModel(true)
//                .setMultiModelMaxSize(3)
//                .setMultiModelToast(true, "最多只能选择3个文件")
//                .setFileSizeProvide(FileSizeProvide fileSizeProvide)
//                .setFileIconProvide(FileIconProvide fileIconProvide)
//                .setFileDateProvide(FileDateProvide fileDateProvide)
//                .setFileFilter(FileFilter fileFilter)
//                .setFileOrderProvide(FileOrderProvide fileOrderProvide)
//                .setHeadTopLineVisibility(View.VISIBLE)
//                .setHeadTopLineColor(Color.RED)
//                .setHeadTopLineHeight(R.dimen.dp_30)
//                .setHeadTopLineHeightDP(30)
//                .setHeadTopLineHeightPX(30)
//                .setHeadTopLineMargin(R.dimen.dp_10, R.dimen.dp_10,R.dimen.dp_10,R.dimen.dp_10)
//                .setHeadTopLineMarginDP(10,10,10,10)
//                .setHeadTopLineMarginPX(10,10,10,10)
//                .setHeadBottomLineVisibility(View.VISIBLE)
//                .setHeadBottomLineColor(@ColorInt int color)
//                .setHeadBottomLineHeight(@DimenRes int resId)
//                .setHeadBottomLineHeightDP(float dpHeight)
//                .setHeadBottomLineHeightPX(float pxHeight)
//                .setHeadBottomLineMargin(@DimenRes int leftDimenRes, @DimenRes int topDimenRes, @DimenRes int rightDimenRes, @DimenRes int bottomDimenRes)
//                .setHeadBottomLineMarginDP(float leftDP, float topDP, float rightDP, float bottomDP)
//                .setHeadBottomLineMarginPX(float leftPX, float topPX, float rightPX, float bottomPX)
//                .setHeadRootHeight(R.dimen.dp_40)
//                .setHeadRootHeightDP(40)
//                .setHeadRootHeightPX(120)
//                .setHeadRootPadding(R.dimen.dp_20,R.dimen.dp_1,R.dimen.dp_30,R.dimen.dp_1)
//                .setHeadRootPaddingDP(float leftDP, float topDP, float rightDP, float bottomDP)
//                .setHeadRootPaddingPX(float leftPX, float topPX, float rightPX, float bottomPX)
//                .setHeadRootBackgroundColor(Color.RED)
//                .setHeadRootBackgroundResource(@DrawableRes int resId)
//                .setHeadLeftTextSize(R.dimen.sp_20)
//                .setHeadLeftTextSizePX(60)
//                .setHeadLeftTextSizeSP(20)
//                .setHeadLeftTextColor(Color.RED)
//                .setHeadLeftTextColor(ColorStateList colors)
//                .setHeadLeftTextBold(true)
//                .setHeadLeftTextEllipsize(TextUtils.TruncateAt.MARQUEE)
//                .setHeadRightImage(Drawable leftDrawable)
//                .setHeadRightImageDP(Drawable leftDrawable, float widthDP, float heightDP)
//                .setHeadRightImagePX(Drawable leftDrawable, float widthPX, float heightPX)
//                .setHeadRightText(@StringRes int resId)
//                .setHeadRightText("上一级")
//                .setHeadRightTextSize(@DimenRes int resId)
//                .setHeadRightTextSizePX(float px)
//                .setHeadRightTextSizeSP(20)
//                .setHeadRightTextColor(Color.RED)
//                .setHeadRightTextColor(ColorStateList colors)
//                .setHeadRightTextBold(true)
//                .setHeadRightTextBackgroundColor(Color.RED)
//                .setHeadRightTextBackgroundResource(@DrawableRes int resId)
//                .setRecyclerViewLineDecoration(RecyclerView.ItemDecoration itemDecoration)
//                .setRecyclerViewLineColorHeight(Color.RED, R.dimen.dp_1, R.dimen.dp_5)
//                .setRecyclerViewLineColorHeightDP(@ColorInt int color, float heightDP, float marginDP)
//                .setRecyclerViewLineColorHeightPX(@ColorInt int color, float heightPX, float marginPX)
//                .setRecyclerViewBackgroundColor(Color.GRAY)
//                .setRecyclerViewBackgroundResource(@DrawableRes int resId)
//                .setEmptyText(@StringRes int resId)
//                .setEmptyText("无数据")
//                .setEmptyTextColor(Color.RED)
//                .setEmptyTextColor(ColorStateList colors)
//                .setEmptyTextSize(@DimenRes int resId)
//                .setEmptyTextSizePX(float px)
//                .setEmptyTextSizeSP(float sp)
//                .setEmptyTopImage(@DrawableRes int topDrawableResId)
//                .setEmptyTopImage(Drawable topDrawable)
//                .setEmptyTopImage(@DrawableRes int topDrawableResId, @DimenRes int widthResId, @DimenRes int heightResId)
//                .setEmptyTopImageDP(Drawable topDrawable, float widthDP, float heightDP)
//                .setEmptyTopImagePX(Drawable topDrawable, float widthPX, float heightPX)
//                .setSubmitTextViewHeight(@DimenRes int resId)
//                .setSubmitTextViewHeightDP(float dpHeight)
//                .setSubmitTextViewHeightPX(float pxHeight)
//                .setSubmitText(@StringRes int resId)
//                .setSubmitText("ok")
//                .setSubmitTextColor(@ColorInt int color)
//                .setSubmitTextColor(ColorStateList colors)
//                .setSubmitTextSize(@DimenRes int resId)
//                .setSubmitTextSizePX(float px)
//                .setSubmitTextSizeSP(float sp)
//                .setSubmitTextBold(boolean isBold)
//                .setSubmitViewBackgroundColor(Color.RED)
//                .setSubmitViewBackgroundResource(@DrawableRes int resId)
                .setup();
