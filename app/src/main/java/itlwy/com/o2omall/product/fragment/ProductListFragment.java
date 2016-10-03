package itlwy.com.o2omall.product.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.adapter.BaseRCAdapter;
import itlwy.com.o2omall.adapter.ProductListAdapter;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.base.BaseMVPFragment;
import itlwy.com.o2omall.bean.CategoryTwo;
import itlwy.com.o2omall.bean.Product;
import itlwy.com.o2omall.factory.FragmentFactory;
import itlwy.com.o2omall.product.ProductActivity;
import itlwy.com.o2omall.product.contract.ProductListContract;
import itlwy.com.o2omall.product.presenter.ProductPresenter;
import itlwy.com.o2omall.utils.UIManager;
import itlwy.com.o2omall.view.AutoRecyclerView;
import itlwy.com.o2omall.view.LoadingPage;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ProductListFragment extends BaseMVPFragment implements ProductListContract.IProductListView {
    private List<Product> list_datas;
    private ProductListHolder holder;
    private ProductListContract.IProductListPresenter presenter;
    private CategoryTwo categoryTwo;

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe(categoryTwo);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void inits() {
        categoryTwo = (CategoryTwo) getArguments().get(CategoryTwo.Tag);
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

    public void bindViewDatas(List<Product> result) {
        holder.setData(result);
    }


    @Override
    public void setPresenter(ProductListContract.IProductListPresenter presenter) {
        this.presenter = presenter;
    }

    public class ProductListHolder extends BaseHolder<List<Product>, Object> {

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
        public void refreshView(List<Product> products) {
            list_datas = products;
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            adapter = new ProductListAdapter(getContext(), products);
            productListRecycleview.setAdapter(adapter);
            productListRecycleview.setLayoutManager(manager);
            adapter.setOnItemClickListener(new BaseRCAdapter.OnItemClick() {
                @Override
                public void onItemClick(View view, int position) {
//                    ViewUtils.showSnack(getActivity().getCurrentFocus(),"跳转");
                    Product product = list_datas.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Product.Tag, product);
                    ProductActivity ownActivity = (ProductActivity) getActivity();
                    ProductFragment productFragment = (ProductFragment) FragmentFactory.
                            createFragment(getContext(), ConstantValue.PRODUCTFRAGMENT);
                    ProductPresenter.newInstance(productFragment);
                    UIManager.getInstance().changeFragment(ownActivity, ownActivity.getFragmentContain(),
                            productFragment, true, bundle);
                }
            });
        }

    }
}
