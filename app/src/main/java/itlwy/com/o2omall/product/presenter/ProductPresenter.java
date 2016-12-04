package itlwy.com.o2omall.product.presenter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.lndroid.lndroidlib.base.BasePresenter;
import com.lndroid.lndroidlib.data.HttpException;
import com.lndroid.lndroidlib.data.ProgressSubscriber;

import java.util.List;

import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.product.ProductRepository;
import itlwy.com.o2omall.data.product.model.ProductModel;
import itlwy.com.o2omall.product.contract.ProductContract;
import itlwy.com.o2omall.user.login.LoginActivity;

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
                new ProgressSubscriber<List<ProductModel.ProductAtt>>(((Fragment) view).getActivity(), view) {
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            if (((HttpException) e).getResultCode() == HttpException.NO_DATA) {
                                view.showToast("此商品无对应图片列表");
                                view.showSuccessView();
                            }
                        } else {
                            Toast.makeText(((Fragment) view).getActivity()
                                    , "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            view.showErrorView();
                        }
                        dismissProgressDialog();
                    }

                    @Override
                    public void onNext(List<ProductModel.ProductAtt> productAtts) {
                        view.bindViewDatas(productAtts);
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
