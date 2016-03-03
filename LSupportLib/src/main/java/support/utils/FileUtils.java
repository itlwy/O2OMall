package support.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class FileUtils {

    /**
     * 将inputstream转成string
     *
     * @param is
     * @return结果string
     * @author Lwy
     * @date 2015-9-15 下午3:47:17
     */
    public static String is2String(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 保存字符串到SharedPreferences中
     *
     * @param context
     * @param sp_name
     * @param key
     * @param value
     * @return
     * @author Lwy
     * @date 2015-9-15 下午3:47:31
     */
    public static boolean save2SP(Context context, String sp_name, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(sp_name, Activity.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putString(key, value);
        return edit.commit();
    }

    /**
     * 从SharedPreferences读取字符串
     *
     * @param context
     * @param sp_name
     * @param key
     * @return
     * @author Lwy
     * @date 2015-9-15 下午3:47:57
     */
    public static String readFromSP(Context context, String sp_name, String key) {
        SharedPreferences sp = context.getSharedPreferences(sp_name, Activity.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    /**
     * 从SharedPreferences删除指定键值对
     *
     * @param context
     * @param sp_name
     * @param key
     * @return
     * @author Lwy
     * @date 2015-9-15 下午3:48:20
     */
    public static boolean clearFromSP(Context context, String sp_name, String key) {
        SharedPreferences sp = context.getSharedPreferences(sp_name, Activity.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.remove(key);
        return edit.commit();
    }

    /**
     * 判断文件是否存在
     *
     * @param sFileName
     * @return
     * @author Lwy
     * @date 2015-9-15 下午3:48:34
     */
    public static Boolean isFileExist(String sFileName) {
        File f = new File(sFileName);
        return f.exists();
    }

    /**
     * 获得yyyyMMddHHmmss_999的随机文件名
     *
     * @return
     * @author Lwy
     * @date 2015-9-15 下午3:48:46
     */
    public static String getRandomFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDate = format.format(new Date());
        int random = new Random().nextInt(1000);
        return new StringBuffer().append(formatDate).append("_").append(random).toString();
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     * @author Lwy
     * @date 2015-9-15 下午3:49:07
     */
    public static void deleteFile(String fileName) {
        File myFile = new File(fileName);
        if (myFile.exists()) {
            myFile.delete();
        }
    }

    /**
     * 删除文件夹及文件夹里的所有文件
     *
     * @param folderPath
     * @return
     * @author Lwy
     * @date 2015-9-15 下午3:49:17
     */
    public static boolean delFolder(String folderPath) {
        boolean flag = false;
        try {
            deleteAllFiles(folderPath); // 删除文件夹里的所有文件
            File folder = new File(folderPath);
            folder.delete(); // 删除文件夹
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除指定路径里的所有文件(不包含该文件夹)
     *
     * @param path
     * @return
     * @author Lwy
     * @date 2015-9-15 下午3:49:55
     */
    public static boolean deleteAllFiles(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
        }
        return flag;
    }

    /**
     * 删除目录下指定时间的所有文件
     * @param day  几天前
     * @param dir  要删除的文件目录
     */
    public static void delFilesBeforeDate(int day, File dir) {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE)
                - day);
        Date borderDate = now.getTime();
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File f : files) {
                long time = f.lastModified();
                now.setTimeInMillis(time);
                if (now.getTime().before(borderDate)) {
                    f.delete();
                }
            }
        }
    }
}
