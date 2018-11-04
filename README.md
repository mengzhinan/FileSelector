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

FileSelector.with(fileSelectorLayout) <br/>
                .listen(onFileSelectListener) <br/>
                .setItemParameter(ItemParameter.with(fileSelectorLayout.getContext()) <br/>
                                .setTitleTextSize(R.dimen.sp_20) <br/>
//                                .setTitleTextColor(Color.RED) <br/>
//                                .setTitleTextBold(true) <br/>
//                                .setImageWidth(R.dimen.dp_50) <br/>
//                                .setImageHeight(R.dimen.dp_50) <br/>
//                                .setImageMarginRight(R.dimen.dp_50) <br/>
//                                .setCountTextColor(Color.RED) <br/>
//                                .setCountTextSize(R.dimen.sp_20) <br/>
//                                .setCountTextBold(true) <br/>
//                                .setDateTextSize(R.dimen.sp_20) <br/>
//                                .setDateTextColor(Color.RED) <br/>
//                                .setDateTextBold(true) <br/>
//                                .setLayoutBackgroundColor(Color.RED) <br/>
//                                .setLayoutPadding(R.dimen.dp_1, R.dimen.dp_1, R.dimen.dp_1, R.dimen.dp_1) <br/>
//                                .setSplitLineColor(Color.RED) <br/>
//                                .setSplitLineHeight(R.dimen.dp_10) <br/>
//                                .setSplitLineWidth(R.dimen.dp_10) <br/>
//                                .setSplitLineMarginLeft(R.dimen.dp_50) <br/>
//                                .setSplitLineMarginRight(R.dimen.dp_50) <br/>
                ) <br/>
                .setMultiSelectionModel(true) <br/>
