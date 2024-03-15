package com.android.launcher.cruisecontrol;

import com.android.launcher.entity.Can35dD4Entity;
import com.android.launcher.util.LogUtils;

/**
* @description: 定速巡航数据发送器
* @createDate: 2023/5/30
*/
@Deprecated
public class CruiseControlDataSender {

    private Can3e1Sender can3e1Sender = new Can3e1Sender();
    private CanSenderBase can3f6Sender = new Can3f6Sender();
    private Can19fSender can19fSender = new Can19fSender();
    private CanSenderBase can39dSender = new Can39dSender();
    private CanSenderBase can39fSender = new Can39fSender();
    private CanSenderBase can746Sender = new Can746Sender();
    private CanSenderBase can756Sender = new Can756Sender();
    private Can35dSender can35dSender = new Can35dSender();

    private Can1C1Sender can1C1Sender = new Can1C1Sender();
    private Can1C2Sender can1C2Sender = new Can1C2Sender();
    private Can3E4Sender can3E4Sender = new Can3E4Sender();
    private Can3EDSender can3EDSender = new Can3EDSender();
    private Can3F3Sender can3F3Sender = new Can3F3Sender();
    private Can74ESender can74ESender = new Can74ESender();
    private Can401Sender can401Sender = new Can401Sender();


    public void startSendData(){
//        //授权
//        can3e1Sender.send();
//        can19fSender.send();
//        can3f6Sender.send();
//        can39dSender.send();
//        can39fSender.send();
//        can746Sender.send();
//        can756Sender.send();
//        can35dSender.send();
//
//        can1C1Sender.send();
//        can1C2Sender.send();
//        can3E4Sender.send();
//        can3EDSender.send();
//        can3F3Sender.send();
//        can74ESender.send();
//        can401Sender.send();
    }

    public void release(){
//        can3e1Sender.release();
//        can3f6Sender.release();
//        can19fSender.release();
//        can39dSender.release();
//        can39fSender.release();
//        can746Sender.release();
//        can756Sender.release();
//        can35dSender.release();
//
//        can1C1Sender.release();
//        can1C2Sender.release();
//        can3E4Sender.release();
//        can3EDSender.release();
//        can3F3Sender.release();
//        can74ESender.release();
//        can401Sender.release();
    }

    /**
     * @description: 自动锁车
     * status:  00: 关, 01：开
     * @createDate: 2023/6/5
     */
    public synchronized void setAutoLock(String status) {
        if(status == null || status.length() != 2){
            LogUtils.printI(CruiseControlDataSender.class.getSimpleName(), "setAutoLock---数据异常----status="+status);
            return;
        }
        if(can19fSender == null){
            return;
        }

        String data = status.substring(1);

        if(data.equals(Can35dD4Entity.SwitchStatus.STATUS_ON.getValue())){
            can19fSender.setAutoLock(Can35dD4Entity.SwitchStatus.STATUS_ON);
        }else{
            can19fSender.setAutoLock(Can35dD4Entity.SwitchStatus.STATUS_OFF);
        }
    }

    /**
     * @description: 上下车辅助
     * @createDate: 2023/6/5
     */
    public synchronized void updateAssist(String status) {
        if(status ==null || status.length() != 2){
            LogUtils.printI(CruiseControlDataSender.class.getSimpleName(), "updateAssist---数据异常----status="+status);
        }else{
            if(can35dSender == null){
                return;
            }

            if(status.equals( Can35dD4Entity.AssistStatus.STEERING_COLUMN.getValue())){
                can35dSender.updateAssist(Can35dD4Entity.AssistStatus.STEERING_COLUMN);
            }else if(status.equals( Can35dD4Entity.AssistStatus.STEERING_COLUMN_AND_SEAT.getValue())){
                can35dSender.updateAssist(Can35dD4Entity.AssistStatus.STEERING_COLUMN_AND_SEAT);
            }else{
                can35dSender.updateAssist(Can35dD4Entity.AssistStatus.STATUS_CLOSE);
            }
        }

    }

    /**
     * @description: 设置背景照明开关状态
     * @createDate: 2023/6/6
     */
    public synchronized void setLighting(String status){
        if(status == null || status.length() != 1){
            LogUtils.printI(CruiseControlDataSender.class.getSimpleName(), "setLighting---数据异常----status="+status);
            return;
        }
        if(can35dSender == null){
            return;
        }

        if(status.equals(Can35dD4Entity.SwitchStatus.STATUS_ON.getValue())){
            can35dSender.setLighting(Can35dD4Entity.SwitchStatus.STATUS_ON);
        }else{
            can35dSender.setLighting(Can35dD4Entity.SwitchStatus.STATUS_OFF);
        }

    }

    /**
     * @description: 设置内部照明延迟时间
     * @createDate: 2023/6/6
     */
    public synchronized void setInnerLighting(String status){
        if(can35dSender!=null){
            can35dSender.setInnerLighting(status);
        }
    }


    /**
     * @description: 设置外部照明延迟时间
     * @createDate: 2023/6/6
     */
    public synchronized void setOuterLighting(String status){
        if(can35dSender!=null){
            can35dSender.setOuterLighting(status);
        }
    }


    /**
     * @description: 设置环境照明
     * @createDate: 2023/6/6
     */
    public synchronized void setEnvirLighting(String status){
        if(can35dSender!=null){
            can35dSender.setEnvirLighting(status);
        }
    }


    /**
    * @description: 安全带收紧
    * @createDate: 2023/7/17
    */
    public void updateSafeBeltFrapStatus(Boolean isFrap) {
        if(can3e1Sender!=null){
            can3e1Sender.updateSafeBeltFrapStatus(isFrap);
        }
    }

    public void setSendPauseStatus(boolean isPause){
        LogUtils.printI(CruiseControlDataSender.class.getSimpleName(), "setSendPauseStatus---isPause="+isPause);
        if(can19fSender!=null){
            can19fSender.setPause(isPause);
        }
        if(can3e1Sender!=null){
            can3e1Sender.setPause(isPause);
        }
        if(can35dSender!=null){
            can35dSender.setPause(isPause);
        }
        if(can3f6Sender!=null){
            can3f6Sender.setPause(isPause);
        }
        if(can39dSender!=null){
            can39dSender.setPause(isPause);
        }
        if(can746Sender!=null){
            can746Sender.setPause(isPause);
        }
        if(can39fSender!=null){
            can39fSender.setPause(isPause);
        }
        if(can756Sender!=null){
            can756Sender.setPause(isPause);
        }

        if(can1C1Sender!=null){
            can1C1Sender.setPause(isPause);
        }
        if(can1C2Sender!=null){
            can1C2Sender.setPause(isPause);
        }
        if(can3E4Sender!=null){
            can3E4Sender.setPause(isPause);
        }
        if(can3EDSender!=null){
            can3EDSender.setPause(isPause);
        }
        if(can3F3Sender!=null){
            can3F3Sender.setPause(isPause);
        }
        if(can74ESender!=null){
            can74ESender.setPause(isPause);
        }
        if(can401Sender!=null){
            can401Sender.setPause(isPause);
        }
    }

    //设置后舱盖高度限制
    public void setRearHatchCover(String status) {
        if(can35dSender!=null){
            can35dSender.setRearHatchCover(status);
        }
    }

    public void setCan35dD5(String can35dD5) {
        if(can35dSender!=null){
            can35dSender.setD5(can35dD5);
        }
    }

    //设置日间行驶灯开关
    public void setDayRunLight(Boolean isOpen) {
        if(can3e1Sender!=null){
            can3e1Sender.setDayRunLight(isOpen);
        }
    }
}
