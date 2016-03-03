package itlwy.com.o2omall.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import itlwy.com.o2omall.bean.Product;
import itlwy.com.o2omall.utils.CrashHandlerHelper;

/**
 * Created by Administrator on 2015/12/22.
 */
public class BaseApplication extends Application {
    private static BaseApplication application;
    private static int mainTid;
    private static Handler handler;
    private List<Product> productShopcar;
    private DisplayImageOptions options;

    public List<Product> getProductShopcar() {
        return productShopcar==null?new ArrayList<Product>():productShopcar;
    }

    public void setProductShopcar(List<Product> productShopcar) {
        this.productShopcar = productShopcar;
    }

    public DisplayImageOptions getOptions() {
        if (options == null){
            options = new DisplayImageOptions.Builder()
                    // // 设置图片在下载期间显示的图片
                    // .showImageOnLoading(R.drawable.small_image_holder_listpage)
                    // // 设置图片Uri为空或是错误的时候显示的图片
                    // .showImageForEmptyUri(R.drawable.small_image_holder_listpage)
                    // // 设置图片加载/解码过程中错误时候显示的图片
                    // .showImageOnFail(R.drawable.small_image_holder_listpage)
                    .cacheInMemory(true)
                            // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)
                            // 设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true)
                    .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                    .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                            // .decodingOptions(android.graphics.BitmapFactory.Options
                            // decodingOptions)//设置图片的解码配置
                    .considerExifParams(true)
                            // 设置图片下载前的延迟
                            // .delayBeforeLoading(int delayInMillis)//int
                            // delayInMillis为你设置的延迟时间
                            // 设置图片加入缓存前，对bitmap进行设置
                            // 。preProcessor(BitmapProcessor preProcessor)
                    .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                            // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                    .displayer(new FadeInBitmapDisplayer(100))// 淡入
                    .build();
        }
        return options;
    }

    @Override
//  在主线程运行的
    public void onCreate() {
        super.onCreate();
        application=this;
        mainTid = android.os.Process.myTid();
        handler=new Handler();
        initImageLoader();
        new CrashHandlerHelper(this);//初始化全局异常捕捉类
    }

    private void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(),
                "O2OMall/Cache");// 获取到缓存的目录地址
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
