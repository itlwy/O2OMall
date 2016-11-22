package itlwy.com.o2omall.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.adapter.ShopCarAdapter;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.base.BaseMVPFragment;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.product.model.ProductModel;
import itlwy.com.o2omall.home.contract.ShopCarContract;
import itlwy.com.o2omall.login.LoginActivity;
import itlwy.com.o2omall.view.AutoRecyclerView;
import itlwy.com.o2omall.view.LoadingPage;

/**
 * Created by Administrator on 2016/2/18.
 */
public class ShopCarFragment extends BaseMVPFragment implements ShopCarContract.IShopCarView {
    private ShopCarHolder holder;
    private ShopCarContract.IShopCarPresenter presenter;

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }


    @Override
    protected View createSuccessView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        holder = new ShopCarHolder(getActivity());
        return holder.getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.SHOPCARFRAGMENT;
    }

    @Override
    protected LoadingPage.ReLoadListener getReloadListener() {
        return null;
    }

    @Override
    protected void inits() {

    }

    @Override
    public void bindViewDatas(List<ProductModel> result) {
        holder.setData(result);
    }


    @Override
    public void setPresenter(ShopCarContract.IShopCarPresenter presenter) {
        this.presenter = presenter;
    }

    public class ShopCarHolder extends BaseHolder<List<ProductModel>, Void> {

        @Bind(R.id.cart_btn_login)
        Button cartBtnLogin;
        @Bind(R.id.cart_suggest_layout)
        RelativeLayout cartSuggestLayout;
        @Bind(R.id.cart_tv_empty)
        TextView cartTvEmpty;
        @Bind(R.id.cart_lv)
        AutoRecyclerView cartLv;
        @Bind(R.id.cart_cb_all)
        CheckBox cartCbAll;
        @Bind(R.id.cart_tv_total)
        TextView cartTvTotal;
        @Bind(R.id.cart_btn_buy)
        Button cartBtnBuy;
        @Bind(R.id.cart_check_layout)
        RelativeLayout cartCheckLayout;
        @Bind(R.id.cart_list_layout)
        LinearLayout cartListLayout;
        private ShopCarAdapter adapter;
        private List<ProductModel> mProductModels;

        public ShopCarHolder(Context ctx) {
            super(ctx);
        }

        @Override
        public View initView() {
            View view = View.inflate(getContext(), R.layout.fragment_cart, null);
            ButterKnife.bind(this, view);
            cartBtnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
            cartBtnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 结算按钮
                    if (cartBtnBuy.getText().toString()
                            .equals(getResources().getString(R.string.buy))) {
                        // 删除按钮
                    } else {

                    }
                }
            });
            // 设置item动画
            cartLv.setItemAnimator(new DefaultItemAnimator());
            cartLv.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            return view;
        }

        @Override
        public void refreshView(final List<ProductModel> productModels) {
            if (ClientKernal.getInstance().getUserModel() != null) {
                // 已登录
                // 隐藏提示登录的布局
                cartSuggestLayout.setVisibility(View.GONE);
            } else {
                // 未登录
                // 隐藏编辑按钮
//                cartBtnEdit.setVisibility(View.GONE);
                // 隐藏购物车列表
                cartListLayout.setVisibility(View.GONE);
                // 隐藏底部付款布局
                cartCheckLayout.setVisibility(View.GONE);
                // 显示提示登录的布局
                cartSuggestLayout.setVisibility(View.VISIBLE);
                // 显示购物车为空的布局
                cartTvEmpty.setVisibility(View.VISIBLE);
                return;
            }
            this.mProductModels = productModels;
            if (adapter == null) {
                adapter = new ShopCarAdapter(getContext(), productModels);
                LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                cartLv.setAdapter(adapter);
                cartLv.setLayoutManager(manager);
            } else
                adapter.notifyDataSetChanged();
            refreshItemView();
            cartCbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    // 全选监听
                    for (int i = 0; i < adapter.getmDatas().size(); i++) {
                        adapter.getmDatas().get(i).setIsCheck(isChecked);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            adapter.setOnPriceChangedListener(new ShopCarAdapter.OnPriceChangedListener() {

                @Override
                public void onPriceChanged(final float price) {
                    cartTvTotal.post(new Runnable() {

                        @Override
                        public void run() {
                            cartTvTotal.setText("合计：$" + price);
                            if (price == 0) {
                                refreshItemView();
                                cartCbAll.setChecked(false);
                            }
                        }
                    });
                }
            });
            adapter.setOnCheckChangedListener(new ShopCarAdapter.OnCheckChangedListener() {
                @Override
                public void onCheckAll(boolean isAllCheck) {
                    cartCbAll.setChecked(isAllCheck);
                }
            });
        }

        /**
         * 刷新购物车列表数据
         */
        private void refreshItemView() {
            if (mProductModels.size() == 0) {
                // 购物车为空
                // 隐藏购物车列表布局
                cartListLayout.setVisibility(View.GONE);
                // 隐藏底部结算布局
                cartCheckLayout.setVisibility(View.GONE);
                // 显示购物车为空的布局
                cartTvEmpty.setVisibility(View.VISIBLE);
            } else {
                // 购物车不为空
                // 隐藏购物车为空的布局
                cartTvEmpty.setVisibility(View.GONE);
                // 显示购物车列表布局
                cartListLayout.setVisibility(View.VISIBLE);
                // 显示底部结算布局
                cartCheckLayout.setVisibility(View.VISIBLE);
                cartTvTotal.setText("合计：$" + adapter.calculatePrice());
            }
        }
    }
}
