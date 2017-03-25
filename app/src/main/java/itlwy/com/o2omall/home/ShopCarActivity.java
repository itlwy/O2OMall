package itlwy.com.o2omall.home;

import android.os.Bundle;
import android.widget.Toast;

import com.lndroid.lndroidlib.base.BaseMVPActivity;
import com.lndroid.lndroidlib.factory.FragmentFactory;
import com.lndroid.lndroidlib.utils.UIManager;

import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.home.fragment.ShopCarFragment;
import itlwy.com.o2omall.home.presenter.ShopCarPresenter;

public class ShopCarActivity extends BaseMVPActivity {


    private ShopCarPresenter mShopCarPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        ShopCarFragment lShopCarFragment =
                (ShopCarFragment) getSupportFragmentManager().findFragmentById(getFragmentContain());
        try {
            if (lShopCarFragment == null) {
                // Create the fragment
                lShopCarFragment = (ShopCarFragment) FragmentFactory.
                        createFragment(ConstantValue.SHOPCARFRAGMENTALONE, true);
            }
            // Create the presenter
            mShopCarPresenter = ShopCarPresenter.newInstance(lShopCarFragment);
            UIManager.getInstance().changeFragment(this, getFragmentContain(), lShopCarFragment, false, null);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}