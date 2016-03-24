package itlwy.com.o2omall.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

import itlwy.com.o2omall.base.BaseRCHolder;
import itlwy.com.o2omall.utils.DensityUtil;

/**
 * Created by Administrator on 2016/2/19.
 */
public abstract class BaseRCAdapter<T> extends RecyclerView.Adapter<BaseRCHolder> {

    private static final int TYPE_ITEM = 1;    //item类型：子项
    private static final int TYPE_HEAD = 2;    //item类型：头
    private static final int TYPE_FOOTER = 3;    //item类型：尾部

    public static final int STATUS_ERROR = 4;    //加载更多状态
    public static final int STATUS_SUCCESS = 5;
    public static final int STATUS_FAILS = 6;
    private int statusCode;

    private BaseRCHolder headHolder;    //头部view的holder
    private DisplayImageOptions options;
    private boolean isLoadMore;  //是否开启加载更多
    private LoadFooterHolder loadMoreHolder;      //加载更多的holder

    private List<T> mDatas;
    private Context context;
    private DisplayMetrics dm;

    public Context getContext() {
        return context;
    }

    public LoadFooterHolder getLoadMoreHolder() {
        return loadMoreHolder;
    }

    public DisplayMetrics getDm() {
        if (dm == null) {
            dm = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay()
                    .getMetrics(dm);
        }
        return dm;
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public DisplayImageOptions getOptions() {
        if (options == null) {
            options = new DisplayImageOptions.Builder()
                    // // 设置图片在下载期间显示的图片
                    // .showImageOnLoading(R.drawable.small_image_holder_listpage)
                    // // 设置图片Uri为空或是错误的时候显示的图片
                    // .showImageForEmptyUri(R.drawable.small_image_holder_listpage)
                    // // 设置图片加载/解码过程中错误时候显示的图片
                    // .showImageOnFail(R.drawable.small_image_holder_listpage)
                    .cacheInMemory(true)
                            // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true)
                            // 设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true)
                    .imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
                    .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                            // .decodingOptions(android.graphics.BitmapFactory.Options
                            // decodingOptions)//设置图片的解码配置
                    .considerExifParams(true)
                            // 设置图片下载前的延迟
                            // .delayBeforeLoading(int delayInMillis)//int
                            // delayInMillis为你设置的延迟时间
                            // 设置图片加入缓存前，对bitmap进行设置
                            // 。preProcessor(BitmapProcessor preProcessor)
                    .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                            // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                    .displayer(new FadeInBitmapDisplayer(100))// 淡入
                    .build();
        }
        return options;
    }

    public void setHeadHolder(BaseRCHolder headHolder) {
        this.headHolder = headHolder;
    }

    public BaseRCHolder getHeadHolder() {
        return headHolder;
    }

    public void setLoadMoreHolder(LoadFooterHolder loadMoreHolder) {
        this.loadMoreHolder = loadMoreHolder;
    }

    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public BaseRCAdapter(Context context, List<T> datas) {
        this.context = context;
        mDatas = datas;
    }

    @Override
    public BaseRCHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEAD: {
                return getHeadViewHolder(parent);
            }
            case TYPE_ITEM: {
                return getItemViewHolder(parent);
            }
            case TYPE_FOOTER: {
                if (loadMoreHolder == null) {
                    TextView footerTV = new TextView(context);
                    loadMoreHolder = new LoadFooterHolder(footerTV);
                }
                return loadMoreHolder;
            }
        }
        return null;
    }

    /**
     * item的viewholder
     *
     * @return
     * @param parent
     */
    protected abstract BaseRCHolder getItemViewHolder(ViewGroup parent);

    /**
     * 头的viewholder
     *
     * @return
     * @param parent
     */
    protected abstract BaseRCHolder getHeadViewHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(final BaseRCHolder holder, final int position) {
        int pos = position;
        if (getHeadHolder() != null){
            if (position == 0)
                return;
            pos = position -1;
        }
        holder.bindDatas(mDatas.get(pos));
        if (itemClickListener != null){
            holder.getContentView().setClickable(true);
            holder.getContentView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tempPos = position;
                    if (getHeadHolder() != null){
                        tempPos = position - 1;
                    }
                    itemClickListener.onItemClick(holder.getContentView(), tempPos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int count = mDatas.size();
        if (isLoadMore) {
            count++;
        }
        if (headHolder != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = TYPE_ITEM;
        if (headHolder != null && position == 0) {  //此处是头
            viewType = TYPE_HEAD;
        } else if (isLoadMore && isFooterPosition(position)) {  //此处是尾
            viewType = TYPE_FOOTER;
        }
        return viewType;
    }

    public boolean isFooterPosition(int position) {
        return position == getItemCount() - 1 ? true : false;

    }

    /**
     * 通知加载更多情况
     *
     * @param flag       暂时无用
     * @param moreDatas  加载的新数据
     * @param statusCode 加载的状态码有STATUS_ERROR、STATUS_SUCCESS、STATUS_FAILS
     */
    public void setLoadMoreFinish(boolean flag, List<T> moreDatas, int statusCode) {
        if (isLoadMore) {
            switch (statusCode) {
                case STATUS_ERROR: {
                    loadMoreHolder.footerTV.setText("加载错误...");
                    break;
                }
                case STATUS_SUCCESS: {
                    loadMoreHolder.footerTV.setText("加载成功...");
                    System.out.println("setLoadMoreFinish");
                    mDatas.addAll(moreDatas);
                    notifyDataSetChanged();
                    break;
                }
                case STATUS_FAILS: {
                    loadMoreHolder.footerTV.setText("加载失败...");
                    break;
                }
            }
            this.statusCode = statusCode;
        }
    }

    /************************
     * 监听
     ************************/
    private OnItemClick itemClickListener;

    public void setOnItemClickListener(OnItemClick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OnItemClick getItemClickListener() {
        return itemClickListener;
    }

    public interface OnItemClick {
        void onItemClick(View view, int position);
    }
    /*************************监听************************/
    /**
     * 尾部holder
     */
    public class LoadFooterHolder extends BaseRCHolder<Object> {

        TextView footerTV;

        public LoadFooterHolder(View itemView) {
            super(itemView);
            footerTV = (TextView) itemView;
            int padding = DensityUtil.dip2px(context, 10);
            footerTV.setPadding(padding, padding, padding, padding);
            footerTV.setTextSize(16);
            footerTV.setGravity(Gravity.CENTER);
        }

        @Override
        public void bindDatas(Object o) {

        }

    }
}
