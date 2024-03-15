package com.android.launcher.mileage;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
* @description:
* @createDate: 2023/9/15
*/
public class MileageMulItemEntity implements MultiItemEntity {

    private MileageMulItemEntity.Type itemType;

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MileageMulItemEntity(MileageMulItemEntity.Type itemType) {
        this.itemType = itemType;
    }

    public MileageMulItemEntity(Type itemType, Object data) {
        this.itemType = itemType;
        this.data = data;
    }

    public void setItemType(Type itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType.ordinal();
    }


    public enum  Type{
        SPEED, //时速
        DRIVING_DISTANCE, //可行驶距离
        LAUNCHER_AFTER, //启动后
        RESTORE_AFTER, //恢复后
        TREED_SPEED, //前卫仪表盘-时速
        TREED_DRIVING_DISTANCE, //前卫仪表盘-可行驶距离
        TREED_LAUNCHER_AFTER, //前卫仪表盘-启动后
        TREED_RESTORE_AFTER //前卫仪表盘-恢复后

    }
}
