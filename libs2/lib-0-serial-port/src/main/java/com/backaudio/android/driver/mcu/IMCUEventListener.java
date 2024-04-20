package com.backaudio.android.driver.mcu;

import java.util.List;

import com.backaudio.android.driver.manage.PublicEnum.EACCTime;
import com.backaudio.android.driver.manage.PublicEnum.ECarLayer;
import com.backaudio.android.driver.manage.PublicEnum.EUpgrade;

public interface IMCUEventListener {
	/**
	 * 获取8836参数
	 */
	void obtainMCU8836(String addr, String value);
	/**
	 * 获取ACC延时
	 */
	void obtainACCTime(EACCTime eTime);
	/**
	 * 获取360类型
	 */
	void obtain360Type(int value);
	/**
	 * 获取音效设置
	 */
	void obtainDSPValues(int[] values);
	/**
	 * 获取AUX输入状态
	 */
	void obtainDVState(boolean isPlaying);
    /**
     * 获取坐标
     */
    void obtainTouchPoi(int x, int y);
    /**
     * 获取存储的数据
     */
    void obtainStoreData(List<Byte> data);
    /**
     * 获取亮度值设置
     */
    void obtainBrightness(int value);

    /**
     * mcu要求android进入待机模式
     */
    void onEnterStandbyMode();

    /**
     * mcu 唤醒android
     */
    void onWakeUp(ECarLayer eCarLayer);

    /**
     * 获取mcu版本日期
     */
    public void obtainVersionDate(String versionDate);
    /**
     * 获取mcu版本号
     */
    public void obtainVersionNumber(String versionNumber);
    /**
     * 倒车
     */
    public void onMcuBackcar(boolean isback);

    /**
     * 均衡器四个喇叭的值
     * @param fLeft
     * @param fRight
     * @param rLeft
     * @param rRight
     */
    void onHornSoundValue(int fLeft, int fRight, int rLeft, int rRight);
    /**
     * mcu升级状态反馈 （错误， 开始， 请求包长度， 完成）
     * @param info
     */
    void onMcuUpgradeState(EUpgrade info);

    /**
     * mcu升级过程请求升级数据（一段一段传送）
     * @param nextIndex
     */
    void onMcuUpgradeForGetDataByIndex(int nextIndex);

    //获取语言类型
    //  0x00:  简体中文
    //  0x01:  繁体中文
    //  0x02:  英文
    void obtainLanguageType(String type);
}
