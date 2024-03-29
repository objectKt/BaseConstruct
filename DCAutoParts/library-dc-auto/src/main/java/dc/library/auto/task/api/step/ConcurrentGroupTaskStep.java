package dc.library.auto.task.api.step;

import androidx.annotation.NonNull;

import dc.library.auto.task.core.ThreadType;
import dc.library.auto.task.core.param.ITaskParam;
import dc.library.auto.task.core.param.ITaskResult;
import dc.library.auto.task.core.step.ITaskStep;
import dc.library.auto.task.core.step.impl.AbstractGroupTaskStep;
import dc.library.auto.task.thread.pool.cancel.ICancelable;
import dc.library.auto.task.utils.TaskUtils;

/**
 * 并行任务组(不进行具体的任务）
 *
 * @author xuexiang
 * @since 2021/11/8 2:04 AM
 */
public class ConcurrentGroupTaskStep extends AbstractGroupTaskStep {

    /**
     * 获取并行任务组
     *
     * @return 并行任务组
     */
    public static ConcurrentGroupTaskStep get() {
        return new ConcurrentGroupTaskStep();
    }

    /**
     * 获取并行任务组
     *
     * @param name 任务组名称
     * @return 并行任务组
     */
    public static ConcurrentGroupTaskStep get(@NonNull String name) {
        return new ConcurrentGroupTaskStep(name);
    }

    /**
     * 获取并行任务组
     *
     * @param threadType 线程类型
     * @return 并行任务组
     */
    public static ConcurrentGroupTaskStep get(@NonNull ThreadType threadType) {
        return new ConcurrentGroupTaskStep(threadType);
    }

    public ConcurrentGroupTaskStep() {
        super();
    }

    public ConcurrentGroupTaskStep(@NonNull String name) {
        super(name);
    }

    public ConcurrentGroupTaskStep(@NonNull ThreadType threadType) {
        super(threadType);
    }

    public ConcurrentGroupTaskStep(@NonNull String name, @NonNull ITaskParam taskParam) {
        super(name, taskParam);
    }

    public ConcurrentGroupTaskStep(@NonNull String name, @NonNull ThreadType threadType) {
        super(name, threadType);
    }

    @Override
    public void doTask() throws Exception {
        initGroupTask();
        if (mTaskTotalSize > 0) {
            for (ITaskStep taskStep : getTasks()) {
                if (taskStep != null && taskStep.accept()) {
                    taskStep.prepareTask(getResult());
                    ICancelable cancelable = TaskUtils.executeTask(taskStep);
                    taskStep.setCancelable(cancelable);
                }
            }
        } else {
            notifyTaskSucceed(getResult());
        }
    }

    @Override
    public void onTaskStepCompleted(@NonNull ITaskStep step, @NonNull ITaskResult result) {
        getResult().saveResultNotPath(result);
        getResult().addGroupPath(step.getName(), mTaskIndex.getAndIncrement(), mTaskTotalSize);
        if (mTaskIndex.get() == mTaskTotalSize) {
            notifyTaskSucceed(result);
        }
    }

    @Override
    public void onTaskStepError(@NonNull ITaskStep step, @NonNull ITaskResult result) {
        if (mTaskIndex.get() != -1) {
            // 并行任务，只通知一次失败
            notifyTaskFailed(result);
            mTaskIndex.set(-1);
        }
    }
}
