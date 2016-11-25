package itlwy.com.o2omall.product.presenter;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.List;

import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.ProgressSubscriber;
import itlwy.com.o2omall.data.product.ProductRepository;
import itlwy.com.o2omall.data.product.model.ProductModel;
import itlwy.com.o2omall.user.login.LoginActivity;
import itlwy.com.o2omall.product.contract.ProductContract;

/**
 * Created by mac on 16/10/3.
 */

public class ProductPresenter extends BasePresenter implements ProductContract.IProductPresenter {
    private ProductRepository repository;
    private ProductContract.IProductView view;
    private ProductModel mProductModel;

    public ProductPresenter(ProductContract.IProductView view, ProductRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }


    public static ProductPresenter newInstance(ProductContract.IProductView view, ProductRepository repository) {
        return new ProductPresenter(view, repository);
    }

    @Override
    public void subscribe(ProductModel productModel) {
        mProductModel = productModel;
        ProgressSubscriber<List<ProductModel.ProductAtt>> subscriber =
                new ProgressSubscriber<List<ProductModel.ProductAtt>>(((Fragment) view).getActivity()) {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.showErrorView();
                    }

                    @Override
                    public void onNext(List<ProductModel.ProductAtt> productAtts) {
                        view.bindViewDatas(productAtts);
                        view.showSuccessView();
                    }
                };

        repository.getProductAtts(subscriber, productModel.getProductID());
        addSubscriber(subscriber);
    }

    @Override
    public void add2ShopCar() {
        if (ClientKernal.getInstance().getUserModel() == null) {
            view.showToast("请先登录,谢谢!");
            Intent intent = new Intent(((Fragment) view).getActivity(), LoginActivity.class);
            ((Fragment) view).getActivity().startActivity(intent);
            return;
        }
        List<ProductModel> products = BaseApplication.getProductModelShopcar();
        boolean isExist = false;
        for (ProductModel item : products) {
            if (item.getProductID() == mProductModel.getProductID()) {
                item.setNum(item.getNum() + 1);
                isExist = true;
                break;
            }
        }
        if (isExist) {
            BaseApplication.setProductModelShopcar(products);
            view.showToast("添加成功！");
        } else {
            mProductModel.setNum(mProductModel.getNum() + 1);
            products.add(mProductModel);
            BaseApplication.setProductModelShopcar(products);
            view.showToast("添加成功！");
        }
    }
}
