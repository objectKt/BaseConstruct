package com.xuexiang.xtask.thread.priority;

import java.util.concurrent.Callable;

/**
 * 具有优先级排序的Callable接口
 *
 * @author xuexiang
 * @since 2021/10/9 2:36 AM
 */
public interface IPriorityCallable<V> extends IPriorityComparable<IPriority>, Callable<V> {

}
