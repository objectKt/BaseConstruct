package com.android.launcher.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.launcher.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @date： 2023/11/6
 * @author: 78495
*/
public class MenuAdapter extends BaseQuickAdapter<MenuItem, BaseViewHolder> {

    public MenuAdapter(@Nullable List<MenuItem> data) {
        super(R.layout.item_menu,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MenuItem item) {
            if(item!=null){
                helper.setImageResource(R.id.menuIV,item.getDrawableId())
                        .setText(R.id.menuNameTV,item.getName());
            }
    }
}
