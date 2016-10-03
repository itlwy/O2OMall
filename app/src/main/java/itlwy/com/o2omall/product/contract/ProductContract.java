package itlwy.com.o2omall.product.contract;

import itlwy.com.o2omall.base.api.IBasePresenter;
import itlwy.com.o2omall.base.api.IBaseView;
import itlwy.com.o2omall.bean.Product;

/**
 * Created by mac on 16/10/3.
 */

public class ProductContract {
    public interface IProductPresenter extends IBasePresenter{
        void subscribe(Product product);
    }

    public interface IProductView extends IBaseView<IProductPresenter>{
        void bindViewDatas(Product result);
    }
}
