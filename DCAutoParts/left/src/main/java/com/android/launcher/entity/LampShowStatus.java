package com.android.launcher.entity;

/**
 * 灯的显示状态
 * @date： 2023/11/20
 * @author: 78495
*/
public class LampShowStatus {

    private boolean lightAutoShow = false;
    //前雾灯显示
    private boolean frontFogLampShow = false;
    //近光灯
    private boolean dippedHeadlightShow = false;

    //远光灯
    private boolean highBeamShow = false;

    //后雾灯
    private boolean rearFogLampShow = false;

    public boolean isLightAutoShow() {
        return lightAutoShow;
    }

    public void setLightAutoShow(boolean lightAutoShow) {
        this.lightAutoShow = lightAutoShow;
    }

    public boolean isFrontFogLampShow() {
        return frontFogLampShow;
    }

    public void setFrontFogLampShow(boolean frontFogLampShow) {
        this.frontFogLampShow = frontFogLampShow;
    }

    public boolean isDippedHeadlightShow() {
        return dippedHeadlightShow;
    }

    public void setDippedHeadlightShow(boolean dippedHeadlightShow) {
        this.dippedHeadlightShow = dippedHeadlightShow;
    }

    public boolean isHighBeamShow() {
        return highBeamShow;
    }

    public void setHighBeamShow(boolean highBeamShow) {
        this.highBeamShow = highBeamShow;
    }

    public boolean isRearFogLampShow() {
        return rearFogLampShow;
    }

    public void setRearFogLampShow(boolean rearFogLampShow) {
        this.rearFogLampShow = rearFogLampShow;
    }

    @Override
    public String toString() {
        return "LampShowStatus{" +
                "lightAutoShow=" + lightAutoShow +
                ", frontFogLampShow=" + frontFogLampShow +
                ", dippedHeadlightShow=" + dippedHeadlightShow +
                ", highBeamShow=" + highBeamShow +
                ", rearFogLampShow=" + rearFogLampShow +
                '}';
    }
}
