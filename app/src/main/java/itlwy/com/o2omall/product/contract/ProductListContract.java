package itlwy.com.o2omall.product.contract;

import java.util.List;

import itlwy.com.o2omall.base.api.IBasePresenter;
import itlwy.com.o2omall.base.api.IBaseView;
import itlwy.com.o2omall.bean.CategoryTwo;
import itlwy.com.o2omall.bean.Product;

/**
 * Created by mac on 16/10/3.
 */

public class ProductListContract {
    public interface IProductListPresenter extends IBasePresenter {
        void subscribe(CategoryTwo categoryTwo);
    }

    public interface IProductListView extends IBaseView<IProductListPresenter> {
        void bindViewDatas(List<Product> result);
    }
}
