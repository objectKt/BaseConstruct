package dc.library.auto.task.core;

import androidx.annotation.NonNull;

import dc.library.auto.task.core.param.ITaskParam;
import dc.library.auto.task.core.step.IGroupTaskStep;
import dc.library.auto.task.core.step.ITaskStepLifecycle;
import dc.library.auto.task.thread.pool.cancel.ICancelable;
import dc.library.auto.task.thread.pool.cancel.ICanceller;

/**
 * 任务链执行引擎实现接口
 *
 * @author xuexiang
 * @since 2021/10/19 1:43 AM
 */
public interface ITaskChainEngine extends ITaskStepLifecycle, IGroupTaskStep, ICanceller {

    /**
     * 获取任务链的名称
     *
     * @return 任务链的名称
     */
    @Override
    String getName();

    /**
     * 初始化任务参数
     *
     * @param taskParam 任务参数
     * @return 任务链执行引擎
     */
    ITaskChainEngine setTaskParam(@NonNull ITaskParam taskParam);

    /**
     * 设置任务链执行回调
     *
     * @param iTaskChainCallback 任务链执行回调
     * @return 任务链执行引擎
     */
    ITaskChainEngine setTaskChainCallback(ITaskChainCallback iTaskChainCallback);

    /**
     * 开始任务
     *
     * @return 取消的接口
     */
    ICancelable start();

    /**
     * 开始任务
     *
     * @param isAddPool 是否增加到取消者订阅池里
     * @return 取消的接口
     */
    ICancelable start(boolean isAddPool);

    /**
     * 重置任务链（可继续使用)
     */
    void reset();

    /**
     * 销毁任务链（不可使用)
     */
    void destroy();

}
