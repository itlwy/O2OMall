package itlwy.com.o2omall.user.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseMVPFragment;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.user.model.AddressModel;
import itlwy.com.o2omall.view.LoadingPage;

public class AddressAddFragment extends BaseMVPFragment implements AddressContract.IAddressAddView {

    @Bind(R.id.add_receiver_edit)
    EditText mAddReceiverEdit;
    @Bind(R.id.add_phone_edit)
    EditText mAddPhoneEdit;
    @Bind(R.id.add_reg_edit)
    EditText mAddRegEdit;
    @Bind(R.id.add_address_details_edit)
    EditText mAddAddressDetailsEdit;
    private AddressContract.IAddressAddPresenter presenter;

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void inits() {

    }

    @Override
    protected View createSuccessView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_add, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected String getFragmentKey() {
        return "";
    }

    @Override
    protected LoadingPage.ReLoadListener getReloadListener() {
        return null;
    }

    public void bindViewDatas(AddressModel results) {

    }

    @Override
    public void setPresenter(AddressContract.IAddressAddPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.add_save_btn)
    public void onClick() {
        AddressModel addressModel = new AddressModel();
        addressModel.setDetailsAddress(mAddAddressDetailsEdit.getText().toString());
        addressModel.setPhone(mAddPhoneEdit.getText().toString());
        addressModel.setReceiver(mAddReceiverEdit.getText().toString());
        addressModel.setReg(mAddRegEdit.getText().toString());
        addressModel.setUserID(ClientKernal.getInstance().getUserModel().getUserID());
        presenter.saveAddress(addressModel);
    }
}
