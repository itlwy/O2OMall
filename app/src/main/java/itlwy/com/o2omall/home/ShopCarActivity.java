package itlwy.com.o2omall.home;

import android.os.Bundle;

import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.base.BaseMVPActivity;
import itlwy.com.o2omall.factory.FragmentFactory;
import itlwy.com.o2omall.home.fragment.ShopCarFragment;
import itlwy.com.o2omall.home.presenter.ShopCarPresenter;
import itlwy.com.o2omall.utils.UIManager;

public class ShopCarActivity extends BaseMVPActivity {


    private ShopCarPresenter mShopCarPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        ShopCarFragment lShopCarFragment =
                (ShopCarFragment) getSupportFragmentManager().findFragmentById(getFragmentContain());
        if (lShopCarFragment == null) {
            // Create the fragment
            lShopCarFragment = (ShopCarFragment) FragmentFactory.
                    createFragment(this, ConstantValue.SHOPCARFRAGMENTALONE,true);
        }
        // Create the presenter
        mShopCarPresenter = ShopCarPresenter.newInstance(lShopCarFragment);
        UIManager.getInstance().changeFragment(this, getFragmentContain(), lShopCarFragment, false, null);

    }

}