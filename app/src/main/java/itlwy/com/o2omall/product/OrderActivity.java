package itlwy.com.o2omall.product;

import android.os.Bundle;

import com.lndroid.lndroidlib.base.BaseMVPActivity;
import com.lndroid.lndroidlib.factory.FragmentFactory;
import com.lndroid.lndroidlib.utils.UIManager;

import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.data.product.ProductRepository;
import itlwy.com.o2omall.data.user.model.AddressModel;
import itlwy.com.o2omall.product.fragment.OrderFragment;
import itlwy.com.o2omall.product.presenter.OrderPresenter;

public class OrderActivity extends BaseMVPActivity {


    private OrderPresenter mOrderPresenter;
    private AddressModel mAddressModel;
    private boolean fromProductDetailsBack;
    private boolean fromAddressBack;

    @Override
    protected void init(Bundle savedInstanceState) {
        OrderFragment lOrderFragment =
                (OrderFragment) getSupportFragmentManager().findFragmentById(getFragmentContain());
        if (lOrderFragment == null) {
            // Create the fragment
            lOrderFragment = (OrderFragment) FragmentFactory.
                    createFragment(this, ConstantValue.ORDERFRAGMENT,true);
        }
        ProductRepository lProductRepository = new ProductRepository();
        // Create the presenter
        mOrderPresenter = OrderPresenter.newInstance(lOrderFragment, lProductRepository);
        UIManager.getInstance().changeFragment(this, getFragmentContain(), lOrderFragment, false, null);

    }

    public AddressModel getAddressModel() {
        return mAddressModel;
    }

    public void setSelectedAddress(AddressModel addressModel){
        mAddressModel = addressModel;
    }

    public boolean isFromProductDetailsBack() {
        return fromProductDetailsBack;
    }

    public void setFromProductDetailsBack(boolean fromProductDetailsBack) {
        this.fromProductDetailsBack = fromProductDetailsBack;
    }

    public boolean isFromAddressBack() {
        return fromAddressBack;
    }

    public void setFromAddressBack(boolean fromAddressBack) {
        this.fromAddressBack = fromAddressBack;
    }
}