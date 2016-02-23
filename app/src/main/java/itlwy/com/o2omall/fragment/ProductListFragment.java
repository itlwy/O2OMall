package itlwy.com.o2omall.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.adapter.BaseRCAdapter;
import itlwy.com.o2omall.adapter.ProductListAdapter;
import itlwy.com.o2omall.base.BaseActivity;
import itlwy.com.o2omall.base.BaseFragment;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.bean.CategoryTwo;
import itlwy.com.o2omall.bean.Product;
import itlwy.com.o2omall.factory.FragmentFactory;
import itlwy.com.o2omall.utils.UIManager;
import itlwy.com.o2omall.view.AutoRecyclerView;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ProductListFragment extends BaseFragment<CategoryTwo, List<Product>> {
    private List<Product> list_datas;

    @Override
    public List<Product> load() {
        CategoryTwo categoryTwo = (CategoryTwo) getArguments().get(CategoryTwo.Tag);
        return getListData();
    }

    @Override
    public View createSuccessView() {
        return new ProductListHolder(getActivity()).getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.PRODUCTLISTFRAGMENT;
    }

    /**
     * 得到url
     */
    private String getUrls() {
        String urlString = "http://i2.sinaimg.cn/IT/h/2009-12-05/1259942752_UQ03Yv.jpg";
        return urlString;
    }

    /**
     * 得到listview数据
     */
    private List<Product> getListData() {
        list_datas = new ArrayList<Product>();
        for (int i = 0; i < 10; i++) {
            Product data = new Product();
            data.setId(i);
            data.setImgUrl(getUrls());
            data.setInfo("樱桃（Cherry） G80-3060HLCUS-2 红轴黑橙二色键帽 60周年限量版机械键盘");
            data.setPrice(1953);
            list_datas.add(data);
        }
        return list_datas;
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
            LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            adapter = new ProductListAdapter(getContext(),products);
            productListRecycleview.setAdapter(adapter);
            productListRecycleview.setLayoutManager(manager);
            adapter.setOnItemClickListener(new BaseRCAdapter.OnItemClick() {
                @Override
                public void onItemClick(View view, int position) {
//                    ViewUtils.showSnack(getActivity().getCurrentFocus(),"跳转");
                    Product product = list_datas.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Product.Tag,product);
                    UIManager.getInstance().changeFragment((BaseActivity) getContext(),R.id.product_contain,
                            FragmentFactory.createFragment(getContext(),ConstantValue.PRODUCTFRAGMENT),true,bundle);
                }
            });
        }

    }
}
