package com.android.launcher.cruisecontrol;

public abstract class CanSenderBase {

    protected static final String DATA_HEAD = "AA0000";

    protected boolean isRunnable = false;

    protected boolean isPause = false;



    public abstract void send();

     public void release(){
         isRunnable = false;
         isPause = false;
     };

     public void setPause(boolean pause){
         isPause = pause;
     }
}
