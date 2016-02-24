package support.customui.widget;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import support.utils.MessageUtils;
import support.utils.WidgetUtils;
import support.utils.WidgetUtils.DialogAsk;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itlwy.support.R;
/**
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>  
 * @author lwy
 *
 */
public class FileBrowseDialog extends Dialog {  
    private Context context = null;
	private String sdDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
	private String rootDir = android.os.Environment.getRootDirectory().getAbsolutePath();
	private List<Map<String, Object>> mData; 
	private OnResultBack listener;
	private FileBrowseAdapter adapter;
	private TextView comm_title_tv;
	private String sourcePath;

	public void setListener(OnResultBack listener) {
		this.listener = listener;
	}

	public interface OnResultBack{
		void onResultBack(String path);
	}
	
	public FileBrowseDialog(Context context,String sourcePath){  
        this(context, R.style.CustomProgressDialog,sourcePath);
    }  
      
    public FileBrowseDialog(Context context, int theme,String sourcePath) {  
        super(context, theme);
        this.context = context;
        this.sourcePath = sourcePath;
        init();
    }

	private void init() {
		View view = View.inflate(this.context,R.layout.filebrowse_main, null);
		setContentView(view);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		
		WindowManager m = ((Activity)this.context).getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		
		Window window = getWindow();
		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
		p.height = (int) (d.getHeight()); // 高度设置为屏幕的
		p.width = (int) (d.getWidth()); // 宽度设置为屏幕的
		window.setAttributes(p);
		
		initContentView(view);
	}  

	private void initContentView(View view) {
		ListView my_lv = (ListView)view.findViewById(R.id.list_lv);
		RelativeLayout comm_title_back = (RelativeLayout)view.findViewById(R.id.comm_title_back);
		comm_title_tv = (TextView)view.findViewById(R.id.comm_title_tv);
		adapter = new FileBrowseAdapter(this.context);
		mData = getData();
		my_lv.setAdapter(adapter);
		my_lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				if ((Integer) mData.get(position).get("img") == R.drawable.ex_folder) {
					FileBrowseDialog.this.sourcePath = (String) mData.get(position).get("info");
					mData = getData();
					adapter.notifyDataSetChanged();
				} else {
					String fileName = (String) mData.get(position).get("title");
					int index = fileName.lastIndexOf(".");
					String format = fileName.substring(index+1);
					if("jpg".equalsIgnoreCase(format)||"jpeg".equalsIgnoreCase(format)
					   ||"bmp".equalsIgnoreCase(format)||"png".equalsIgnoreCase(format)){
						WidgetUtils.showWebDialog(FileBrowseDialog.this.context, "询问", "确定选择此图片吗？",new DialogAsk(){
							@Override
							public boolean confirm() {
								if(listener != null){
									listener.onResultBack((String) mData.get(position).get("info"));
									FileBrowseDialog.this.dismiss();
								}
								return false;
							}
							@Override
							public boolean cancel() {
								return false;
							}}, R.drawable.ic_launcher);
					}else{
						MessageUtils.showToast(FileBrowseDialog.this.context, "选择的图片文件格式错误！");
						return;
					}
				}
			}
		});
		comm_title_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		String path = this.sourcePath == null?sdDir:this.sourcePath;
		File f = new File(path);
		File[] files = f.listFiles();
		if (!path.equals(rootDir)) {
			map = new HashMap<String, Object>();
			map.put("title", "Back to ../");
			map.put("info", f.getParent());
			map.put("img", R.drawable.ex_folder);
			list.add(map);
		}
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				map = new HashMap<String, Object>();
				map.put("title", files[i].getName());
				map.put("info", files[i].getPath());
				if (files[i].isDirectory())
					map.put("img", R.drawable.ex_folder);
				else
					map.put("img", R.drawable.ex_doc);
				list.add(map);
			}
		}
		return list;
	}
	
	public class FileBrowseAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public FileBrowseAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.filebrowse_lv, null);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.info = (TextView) convertView.findViewById(R.id.info);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.img.setBackgroundResource((Integer) mData.get(position).get(
					"img"));
			holder.title.setText((String) mData.get(position).get("title"));
			holder.info.setText((String) mData.get(position).get("info"));
			return convertView;
		}

		public class ViewHolder {
			public ImageView img;
			public TextView title;
			public TextView info;
		}
		
	}
	
	public void setTitle(String title){
		comm_title_tv.setText(title);
	}
}  
