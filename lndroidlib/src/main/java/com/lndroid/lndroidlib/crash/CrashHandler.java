package com.lndroid.lndroidlib.crash;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2016/3/2.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler ourInstance = new CrashHandler();
    private static final Thread.UncaughtExceptionHandler sDefaultHandler = Thread
            .getDefaultUncaughtExceptionHandler();
    private static final ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();
    private CrashListener mListener;
    private Future<?> future;

    public void setmListener(CrashListener mListener) {
        this.mListener = mListener;
    }

    public static CrashHandler getInstance() {
        return ourInstance;
    }

    private CrashHandler() {
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(thread, ex) && sDefaultHandler != null) { //用户处理
            sDefaultHandler.uncaughtException(thread, ex);
        } else {
            //如果用户没有处理则让系统默认的异常处理器来处理
            try {
                Thread.sleep(1500);
                sDefaultHandler.uncaughtException(thread, ex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean handleException(final Thread thread, final Throwable ex) {
        if (ex == null)
            return false;
        future = THREAD_POOL.submit(new Runnable() {
            public void run() {
                if (mListener != null) {
                    mListener.afterCrash(thread, ex);
                }
            }
        });
        if (!future.isDone()) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
