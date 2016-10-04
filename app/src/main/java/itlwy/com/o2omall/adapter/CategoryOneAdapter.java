package itlwy.com.o2omall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseRCHolder;
import itlwy.com.o2omall.data.model.CategoryOne;

/**
 * Created by Administrator on 2016/2/17.
 */
public class CategoryOneAdapter extends BaseRCAdapter<CategoryOne> {

    private CategoryOneHolder holderSelected;

    public CategoryOneAdapter(Context context, List<CategoryOne> datas) {
        super(context,datas);
    }


    @Override
    protected BaseRCHolder getItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_category_one, parent, false);
        return new CategoryOneHolder(v);
    }

    @Override
    public void onBindViewHolder(BaseRCHolder holder, int position) {
        holder.bindDatas(getmDatas().get(position));
    }

    @Override
    protected BaseRCHolder getHeadViewHolder(ViewGroup parent) {
        return null;
    }



    public class CategoryOneHolder extends BaseRCHolder<CategoryOne> implements View.OnClickListener{

        @Bind(R.id.item_category_left_tv)
        TextView itemCategoryLeftTv;
        @Bind(R.id.catrgory_show_llt)
        LinearLayout catrgoryShowLlt;
        private TextView contentView;
        private CategoryOne categoryOne;
        private int position;

        public CategoryOneHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindDatas(CategoryOne data) {
            categoryOne = data;
            position = getLayoutPosition();
            if (position == 0){  //默认选中第一个
                catrgoryShowLlt.setSelected(true);
                holderSelected = this;
            }
            itemCategoryLeftTv.setText(categoryOne.getName());
            catrgoryShowLlt.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (holderSelected != null)
                holderSelected.catrgoryShowLlt.setSelected(false);   //取消上一个选中状态
            v.setSelected(true);
            holderSelected = this;
            if (getItemClickListener() != null){
                getItemClickListener().onItemClick(v,position);
            }
        }
    }
}
