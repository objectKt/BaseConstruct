package com.backaudio.android.driver.can;

import com.backaudio.android.driver.beans.BackupBean;
import com.backaudio.android.driver.beans.CarBaseInfo;
import com.backaudio.android.driver.beans.CarRadarInfo;
import com.backaudio.android.driver.beans.CarRunInfo;
import com.backaudio.android.driver.beans.SCarPanelInfo;
import com.backaudio.android.driver.beans.SteerWheelAngle;
import com.backaudio.android.driver.manage.PublicEnum.EAUXStutas;
import com.backaudio.android.driver.manage.PublicEnum.EBtnStateEnum;
import com.backaudio.android.driver.manage.PublicEnum.ECanboxUpgrade;
import com.backaudio.android.driver.manage.PublicEnum.EControlSource;
import com.backaudio.android.driver.manage.PublicEnum.EIdriverEnum;
import com.backaudio.android.driver.beans.ACTable;

public interface ICANEventListener {
	/**
	 * 触摸板原车坐标
	 * @param isValid
	 * @param x
	 * @param y
	 */
    void onCarTouchBroad(boolean isValid, int x, int y);
	 /**
     * canbox升级状态反馈 （错误， 开始， 请求包长度， 完成）
     * @param info
     */
    void onCanboxUpgradeState(ECanboxUpgrade info);

    /**
     * canbox升级过程请求升级数据（一段一段传送）
     * @param nextIndex
     */
    void onCanboxUpgradeForGetDataByIndex(int nextIndex);
    
    /**
     * 倒车雷达信息
     * @param backupBean
     */
    void onRadarInfo(BackupBean backupBean);
    /**
     * 获取canbxo信息（只有版本号）
     * @param info 版本号相关信息
     */
    void onCanboxInfo(String info);

    /**
     * Idriver面板控制响应
     * @param value IDriver的键值
     * @param state IDriver的状态 按下 抬起 长按 三种状态
     */
    void onHandleIdriver(EIdriverEnum value, EBtnStateEnum state);


    /**
     * 空调信息
     * @param info
     */
    void onACTable(ACTable info);
    
    /**
     * 车辆基本信息
     * @param info
     */
    void onCarBaseInfo(CarBaseInfo info);

    /**
     * @param year
     * @param month
     * @param day
     * @param timeFormat 时间格式 12 和 24两种
     * @param time
     * @param min
     * @param sec
     */
    void onTime(int year, int month, int day, int timeFormat, int time, int min, int sec);
    
    /**
     * 原车当前界面
     * @param source 当前属于哪个界面
     * @param iOriginal 原车还是加装
     */
    void onOriginalCarView(EControlSource source, boolean iOriginal);
    /**
     * AUX激活状态
     */
    void onAUXActivateStutas(EAUXStutas eStutas, String str);
    /**
     * 车辆信息
     * @param  info 当前车辆运行信息
     */
    void onCarRunningInfo(CarRunInfo info);
    /**
     * S级面板信息
     * @param info
     */
    void onSCarPanelInfo(SCarPanelInfo info);
    
    /**
     * 打印canbox信息
     * @param  str 一条canbox信息
     */
    void logcatCanbox(String str);
    /**
     * 车辆雷达信息
     */
    void onCarRadarInfo(CarRadarInfo info);

    //底部面板条
    void onBottomBarPanel(String key);

    //方向盘转角信息
    void onSteerWheelAngle(byte[] buffer);
}
