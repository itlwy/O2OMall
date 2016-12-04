package itlwy.com.o2omall.user.address;

import android.support.v4.app.Fragment;

import com.lndroid.lndroidlib.base.BasePresenter;
import com.lndroid.lndroidlib.data.ProgressSubscriber;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.data.user.model.AddressModel;

public class AddressAddPresenter extends BasePresenter implements AddressContract.IAddressAddPresenter {
    private UserRepository repository;
    private AddressContract.IAddressAddView view;

    public AddressAddPresenter(AddressContract.IAddressAddView view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe(Object param) {
        view.showSuccessView();
    }

    public static AddressAddPresenter newInstance(AddressContract.IAddressAddView view, UserRepository repository) {
        return new AddressAddPresenter(view, repository);
    }

    @Override
    public void saveAddress(AddressModel addressModel) {
        ProgressSubscriber<String> subscriber = new ProgressSubscriber<String>(((Fragment) view).getActivity()) {
            @Override
            public void onNext(String s) {
                view.showToast("新增成功！");
                view.killMyself();
            }
        };
        repository.addAddress(subscriber, addressModel);
        addSubscriber(subscriber);
    }
}
