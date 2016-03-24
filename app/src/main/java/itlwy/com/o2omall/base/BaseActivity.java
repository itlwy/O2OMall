package itlwy.com.o2omall.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

import itlwy.com.o2omall.protocal.BackHandledInterface;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2015/12/22.
 */
public abstract class BaseActivity extends AppCompatActivity implements BackHandledInterface {

    // 管理运行的所有的activity
    public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

    public static BaseActivity activity;

    private BaseFragment mBackHandedFragment;
    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onResume() {
        super.onResume();
        activity=this;
    }
    @Override
    protected void onPause() {
        super.onPause();
        activity=null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        synchronized (mActivities) {
            mActivities.add(this);
        }
        init();
        initView();
        initActionBar();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    public void killAll() {
        // 复制了一份mActivities 集合
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }
    @Override
    public void onBackPressed() {
        if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){
            if(getSupportFragmentManager().getBackStackEntryCount() <= 1){
//                super.onBackPressed();
                finish();
            }else{
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    /**
     * 增加订阅事件至集合中
     * @param s
     */
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    public abstract void init();
    public abstract void initView();
    public abstract void initActionBar();
}
