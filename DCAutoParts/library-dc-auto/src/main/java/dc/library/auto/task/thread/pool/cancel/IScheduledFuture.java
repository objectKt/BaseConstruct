package dc.library.auto.task.thread.pool.cancel;

import java.util.concurrent.RunnableScheduledFuture;

/**
 * 可直接取消任务的RunnableScheduledFuture接口
 *
 * @author xuexiang
 * @since 3/20/22 1:30 AM
 */
public interface IScheduledFuture<T> extends RunnableScheduledFuture<T>, ICancelable {

}
