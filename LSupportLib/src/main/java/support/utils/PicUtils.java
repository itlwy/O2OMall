package support.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;

import support.utils.bean.PicTag;
/**
 * 图片处理工具类
 * support.utils
 * PicUtils.java
 * 
 * 2015-9-24-下午9:36:06
 * author-lwy
 * @version 1.0.0
 */
public class PicUtils {

	/**
	 * 释放bitmap占用内存
	 * @param bmp
	 * @author Lwy
	 * @date 2015-9-15 下午3:50:26
	 */
	public static void freebmp(Bitmap bmp) {
		if (bmp != null && !bmp.isRecycled()) {
			bmp.recycle(); //回收图片所占的内存
			System.gc(); // 提醒系统及时回收
		}
	}

	public static Bitmap getBitmapFromFile(File dst, int width, int height) {
		if (null != dst && dst.exists()) {
			BitmapFactory.Options opts = null;
			int needWidth = width;
			int needHeight = height;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(dst.getPath(), opts);

				double realWidth = opts.outWidth;
				double realHeight = opts.outHeight;

				if (realWidth > realHeight) {
					needWidth = width;
					needHeight = height;
				} else {
					needWidth = height;
					needHeight = width;
				}
				int sampleSize = 1;

				if (realWidth > needWidth || realHeight > needHeight) {
					sampleSize = (int) Math.floor(Math.min(realWidth / needWidth, realHeight / needHeight));
				}
				int roundedSize;
				if (sampleSize <= 8) {
					roundedSize = 1;
					while (roundedSize < sampleSize) {
						roundedSize <<= 1;
					}
					if (roundedSize != sampleSize) {
						roundedSize >>= 1;
					}
				} else {
					roundedSize = (sampleSize + 7) / 8 * 8;
				}

				opts.inSampleSize = roundedSize;
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			try {
				Bitmap bitmap = BitmapFactory.decodeFile(dst.getPath(), opts);
				int loadWidth = bitmap.getWidth();
				int loadHeight = bitmap.getHeight();
				// 计算缩放率，新尺寸除原始尺寸
				float scaleWidth = ((float) needWidth) / loadWidth;
				float scaleHeight = ((float) needHeight) / loadHeight;
				Matrix matrix = new Matrix();
				// 缩放图片动作
				float minscale = Math.min(scaleWidth, scaleHeight);
				matrix.postScale(minscale, minscale);
				// 创建新的图片
				Bitmap resizedBitmap = null;
				resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, loadWidth, loadHeight, matrix, true);
				if (resizedBitmap != bitmap) {
					bitmap.recycle();
				}
				return resizedBitmap;
			} catch (OutOfMemoryError e) {
				LogUtil.d("PublicTools", e.toString());
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 以指定像素读取指定路径下的图片转换成bitmap
	 * @param absolutePath
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * @author Lwy
	 * @date 2015-9-15 下午3:50:41
	 */
	public static Bitmap decodeBitmapFromFile(String absolutePath, int reqWidth, int reqHeight) {
       Bitmap bm = null;
         // First decode with inJustDecodeBounds=true to check dimensions
       final BitmapFactory.Options options = new BitmapFactory.Options();
       options. inJustDecodeBounds = true ;
       BitmapFactory. decodeFile(absolutePath, options);
       // Calculate inSampleSize
       // Raw height and width of image
  	   int height = options.outHeight;
  	   int width = options.outWidth;
  	   int inSampleSize = 1;
  	   if (height > reqHeight || width > reqWidth) {
  	    if (width > height) {
  	     inSampleSize = Math.round((float)height / ( float)reqHeight);  
  	    } else {
  	     inSampleSize = Math.round((float)width / ( float)reqWidth);  
  	    }  
  	   }
         options. inSampleSize = inSampleSize;
        
         // Decode bitmap with inSampleSize set
         options. inJustDecodeBounds = false ;
         bm = BitmapFactory. decodeFile(absolutePath, options);
         return bm; 
    }

	/**
	 * 将bitmap以指定的像素比保存至文件
	 * @param context
	 * @param bmp
	 * @param fileDir
	 * @param fileName
	 * @param width
	 * @param height
	 * @param picTag
	 * @return
	 * @throws Exception
	 */
	public static boolean bitmap2file(Context context,Bitmap bmp,String fileDir,String fileName,float width,float height, PicTag picTag) throws Exception {
		boolean flag = false;
		// 获取这个图片的宽和高
		int width_temp;
		int height_temp;
		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth;
		float scaleHeight;
		try {
			width_temp = bmp.getWidth();
			height_temp = bmp.getHeight();
			scaleWidth = width / width_temp;
			scaleHeight = height / height_temp;
		} catch (Exception e) {
			MessageUtils.showToast(context, "装载图片文件出错，请检查图片文件是否正确，再重新导入");
			freebmp(bmp);
			return flag;
		}
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 缩放图片动作
		float minscale = Math.min(scaleWidth, scaleHeight);
		matrix.postScale(minscale, minscale);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width_temp,
				height_temp, matrix, true);
		FileOutputStream outputStream = null;
		File dir = new File(fileDir);
		if(!dir.exists())
			dir.mkdirs();
		String FileFullPath = String.format("%s%s", fileDir,fileName);
		File out = new File(FileFullPath);
		if (!out.exists())
			out.createNewFile();
		outputStream = new FileOutputStream(out);
		if (outputStream != null) {
			if(picTag != null){
				String sbmptxt0 = String.format("拍摄人:%s", picTag.userName);
				try {
					setTextToBmp(context,resizedBitmap, sbmptxt0, 40,true);
				} catch (Exception e) {
					setTextToBmp(context,resizedBitmap, sbmptxt0, 40,false);
					e.printStackTrace();
				}
				String sbmptxt = String.format("拍摄时间:%s", picTag.sTime);
				try {
					setTextToBmp(context,resizedBitmap, sbmptxt, 80,true);
				} catch (Exception e) {
					setTextToBmp(context,resizedBitmap, sbmptxt0, 80,false);
					e.printStackTrace();
				}
				String sbmptxt2 = String.format("经度:%s  纬度:%s", picTag.lng, picTag.lat);
				try {
					setTextToBmp(context,resizedBitmap, sbmptxt2, 120,true);
				} catch (Exception e) {
					setTextToBmp(context,resizedBitmap, sbmptxt2, 120,false);
					e.printStackTrace();
				}
			}
			if (resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80,
					outputStream)) {
				flag = true;
			 }
		}
		return flag;
	}
	/**
	 * 给bitmap加水印
	 * @param bmp
	 * @param txt
	 * @param ntop
	 * @param flag
	 * @author Lwy
	 * @date 2015-9-23 下午5:08:48
	 */
	private static void setTextToBmp(Context context,Bitmap bmp, String txt, int ntop,boolean flag) {
		Canvas canvasTmp;
		if (bmp == null || txt == null)
			return;
		if(!flag){
			Bitmap resizedBitmap = Bitmap.createBitmap(bmp.getWidth(),
					bmp.getHeight(), bmp.getConfig());
			if (resizedBitmap == null) {
				MessageUtils.showToast(context, "图片格式错误");
				return;
			}
			canvasTmp = new Canvas(resizedBitmap);
		}else{
			canvasTmp = new Canvas(bmp);
		}
		canvasTmp.drawColor(Color.TRANSPARENT);
		Paint p = new Paint();
		Typeface font = Typeface.create("宋体", Typeface.BOLD);
		p.setColor(Color.RED);
		p.setTypeface(font);
		p.setTextSize(20);
		canvasTmp.drawBitmap(bmp, 0, 0, p);
		canvasTmp.drawText(txt, 20, ntop, p);
		canvasTmp.save(Canvas.ALL_SAVE_FLAG);
		canvasTmp.restore();
	}
	/**
	 * Get the size in bytes of a bitmap.
	 * @param bitmap
	 * @return size in bytes
	 */
    @SuppressLint("NewApi")
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
