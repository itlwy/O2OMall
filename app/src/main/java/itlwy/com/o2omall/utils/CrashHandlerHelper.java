package itlwy.com.o2omall.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import support.common.Crash.CrashHandler;
import support.common.Crash.CrashListener;
import support.common.Crash.CrashLogUtil;

/**
 * Created by Administrator on 2016/3/2.
 */
public class CrashHandlerHelper implements CrashListener {

    private Context context;
    private String log_dir;
    private String log_path;

    public String getLog_dir() {
        if (TextUtils.isEmpty(log_dir)) {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = context.getApplicationInfo();
            String label = (String) pm.getApplicationLabel(ai);
            log_dir = android.os.Environment
                    .getExternalStorageDirectory().getAbsolutePath() + "/" + label+"/crash";
        }
        return log_dir;
    }

    public String getLog_path() {
        if (TextUtils.isEmpty(log_path)) {
            Date nowtime = new Date();
            SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式
            String needWriteFiel = logfile.format(nowtime);
            log_path = getLog_dir() + "/"+needWriteFiel+"_crash.log";
        }
        return log_path;
    }

    public CrashHandlerHelper(Context ctx) {
        context = ctx;
        CrashHandler.getInstance().setmListener(this);
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler.getInstance());
    }


    @Override
    public void afterCrash(final Thread thread, final Throwable ex) {
        File log = new File(getLog_path());
        CrashLogUtil.writeLog(log, "CrashHandler", "", ex);//collectDevInfo(ex)
        // 使用 Toast 来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "很抱歉，程序出现异常，即将退出。", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        // 重新启动程序
//        Intent intent = new Intent();
//        intent.setClass(context,Login1Activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(1);
    }

    private String collectDevInfo(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        sb.append("APPLICATION INFORMATION").append('\n');
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai = context.getApplicationInfo();
        sb.append("Application : ").append(pm.getApplicationLabel(ai)).append('\n');
        try {
            PackageInfo pi = pm.getPackageInfo(ai.packageName, 0);
            sb.append("Version Code: ").append(pi.versionCode).append('\n');
            sb.append("Version Name: ").append(pi.versionName).append('\n');
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        sb.append('\n').append("DEVICE INFORMATION").append('\n');
        sb.append("Board: ").append(Build.BOARD).append('\n');
        sb.append("BOOTLOADER: ").append(Build.BOOTLOADER).append('\n');
        sb.append("BRAND: ").append(Build.BRAND).append('\n');
        sb.append("CPU_ABI: ").append(Build.CPU_ABI).append('\n');
        sb.append("CPU_ABI2: ").append(Build.CPU_ABI2).append('\n');
        sb.append("DEVICE: ").append(Build.DEVICE).append('\n');
        sb.append("DISPLAY: ").append(Build.DISPLAY).append('\n');
        sb.append("FINGERPRINT: ").append(Build.FINGERPRINT).append('\n');
        sb.append("HARDWARE: ").append(Build.HARDWARE).append('\n');
        sb.append("HOST: ").append(Build.HOST).append('\n');
        sb.append("ID: ").append(Build.ID).append('\n');
        sb.append("MANUFACTURER: ").append(Build.MANUFACTURER).append('\n');
        sb.append("PRODUCT: ").append(Build.PRODUCT).append('\n');
        sb.append("TAGS: ").append(Build.TAGS).append('\n');
        sb.append("TYPE: ").append(Build.TYPE).append('\n');
        sb.append("USER: ").append(Build.USER).append('\n');
        sb.append("{").append(ex.getMessage()).append("}");
        return sb.toString();
    }
}
