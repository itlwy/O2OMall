package itlwy.com.o2omall.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import itlwy.com.o2omall.base.BaseActivity;
import itlwy.com.o2omall.base.BaseFragment;


/**
 * Created by Administrator on 2015/12/22.
 */
public class UIManager {
    private static UIManager instance = new UIManager();

    public static UIManager getInstance(){
        return instance;
    }
    private UIManager() {
    }

    /**
     * 切换界面
     */
    public void changeFragment(BaseActivity a,int resId,BaseFragment target,boolean isAddStack,
                               Bundle bundle){
        if (target == null){
//            throw new IllegalArgumentException();
            Toast.makeText(a, "工厂创建的fragment为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(bundle != null){
            target.setArguments(bundle);
        }
        FragmentManager manager = a.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // 第一参数：中间容器的id，第二个参数：添加的Fragment
        transaction.replace(resId, target);

        // 返回键操作
        if(isAddStack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
//        target.show(null);
    }
    public void changeActivity(Activity activity,Class clazz,Bundle bundle){
        Intent intent = new Intent(activity,clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
