package dc.library.auto.task.thread.pool.cancel;

/**
 * 取消者
 *
 * @author xuexiang
 * @since 2/7/22 12:39 PM
 */
public interface ICanceller extends ICancelable {

    /**
     * 获取取消的标识
     *
     * @return 取消的标识
     */
    String getName();
}
