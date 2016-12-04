package itlwy.com.o2omall.home.contract;

import java.util.List;

import com.lndroid.lndroidlib.base.api.IBasePresenter;
import com.lndroid.lndroidlib.base.api.IBaseView;
import itlwy.com.o2omall.data.product.model.ProductModel;

/**
 * Created by mac on 16/10/3.
 */

public class ShopCarContract {
    public interface IShopCarPresenter extends IBasePresenter {

    }

    public interface IShopCarView extends IBaseView<IShopCarPresenter> {
        void bindViewDatas(List<ProductModel> result);
    }
}
