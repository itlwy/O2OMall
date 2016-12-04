package itlwy.com.o2omall.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.lndroid.lndroidlib.utils.InterceptorUtil;
import com.lndroid.lndroidlib.utils.CrashHandlerHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import itlwy.com.o2omall.data.product.model.ProductModel;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2015/12/22.
 */
public class BaseApplication extends Application {
    private static BaseApplication application;
    private static int mainTid;
    private static Handler handler;
    private static List<ProductModel> sProductModelShopcar;

    //    private PatchManager patchManager;
    public static String appversion;

    public static String sTempImagePath;  // 应用的临时图片 保存地址
    public static String sImagePath;   // 应用保存的图片

//    public PatchManager getPatchManager() {
//        return patchManager;
//    }

    public static List<ProductModel> getProductModelShopcar() {
        return sProductModelShopcar == null ? new ArrayList<ProductModel>() : sProductModelShopcar;
    }

    public static void setProductModelShopcar(List<ProductModel> productModelShopcar) {
        sProductModelShopcar = productModelShopcar;
    }


    @Override
//  在主线程运行的
    public void onCreate() {
        super.onCreate();
        application = this;
        mainTid = android.os.Process.myTid();
        handler = new Handler();
        initFiles();
        initImageLoader();
        new CrashHandlerHelper(this);//初始化全局异常捕捉类
        initOkHttp();
        //初始化andFix
//        try {
//            appversion= getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//            patchManager = new PatchManager(this);
//            patchManager.init(appversion);//current version
//            patchManager.loadPatch();
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(InterceptorUtil.getInstance().getInterceptor())
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    private void initFiles() {
        sTempImagePath = getExternalCacheDir().getAbsolutePath() + "/";
        sImagePath = getExternalFilesDir("dir_image").getAbsolutePath() + "/";
    }


    private void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(),
                getExternalCacheDir().getAbsolutePath());// 获取到缓存的目录地址
        // 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                // 线程池内加载的数量
                .threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);// 全局初始化此配置


    }

    public static Context getApplication() {
        return application;
    }

    public static int getMainTid() {
        return mainTid;
    }

    public static Handler getHandler() {
        return handler;
    }
}