//                .setMultiModelMaxSize(3) <br/>
//                .setMultiModelToast(true, "最多只能选择3个文件") <br/>
//                .setFileSizeProvide(FileSizeProvide fileSizeProvide) <br/>
//                .setFileIconProvide(FileIconProvide fileIconProvide) <br/>
//                .setFileDateProvide(FileDateProvide fileDateProvide) <br/>
//                .setFileFilter(FileFilter fileFilter) <br/>
//                .setFileOrderProvide(FileOrderProvide fileOrderProvide) <br/>
//                .setHeadTopLineVisibility(View.VISIBLE) <br/>
//                .setHeadTopLineColor(Color.RED) <br/>
//                .setHeadTopLineHeight(R.dimen.dp_30) <br/>
//                .setHeadTopLineHeightDP(30) <br/>
//                .setHeadTopLineHeightPX(30) <br/>
//                .setHeadTopLineMargin(R.dimen.dp_10, R.dimen.dp_10,R.dimen.dp_10,R.dimen.dp_10) <br/>
//                .setHeadTopLineMarginDP(10,10,10,10) <br/>
//                .setHeadTopLineMarginPX(10,10,10,10) <br/>
//                .setHeadBottomLineVisibility(View.VISIBLE) <br/>
//                .setHeadBottomLineColor(@ColorInt int color) <br/>
//                .setHeadBottomLineHeight(@DimenRes int resId) <br/>
//                .setHeadBottomLineHeightDP(float dpHeight) <br/>
//                .setHeadBottomLineHeightPX(float pxHeight) <br/>
//                .setHeadBottomLineMargin(@DimenRes int leftDimenRes, @DimenRes int topDimenRes, @DimenRes int rightDimenRes, @DimenRes int bottomDimenRes) <br/>
//                .setHeadBottomLineMarginDP(float leftDP, float topDP, float rightDP, float bottomDP) <br/>
//                .setHeadBottomLineMarginPX(float leftPX, float topPX, float rightPX, float bottomPX) <br/>
//                .setHeadRootHeight(R.dimen.dp_40) <br/>
//                .setHeadRootHeightDP(40) <br/>
//                .setHeadRootHeightPX(120) <br/>
//                .setHeadRootPadding(R.dimen.dp_20,R.dimen.dp_1,R.dimen.dp_30,R.dimen.dp_1) <br/>
//                .setHeadRootPaddingDP(float leftDP, float topDP, float rightDP, float bottomDP) <br/>
//                .setHeadRootPaddingPX(float leftPX, float topPX, float rightPX, float bottomPX) <br/>
//                .setHeadRootBackgroundColor(Color.RED) <br/>
//                .setHeadRootBackgroundResource(@DrawableRes int resId) <br/>
//                .setHeadLeftTextSize(R.dimen.sp_20) <br/>
//                .setHeadLeftTextSizePX(60) <br/>
//                .setHeadLeftTextSizeSP(20) <br/>
//                .setHeadLeftTextColor(Color.RED) <br/>
//                .setHeadLeftTextColor(ColorStateList colors) <br/>
//                .setHeadLeftTextBold(true) <br/>
//                .setHeadLeftTextEllipsize(TextUtils.TruncateAt.MARQUEE) <br/>
//                .setHeadRightImage(Drawable leftDrawable) <br/>
//                .setHeadRightImageDP(Drawable leftDrawable, float widthDP, float heightDP) <br/>
//                .setHeadRightImagePX(Drawable leftDrawable, float widthPX, float heightPX) <br/>
//                .setHeadRightText(@StringRes int resId) <br/>
//                .setHeadRightText("上一级") <br/>
//                .setHeadRightTextSize(@DimenRes int resId) <br/>
//                .setHeadRightTextSizePX(float px) <br/>
//                .setHeadRightTextSizeSP(20) <br/>
//                .setHeadRightTextColor(Color.RED) <br/>
//                .setHeadRightTextColor(ColorStateList colors) <br/>
//                .setHeadRightTextBold(true) <br/>
//                .setHeadRightTextBackgroundColor(Color.RED) <br/>
//                .setHeadRightTextBackgroundResource(@DrawableRes int resId) <br/>
//                .setRecyclerViewLineDecoration(RecyclerView.ItemDecoration itemDecoration) <br/>
//                .setRecyclerViewLineColorHeight(Color.RED, R.dimen.dp_1, R.dimen.dp_5) <br/>
//                .setRecyclerViewLineColorHeightDP(@ColorInt int color, float heightDP, float marginDP) <br/>
//                .setRecyclerViewLineColorHeightPX(@ColorInt int color, float heightPX, float marginPX) <br/>
//                .setRecyclerViewBackgroundColor(Color.GRAY) <br/>
//                .setRecyclerViewBackgroundResource(@DrawableRes int resId) <br/>
//                .setEmptyText(@StringRes int resId) <br/>
//                .setEmptyText("无数据") <br/>
//                .setEmptyTextColor(Color.RED) <br/>
//                .setEmptyTextColor(ColorStateList colors) <br/>
//                .setEmptyTextSize(@DimenRes int resId) <br/>
//                .setEmptyTextSizePX(float px) <br/>
//                .setEmptyTextSizeSP(float sp) <br/>
//                .setEmptyTopImage(@DrawableRes int topDrawableResId) <br/>
//                .setEmptyTopImage(Drawable topDrawable) <br/>
//                .setEmptyTopImage(@DrawableRes int topDrawableResId, @DimenRes int widthResId, @DimenRes int heightResId) <br/>
//                .setEmptyTopImageDP(Drawable topDrawable, float widthDP, float heightDP) <br/>
//                .setEmptyTopImagePX(Drawable topDrawable, float widthPX, float heightPX) <br/>
//                .setSubmitTextViewHeight(@DimenRes int resId) <br/>
//                .setSubmitTextViewHeightDP(float dpHeight) <br/>
//                .setSubmitTextViewHeightPX(float pxHeight) <br/>
//                .setSubmitText(@StringRes int resId) <br/>
//                .setSubmitText("ok") <br/>
//                .setSubmitTextColor(@ColorInt int color) <br/>
//                .setSubmitTextColor(ColorStateList colors) <br/>
//                .setSubmitTextSize(@DimenRes int resId) <br/>
//                .setSubmitTextSizePX(float px) <br/>
//                .setSubmitTextSizeSP(float sp) <br/>
//                .setSubmitTextBold(boolean isBold) <br/>
//                .setSubmitViewBackgroundColor(Color.RED) <br/>
//                .setSubmitViewBackgroundResource(@DrawableRes int resId) <br/>
                .setup(); <br/>
