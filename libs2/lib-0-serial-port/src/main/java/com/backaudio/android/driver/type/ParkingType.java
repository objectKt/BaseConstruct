package com.backaudio.android.driver.type;

/**
 * 倒车类型
 * 高清机型：
 *   0x00: 原车倒车
 *   0x01: 加装AHD
 *   0x02: 加装CVBS
 *   0x03: 加装360 CVBS
 *   0x04: 加装360 AHD
 *   0x05: 原车360
 *   0x06: AHD1080P
 * @date： 2024/2/22
 * @author: 78495
*/
public enum ParkingType {

    ORIGINAL,
    AHD,
    CVBS,
    CVBS_360,
    AHD_360,
    ORIGINAL_360,
    AHD_1080P
}
