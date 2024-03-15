package com.android.launcher.entity;

import java.util.HashMap;
import java.util.Map;

/**
* @description: 胎压
* @createDate: 2023/6/25
*/
public class CarTyre {
    private Map<String,String> tempMap = new HashMap<>() ;

    public CarTyre(Map<String, String> tempMap) {
        this.tempMap = tempMap;
    }

    public Map<String, String> getTempMap() {
        return tempMap;
    }
}
