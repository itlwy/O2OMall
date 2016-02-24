package support.customui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itlwy.support.R;
  
public class CProgressDialog extends Dialog {  
    private Context context = null;  
    private static CProgressDialog customProgressDialog = null;  
    private boolean flag =false;  //加载框:false 选择框:true
    public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public CProgressDialog(Context context){  
        super(context);  
        this.context = context;
    }  
      
    public CProgressDialog(Context context, int theme) {  
        super(context, theme);  
    }  
      
    public static CProgressDialog createWaitDialog(Context context){  
        customProgressDialog = new CProgressDialog(context,R.style.CustomProgressDialog);  
        customProgressDialog.setContentView(R.layout.customprogressdialog);  
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;  
        return customProgressDialog;  
    }  
    
    public static CProgressDialog createAskNoDialog(Context context,View view){  
        customProgressDialog = new CProgressDialog(context,R.style.CustomProgressDialog);  
        customProgressDialog.setContentView(view);  
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;  
        customProgressDialog.setFlag(true);
        return customProgressDialog;  
    } 
   
    public void onWindowFocusChanged(boolean hasFocus){  
        if (customProgressDialog == null ){  
            return;  
        }  
        if(customProgressDialog.isFlag()){
        	return;
        }
        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);  
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();  
        animationDrawable.start();  
    }  
   
    /** 
     *       setTitile 设置标题
     * @param strTitle 
     * @return 
     * 
     */  
    public CProgressDialog setTitile(String strTitle){  
    	TextView tvMsg = (TextView)findViewById(R.id.message_title_tv);  
        if (tvMsg != null){  
            tvMsg.setText(strTitle);  
        }  
        return customProgressDialog;  
    }  
    /** 
     *       setMessage 设置提示信息
     * @param strMessage 
     * @return 
     * 
     */  
    public CProgressDialog setMessage(String strMessage){  
        TextView tvMsg = (TextView)findViewById(R.id.id_tv_loadingmsg);  
        if (tvMsg != null){  
            tvMsg.setText(strMessage);  
        }  
        return customProgressDialog;  
    }  
}  
