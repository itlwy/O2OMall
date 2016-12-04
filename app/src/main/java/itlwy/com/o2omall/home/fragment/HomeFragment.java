package itlwy.com.o2omall.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.lndroid.lndroidlib.base.BaseMVPFragment;
import com.lndroid.lndroidlib.base.BaseRCHolder;
import com.lndroid.lndroidlib.utils.DensityUtil;
import com.lndroid.lndroidlib.utils.ImageLoaderUtil;
import com.lndroid.lndroidlib.utils.ViewUtils;
import com.lndroid.lndroidlib.view.AutoRecyclerView;
import com.lndroid.lndroidlib.view.CirclePageIndicator;
import com.lndroid.lndroidlib.view.LoadingPage;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.adapter.GroupRCAdapter;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.data.model.SectionModel;
import itlwy.com.o2omall.data.product.model.AdvertModel;
import itlwy.com.o2omall.data.product.model.ProductModel;
import itlwy.com.o2omall.home.contract.HomeContract;

/**
 * Created by Administrator on 2015/12/22.
 */
public class HomeFragment extends BaseMVPFragment implements HomeContract.IHomeView {
    private List<SectionModel> list;
    private ListView lv;
    private HomeHolder homeHolder;
    private HomeContract.IHomePresenter presenter;


    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }


    @Override
    protected View createSuccessView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeHolder = new HomeHolder(getActivity());
        return homeHolder.getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.HOMEFRAGMENT;
    }

    @Override
    protected LoadingPage.ReLoadListener getReloadListener() {
        return new LoadingPage.ReLoadListener() {
            @Override
            public void reLoad() {
                presenter.subscribe(null);
            }
        };
    }

    @Override
    protected void inits() {

    }

    @Override
    public void bindViewDatas(List<ProductModel> result) {
        homeHolder.setData(result);
    }

    @Override
    public void bindHeaderDatas(List<AdvertModel> adverts) {
        homeHolder.headHolder.bindDatas(adverts);
    }

    @Override
    public void setLoadMoreFinish(boolean flag, List<ProductModel> moreDatas, int statusCode) {
        homeHolder.getAdapter().setLoadMoreFinish(flag, moreDatas, statusCode);
        homeHolder.getHomeRecycleView().setIsLoading(false);
    }


    @Override
    public void setPresenter(HomeContract.IHomePresenter presenter) {
        this.presenter = presenter;
    }


    /**
     * 商品列表
     */
    public class HomeHolder extends BaseHolder<List<ProductModel>, Void> implements GroupRCAdapter.OnItemClick {

        @Bind(R.id.home_recycleView)
        AutoRecyclerView homeRecycleView;
        private GroupRCAdapter adapter;
        private HeadViewHolder headHolder;

        public AutoRecyclerView getHomeRecycleView() {
            return homeRecycleView;
        }

        public GroupRCAdapter getAdapter() {
            return adapter;
        }

        public HomeHolder(Context ctx) {
            super(ctx);
        }

        @Override
        public View initView() {
            View v = View.inflate(getContext(), R.layout.fragment_home, null);
            ButterKnife.bind(this, v);
            View headView = View.inflate(getActivity(), R.layout.item_adhead, null);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getActivity(), 200));
            headView.setLayoutParams(params);
            headHolder = new HeadViewHolder(headView);
            homeRecycleView.setHasFixedSize(true);
            homeRecycleView.setOnPauseListenerParams(ImageLoader.getInstance(), true, true);
            homeRecycleView.setItemAnimator(new DefaultItemAnimator());
            homeRecycleView.setIsLoadingMore(true);
            homeRecycleView.setLoadMoreListener(new AutoRecyclerView.onLoadMoreListener() {
                @Override
                public void loadMore() {
//                    int startPos = adapter.getItemCount()-1;
                    presenter.getMoreDatas();
//                    homeRecycleView.scrollToPosition(startPos);
                }
            });
            return v;
        }

        @Override
        public void refreshView(List<ProductModel> datas) {
            if (adapter == null) {
                adapter = new GroupRCAdapter(getActivity(), datas);
                adapter.setHeadHolder(headHolder);
            } else
                adapter.setmDatas(datas);
            adapter.setIsLoadMore(true);
            homeRecycleView.setAdapter(adapter);
            final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
//            StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int size;
                    if ((adapter.getHeadHolder() != null && position == 0) || (adapter.getGroupMap().containsKey(position))
                            || adapter.isFooterPosition(position))
                        size = manager.getSpanCount();
                    else
                        size = 1;
                    return size;
                }
            });
            homeRecycleView.setLayoutManager(manager);
            adapter.setOnItemClickListener(this);
        }

        @Override
        public void onItemClick(View view, int position) {
            ViewUtils.showSnack(getContentView(), "点击了:" + position);
        }

        /**
         * 广告头
         */
        public class HeadViewHolder extends BaseRCHolder<List<AdvertModel>> {

            @Bind(R.id.home_viewPager)
            AutoScrollViewPager homeViewPager;
            @Bind(R.id.home_indicator)
            CirclePageIndicator homeIndicator;
            private AdvertPagerAdapter pagerAdapter;

            /**
             * 广告自动循环切换的时间间隔
             */
            private static final int AUTO_SCROLL_INTERVAL = 3000;
            private ArrayList<String> urls;

            public HeadViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                initHead();
            }

            @Override
            public void bindDatas(List<AdvertModel> datas) {
                if (pagerAdapter.getViewContainer() == null) {
                    pagerAdapter.setViewContainer(new ArrayList<View>());
                }
                pagerAdapter.setAdvertModels(datas);
            }

            private void initHead() {
                pagerAdapter = new AdvertPagerAdapter();

                homeViewPager.setAdapter(pagerAdapter);
                homeIndicator.setViewPager(homeViewPager);
                homeViewPager.setInterval(AUTO_SCROLL_INTERVAL);
                homeViewPager.startAutoScroll();
            }


            public class AdvertPagerAdapter extends PagerAdapter {
                private ArrayList<View> viewContainer;
                private List<AdvertModel> mAdvertModels;

                public List<AdvertModel> getAdvertModels() {
                    return mAdvertModels;
                }

                public void setAdvertModels(List<AdvertModel> advertModels) {
                    mAdvertModels = advertModels;
                    viewContainer.clear();
                    for (AdvertModel item : advertModels) {
                        ImageView iv = new ImageView(getActivity());
                        ImageLoader.getInstance().displayImage(item.getImageUrl(), iv,
                                ImageLoaderUtil.getInstance().getOptions());
                        iv.setScaleType(ImageView.ScaleType.FIT_XY);
                        viewContainer.add(iv);
                    }
                    notifyDataSetChanged();
                }

                public ArrayList<View> getViewContainer() {
                    return viewContainer;
                }

                public void setViewContainer(ArrayList<View> viewContainer) {
                    this.viewContainer = viewContainer;
                }

                public AdvertPagerAdapter() {

                }

                @Override
                public void destroyItem(ViewGroup container, int position,
                                        Object object) {
                    ((AutoScrollViewPager) container).removeView(viewContainer
                            .get(position));
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    ((AutoScrollViewPager) container).addView(viewContainer
                            .get(position));
                    return viewContainer.get(position);
                }

                @Override
                public boolean isViewFromObject(View arg0, Object arg1) {
                    return arg0 == arg1;
                }

                @Override
                public int getCount() {
                    if (viewContainer != null) {
                        return viewContainer.size();
                    } else
                        return 0;
                }
            }

        }

    }
}
