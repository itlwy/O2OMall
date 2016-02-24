package support.utils;

import android.content.Context;
import android.widget.Toast;

public class MessageUtils {
	/**
	 * show短时toast
	 * @param context
	 * @param text
	 * @author Lwy
	 * @date 2015-9-16 下午2:15:57
	 */
	public static void showToast(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
}
