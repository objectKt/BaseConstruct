package dc.library.auto.task.thread;

import androidx.annotation.NonNull;

import dc.library.auto.task.thread.executor.ICategoryExecutorCore;
import dc.library.auto.task.thread.executor.IPriorityExecutorCore;
import dc.library.auto.task.thread.executor.IScheduledExecutorCore;
import dc.library.auto.task.thread.executor.impl.CategoryExecutorCore;
import dc.library.auto.task.thread.executor.impl.PriorityExecutorCore;
import dc.library.auto.task.thread.executor.impl.ScheduledExecutorCore;
import dc.library.auto.task.thread.pool.cancel.ICancelable;

import java.util.concurrent.TimeUnit;

/**
 * XTask的执行者
 *
 * @author xuexiang
 * @since 2021/10/9 2:30 AM
 */
public class XTaskExecutor implements IPriorityExecutorCore, ICategoryExecutorCore, IScheduledExecutorCore {

    private static volatile XTaskExecutor sInstance = null;

    /**
     * 优先级执行内核实现接口
     */
    private IPriorityExecutorCore mPriorityExecutorCore;
    /**
     * 分类执行内核实现接口
     */
    private ICategoryExecutorCore mCategoryExecutorCore;
    /**
     * 周期执行内核实现接口
     */
    private IScheduledExecutorCore mScheduledExecutorCore;

    /**
     * 获取XTask的执行者
     *
     * @return XTask的执行者
     */
    public static XTaskExecutor get() {
        if (sInstance == null) {
            synchronized (XTaskExecutor.class) {
                if (sInstance == null) {
                    sInstance = new XTaskExecutor();
                }
            }
        }
        return sInstance;
    }

    /**
     * 私有构造方法
     */
    private XTaskExecutor() {
        mCategoryExecutorCore = new CategoryExecutorCore();
        mPriorityExecutorCore = new PriorityExecutorCore();
        mScheduledExecutorCore = new ScheduledExecutorCore();
    }

    /**
     * 设置优先级控制的执行内核实现接口
     *
     * @param priorityExecutorCore 优先级控制的执行内核实现接口
     * @return this
     */
    public XTaskExecutor setPriorityExecutorCore(@NonNull IPriorityExecutorCore priorityExecutorCore) {
        mPriorityExecutorCore = priorityExecutorCore;
        return this;
    }

    /**
     * 设置类别执行内核实现接口
     *
     * @param categoryExecutorCore 类别执行内核实现接口
     * @return this
     */
    public XTaskExecutor setCategoryExecutorCore(@NonNull ICategoryExecutorCore categoryExecutorCore) {
        mCategoryExecutorCore = categoryExecutorCore;
        return this;
    }

    /**
     * 设置周期执行内核的实现接口
     *
     * @param scheduledExecutorCore 周期执行内核的实现接口
     * @return this
     */
    public XTaskExecutor setScheduledExecutorCore(@NonNull IScheduledExecutorCore scheduledExecutorCore) {
        mScheduledExecutorCore = scheduledExecutorCore;
        return this;
    }

    @Override
    public void shutdown() {
        mCategoryExecutorCore.shutdown();
        mPriorityExecutorCore.shutdown();
        mScheduledExecutorCore.shutdown();
    }

    //================PriorityExecutorCore==================//

    @Override
    public ICancelable submit(Runnable task, int priority) {
        return mPriorityExecutorCore.submit(task, priority);
    }

    @Override
    public ICancelable submit(String groupName, Runnable task, int priority) {
        return mPriorityExecutorCore.submit(groupName, task, priority);
    }

    //================CategoryExecutorCore==================//

    @Override
    public boolean postToMain(Runnable task) {
        return mCategoryExecutorCore.postToMain(task);
    }

    @Override
    public ICancelable postToMainDelay(Runnable task, long delayMillis) {
        return mCategoryExecutorCore.postToMainDelay(task, delayMillis);
    }

    @Override
    public ICancelable emergentSubmit(Runnable task) {
        return mCategoryExecutorCore.emergentSubmit(task);
    }

    @Override
    public ICancelable submit(Runnable task) {
        return mCategoryExecutorCore.submit(task);
    }

    @Override
    public ICancelable backgroundSubmit(Runnable task) {
        return mCategoryExecutorCore.backgroundSubmit(task);
    }

    @Override
    public ICancelable ioSubmit(Runnable task) {
        return mCategoryExecutorCore.ioSubmit(task);
    }

    @Override
    public ICancelable groupSubmit(String groupName, Runnable task) {
        return mCategoryExecutorCore.groupSubmit(groupName, task);
    }

    //================ScheduledExecutorCore==================//

    @Override
    public ICancelable schedule(Runnable task, long delay, TimeUnit unit) {
        return mScheduledExecutorCore.schedule(task, delay, unit);
    }

    @Override
    public ICancelable scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return mScheduledExecutorCore.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    @Override
    public ICancelable scheduleWithFixedDelay(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return mScheduledExecutorCore.scheduleWithFixedDelay(task, initialDelay, period, unit);
    }
}
