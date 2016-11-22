package itlwy.com.o2omall.product.contract;

import java.util.List;

import itlwy.com.o2omall.base.api.IBasePresenter;
import itlwy.com.o2omall.base.api.IBaseView;
import itlwy.com.o2omall.data.product.model.CategoryTwoModel;
import itlwy.com.o2omall.data.product.model.ProductModel;

/**
 * Created by mac on 16/10/3.
 */

public class ProductListContract {
    public interface IProductListPresenter extends IBasePresenter {
        void subscribe(CategoryTwoModel categoryTwoModel);
    }

    public interface IProductListView extends IBaseView<IProductListPresenter> {
        void bindViewDatas(List<ProductModel> result);
    }
}
