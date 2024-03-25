package com.android.launcher.mileage;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.diff.BaseQuickDiffCallback;

import java.util.List;

public class DiffDemoCallback extends BaseQuickDiffCallback<MileageMulItemEntity> {

    public DiffDemoCallback(@Nullable List<MileageMulItemEntity> newList) {
        super(newList);
    }

    /**
     * 判断是否是同一个item
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return
     */
    @Override
    protected boolean areItemsTheSame(MileageMulItemEntity oldItem, MileageMulItemEntity newItem) {
        return false;
    }

    /**
     * 当是同一个item时，再判断内容是否发生改变
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return
     */
    @Override
    protected boolean areContentsTheSame(MileageMulItemEntity oldItem, MileageMulItemEntity newItem) {
        return false;
    }

    /**
     * 可选实现
     * 如果需要精确修改某一个view中的内容，请实现此方法。
     * 如果不实现此方法，或者返回null，将会直接刷新整个item。
     *
     * @param oldItem Old data
     * @param newItem New data
     * @return Payload info. if return null, the entire item will be refreshed.
     */
    @Override
    protected Object getChangePayload(MileageMulItemEntity oldItem, MileageMulItemEntity newItem) {

        return null;
    }
}