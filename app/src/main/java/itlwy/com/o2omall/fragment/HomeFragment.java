package itlwy.com.o2omall.fragment;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.adapter.GroupRCAdapter;
import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.base.BaseFragment;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.base.BaseRCHolder;
import itlwy.com.o2omall.bean.Section;
import itlwy.com.o2omall.utils.DensityUtil;
import itlwy.com.o2omall.utils.ViewUtils;
import itlwy.com.o2omall.view.AutoRecyclerView;
import itlwy.com.o2omall.view.CirclePageIndicator;

/**
 * Created by Administrator on 2015/12/22.
 */
public class HomeFragment extends BaseFragment<Void, List<Section>> {
    private List<Section> list;
    private ListView lv;

    @Override
    public View createSuccessView() {
        HomeHolder homeHolder = new HomeHolder(getActivity());
        return homeHolder.getContentView();
    }

    @Override
    public List<Section> load() {
        list = downloadDatas();
        return list;
    }

    private List<Section> downloadDatas() {
        List<Section> list = new ArrayList<Section>();
        for (int i = 0; i < 5; i++) {
            Section sec = new Section();
            List<Section.Item> items = new ArrayList<Section.Item>();
            sec.setTitle(String.format("组%d", i));
            for (int k = 0; k < 5; k++) {
                Section.Item item = sec.new Item();
                item.setDesc(String.format("描述:%d", k));
                item.setPic("http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg");
                item.setPrice(200);
                items.add(item);
            }
            sec.setItems(items);
            list.add(sec);
        }
        return list;
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.HOMEFRAGMENT;
    }


    /**
     * 商品列表
     */
    public class HomeHolder extends BaseHolder<List<Section>, Void> implements GroupRCAdapter.OnItemClick {

        @Bind(R.id.home_recycleView)
        AutoRecyclerView homeRecycleView;
        private GroupRCAdapter adapter;
        private HeadViewHolder headHolder;

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
                    adapter.setLoadMoreFinish(true, downloadDatas(), GroupRCAdapter.STATUS_SUCCESS);
                    homeRecycleView.setIsLoading(false);
//                    homeRecycleView.scrollToPosition(startPos);
                }
            });
            return v;
        }

        @Override
        public void refreshView(List<Section> datas) {
            if (adapter == null)
                adapter = new GroupRCAdapter(getActivity(), datas);
            else
                adapter.setmDatas(datas);
            adapter.setHeadHolder(headHolder);
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
        public class HeadViewHolder extends BaseRCHolder {

            @Bind(R.id.home_viewPager)
            AutoScrollViewPager homeViewPager;
            @Bind(R.id.home_indicator)
            CirclePageIndicator homeIndicator;
            private ArrayList<View> viewContainer;
            private PagerAdapter pagerAdapter;

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
            public void bindDatas(Object data) {

            }

            private void initHead() {
                getViewImage();
                pagerAdapter = new PagerAdapter() {

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
                        return viewContainer.size();
                    }
                };

                homeViewPager.setAdapter(pagerAdapter);
                homeIndicator.setViewPager(homeViewPager);
                homeViewPager.setInterval(AUTO_SCROLL_INTERVAL);
                homeViewPager.startAutoScroll();
            }

            private void getViewImage() {
                if (viewContainer == null) {
                    viewContainer = new ArrayList<View>();
                }
                if (urls == null) {
                    urls = new ArrayList<String>();
                }
                urls = getImageUrls();
                viewContainer.clear();
                for (String url : urls) {
                    ImageView iv = new ImageView(getActivity());
                    ImageLoader.getInstance().displayImage(url, iv,
                            ((BaseApplication) getActivity().getApplication()).getOptions());
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    viewContainer.add(iv);
                }
            }

            /**
             * 获取广告位的图片资源url数组
             * 每次从服务器获得url数组先做永久性存储，获取时先从本地显示之前的缓存，等获取成功之后再调用notifyDataSetChanged()
             *
             * @return url数组
             */
            private ArrayList<String> getImageUrls() {
                ArrayList<String> list = new ArrayList<String>();
                // //////////////////////////////////////
                // ////////////////假数据/////////////////
                list.add("http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg");
                list.add("http://c.hiphotos.baidu.com/image/pic/item/37d12f2eb9389b503564d2638635e5dde7116e63.jpg");
                list.add("http://g.hiphotos.baidu.com/image/pic/item/cf1b9d16fdfaaf517578b38e8f5494eef01f7a63.jpg");
                list.add("http://f.hiphotos.baidu.com/image/pic/item/77094b36acaf2eddce917bd88e1001e93901939a.jpg");
                list.add("http://g.hiphotos.baidu.com/image/pic/item/f703738da97739124dd7b750fb198618367ae20a.jpg");
                // //////////////////////////////////////
                return list;
            }
        }

    }
}
