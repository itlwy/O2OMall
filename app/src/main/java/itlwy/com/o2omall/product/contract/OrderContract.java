package itlwy.com.o2omall.product.contract;

import java.util.List;

import com.lndroid.lndroidlib.base.api.IBasePresenter;
import com.lndroid.lndroidlib.base.api.IBaseView;
import itlwy.com.o2omall.data.product.model.OrdersModel;
import itlwy.com.o2omall.data.product.model.ProductModel;
import itlwy.com.o2omall.data.user.model.AddressModel;

public class OrderContract {
    public interface IOrderPresenter extends IBasePresenter<AddressModel> {
        void submitOrder(OrdersModel orderModel);
    }

    public interface IOrderView extends IBaseView<IOrderPresenter> {
        void bindViewDatas(List<ProductModel> results, AddressModel addressModel);
    }
}