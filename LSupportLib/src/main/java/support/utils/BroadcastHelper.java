package support.utils;

import android.content.Context;
import android.content.Intent;

public class BroadcastHelper {

	/**
	 * 发送String 类型的值的广播
	 * @param context
	 * @param action
	 * @param key
	 * @param value
	 */
	public static void sendBroadCast(Context context,String action,String key,String value) {
		Intent intent = new Intent();
        intent.setAction(action);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(key, value);
        context.sendBroadcast(intent);
	}
	
}
