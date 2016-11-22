package itlwy.com.o2omall.home.presenter;

import android.support.v4.app.Fragment;

import java.util.List;

import itlwy.com.o2omall.adapter.GroupRCAdapter;
import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.data.ProgressSubscriber;
import itlwy.com.o2omall.data.product.ProductRepository;
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
    public void subscribe() {
        load();
    }

//    private List<SectionModel> downloadDatas() {
//        List<SectionModel> list = new ArrayList<SectionModel>();
//        for (int i = 0; i < 5; i++) {
//            SectionModel sec = new SectionModel();
//            List<SectionModel.Item> items = new ArrayList<SectionModel.Item>();
//            sec.setTitle(String.format("组%d", i));
//            for (int k = 0; k < 5; k++) {
//                SectionModel.Item item = sec.new Item();
//                item.setDesc(String.format("描述:%d", k));
//                item.setPic("http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg");
//                item.setPrice(200);
//                items.add(item);
//            }
//            sec.setItems(items);
//            list.add(sec);
//        }
//        return list;
//    }

    private void load() {
        pageNum = 1;
        view.showLoadingView();
        Subscriber<List<ProductModel>> subscriber = new ProgressSubscriber<List<ProductModel>>(((Fragment) view).getActivity()) {
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
