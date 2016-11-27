package itlwy.com.o2omall.user.address;

import android.os.Bundle;

import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.base.BaseMVPActivity;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.factory.FragmentFactory;
import itlwy.com.o2omall.utils.UIManager;

public class AddressActivity extends BaseMVPActivity {


    private AddressManagerPresenter mAddressManagerPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        AddressManagerFragment lAddressManagerFragment =
                (AddressManagerFragment) getSupportFragmentManager().findFragmentById(getFragmentContain());
        if (lAddressManagerFragment == null) {
            // Create the fragment
            lAddressManagerFragment = (AddressManagerFragment) FragmentFactory.
                    createFragment(this, ConstantValue.ADDRESSMANAGERFRAGMENT,true);
        }
        UserRepository repository = new UserRepository();
        // Create the presenter
        mAddressManagerPresenter = AddressManagerPresenter.newInstance(lAddressManagerFragment,repository);
        UIManager.getInstance().changeFragment(this, getFragmentContain(), lAddressManagerFragment, false, null);
        setTitle("地址管理");
    }

}