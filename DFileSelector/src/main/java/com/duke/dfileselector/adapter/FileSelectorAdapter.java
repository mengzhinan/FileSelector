package com.duke.dfileselector.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duke.dfileselector.R;
import com.duke.dfileselector.bean.FileItem;
import com.duke.dfileselector.helper.ItemParameter;
import com.duke.dfileselector.provide.dateFormat.FileDateProvide;
import com.duke.dfileselector.provide.dateFormat.FileDateProvideDefault;
import com.duke.dfileselector.provide.icon.FileIconProvide;
import com.duke.dfileselector.provide.icon.FileIconProvideDefault;
import com.duke.dfileselector.provide.size.FileSizeProvide;
import com.duke.dfileselector.provide.size.FileSizeProvideDefault;
import com.duke.dfileselector.util.FileSelectorUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author duke
 * @dateTime 2018-09-08 10:23
 * @description 文件列表适配器
 */
public class FileSelectorAdapter extends RecyclerView.Adapter<FileSelectorAdapter.FileViewHolder> {
    private Context context;
    private List<FileItem> list = new ArrayList<>();

    private ItemParameter itemParameter;

    private OnFileItemClickListener onFileItemClickListener;

    private FileIconProvide fileIconProvideDefault = new FileIconProvideDefault();
    private FileIconProvide fileIconProvide = fileIconProvideDefault;

    private FileDateProvideDefault fileDateProvideDefault = new FileDateProvideDefault();
    private FileDateProvide fileDateProvide = fileDateProvideDefault;

    private FileSizeProvideDefault fileSizeProvideDefault = new FileSizeProvideDefault();
    private FileSizeProvide fileSizeProvide = fileSizeProvideDefault;
    private FileFilter fileFilter;

    private boolean isMultiSelectionModel;
    private int multiModelMaxSize;
    private int currentSize;

    private boolean isNeedToastIfOutOfMaxSize;
    private String toastStringIfOutOfMaxSize;

    public void setOnFileItemClickListener(OnFileItemClickListener l) {
        this.onFileItemClickListener = l;
    }

    public void setMultiModelToast(boolean isNeedToastIfOutOfMaxSize, @NonNull String toastStringIfOutOfMaxSize) {
        this.isNeedToastIfOutOfMaxSize = isNeedToastIfOutOfMaxSize;
        this.toastStringIfOutOfMaxSize = toastStringIfOutOfMaxSize;
    }

    public void setMultiModelMaxSize(int multiModelMaxSize) {
        this.multiModelMaxSize = multiModelMaxSize;
    }

    public void setMultiSelectionModel(boolean multiSelectionModel) {
        if (this.isMultiSelectionModel != multiSelectionModel) {
            this.isMultiSelectionModel = multiSelectionModel;
        }
    }

    public void setFileFilter(FileFilter fileFilter) {
        if (this.fileFilter != fileFilter) {
            this.fileFilter = fileFilter;
        }
    }

    public void setFileSizeProvide(FileSizeProvide fileSizeProvide) {
        if (fileSizeProvide == null) {
            //不能为null
            return;
        }
        this.fileSizeProvide = fileSizeProvide;
    }

    public void setFileDateProvide(FileDateProvide fileDateProvide) {
        if (fileDateProvide == null) {
            //不能为null
            return;
        }
        this.fileDateProvide = fileDateProvide;
    }

    public void setFileIconProvide(FileIconProvide fileIconProvide) {
        if (fileIconProvide == null) {
            //不能为null
            return;
        }
        this.fileIconProvide = fileIconProvide;
    }

    public void setItemParameter(ItemParameter itemParameter) {
        this.itemParameter = itemParameter;
    }

    public FileSelectorAdapter(Context context) {
        this.context = context;
    }

    public void setRefreshData(List<FileItem> list) {
        setData(list);
        notifyDataSetChanged();
    }

    public void setData(List<FileItem> list) {
        this.list.clear();
        if (!FileSelectorUtils.isEmpty(list)) {
            this.list.addAll(list);
        }
    }

    public List<FileItem> getList() {
        return this.list;
    }

