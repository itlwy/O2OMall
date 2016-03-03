package support.common.Crash;

import java.io.File;

/**
 * Created by Administrator on 2016/3/2.
 */
public interface CrashListener {
    /**
     * 保存异常的日志。
     *
     * @param file
     */
    void afterCrash(Thread thread, Throwable ex);
}
