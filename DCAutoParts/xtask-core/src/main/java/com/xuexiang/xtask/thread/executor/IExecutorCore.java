package com.xuexiang.xtask.thread.executor;

/**
 * 执行者内核实现接口
 *
 * @author xuexiang
 * @since 1/26/22 2:25 AM
 */
public interface IExecutorCore {

    /**
     * 停止工作
     */
    void shutdown();
}
