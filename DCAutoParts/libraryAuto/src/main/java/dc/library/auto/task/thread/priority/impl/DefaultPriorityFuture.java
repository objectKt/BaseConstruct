package dc.library.auto.task.thread.priority.impl;

import dc.library.auto.task.thread.priority.IPriority;
import dc.library.auto.task.thread.priority.IPriorityFuture;
import dc.library.auto.task.thread.utils.PriorityUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 具有优先级排序的Future
 *
 * @author xuexiang
 * @since 2021/10/9 11:16 AM
 */
public class DefaultPriorityFuture<V> extends FutureTask<V> implements IPriorityFuture<V> {

    /**
     * 优先级
     */
    private IPriority mPriority;

    public DefaultPriorityFuture(Callable<V> callable) {
        super(callable);
        if (callable instanceof IPriority) {
            mPriority = (IPriority) callable;
        }
        setId(PriorityUtils.generateId());
    }

    public DefaultPriorityFuture(Runnable runnable, V result) {
        super(runnable, result);
        if (runnable instanceof IPriority) {
            mPriority = (IPriority) runnable;
        }
        setId(PriorityUtils.generateId());
    }

    @Override
    public void priority(int priority) {
        if (mPriority != null) {
            mPriority.priority(priority);
        }
    }

    @Override
    public long getId() {
        return mPriority != null ? mPriority.getId() : 0;
    }

    @Override
    public void setId(long id) {
        if (mPriority != null) {
            mPriority.setId(id);
        }
    }

    @Override
    public int priority() {
        return mPriority != null ? mPriority.priority() : 0;
    }

    @Override
    public int compareTo(IPriority other) {
        return PriorityUtils.compare(this, other);
    }

    @Override
    public String toString() {
        return "DefaultPriorityFuture{" +
                ", mPriority=" + mPriority +
                '}';
    }

    @Override
    public void cancel() {
        cancel(true);
    }
}
