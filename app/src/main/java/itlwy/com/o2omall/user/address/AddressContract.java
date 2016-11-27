package itlwy.com.o2omall.user.address;

import java.util.List;

import itlwy.com.o2omall.base.api.IBasePresenter;
import itlwy.com.o2omall.base.api.IBaseView;
import itlwy.com.o2omall.data.user.model.AddressModel;

public class AddressContract {
    public interface IAddressManagerPresenter extends IBasePresenter {
        void deleteAddress(int addressID);

        void updateDefaultAddress(AddressModel cancelModel, AddressModel addressModel);
    }

    public interface IAddressManagerView extends IBaseView<IAddressManagerPresenter> {
        void bindViewDatas(List<AddressModel> results);

        void refreshView();
    }

    public interface IAddressAddView extends IBaseView<IAddressAddPresenter> {

    }

    public interface IAddressAddPresenter extends IBasePresenter {
        void saveAddress(AddressModel addressModel);
    }

    public interface IAddressEditPresenter extends IBasePresenter<AddressModel> {
        void subscribe(AddressModel addressModel);

        void updateAddress(AddressModel addressModel);
    }

    public interface IAddressEditView extends IBaseView<IAddressEditPresenter> {
        void bindViewDatas(AddressModel results);

    }
}