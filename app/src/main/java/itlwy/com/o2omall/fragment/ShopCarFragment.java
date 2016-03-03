package itlwy.com.o2omall.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.Activity.LoginActivity;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.GlobalParams;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.adapter.ShopCarAdapter;
import itlwy.com.o2omall.base.BaseFragment;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.bean.Product;
import itlwy.com.o2omall.view.AutoRecyclerView;

/**
 * Created by Administrator on 2016/2/18.
 */
public class ShopCarFragment extends BaseFragment<Void, List<Product>> {
    private ShopCarHolder holder;

    @Override
    public List<Product> load() {
//        return ((BaseApplication)getActivity().getApplication()).getProductShopcar();
        return getCartProducts();
    }

    @Override
    public View createSuccessView() {
        holder =new ShopCarHolder(getActivity());
        return holder.getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.SHOPCARFRAGMENT;
    }

    @Override
    protected void bindViewDatas(List<Product> result) {
        holder.setData(result);
    }

    /**
     * 从服务器获取购物车商品数据
     */
    private List<Product> getCartProducts() {
        /**
         * 刷新数量和选中标记集合
         */
        ArrayList<Product> products = new ArrayList<Product>();
        for (int i = 0; i < 5; i++) {
            Product data = new Product();
            data.setId(i);
            data.setImgUrl("http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg");
            data.setInfo("上岛咖啡上岛咖啡上岛咖啡上岛咖啡上岛咖啡上岛咖啡");
            data.setPrice(120);
            products.add(data);
        }
        return products;
    }

    public class ShopCarHolder extends BaseHolder<List<Product>, Void> {

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
        private List<Product> products;

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
                    // 跳转登录界面
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    // ////////////////////////////
                    // //////////模拟登录///////////
                    GlobalParams.isLogin = true;
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
        public void refreshView(final List<Product> products) {
            if (GlobalParams.isLogin) {
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
            this.products = products;
            if (adapter == null) {
                adapter = new ShopCarAdapter(getContext(), products);
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
            if (products.size() == 0) {
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
