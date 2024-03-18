package com.android.launcher.wifi;

import android.util.Log;

import com.android.launcher.util.LogUtils;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author xxh
 */
@Deprecated
public class ServerRunnable implements Runnable {

    private static final String TAG = ServerRunnable.class.getSimpleName();
    private boolean isStart = false;
    private ServerSocket serverSocket = null;
    //    private Socket socket = null;
    private PrintStream sendMessageStream;
    private HandleServerMessage handleServerMessage;

    private List<Socket> clientSockets = new ArrayList<>();
    private List<HandleServerMessage> handleServerMessageList = new ArrayList<>();

    //发送队列
    private static LinkedTransferQueue<String> mSendQueue = new LinkedTransferQueue<String>();

    public boolean isStart() {
        return isStart;
    }

    @Override
    public void run() {
        try {
            if (serverSocket != null) {
                stop();
            }
            //创建一个ServerSocket，用于监听客户端Socket的连接请求
            serverSocket = new ServerSocket(35182);
            //启动成功的操作
            StartSucces();
            isStart = true;

            while (isStart) {
                Socket socket = serverSocket.accept();
                closeSocketList();
                clientSockets.add(socket);
                ConnetSucces();
                startSendMessageThread(socket);
                //启动一个异步线程去接收数据
                Log.i("hufei", "执行了 ServerRunnable run");
                handleServerMessage = new HandleServerMessage(socket);
                new Thread(handleServerMessage).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            stop();
            LogUtils.printE(TAG, "ServerRunnable---" + e.getMessage());

            try {
                Thread.sleep(2000);
                WifiServerSocket.getInstance().start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private void startSendMessageThread(final Socket socket) {
        try {
            sendMessageStream = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.printI(ServerRunnable.class.getSimpleName(), e.getMessage());
        }
        if (sendMessageStream == null) {
            LogUtils.printI(ServerRunnable.class.getSimpleName(), "sendMessageStream is null");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isStart) {
                    try {
                        String message = mSendQueue.take();
                        if (socket != null || !socket.isClosed() || sendMessageStream != null) {
                            LogUtils.printI(ServerRunnable.class.getName(), "startSendMessageThread-----printStream=" + sendMessageStream);
                            //向该输出流中写入要广播的内容
                            sendMessageStream.println(message);
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        LogUtils.printE(ServerRunnable.class.getSimpleName(), "startSendMessageThread---" + e.getMessage());
                    }
                }
                closeSendMessageStream();
            }
        }).start();
    }

    //启动成功的操作在里面写
    private void StartSucces() {
        Log.i(TAG, "StartSucces: SocetServer启动成功");

    }

    private void ConnetSucces() {
        Log.i(TAG, "ConnetSucces: 服务端收到一个客户端连接成功 ");

    }

    // 发送方法，这里采用了线程池去启动发送线程
    public void sendMessage(String message) {
        if (isStart) {
//            ThreadUtils.executeBySingle(new ServerPushTask(socket,message));
            mSendQueue.put(message);
        } else {
            Log.e(TAG, "sendMessage: socket服务器未连接");
        }
    }

    //通过捕获异常退出
    public void stop() {
        closeSendMessageStream();
        try {
            if (handleServerMessage != null) {
                handleServerMessage.close();
                handleServerMessage = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.printI(ServerRunnable.class.getSimpleName(), e.getMessage());
        }
        closeSocketList();
        try {
            if (serverSocket != null && isStart) {
                serverSocket.close();
                isStart = false;
            } else {
                LogUtils.printI(ServerRunnable.class.getSimpleName(), "not open serverSocket");
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.printI(ServerRunnable.class.getSimpleName(), e.getMessage());
        }
        mSendQueue.clear();
    }

    private void closeSendMessageStream() {
        try {
            if (sendMessageStream != null) {
                sendMessageStream.close();
                sendMessageStream = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.printI(ServerRunnable.class.getSimpleName(), e.getMessage());
        }
    }

    private void closeSocketList() {
        try {
            for (int i = 0; i < clientSockets.size(); i++) {
                Socket socket = clientSockets.get(i);
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            }
            clientSockets.clear();
            LogUtils.printI(ServerRunnable.class.getSimpleName(), "closeSocketList---");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.printI(ServerRunnable.class.getSimpleName(), "closeSocketList----" + e.getMessage());
        }
    }
}
