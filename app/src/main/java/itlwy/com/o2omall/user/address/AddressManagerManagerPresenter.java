package itlwy.com.o2omall.user.address;

import android.support.v4.app.Fragment;

import java.util.List;

import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.ProgressSubscriber;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.data.user.model.AddressModel;

public class AddressManagerManagerPresenter extends BasePresenter implements AddressContract.IAddressManagerPresenter {
    private UserRepository repository;
    private AddressContract.IAddressManagerView view;

    public AddressManagerManagerPresenter(AddressContract.IAddressManagerView view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe(Object param) {
        ProgressSubscriber subscriber = new ProgressSubscriber<List<AddressModel>>(((Fragment) view).getActivity()) {
            @Override
            public void onNext(List<AddressModel> addressModels) {
                view.bindViewDatas(addressModels);
                view.showSuccessView();
            }
        };
        addSubscriber(subscriber);
        repository.getOwnAddressList(subscriber, ClientKernal.getInstance().getUserModel().getUserID());
    }

    public static AddressManagerManagerPresenter newInstance(AddressContract.IAddressManagerView view, UserRepository repository) {
        return new AddressManagerManagerPresenter(view, repository);
    }

    @Override
    public void deleteAddress(int addressID) {
        ProgressSubscriber<String> subscriber = new ProgressSubscriber<String>(((Fragment) view).getActivity()) {
            @Override
            public void onNext(String s) {
                view.showToast("删除成功!");
                view.refreshView();
            }
        };
        addSubscriber(subscriber);
        repository.deleteAddress(subscriber,addressID);
    }

    @Override
    public void updateAddress(AddressModel addressModel) {

    }
}
