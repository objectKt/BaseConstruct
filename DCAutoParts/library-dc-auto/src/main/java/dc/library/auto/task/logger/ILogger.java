package dc.library.auto.task.logger;

/**
 * 日志记录接口
 *
 * @author xuexiang
 * @since 2021/10/9 4:33 PM
 */
public interface ILogger {

    /**
     * 打印信息
     *
     * @param priority 优先级
     * @param tag      标签
     * @param message  信息
     * @param t        出错信息
     */
    void log(int priority, String tag, String message, Throwable t);

}
