package itlwy.com.o2omall.product;

import android.os.Bundle;
import android.view.Menu;

import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseMVPActivity;
import itlwy.com.o2omall.data.model.CategoryTwoModel;
import itlwy.com.o2omall.factory.FragmentFactory;
import itlwy.com.o2omall.product.fragment.ProductListFragment;
import itlwy.com.o2omall.product.presenter.ProductListPresenter;
import itlwy.com.o2omall.utils.UIManager;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ProductActivity extends BaseMVPActivity {


    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }


    public void initView() {
        CategoryTwoModel categoryTwoModel = getIntent().getParcelableExtra(CategoryTwoModel.Tag);
        Bundle bundle = new Bundle();
        bundle.putParcelable(CategoryTwoModel.Tag, categoryTwoModel);
        ProductListFragment productListFragment = (ProductListFragment) FragmentFactory.
                createFragment(this, ConstantValue.PRODUCTLISTFRAGMENT);
        ProductListPresenter.newInstance(productListFragment);
        UIManager.getInstance().changeFragment(this, getFragmentContain(), productListFragment, true, bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
