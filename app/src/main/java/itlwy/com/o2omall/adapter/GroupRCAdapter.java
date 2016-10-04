package itlwy.com.o2omall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseRCHolder;
import itlwy.com.o2omall.data.model.Section;
import itlwy.com.o2omall.utils.DensityUtil;

/**
 * Created by Administrator on 2016/1/5.
 */
public class GroupRCAdapter extends BaseRCAdapter<Section> {

    private static final int TYPE_TITLE = 0;   //item类型：标题


    private Map<Integer, String> groupMap;   //标题组  key为position
    private Map<Integer, Section.Item> itemMap;  //子项组 key为position


    public void setmDatas(List<Section> mDatas) {
        preparedDatas(mDatas);
        super.setmDatas(mDatas);
    }

    public Map<Integer, String> getGroupMap() {
        return groupMap;
    }


    public void setHeadHolder(BaseRCHolder headHolder) {
        super.setHeadHolder(headHolder);
        preparedDatas(null);
    }


    public GroupRCAdapter(Context context, List<Section> datas) {
        super(context, datas);
        preparedDatas(datas);
    }

    private void preparedDatas(List<Section> datas) {
        List<Section> tempDatas;
        if (datas == null) {
            tempDatas = getmDatas();
        } else
            tempDatas = datas;

        if (groupMap == null)
            groupMap = new HashMap<Integer, String>();

        if (itemMap == null)
            itemMap = new HashMap<Integer, Section.Item>();

        groupMap.clear();
        itemMap.clear();

        int recycleIndex = getHeadHolder() == null ? 0 : 1;
        for (Section sec : tempDatas) {
            groupMap.put(recycleIndex, sec.getTitle());
            recycleIndex++;
            for (Section.Item item : sec.getItems()) {
                itemMap.put(recycleIndex, item);
                recycleIndex++;
            }
        }
    }


    @Override
    public BaseRCHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_TITLE == viewType) {
            TextView title_tv = new TextView(parent.getContext());
            return new TitleViewHolder(title_tv);
        } else
            return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected BaseRCHolder getItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.item_normal, parent, false);
        int screenWidth = getDm().widthPixels;
        int width = screenWidth / 2 - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, getDm());
        int height = (int) (screenWidth * 1.5 / 2);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(width, height);
        view.setLayoutParams(layoutParams);
        return new ItemHolder(view);
    }

    @Override
    protected BaseRCHolder getHeadViewHolder(ViewGroup parent) {
        return getHeadHolder();
    }

    @Override
    public void onBindViewHolder(final BaseRCHolder holder, final int position) {
        if (getHeadHolder() != null && position == 0) {
            //广告头
        } else if (groupMap.containsKey(position)) {
//            ((TextViewHolder) holder).title_tv.setText(groupMap.get(position));
        } else if (isFooterPosition(position)) {

        } else {
            Section.Item item = itemMap.get(position);
            ((ItemHolder) holder).bindDatas(item);
            if (getItemClickListener() != null) {
                ((ItemHolder) holder).getContentView().setClickable(true);
                ((ItemHolder) holder).getContentView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getItemClickListener().onItemClick(((ItemHolder) holder).getContentView(), position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (groupMap.containsKey(position)) {  //此位置是title
            return TYPE_TITLE;
        }
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        int count = groupMap.size() + itemMap.size();
        if (isLoadMore()) {
            count++;
        }
        if (getHeadHolder() != null) {
            count++;
        }
        return count;
    }


    @Override
    public void setLoadMoreFinish(boolean flag, List<Section> moreDatas, int statusCode) {
        if (isLoadMore()) {
            switch (statusCode) {
                case STATUS_ERROR: {
                    getLoadMoreHolder().footerTV.setText("加载错误...");
                    break;
                }
                case STATUS_SUCCESS: {
                    getLoadMoreHolder().footerTV.setText("加载成功...");
                    getmDatas().addAll(moreDatas);
                    preparedDatas(getmDatas());
                    notifyDataSetChanged();
                    break;
                }
                case STATUS_FAILS: {
                    getLoadMoreHolder().footerTV.setText("加载失败...");
                    break;
                }
            }
        }
    }

    /**
     * 项
     */
    public class ItemHolder extends BaseRCHolder<Section.Item> {

        @Bind(R.id.item_home_iv)
        ImageView itemHomeIv;
        @Bind(R.id.item_home_tv_info)
        TextView itemHomeTvInfo;
        @Bind(R.id.item_home_tv_price)
        TextView itemHomeTvPrice;
        private View contentView;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            contentView = itemView;
            contentView.setPadding(0, 0, DensityUtil.dip2px(getContext(), 10), 0);
        }

        public View getContentView() {
            return contentView;
        }

        public void bindDatas(Section.Item item) {
            ImageLoader.getInstance().displayImage(item.getPic(), itemHomeIv, getOptions());
            itemHomeTvInfo.setText(item.getDesc());
            itemHomeTvPrice.setText("$" + item.getPrice());
        }
    }

    /**
     * 组
     */
    public class TitleViewHolder extends BaseRCHolder {
        public TextView title_tv;

        public TitleViewHolder(View itemView) {
            super(itemView);
            title_tv = (TextView) itemView;
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , DensityUtil.dip2px(getContext(), 10));
            title_tv.setLayoutParams(params);
            title_tv.setBackgroundResource(R.color.divider_grey);
        }

        @Override
        public void bindDatas(Object data) {

        }
    }

    /**
     * 尾部holder
     */
    public class LoadFooterHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.loadmore_tv)
        TextView loadmoreTv;

        public LoadFooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindDatas() {

        }
    }
}
