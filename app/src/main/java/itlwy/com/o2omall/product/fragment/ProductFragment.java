package itlwy.com.o2omall.product.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.base.BaseMVPFragment;
import itlwy.com.o2omall.data.product.model.ProductModel;
import itlwy.com.o2omall.home.ShopCarActivity;
import itlwy.com.o2omall.product.contract.ProductContract;
import itlwy.com.o2omall.view.CirclePageIndicator;
import itlwy.com.o2omall.view.LoadingPage;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ProductFragment extends BaseMVPFragment implements ProductContract.IProductView {
    private ProductHolder holder;
    private ProductContract.IProductPresenter presenter;
    private ProductModel mProductModel;

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe(mProductModel);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void inits() {
        mProductModel = getArguments().getParcelable(ProductModel.Tag);
    }

    @Override
    protected View createSuccessView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        holder = new ProductHolder(getActivity());
        return holder.getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.PRODUCTFRAGMENT;
    }

    @Override
    protected LoadingPage.ReLoadListener getReloadListener() {
        return new LoadingPage.ReLoadListener() {
            @Override
            public void reLoad() {
                // TODO: 重新加载处
            }
        };
    }

    public void bindViewDatas(List<ProductModel.ProductAtt> results) {
        holder.setData(results);
    }

    @Override
    public void setPresenter(ProductContract.IProductPresenter presenter) {
        this.presenter = presenter;
    }

    public class ProductHolder extends BaseHolder<List<ProductModel.ProductAtt>, Object> {
        @Bind(R.id.product_viewPager)
        AutoScrollViewPager productViewPager;
        @Bind(R.id.product_indicator)
        CirclePageIndicator productIndicator;
        @Bind(R.id.pd_btn_collect)
        ImageButton pdBtnCollect;
        @Bind(R.id.pd_btn_addIntoCart)
        Button pdBtnAddIntoCart;
        @Bind(R.id.pd_btn_cart)
        ImageButton pdBtnCart;
        private boolean collected;
        private ProductPagerAdapter pagerAdapter;

        /**
         * 广告自动循环切换的时间间隔
         */
        private static final int AUTO_SCROLL_INTERVAL = 3000;

        public ProductHolder(Context ctx) {
            super(ctx);
        }

        @Override
        public View initView() {
            View view = View.inflate(getContext(), R.layout.fragment_product_details, null);
            ButterKnife.bind(this, view);

            return view;
        }

        @Override
        public void refreshView(List<ProductModel.ProductAtt> productAtts) {
            if (pagerAdapter == null) {
                pagerAdapter = new ProductPagerAdapter();

                productViewPager.setAdapter(pagerAdapter);
                productIndicator.setViewPager(productViewPager);
                productViewPager.setInterval(AUTO_SCROLL_INTERVAL);
                productViewPager.startAutoScroll();
            }
            if (pagerAdapter.getViewContainer() == null) {
                pagerAdapter.setViewContainer(new ArrayList<View>());
            }
            pagerAdapter.getViewContainer().clear();
            for (ProductModel.ProductAtt item : productAtts) {
                ImageView iv = new ImageView(getActivity());
                ImageLoader.getInstance().displayImage(item.getAttUrl(), iv,
                        ((BaseApplication) getActivity().getApplication()).getOptions());
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                pagerAdapter.getViewContainer().add(iv);
            }
            pagerAdapter.notifyDataSetChanged();
        }

        @OnClick({R.id.pd_btn_collect, R.id.pd_btn_addIntoCart, R.id.pd_btn_cart})
        public void btnClicked(View view) {
            switch (view.getId()) {
                case R.id.pd_btn_collect:
                    if (!collected) {
                        ((TransitionDrawable) pdBtnCollect.getDrawable())
                                .startTransition(200);
                        collected = true;
                    } else {
                        ((TransitionDrawable) pdBtnCollect.getDrawable())
                                .reverseTransition(200);
                        collected = false;
                    }

                    break;
                case R.id.pd_btn_addIntoCart:
                    presenter.add2ShopCar();
                    break;
                case R.id.pd_btn_cart:
                    Intent intent = new Intent(getActivity(), ShopCarActivity.class);
                    startActivity(intent);
                    break;
                default:
//                    ProductDetailsThirdLayerActivity.this.finish();
                    break;
            }
        }

        public class ProductPagerAdapter extends PagerAdapter {
            private List<View> viewContainer;
//            private List<ProductModel.ProductAtt> productAtts;

            public List<View> getViewContainer() {
                return viewContainer;
            }

            public void setViewContainer(List<View> viewContainer) {
                this.viewContainer = viewContainer;
            }


            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                if (viewContainer == null)
                    return 0;
                return viewContainer.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((AutoScrollViewPager) container).removeView(viewContainer
                        .get(position));
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((AutoScrollViewPager) container).addView(viewContainer
                        .get(position));
                return viewContainer.get(position);
            }

        }


//        /**
//         * 获取广告位的图片资源url数组
//         * 每次从服务器获得url数组先做永久性存储，获取时先从本地显示之前的缓存，等获取成功之后再调用notifyDataSetChanged()
//         *
//         * @return url数组
//         */
//        private ArrayList<String> getImageUrls() {
//            ArrayList<String> list = new ArrayList<String>();
//            // //////////////////////////////////////
//            // ////////////////假数据/////////////////
//            list.add("http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg");
//            list.add("http://c.hiphotos.baidu.com/image/pic/item/37d12f2eb9389b503564d2638635e5dde7116e63.jpg");
//            list.add("http://g.hiphotos.baidu.com/image/pic/item/cf1b9d16fdfaaf517578b38e8f5494eef01f7a63.jpg");
//            list.add("http://f.hiphotos.baidu.com/image/pic/item/77094b36acaf2eddce917bd88e1001e93901939a.jpg");
//            list.add("http://g.hiphotos.baidu.com/image/pic/item/f703738da97739124dd7b750fb198618367ae20a.jpg");
//            // //////////////////////////////////////
//            return list;
//        }
    }
}
