package com.android.launcher.adapter;

import android.graphics.Color;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

import com.android.launcher.R;
import com.android.launcher.vo.AlertVo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/**
* @description: 警告信息
* @createDate: 2023/9/25
*/
public class WarnInfoAdapter extends BaseQuickAdapter<AlertVo, BaseViewHolder> {

    public WarnInfoAdapter(@Nullable List<AlertVo> data) {
        super(R.layout.item_warn_info,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AlertVo item) {
        if(item!=null){
            try {
                helper.setImageResource(R.id.infoIV,item.getAlertImg());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                helper.setTextColor(R.id.infoTV, Color.parseColor(item.getAlertColor()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                helper.setText(R.id.infoTV,item.getAlertMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
