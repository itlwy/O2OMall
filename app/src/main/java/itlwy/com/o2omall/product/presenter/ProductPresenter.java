package itlwy.com.o2omall.product.presenter;

import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.bean.Product;
import itlwy.com.o2omall.product.contract.ProductContract;

/**
 * Created by mac on 16/10/3.
 */

public class ProductPresenter extends BasePresenter implements ProductContract.IProductPresenter {
    private ProductContract.IProductView view;

    public ProductPresenter(ProductContract.IProductView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    public static ProductPresenter newInstance(ProductContract.IProductView view) {
        return new ProductPresenter(view);
    }

    @Override
    public void subscribe(Product product) {
        view.showLoadingView();
        view.bindViewDatas(product);
        view.showSuccessView();
    }
}
