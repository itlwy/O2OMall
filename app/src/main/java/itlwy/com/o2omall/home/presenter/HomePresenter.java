package itlwy.com.o2omall.home.presenter;

import android.support.v4.app.Fragment;

import com.lndroid.lndroidlib.base.BasePresenter;
import com.lndroid.lndroidlib.data.ProgressSubscriber;

import java.util.List;

import itlwy.com.o2omall.adapter.GroupRCAdapter;
import itlwy.com.o2omall.data.product.ProductRepository;
import itlwy.com.o2omall.data.product.model.AdvertModel;
import itlwy.com.o2omall.data.product.model.ProductModel;
import itlwy.com.o2omall.home.contract.HomeContract;
import rx.Subscriber;

/**
 * Created by mac on 16/10/3.
 */

public class HomePresenter extends BasePresenter implements HomeContract.IHomePresenter {
    private ProductRepository repository;
    private HomeContract.IHomeView view;
    private int pageNum = 1;
    private int count = 10;

    public HomePresenter(HomeContract.IHomeView view, ProductRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe(Object param) {
        load();
        loadAdverts();
    }

    private void loadAdverts() {
        Subscriber<List<AdvertModel>> subscriber = new ProgressSubscriber<List<AdvertModel>>(((Fragment) view).getActivity()) {

            @Override
            public void onNext(List<AdvertModel> advertModels) {
                view.bindHeaderDatas(advertModels);
            }
        };
        repository.getAdvertInfo(subscriber);
        addSubscriber(subscriber);
    }

    private void load() {
        pageNum = 1;
//        view.showLoadingView();
        ProgressSubscriber<List<ProductModel>> subscriber = new ProgressSubscriber<List<ProductModel>>(((Fragment) view).getActivity()) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showErrorView();
            }

            @Override
            public void onNext(List<ProductModel> productModels) {
                view.showSuccessView();
                view.bindViewDatas(productModels);
                pageNum++;
            }
        };
        repository.getHomeProducts(subscriber, pageNum, count);
        addSubscriber(subscriber);
    }

    public static HomePresenter newInstance(HomeContract.IHomeView view, ProductRepository repository) {
        return new HomePresenter(view, repository);
    }

    @Override
    public void getMoreDatas() {
        Subscriber<List<ProductModel>> subscriber = new ProgressSubscriber<List<ProductModel>>(((Fragment) view).getActivity()) {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showErrorView();
                view.setLoadMoreFinish(true, null, GroupRCAdapter.STATUS_NOMORE);
            }

            @Override
            public void onNext(List<ProductModel> productModels) {
                view.showSuccessView();
                view.setLoadMoreFinish(true, productModels, GroupRCAdapter.STATUS_SUCCESS);
//                view.bindViewDatas(productModels);
                pageNum++;
            }
        };
        repository.getHomeProducts(subscriber, pageNum, count);
        addSubscriber(subscriber);
//        return downloadDatas();
    }
}
