package itlwy.com.o2omall.home.presenter;

import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.home.contract.ShopCarContract;

/**
 * Created by mac on 16/10/3.
 */

public class ShopCarPresenter extends BasePresenter implements ShopCarContract.IShopCarPresenter {
    private ShopCarContract.IShopCarView view;

    public ShopCarPresenter(ShopCarContract.IShopCarView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe(Object param) {
        view.showLoadingView();
        view.bindViewDatas(BaseApplication.getProductModelShopcar());
        view.showSuccessView();
    }

    public static ShopCarPresenter newInstance(ShopCarContract.IShopCarView view) {
        return new ShopCarPresenter(view);
    }

}
