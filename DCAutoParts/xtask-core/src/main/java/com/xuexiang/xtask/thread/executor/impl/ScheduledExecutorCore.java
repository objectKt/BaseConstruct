package com.xuexiang.xtask.thread.executor.impl;

import androidx.annotation.NonNull;

import com.xuexiang.xtask.thread.executor.IScheduledExecutorCore;
import com.xuexiang.xtask.thread.pool.DefaultScheduledThreadPoolExecutor;
import com.xuexiang.xtask.thread.pool.cancel.ICancelable;
import com.xuexiang.xtask.thread.utils.ExecutorUtils;

import java.util.concurrent.TimeUnit;

/**
 * 拥有周期执行能力的内核实现
 *
 * @author xuexiang
 * @since 3/20/22 1:15 AM
 */
public class ScheduledExecutorCore implements IScheduledExecutorCore {

    private DefaultScheduledThreadPoolExecutor mExecutor;

    @Override
    public ICancelable schedule(Runnable task, long delay, TimeUnit unit) {
        return getThreadPoolExecutor().schedule(task, delay, unit);
    }

    @Override
    public ICancelable scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return getThreadPoolExecutor().scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    @Override
    public ICancelable scheduleWithFixedDelay(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return getThreadPoolExecutor().scheduleWithFixedDelay(task, initialDelay, period, unit);
    }

    @Override
    public void shutdown() {
        ExecutorUtils.shutdown(mExecutor);
        mExecutor = null;
    }

    /**
     * 获取线程池
     *
     * @return 线程池
     */
    @NonNull
    private DefaultScheduledThreadPoolExecutor getThreadPoolExecutor() {
        if (mExecutor == null) {
            mExecutor = DefaultScheduledThreadPoolExecutor.getDefault();
        }
        return mExecutor;
    }

}
