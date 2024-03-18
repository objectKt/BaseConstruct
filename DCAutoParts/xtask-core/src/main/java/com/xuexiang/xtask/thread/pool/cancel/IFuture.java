package com.xuexiang.xtask.thread.pool.cancel;

import java.util.concurrent.Future;

/**
 * 可直接取消任务的Future接口
 *
 * @author xuexiang
 * @since 1/25/22 2:04 AM
 */
public interface IFuture<T> extends Future<T>, ICancelable {

}
