package itlwy.com.o2omall.home.contract;

import java.util.List;

import itlwy.com.o2omall.base.api.IBasePresenter;
import itlwy.com.o2omall.base.api.IBaseView;
import itlwy.com.o2omall.bean.Product;

/**
 * Created by mac on 16/10/3.
 */

public class ShopCarContract {
    public interface IShopCarPresenter extends IBasePresenter {

    }

    public interface IShopCarView extends IBaseView<IShopCarPresenter> {
        void bindViewDatas(List<Product> result);
    }
}
