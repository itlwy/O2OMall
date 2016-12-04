package itlwy.com.o2omall.user.address;

import android.support.v4.app.Fragment;

import com.lndroid.lndroidlib.base.BasePresenter;
import com.lndroid.lndroidlib.data.ProgressSubscriber;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.data.user.model.AddressModel;

public class AddressEditPresenter extends BasePresenter implements AddressContract.IAddressEditPresenter {
    private UserRepository repository;
    private AddressContract.IAddressEditView view;

    public AddressEditPresenter(AddressContract.IAddressEditView view, UserRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    public static AddressEditPresenter newInstance(AddressContract.IAddressEditView view, UserRepository repository) {
        return new AddressEditPresenter(view, repository);
    }

    @Override
    public void subscribe(AddressModel addressModel) {
        view.showSuccessView();
        view.bindViewDatas(addressModel);
    }

    @Override
    public void updateAddress(AddressModel addressModel) {
        ProgressSubscriber<String> subscriber = new ProgressSubscriber<String>(((Fragment) view).getActivity()) {
            @Override
            public void onNext(String result) {
                view.showToast("更新成功！");
                view.killMyself();
            }
        };
        repository.updateAddress(subscriber, addressModel);
        addSubscriber(subscriber);
    }

}
