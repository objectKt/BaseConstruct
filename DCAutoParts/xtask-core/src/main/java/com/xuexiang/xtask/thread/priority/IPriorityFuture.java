package com.xuexiang.xtask.thread.priority;

import com.xuexiang.xtask.thread.pool.cancel.IFuture;

/**
 * 具有优先级排序的Future接口
 *
 * @author xuexiang
 * @since 2021/10/9 2:31 AM
 */
public interface IPriorityFuture<V> extends IPriorityComparable<IPriority>, IFuture<V> {

}
