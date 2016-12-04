package itlwy.com.o2omall.product.contract;

import java.util.List;

import com.lndroid.lndroidlib.base.api.IBasePresenter;
import com.lndroid.lndroidlib.base.api.IBaseView;
import itlwy.com.o2omall.data.product.model.ProductModel;

/**
 * Created by mac on 16/10/3.
 */

public class ProductContract {
    public interface IProductPresenter extends IBasePresenter<ProductModel>{
        void add2ShopCar();
    }

    public interface IProductView extends IBaseView<IProductPresenter>{
        void bindViewDatas(List<ProductModel.ProductAtt> results);
    }
}
