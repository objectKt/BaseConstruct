package com.android.launcher.wifi;


import com.android.launcher.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;

import java.io.PrintStream;
import java.net.Socket;

/**
 * @author xxh
 * @date 2018/12/11
 */
@Deprecated
public class ServerPushTask extends ThreadUtils.SimpleTask {
    Socket socket;
    String message;
    ServerPushTask(Socket socket,String message) {
        this.socket = socket;
        this.message = message;
    }

    @Override
    public Object doInBackground() throws Throwable {
        LogUtils.printI(ServerPushTask.class.getName(),"socket="+socket);
        if (socket!=null){
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            LogUtils.printI(ServerPushTask.class.getName(),"printStream="+printStream);
            //向该输出流中写入要广播的内容
            printStream.println(message);
        }

        return null;
    }

    @Override
    public void onSuccess(Object result) {

    }
}
