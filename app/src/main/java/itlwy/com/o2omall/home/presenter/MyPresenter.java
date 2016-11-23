package itlwy.com.o2omall.home.presenter;

import android.support.v4.app.Fragment;

import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.HttpResultFunc;
import itlwy.com.o2omall.data.GsonResultFunc;
import itlwy.com.o2omall.data.ProgressSubscriber;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.home.contract.MyContract;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mac on 16/10/3.
 */

public class MyPresenter extends BasePresenter implements MyContract.IMyPresenter {

    private UserRepository repository;
    private MyContract.IMyView view;

    public MyPresenter(MyContract.IMyView view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        view.bindViewDatas("");
        view.showSuccessView();
    }

    public static MyPresenter newInstance(MyContract.IMyView view, UserRepository repository) {
        return new MyPresenter(view, repository);
    }

    @Override
    public void uploadMyLogo(String imageName) {
        ProgressSubscriber subscriber = new ProgressSubscriber<String>(((Fragment) view).getActivity()) {
            @Override
            public void onNext(String url) {
                ClientKernal.getInstance().getUserModel().setLogo(url);
                view.refreshMyLogo(url);
            }
        };
        repository.getUploadMyLogoOB(imageName)
                .map(new GsonResultFunc<String>())
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        addSubscriber(subscriber);
    }
}
