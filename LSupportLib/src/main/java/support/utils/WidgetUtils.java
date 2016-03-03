package support.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.itlwy.support.R;

import java.util.ArrayList;
import java.util.List;

import support.customui.widget.CProgressDialog;
import support.utils.bean.XY;

public class WidgetUtils {

	/**
	 * 给指定布局加一分隔线
	 * @param a
	 * @param ll  布局
	 * @param color  分割线颜色
	 * @author Lwy
	 * @date 2015-9-15 下午3:54:38
	 */
	public static void setDividerLine(Activity a,LinearLayout ll,int color){
		View line = new View(a);
		LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,1);
		line.setBackgroundColor(color);
		ll.addView(line,lineParams);
	}
	/**
	 * 获得一个popWindow
	 * @param view
	 * @param width
	 * @param height
	 * @return
	 * @author Lwy
	 * @date 2015-9-15 下午3:55:12
	 */
	public static PopupWindow getPopWindow(View view,int width,int height){
		PopupWindow pw = new PopupWindow(view,width,height,true);
    	pw.setWidth(width);
		pw.setHeight(height);
		// 需要设置一下此参数，点击外边可消失  
		pw.setBackgroundDrawable(new BitmapDrawable());  
		//设置点击窗口外边窗口消失  
		pw.setOutsideTouchable(true);  
		 // 设置此参数获得焦点，否则无法点击 
		pw.setFocusable(true);  
		return pw;
	}
	/**
	 * 设置TextView字体粗体
	 * @param tv  
	 * @author Lwy
	 * @date 2015-9-15 下午4:08:59
	 */
	public static void setTextViewBold(TextView tv){
		TextPaint tp = tv.getPaint(); 
		tp.setFakeBoldText(true); 
	}
	/**
	 * 获取屏幕宽度
	 * @param context
	 * @author Lwy
	 * @date 2015-9-15 下午4:11:59
	 */
	public static DisplayMetrics getWindowParam(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
//		float density = dm.density;
//		int width = dm.widthPixels;
//		int height = dm.heightPixels;
//		if (width <= 320) {
//			width = (int) Math.ceil(width * density);
//			height = (int) Math.ceil(height * density);
//		}
		return dm;
	}
	/**
	 * 自定义单选对话框的item点击事件
	 * @author Lwy
	 */
	public interface OnItemClick{
		void onItemClick(AdapterView<?> parent, View view, int position,
				long id);
	}

	/**
	 * 自定义单选对话框
	 * @param a
	 * @param datas
	 * @param title
	 * @param event
	 * @return
	 */
	public static CProgressDialog showSingleChoiceDialog(Context a,String[] datas,String title,final OnItemClick event) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < datas.length; i++) {
			list.add(datas[i]);
		}
		View view = View.inflate(a, R.layout.common_lv_dialog, null);
		TextView title_tv = (TextView) view.findViewById(R.id.title_tv);
		ListView title_lv = (ListView) view.findViewById(R.id.title_lv);
		WindowManager m = ((Activity) a).getWindowManager();  
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用  
		title_tv.setText(title);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(a,R.layout.list_item_style, list);
		title_lv.setAdapter(adapter);
		final CProgressDialog dialog = CProgressDialog.createAskNoDialog(a,view);
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
		title_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if(event != null){
					event.onItemClick(parent,view,position,id);
					dialog.dismiss();
				}
				
			}
		});
		return dialog;
    }

	/**
	 * 弹出web端对话框
	 * xy置null即采用默认宽高
	 * @param a
	 * @param view
	 * @param xy
	 * @return
	 */
	public static AlertDialog showWebDialog(Activity a, View view,XY xy) {
		WindowManager m = a.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		final AlertDialog dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		Window window = dialog.getWindow();
		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
		if(xy == null){
			p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.5
			p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.8
		}else{
			p.height = DensityUtil.dip2px(a, xy.y);
			p.width = DensityUtil.dip2px(a, xy.x);
		}
		window.setAttributes(p);
		return dialog;
	}
	/**
	 * 返回全屏显示的自定义dialog
	 * @param a
	 * @param view  需要显示的view
	 * @return
	 * @date 2015-10-9 下午4:51:52
	 */
	public static Dialog showFullScreenDialog(Activity a, View view) {
		Dialog dialog = new Dialog(a, R.style.Dialog_Fullscreen);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		view.setLayoutParams(params);
		dialog.setContentView(view);
		dialog.show();
		return dialog;
	}
	public interface DialogAsk{
		boolean confirm();
		boolean cancel();
	}
	/**
	 * 自定义对话框
	 * @param a
	 * @param title
	 * @param content
	 * @param event
	 * @return
	 */
	public static Dialog showWebDialog(Context a,String title,String content,final DialogAsk event,int res) {
		View view = View.inflate(a, R.layout.messagedialog, null);
		Button message_confirm = (Button) view.findViewById(R.id.message_confirm);
		Button message_cancel = (Button) view.findViewById(R.id.message_cancel);
		TextView message_tv = (TextView) view.findViewById(R.id.message_tv);
		TextView message_title = (TextView) view.findViewById(R.id.message_title_tv);
		ImageView icon = (ImageView)view.findViewById(R.id.icon);
		message_title.setText(title);
		message_tv.setText(content);
		icon.setBackgroundResource(res);
    	WindowManager m = ((Activity) a).getWindowManager();  
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用  
        final CProgressDialog dialog = CProgressDialog.createAskNoDialog(a,view);
		dialog.show();
		message_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				event.confirm();
				dialog.dismiss();
			}
		});
		message_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return dialog;
    }
	public interface DialogInputCallBack {
		void confirm(String content);

		void cancel();
	}

	public static Dialog showInputDialog(Context a, String title, final DialogInputCallBack event) {
		View view = View.inflate(a, R.layout.common_dialog_input, null);
		final EditText dialog_input = (EditText) view
				.findViewById(R.id.dialog_input);
		TextView dialog_title = (TextView) view
				.findViewById(R.id.dialog_title);
		dialog_title.setText(title);
		final AlertDialog dialog = WidgetUtils.showWebDialog((Activity) a, view,null);
		view.findViewById(R.id.dialog_confirm).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (event != null)
							event.confirm(dialog_input.getText().toString());
						dialog.dismiss();
					}

				});
		view.findViewById(R.id.dialog_cancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (event != null)
							event.cancel();
						dialog.dismiss();
					}
				});
		return dialog;
	}
}
