package itlwy.com.o2omall.utils;

import itlwy.com.o2omall.base.BaseApplication;

/**
 * Created by Administrator on 2015/12/22.
 */
public class UIUtils {

    /**
     * 把Runnable 方法提交到主线程运行
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        // 在主线程运行
        if(android.os.Process.myTid()== BaseApplication.getMainTid()){
            runnable.run();
        }else{
            //获取handler
            BaseApplication.getHandler().post(runnable);
        }
    }
}
