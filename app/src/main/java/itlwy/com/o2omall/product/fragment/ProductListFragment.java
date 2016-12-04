package itlwy.com.o2omall.product.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lndroid.lndroidlib.adapter.BaseRCAdapter;
import com.lndroid.lndroidlib.base.BaseMVPFragment;
import com.lndroid.lndroidlib.factory.FragmentFactory;
import com.lndroid.lndroidlib.utils.UIManager;
import com.lndroid.lndroidlib.view.AutoRecyclerView;
import com.lndroid.lndroidlib.view.LoadingPage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.adapter.ProductListAdapter;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.data.product.ProductRepository;
import itlwy.com.o2omall.data.product.model.CategoryTwoModel;
import itlwy.com.o2omall.data.product.model.ProductModel;
import itlwy.com.o2omall.product.ProductActivity;
import itlwy.com.o2omall.product.contract.ProductListContract;
import itlwy.com.o2omall.product.presenter.ProductPresenter;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ProductListFragment extends BaseMVPFragment implements ProductListContract.IProductListView {
    private List<ProductModel> list_datas;
    private ProductListHolder holder;
    private ProductListContract.IProductListPresenter presenter;
    private CategoryTwoModel mCategoryTwoModel;

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe(mCategoryTwoModel);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void inits() {
        mCategoryTwoModel = (CategoryTwoModel) getArguments().get(CategoryTwoModel.Tag);
    }

    @Override
    protected View createSuccessView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        holder = new ProductListHolder(getActivity());
        return holder.getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.PRODUCTLISTFRAGMENT;
    }

    @Override
    protected LoadingPage.ReLoadListener getReloadListener() {
        return null;
    }

    public void bindViewDatas(List<ProductModel> result) {
        holder.setData(result);
    }


    @Override
    public void setPresenter(ProductListContract.IProductListPresenter presenter) {
        this.presenter = presenter;
    }

    public class ProductListHolder extends BaseHolder<List<ProductModel>, Object> {

        @Bind(R.id.btn_pd2_total)
        Button btnPd2Total;
        @Bind(R.id.btn_pd2_sales)
        Button btnPd2Sales;
        @Bind(R.id.btn_pd2_price)
        Button btnPd2Price;
        @Bind(R.id.btn_pd2_select)
        Button btnPd2Select;
        @Bind(R.id.product_list_recycleview)
        AutoRecyclerView productListRecycleview;
        private ProductListAdapter adapter;

        public ProductListHolder(Context ctx) {
            super(ctx);
        }

        @Override
        public View initView() {
            View view = View.inflate(getContext(), R.layout.fragment_product_list, null);
            ButterKnife.bind(this, view);
            return view;
        }

        @Override
        public void refreshView(List<ProductModel> productModels) {
            list_datas = productModels;
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            adapter = new ProductListAdapter(getContext(), productModels);
            productListRecycleview.setAdapter(adapter);
            productListRecycleview.setLayoutManager(manager);
            adapter.setOnItemClickListener(new BaseRCAdapter.OnItemClick() {
                @Override
                public void onItemClick(View view, int position) {
//                    ViewUtils.showSnack(getActivity().getCurrentFocus(),"跳转");
                    ProductModel productModel = list_datas.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ProductModel.Tag, productModel);
                    ProductActivity ownActivity = (ProductActivity) getActivity();
                    ProductFragment productFragment = (ProductFragment) FragmentFactory.
                            createFragment(getContext(), ConstantValue.PRODUCTFRAGMENT,true);
                    ProductPresenter.newInstance(productFragment,new ProductRepository());
                    UIManager.getInstance().changeFragment(ownActivity, ownActivity.getFragmentContain(),
                            productFragment, true, bundle);
                }
            });
        }

    }
}
