package com.android.launcher.util;


import java.util.LinkedList;

/**
 * 事件对象
 * 请求时间获
 *
* <p>
* Created by lxb on 2017/3/31.
 *
 *
 */
public class HandlerRequest {

    private LinkedList<byte[]> requestQueue = new LinkedList<>();
    private int count;    // 任务队列请求数
  //TODO BlockingQueue
    public HandlerRequest() {
        this.count = 0;
    }

    public synchronized void buildEvent( byte[] request) throws InterruptedException {

        while (!requestQueue.isEmpty() && count >= requestQueue.size()) {
            wait();
        }
        requestQueue.addLast(request);
        count++;
        notifyAll();
    }

    public synchronized byte[] sendEvent() throws InterruptedException {
        while (count <= 0) {
            wait();
        }
        byte[] request = requestQueue.pop();
        count--;
        notifyAll();
        return request;
    }
}