package itlwy.com.o2omall.product.contract;

import java.util.List;

import itlwy.com.o2omall.base.api.IBasePresenter;
import itlwy.com.o2omall.base.api.IBaseView;
import itlwy.com.o2omall.data.product.model.ProductModel;

/**
 * Created by mac on 16/10/3.
 */

public class ProductContract {
    public interface IProductPresenter extends IBasePresenter{
        void subscribe(ProductModel productModel);
        void add2ShopCar();
    }

    public interface IProductView extends IBaseView<IProductPresenter>{
        void bindViewDatas(List<ProductModel.ProductAtt> results);
    }
}
