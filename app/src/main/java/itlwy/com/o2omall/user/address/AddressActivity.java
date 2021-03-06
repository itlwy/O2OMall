package itlwy.com.o2omall.user.address;

import android.os.Bundle;
import android.widget.Toast;

import com.lndroid.lndroidlib.base.BaseMVPActivity;
import com.lndroid.lndroidlib.factory.FragmentFactory;
import com.lndroid.lndroidlib.utils.UIManager;

import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.data.user.UserRepository;

public class AddressActivity extends BaseMVPActivity {


    private AddressManagerPresenter mAddressManagerPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        AddressManagerFragment lAddressManagerFragment =
                (AddressManagerFragment) getSupportFragmentManager().findFragmentById(getFragmentContain());
        try {
            if (lAddressManagerFragment == null) {
                // Create the fragment
                lAddressManagerFragment = (AddressManagerFragment) FragmentFactory.
                        createFragment(ConstantValue.ADDRESSMANAGERFRAGMENT,true);
            }
            UserRepository repository = new UserRepository();
            // Create the presenter
            mAddressManagerPresenter = AddressManagerPresenter.newInstance(lAddressManagerFragment,repository);
            UIManager.getInstance().changeFragment(this, getFragmentContain(), lAddressManagerFragment, false, null);
            setTitle("地址管理");
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}