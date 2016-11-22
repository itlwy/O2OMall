package itlwy.com.o2omall.product.presenter;

import android.support.v4.app.Fragment;

import java.util.List;

import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.data.ProgressSubscriber;
import itlwy.com.o2omall.data.product.ProductRepository;
import itlwy.com.o2omall.data.product.model.CategoryTwoModel;
import itlwy.com.o2omall.data.product.model.ProductModel;
import itlwy.com.o2omall.product.contract.ProductListContract;

/**
 * Created by mac on 16/10/3.
 */

public class ProductListPresenter extends BasePresenter implements ProductListContract.IProductListPresenter {
    private ProductRepository repository;
    private ProductListContract.IProductListView view;

    public ProductListPresenter(ProductListContract.IProductListView view, ProductRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    public static ProductListPresenter newInstance(ProductListContract.IProductListView view, ProductRepository repository) {
        return new ProductListPresenter(view, repository);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void subscribe(CategoryTwoModel categoryTwoModel) {
        ProgressSubscriber<List<ProductModel>> subscriber =
                new ProgressSubscriber<List<ProductModel>>(((Fragment) view).getActivity()) {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.showErrorView();
                    }

                    @Override
                    public void onNext(List<ProductModel> productModels) {
                        view.bindViewDatas(productModels);
                        view.showSuccessView();
                    }
                };

        repository.getProductList(subscriber, categoryTwoModel.getId());
        addSubscriber(subscriber);
    }
}
