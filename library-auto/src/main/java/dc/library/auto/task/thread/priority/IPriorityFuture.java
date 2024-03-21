package dc.library.auto.task.thread.priority;

import dc.library.auto.task.thread.pool.cancel.IFuture;

/**
 * 具有优先级排序的Future接口
 *
 * @author xuexiang
 * @since 2021/10/9 2:31 AM
 */
public interface IPriorityFuture<V> extends IPriorityComparable<IPriority>, IFuture<V> {

}
