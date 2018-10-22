package com.duke.dfileselector.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author duke
 * @dateTime 2018-09-08 13:47
 * @description
 */
public class FileRecyclerView extends RecyclerView {
    private View mEmptyView;

    public FileRecyclerView(@NonNull Context context) {
        this(context, null, 0);
    }

    public FileRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FileRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

    }

    @Override
    public void setAdapter(Adapter adapter) {
        Adapter adapterOld = getAdapter();
        if (adapterOld != null) {
            adapterOld.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkDataSize();
        }
    };

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        checkDataSize();
    }

    private void checkDataSize() {
        if (this.mEmptyView == null || getAdapter() == null) {
            return;
        }
        this.mEmptyView.setVisibility(getAdapter().getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }
}
