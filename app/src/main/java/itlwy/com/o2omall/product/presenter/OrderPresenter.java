package itlwy.com.o2omall.product.presenter;

import android.support.v4.app.Fragment;

import com.lndroid.lndroidlib.base.BasePresenter;
import com.lndroid.lndroidlib.data.ProgressSubscriber;

import java.util.List;

import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.product.ProductRepository;
import itlwy.com.o2omall.data.product.model.OrdersModel;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.data.user.model.AddressModel;
import itlwy.com.o2omall.product.contract.OrderContract;

public class OrderPresenter extends BasePresenter implements OrderContract.IOrderPresenter {
    private ProductRepository repository;
    private OrderContract.IOrderView view;
    private UserRepository userRepository;
    private List<AddressModel> mOwnAddressList;

    public OrderPresenter(OrderContract.IOrderView view, ProductRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe(AddressModel addressModel) {
        if (addressModel != null) {
            view.bindViewDatas(null, addressModel);
            return;
        }
        ProgressSubscriber<List<AddressModel>> subscriber = new ProgressSubscriber<List<AddressModel>>
                (((Fragment) view).getActivity()) {
            @Override
            public void onNext(List<AddressModel> addressModels) {
                mOwnAddressList = addressModels;
                AddressModel needModel = null;
                for (AddressModel item : addressModels) {
                    if (item.getIsDefault() == 1) {
                        needModel = item;
                        break;
                    }
                }
                view.showSuccessView();
                view.bindViewDatas(BaseApplication.getProductModelShopcar(), needModel);
            }
        };
        addSubscriber(subscriber);
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        userRepository.getOwnAddressList(subscriber, ClientKernal.getInstance().getUserModel().getUserID());
    }

    public static OrderPresenter newInstance(OrderContract.IOrderView view, ProductRepository repository) {
        return new OrderPresenter(view, repository);
    }

    @Override
    public void submitOrder(OrdersModel orderModel) {
        ProgressSubscriber<String> subscriber = new ProgressSubscriber<String>(((Fragment) view).getActivity()) {
            @Override
            public void onNext(String s) {
                view.showToast("提交订单成功！");
                BaseApplication.getProductModelShopcar().clear();
                view.killMyself();
            }
        };
        repository.submitOrder(subscriber, orderModel);
        addSubscriber(subscriber);
    }
}
