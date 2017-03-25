package itlwy.com.o2omall.product;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.lndroid.lndroidlib.base.BaseMVPActivity;
import com.lndroid.lndroidlib.factory.FragmentFactory;
import com.lndroid.lndroidlib.utils.UIManager;

import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.data.product.ProductRepository;
import itlwy.com.o2omall.data.product.model.CategoryTwoModel;
import itlwy.com.o2omall.product.fragment.ProductListFragment;
import itlwy.com.o2omall.product.presenter.ProductListPresenter;

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
        ProductListFragment productListFragment = null;
        try {
            productListFragment = (ProductListFragment) FragmentFactory.
                    createFragment(ConstantValue.PRODUCTLISTFRAGMENT, true);
            ProductRepository repository = new ProductRepository();
            ProductListPresenter.newInstance(productListFragment, repository);
            UIManager.getInstance().changeFragment(this, getFragmentContain(), productListFragment, false, bundle);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
