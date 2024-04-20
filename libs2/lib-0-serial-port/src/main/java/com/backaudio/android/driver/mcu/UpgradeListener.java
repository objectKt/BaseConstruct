package com.backaudio.android.driver.mcu;


import com.backaudio.android.driver.manage.PublicEnum;

public interface UpgradeListener {
	/**
     * mcu升级状态反馈 （错误， 开始， 请求包长度， 完成）
     * @param info
     */
    void onMcuUpgradeState(PublicEnum.EUpgrade info);

    /**
     * mcu升级过程请求升级数据（一段一段传送）
     * @param nextIndex
     */
    void onMcuUpgradeForGetDataByIndex(int nextIndex);
}
