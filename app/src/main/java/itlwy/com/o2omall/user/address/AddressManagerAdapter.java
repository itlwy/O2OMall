package itlwy.com.o2omall.user.address;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lndroid.lndroidlib.adapter.BaseRCAdapter;
import com.lndroid.lndroidlib.base.BaseMVPActivity;
import com.lndroid.lndroidlib.base.BaseMVPFragment;
import com.lndroid.lndroidlib.base.BaseRCHolder;
import com.lndroid.lndroidlib.factory.FragmentFactory;
import com.lndroid.lndroidlib.utils.UIManager;
import com.lndroid.lndroidlib.view.TextDrawable;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.data.user.model.AddressModel;

/**
 * Created by mac on 16/11/24.
 */

public class AddressManagerAdapter extends BaseRCAdapter<AddressModel> {
    private AddressContract.IAddressManagerPresenter mPresenter;

    public AddressManagerAdapter(Context context, List<AddressModel> datas, AddressContract.IAddressManagerPresenter presenter) {
        super(context, datas);
        mPresenter = presenter;
    }

    @Override
    protected BaseRCHolder getItemViewHolder(ViewGroup parent) {
        View view = View.inflate(getContext(), R.layout.item_address_manager, null);
        return new AddressManager_ItemHolder(view);
    }

    @Override
    protected BaseRCHolder getHeadViewHolder(ViewGroup parent) {
        return null;
    }

    public class AddressManager_ItemHolder extends BaseRCHolder<AddressModel> {
        @Bind(R.id.manager_name_tv)
        TextView mManagerNameTv;
        @Bind(R.id.manager_phone_tv)
        TextView mManagerPhoneTv;
        @Bind(R.id.manager_address_tv)
        TextView mManagerAddressTv;
        @Bind(R.id.manager_default_tv)
        TextDrawable mManager_default_tv;
        private AddressModel addressModel;

        public AddressManager_ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindDatas(AddressModel data) {
            addressModel = data;
            mManagerNameTv.setText(data.getReceiver());
            mManagerPhoneTv.setText(data.getPhone());
            mManagerAddressTv.setText(data.getDetailsAddress());
            if (data.getIsDefault() == 1) {
                mManager_default_tv.setDrawableLeft(R.drawable.check);
            } else {
                mManager_default_tv.setDrawableLeft(R.drawable.abc_btn_check_to_on_mtrl_000);
            }
        }

        @OnClick({R.id.manager_delete_tv, R.id.manager_edit_tv, R.id.manager_default_tv})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.manager_delete_tv:
                    mPresenter.deleteAddress(addressModel.getAddressID());
                    break;
                case R.id.manager_edit_tv:
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("addressModel", addressModel);
                        BaseMVPFragment fragment = FragmentFactory.createFragment(ConstantValue.ADDRESSEDITFRAGMENT, true);
                        AddressEditPresenter.newInstance((AddressContract.IAddressEditView) fragment, new UserRepository());
                        UIManager.getInstance().changeFragment((FragmentActivity) getContext(), ((BaseMVPActivity) getContext()).getFragmentContain()
                                , fragment, true, bundle);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }

                    break;
                case R.id.manager_default_tv:
                    if (addressModel.getIsDefault() == 1) {
                        return;
                    }
                    AddressModel cancelModel = null;
                    for (AddressModel item : getmDatas()) {
                        if (item.getIsDefault() == 1) {
                            item.setIsDefault(0);
                            cancelModel = item;
                            break;
                        }
                    }
                    addressModel.setIsDefault(1);
                    mPresenter.updateDefaultAddress(cancelModel,addressModel);
                    break;
            }
        }
    }
}
