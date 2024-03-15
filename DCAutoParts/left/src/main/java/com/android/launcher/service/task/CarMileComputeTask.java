package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.can.Can203;
import com.android.launcher.util.FuncUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 汽车里程计算
 * @date： 2024/1/3
 * @author: 78495
*/
public class CarMileComputeTask extends TaskBase{



    public CarMileComputeTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            List<String> milesCopy = new ArrayList<>();
            milesCopy.addAll(Can203.miles);
            Can203.miles.clear();
            FuncUtil.getResultMap(milesCopy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
