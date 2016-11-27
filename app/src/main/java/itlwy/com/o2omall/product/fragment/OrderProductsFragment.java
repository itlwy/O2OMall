package itlwy.com.o2omall.product.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.adapter.ProductListAdapter;
import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.base.BaseMVPFragment;
import itlwy.com.o2omall.product.OrderActivity;
import itlwy.com.o2omall.view.LoadingPage;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderProductsFragment extends BaseMVPFragment {


    @Bind(R.id.order_products_rcv)
    RecyclerView mOrderProductsRcv;
    private ProductListAdapter adapter;

    @Override
    protected void inits() {

    }

    @Override
    protected View createSuccessView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_products, null);
        ButterKnife.bind(this, view);
        adapter = new ProductListAdapter(getActivity(), BaseApplication.getProductModelShopcar());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mOrderProductsRcv.setAdapter(adapter);
        mOrderProductsRcv.setLayoutManager(manager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        showSuccessView();
    }

    @Override
    public void onStop() {
        ((OrderActivity) getActivity()).setFromProductDetailsBack(true);
        super.onStop();
    }

    @Override
    protected String getFragmentKey() {
        return null;
    }

    @Override
    protected LoadingPage.ReLoadListener getReloadListener() {
        return null;
    }

}
