package com.android.launcher.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.launcher.R;
import com.android.launcher.vo.MaintainItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
* @description:
* @createDate: 2023/9/21
*/
public class MaintainHomeListAdapter extends BaseQuickAdapter<MaintainItem, BaseViewHolder> {

    public MaintainHomeListAdapter(@Nullable List<MaintainItem> data) {
        super(R.layout.item_list_maintain,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MaintainItem item) {
        if(item!=null){
            helper.setText(R.id.maintainListName,item.getName());
            if(item.isSelected()){
                helper.setBackgroundRes(R.id.itemRootLL, R.drawable.item_seletor);
            }else{
                helper.setBackgroundRes(R.id.itemRootLL,0);
            }
        }
    }
}
