package itlwy.com.o2omall.user.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lndroid.lndroidlib.base.BaseMVPFragment;
import com.lndroid.lndroidlib.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.data.user.model.AddressModel;

public class AddressEditFragment extends BaseMVPFragment implements AddressContract.IAddressEditView {

    @Bind(R.id.add_receiver_edit)
    EditText mAddReceiverEdit;
    @Bind(R.id.add_phone_edit)
    EditText mAddPhoneEdit;
    @Bind(R.id.add_reg_edit)
    EditText mAddRegEdit;
    @Bind(R.id.add_address_details_edit)
    EditText mAddAddressDetailsEdit;
    private AddressContract.IAddressEditPresenter presenter;
    private AddressModel addressModel;

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe(addressModel);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void inits(Bundle savedInstanceState) {
        addressModel = getArguments().getParcelable("addressModel");
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

    @Override
    public void bindViewDatas(AddressModel results) {
        mAddPhoneEdit.setText(results.getPhone());
        mAddAddressDetailsEdit.setText(results.getDetailsAddress());
        mAddReceiverEdit.setText(results.getReceiver());
        mAddRegEdit.setText(results.getReg());
    }

    @Override
    public void setPresenter(AddressContract.IAddressEditPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.add_save_btn)
    public void onClick() {
        addressModel.setDetailsAddress(mAddAddressDetailsEdit.getText().toString());
        addressModel.setPhone(mAddPhoneEdit.getText().toString());
        addressModel.setReceiver(mAddReceiverEdit.getText().toString());
        addressModel.setReg(mAddRegEdit.getText().toString());
        presenter.updateAddress(addressModel);
    }
}
