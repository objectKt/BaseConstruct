package com.android.launcher.wifi;

import android.util.Log;

import com.android.launcher.usbdriver.BenzHandlerData;
import com.android.launcher.util.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 *
 */
@Deprecated
public class HandleServerMessage implements Runnable {

    private static final String TAG = HandleServerMessage.class.getSimpleName();
    private BufferedReader bufferedReader;
    private Socket mSocket;

    public HandleServerMessage(Socket socket) throws IOException {
        //获取该socket对应的输入流
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.mSocket = socket;
    }

    @Override
    public void run() {
        try {
            String content = null;
            while (mSocket !=null && !mSocket.isClosed() && bufferedReader != null && (content = bufferedReader.readLine()) != null) {
                LogUtils.printI(TAG, "run---content="+content);
                Log.i("hufei", "执行了 HandleServerMessage run");
                BenzHandlerData.handlerFromRight(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.printI(TAG, "run---"+e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.printI(TAG, "run---"+e.getMessage());
        }
        close();
        LogUtils.printI(TAG, "run finish");
    }

    public void close(){
        try {
            if(bufferedReader!=null){
                bufferedReader.close();
                bufferedReader = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.printE(TAG,"");
        }
    }
}
