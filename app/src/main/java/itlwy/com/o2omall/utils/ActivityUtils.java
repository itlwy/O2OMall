package itlwy.com.o2omall.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.io.File;

import itlwy.com.o2omall.base.BaseApplication;

/**
 * Created by mac on 16/10/2.
 */

public class ActivityUtils {
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * 调用系统相册拍照
     *
     * @param activity
     * @param requestCode
     * @param imageName   要保存的图片名
     */
    public static void takePicture(Activity activity, int requestCode, String imageName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(BaseApplication.sTempImagePath, imageName));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 调用系统相册拍照
     *
     * @param fragment
     * @param requestCode
     * @param imageName   要保存的图片名
     */
    public static void takePicture(Fragment fragment, int requestCode, String imageName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(BaseApplication.sTempImagePath, imageName));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        fragment.startActivityForResult(intent, requestCode);
    }
}
