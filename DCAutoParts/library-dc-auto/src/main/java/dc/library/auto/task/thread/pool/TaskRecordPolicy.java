package dc.library.auto.task.thread.pool;

import dc.library.auto.task.logger.TaskLogger;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 记录日志的拒绝策略
 *
 * @author xuexiang
 * @since 2021/12/16 2:02 AM
 */
public class TaskRecordPolicy implements RejectedExecutionHandler {

    private static final String TAG = TaskLogger.getLogTag("RecordPolicy");

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        TaskLogger.eTag(TAG, "Runnable task has been rejected! Thread [" + Thread.currentThread().getName() + "], Runnable: " + r + ", ThreadPoolExecutor: " + e);
    }
}
