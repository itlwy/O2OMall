package support.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import support.utils.bean.XY;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class SystemUtils {

	/**
	 * 判断GPS是否开启
	 * @param context
	 * @return
	 * @author Lwy
	 * @date 2015-9-15 下午3:52:52
	 */
   public static boolean isGpsOpen(Context context){
	   LocationManager locationManager = (LocationManager)context.
			   							getSystemService(Context.LOCATION_SERVICE);
	   return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
   }
  /**
   * 获取APP版本号
   * @param context
   * @return
   * @author Lwy
   * @date 2015-9-15 下午3:53:05
   */
   public static int appVersionCode(Context context) {
		int result;
		try {
			result = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}
  /**
   * 获取App名称
   * @param context
   * @return
   * @author Lwy
   * @date 2015-9-15 下午3:53:13
   */
   public static String appVersionName(Context context) {
		String result = "";
		try {
			result = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
   /**
    * 获取当前正在运行的类名
    * @param context
    * @return
    * @author Lwy
    * @date 2015-9-15 下午3:53:26
    */
	public static String getCurRunClassName(Context context) {
		String classname = "";
		if (context != null) {
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningTaskInfo> runningTaskInfos = am.getRunningTasks(1);
			if (null != runningTaskInfos) {
				ComponentName cn = runningTaskInfos.get(0).topActivity;
				classname = cn.getClassName();
			}
		}
		return classname;
	}
	/**
	 * 获得可用内存
	 * @param mContext
	 * @return
	 * @author Lwy
	 * @date 2015-9-15 下午3:53:38
	 */
    public static long getAvailMem(Context mContext) {
        long MEM_UNUSED;
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        MEM_UNUSED = mi.availMem / 1024;
        return MEM_UNUSED;
    }
   /**
    * 获得总内存
    * @return
    * @author Lwy
    * @date 2015-9-15 下午3:53:51
    */
    public static long getTotalMem() {
        long mTotal;
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // beginIndex
        int begin = content.indexOf(':');
        // endIndex
        int end = content.indexOf('k');

        content = content.substring(begin + 1, end).trim();
        mTotal = Integer.parseInt(content);
        return mTotal;
    }
   /**
    * 返回一个可以调用系统相机的意图
    * @param filePath  拍的照片存的路径
    * @return
    * @author Lwy
    * @date 2015-9-15 下午3:54:04
    */
	public static Intent returnPhotoIntent(String filePath){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		return intent;
	}
	/**
	 * 设置屏幕亮度
	 * @param level
	 * @author Lwy
	 * @date 2015-9-15 下午4:00:20
	 */
	public static void setBrightness(Activity activity,int level) { 
		ContentResolver cr = activity.getContentResolver(); 
		Settings.System.putInt(cr, "screen_brightness", level); 
		Window window = activity.getWindow(); 
		LayoutParams attributes = window.getAttributes(); 
		float flevel = level; 
		attributes.screenBrightness = flevel / 255; 
		activity.getWindow().setAttributes(attributes); 
	}
	/**
	 * 隐藏软键盘
	 * 
	 * @author Lwy
	 * @date 2015-9-15 下午4:04:37
	 */
	public static void setSoftInputDismiss(Activity activity){
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
	            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	/**
	 * 静默安装    (前提是获得了root权限)
	 * @param file
	 * @return
	 * @author Lwy
	 * @date 2015-9-15 下午4:14:13
	 */
	public static boolean slientInstall(File file) {  
	    boolean result = false;  
	    Process process = null;  
	    OutputStream out = null;  
	    try {  
	        process = Runtime.getRuntime().exec("su");  
	        out = process.getOutputStream();  
	        DataOutputStream dataOutputStream = new DataOutputStream(out);  
	        dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");  
	        dataOutputStream.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " +  
	                file.getPath());  
	        // 提交命令  
	        dataOutputStream.flush();  
	        // 关闭流操作  
	        dataOutputStream.close();  
	        out.close();  
	        int value = process.waitFor();  
	          
	        // 代表成功  
	        if (value == 0) {  
	            result = true;  
	        } else if (value == 1) { // 失败  
	            result = false;  
	        } else { // 未知情况  
	            result = false;  
	        }  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } catch (InterruptedException e) {  
	        e.printStackTrace();  
	    }  
	    return result;  
	} 
	/**
	 * 获取手机sim卡串号 <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
	 * @param context
	 * @return
	 * @author Lwy
	 * @date 2015-9-15 下午4:16:31
	 */
	public static String getSIMNumber(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);					
		String sim = tm.getSimSerialNumber();
		return sim;
	}
	/**
	 * 手机震动  权限:<uses-permission android:name="android.permission.VIBRATE"/>
	 * @param context
	 * @author Lwy
	 * @date 2015-9-15 下午4:19:28
	 */
	public static void setVibration(Context context){
		//vibrator.vibrate(2000);  //振动2秒
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = {200,200,300,300,1000,2000}; //{停，振动，停，振动，停，振动}
					 //-1不重复 0循环振动 1；
		vibrator.vibrate(pattern, -1);
	}
	
	/**
     * Check if external storage is built-in or removable.
     * 检查外部存储器(eg:sd卡)是否被移除
     * @return True if external storage is removable (like an SD card), false
     *         otherwise.
     */
    @SuppressLint("NewApi")
    public static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }
    /**
     * Check how much usable space is available at a given path.
     * 获取指定目录仍可用的空间大小
     * @param path The path to check
     * @return The space available in bytes
     */
    @SuppressLint("NewApi")
    public static long getAvailableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }
    /**
     * Get the external app cache directory.
     * 获取APP目录
     * @param context The context to use
     * @return The external cache dir
     */
    @SuppressLint("NewApi")
    public static File getExternalCacheDir(Context context) {
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }
	/**
     * Check if OS version has built-in external cache dir method.
     * 检查当前系统版本是否有获得APP的Cache的方法
     * @return
     */
    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }
    /**
     * 获取屏幕宽高数据
     * @param context
     * @return
     * @date 2015-10-9 下午4:20:15
     */
    public static XY getScreenPixel(Context context){
//    	DisplayMetrics dm = new DisplayMetrics();
//    	((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
//    	XY xy = new XY(dm.widthPixels, dm.heightPixels);
    	WindowManager m = ((Activity)context).getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		XY xy = new XY(d.getWidth(), d.getHeight());
    	return xy;
    }
}
