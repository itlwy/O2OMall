package itlwy.com.o2omall.utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import itlwy.com.o2omall.R;

/**
 * Created by Administrator on 2015/12/22.
 */
public class ViewUtils {
    public static void removeParent(View v){
        //  先找到爹 在通过爹去移除孩子
        ViewParent parent = v.getParent();
        //所有的控件 都有爹  爹一般情况下 就是ViewGoup
        if(parent instanceof ViewGroup){
            ViewGroup group=(ViewGroup) parent;
            group.removeView(v);
        }
    }
    public static void showSnack(View view,String content){
        Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.WHITE);
        setSnackbarMessageTextColor(snackbar,Color.WHITE);
        snackbar.show();
    }
    public static void setSnackbarMessageTextColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);
    }
}
