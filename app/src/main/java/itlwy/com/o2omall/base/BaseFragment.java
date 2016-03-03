package itlwy.com.o2omall.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import itlwy.com.o2omall.factory.FragmentFactory;
import itlwy.com.o2omall.protocal.BackHandledInterface;
import itlwy.com.o2omall.utils.ViewUtils;
import itlwy.com.o2omall.view.LoadingPage;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2015/12/22.
 */
public abstract class BaseFragment<T,K> extends Fragment {
    private LoadingPage loadingPage;
    private View successView;
    private String fragmentKey;

    private CompositeSubscription mCompositeSubscription;

    protected BackHandledInterface mBackHandledInterface;

    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
    protected boolean onBackPressed(){
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!(getActivity() instanceof BackHandledInterface)){
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        }else{
            this.mBackHandledInterface = (BackHandledInterface)getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (loadingPage == null) {
            loadingPage = new LoadingPage(getActivity());
            loadingPage.setReLoadListener(new LoadingPage.ReLoadListener() {
                @Override
                public void reLoad() {
                    BaseFragment.this.show(null);
                }
            });
        }else{
            ViewUtils.removeParent(loadingPage);// 移除frameLayout之前的爹
       }
        return loadingPage;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        fragmentKey = getFragmentKey();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        //告诉FragmentActivity，当前Fragment在栈顶
        mBackHandledInterface.setSelectedFragment(this);
        show(null);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (!TextUtils.isEmpty(fragmentKey)){
            BaseFragment target = FragmentFactory.get(fragmentKey);
            if (target != null){
                FragmentFactory.remove(fragmentKey);
            }
        }
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    public void show(T params){
        if(loadingPage!=null){
            loadingPage.setState(loadingPage.STATE_LOADING);
            loadingPage.showPage();
        }
        Subscription subscription = Observable.create(new Observable.OnSubscribe<K>() {
            @Override
            public void call(Subscriber<? super K> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    K result = load();
                    //4.发布事件通知订阅者
                    subscriber.onNext(result);
                    //5.事件通知完成
                    subscriber.onCompleted();
                } catch (Exception e) {
                    //6.出现异常，通知订阅者
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<K>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        ViewUtils.showSnack(getActivity().getWindow().getCurrentFocus(),e.toString());
                    }
                    @Override
                    public void onNext(K result) {
                        LoadingPage.LoadResult resultCode = checkData(result);
                        int state = resultCode.getValue();
                        loadingPage.setState(resultCode.getValue());
                        if (state == LoadingPage.STATE_SUCCESS){
                            if (successView == null) {
                                successView = createSuccessView();
                                if (successView == null){
                                    Toast.makeText(getActivity(), "请正确覆写createSuccessView()",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                loadingPage.setSuccessView(successView);
                                loadingPage.addView(successView, new FrameLayout.LayoutParams(
                                        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                            }
//                            ((BaseHolder)successView.getTag()).setData(result);
                            bindViewDatas(result);
                        }
                        loadingPage.showPage();
                    }
                });
        addSubscription(subscription);
    }


    /**
     * 请求服务器
     * @return
     */
    public abstract K load();
    /***
     *  创建成功的界面
     * @return
     */
    public abstract View createSuccessView();
//    /**
//     * 初始化view数据
//     * @param tag
//     */
//    protected abstract void bindViewDatas(BaseHolder tag);
    /**
     * 标志每个fragment的key
     * @return
     */
    protected abstract String getFragmentKey();

    /**
     * 绑定数据
     * @param result
     */
    protected abstract void bindViewDatas(K result);

    /**校验数据 */
    public LoadingPage.LoadResult checkData(K datas) {
        if(datas==null){
            return LoadingPage.LoadResult.error;//  请求服务器失败
        }else{
//            if(datas.size()==0){
//                return LoadingPage.LoadResult.empty;
//            }else{
                return LoadingPage.LoadResult.success;
//            }
        }
    }

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
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

}
