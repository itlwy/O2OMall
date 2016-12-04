package com.lndroid.lndroidlib.crash;

/**
 * Created by Administrator on 2016/3/2.
 */
public interface CrashListener {
    /**
     * 保存异常的日志。
     *
     */
    void afterCrash(Thread thread, Throwable ex);
}