    @Override
    public int getItemCount() {
        if (this.list == null) {
            return 0;
        }
        return this.list.size();
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dfileselector_list_item, viewGroup, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileViewHolder viewHolder, int position) {
        if (FileSelectorUtils.isEmpty(list)) {
            return;
        }
        final FileItem fileItem = list.get(position);
        if (fileItem == null || fileItem.file == null) {
            return;
        }

        //设置文件类型图标
        viewHolder.fileItemImage.setImageResource(fileIconProvide.getFileDrawableResId(this.context, fileItem.file));
        viewHolder.fileItemName.setText(fileItem.file.getName());
        viewHolder.fileItemModify.setText(fileDateProvide.formatDate(fileItem.file.lastModified()));
        viewHolder.fileItemSizeOrCount.setText(fileSizeProvide.getFileSize(this.context, fileItem.file, fileFilter));

        setViewProperties(viewHolder);

        //防止数据错乱
        //1.根据数据集合item的状态，设置checkbox。注意是setChecked()方法，不是setSelected()方法
        viewHolder.fileItemCheckBox.setChecked(!fileItem.file.isDirectory() && fileItem.isChecked);
        viewHolder.fileItemCheckBox.setVisibility(fileItem.file.isFile() && isMultiSelectionModel ? View.VISIBLE : View.GONE);
        //2.监控checkbox点击事件。注意是setOnClickListener事件，setOnCheckedChangeListener事件不行的
        viewHolder.fileItemCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(v instanceof CheckBox)) {
                    return;
                }
                //3.及时获取checkbox状态，保存到数据集中。注意是方法isChecked()获取状态
                //注意此处checkbox的选中状态是checkbox自己触发的，故获取到的是改变后的状态
                setCheckStatusAndCount(fileItem, viewHolder.fileItemCheckBox, !viewHolder.fileItemCheckBox.isChecked());
            }
        });

        viewHolder.fileItemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMultiSelectionModel) {
                    if (fileItem.file.isFile()) {
                        //注意此处checkbox的选中状态是认为触发的，故获取到的是以前的状态
                        setCheckStatusAndCount(fileItem, viewHolder.fileItemCheckBox, viewHolder.fileItemCheckBox.isChecked());
                    } else {
                        if (onFileItemClickListener != null && fileItem.file.isDirectory()) {
                            onFileItemClickListener.onFolderClick(fileItem.file);
                        }
                    }
                } else {
                    if (onFileItemClickListener != null) {
                        if (fileItem.file.isDirectory()) {
                            onFileItemClickListener.onFolderClick(fileItem.file);
                        } else {
                            onFileItemClickListener.onFileSelected(fileItem.file);
                        }
                    }
                }
            }
        });
    }

    private void setCheckStatusAndCount(@NonNull FileItem fileItem, @NonNull CheckBox checkBox, boolean oldCheckedStatus) {
        if (!oldCheckedStatus) {
            //选中操作
            if (multiModelMaxSize != 0 && currentSize >= multiModelMaxSize) {
                //禁止
                if (isNeedToastIfOutOfMaxSize) {
                    Toast.makeText(context, toastStringIfOutOfMaxSize, Toast.LENGTH_SHORT).show();
                }
                checkBox.setChecked(false);
            } else {
                checkBox.setChecked(true);
                fileItem.isChecked = true;
                currentSize++;
            }
        } else {
            //取消操作
            checkBox.setChecked(false);
            fileItem.isChecked = false;
            currentSize--;
            if (currentSize < 0) {
                currentSize = 0;
            }
        }
    }

    private void setViewProperties(FileViewHolder viewHolder) {
        if (itemParameter == null) {
            return;
        }
        if (itemParameter.isSetLayoutPadding()) {
            viewHolder.fileItemRoot.setPadding((int) itemParameter.getLayoutPaddingLeft(),
                    (int) itemParameter.getLayoutPaddingTop(),
                    (int) itemParameter.getLayoutPaddingRight(),
                    (int) itemParameter.getLayoutPaddingBottom());
        }

        if (itemParameter.isSetLayoutBackgroundResourceId()) {
            viewHolder.fileItemRoot.setBackgroundResource(itemParameter.getLayoutBackgroundResourceId());
        }

        if (itemParameter.isSetLayoutBackgroundColor()) {
            viewHolder.fileItemRoot.setBackgroundColor(itemParameter.getLayoutBackgroundColor());
        }

        if (itemParameter.isSetImageWidth()
                || itemParameter.isSetImageHeight()
                || itemParameter.isSetImageMarginRight()) {
            ViewGroup.LayoutParams layoutParams = viewHolder.fileItemImage.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            if (itemParameter.isSetImageWidth()) {
                layoutParams.width = (int) itemParameter.getImageWidth();
            }
            if (itemParameter.isSetImageHeight()) {
                layoutParams.height = (int) itemParameter.getImageHeight();
            }
            if (itemParameter.isSetImageMarginRight() && layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams.rightMargin = (int) itemParameter.getImageMarginRight();
            }
            viewHolder.fileItemImage.setLayoutParams(layoutParams);
        }

        if (itemParameter.isSetTitleTextSize()) {
            viewHolder.fileItemName.getPaint().setTextSize(itemParameter.getTitleTextSize());
        }

        if (itemParameter.isSetTitleTextColor()) {
            viewHolder.fileItemName.setTextColor(itemParameter.getTitleTextColor());
        }

        viewHolder.fileItemName.setTypeface(FileSelectorUtils.getTypeface(itemParameter.isTitleTextBold()));

        if (itemParameter.isSetCountTextSize()) {
            viewHolder.fileItemSizeOrCount.getPaint().setTextSize(itemParameter.getCountTextSize());
        }

        if (itemParameter.isSetCountTextColor()) {
            viewHolder.fileItemSizeOrCount.setTextColor(itemParameter.getCountTextColor());
        }

        viewHolder.fileItemSizeOrCount.setTypeface(FileSelectorUtils.getTypeface(itemParameter.isCountTextBold()));

        if (itemParameter.isSetDateTextSize()) {
            viewHolder.fileItemModify.getPaint().setTextSize(itemParameter.getDateTextSize());
        }

        if (itemParameter.isSetDateTextColor()) {
            viewHolder.fileItemModify.setTextColor(itemParameter.getDateTextColor());
        }

        viewHolder.fileItemModify.setTypeface(FileSelectorUtils.getTypeface(itemParameter.isDateTextBold()));

        if (itemParameter.isSetSplitLineColor()) {
            viewHolder.splitView.setBackgroundColor(itemParameter.getSplitLineColor());
        }

        if (itemParameter.isSetSplitLineWidth()
                || itemParameter.isSetSplitLineHeight()
                || itemParameter.isSetSplitLineMarginLeft()
                || itemParameter.isSetSplitLineMarginRight()
                ) {
            ViewGroup.LayoutParams layoutParams = viewHolder.splitView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            if (itemParameter.isSetSplitLineWidth()) {
                layoutParams.width = (int) itemParameter.getSplitLineWidth();
            }
            if (itemParameter.isSetSplitLineHeight()) {
                layoutParams.height = (int) itemParameter.getSplitLineHeight();
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                if (itemParameter.isSetSplitLineMarginLeft()) {
                    marginLayoutParams.leftMargin = (int) itemParameter.getSplitLineMarginLeft();
                }
                if (itemParameter.isSetSplitLineMarginRight()) {
                    marginLayoutParams.rightMargin = (int) itemParameter.getSplitLineMarginRight();
                }
            }
            viewHolder.splitView.setLayoutParams(layoutParams);
        }
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout fileItemRoot;
        private ImageView fileItemImage;
        private TextView fileItemName;
        private TextView fileItemSizeOrCount;
        private TextView fileItemModify;
        private CheckBox fileItemCheckBox;
        private View splitView;

        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileItemRoot = itemView.findViewById(R.id.file_item_root);
            fileItemImage = itemView.findViewById(R.id.file_item_image);
            fileItemName = itemView.findViewById(R.id.file_item_name);
            fileItemSizeOrCount = itemView.findViewById(R.id.file_item_size_or_count);
            fileItemModify = itemView.findViewById(R.id.file_item_modify);
            fileItemCheckBox = itemView.findViewById(R.id.file_item_checkbox);
            splitView = itemView.findViewById(R.id.file_item_split);
        }
    }

    public interface OnFileItemClickListener {
        void onFolderClick(File file);

        void onFileSelected(File file);
    }
}
