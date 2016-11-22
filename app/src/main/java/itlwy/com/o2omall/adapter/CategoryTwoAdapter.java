package itlwy.com.o2omall.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseRCHolder;
import itlwy.com.o2omall.data.product.model.CategoryTwoModel;

/**
 * Created by Administrator on 2016/2/17.
 */
public class CategoryTwoAdapter extends BaseRCAdapter<CategoryTwoModel> {

    public CategoryTwoAdapter(Context context, List<CategoryTwoModel> datas) {
        super(context, datas);
    }


    @Override
    protected BaseRCHolder getItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_category_two, parent, false);
        // 设置图片宽高相等
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels / 4 - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, dm);
        int height = width;
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(width, height);
        v.setLayoutParams(layoutParams);
        CategoryTwoHolder categoryTwoHolder = new CategoryTwoHolder(v);
        return categoryTwoHolder;
    }

    @Override
    protected BaseRCHolder getHeadViewHolder(ViewGroup parent) {
        return null;
    }


    public class CategoryTwoHolder extends BaseRCHolder<CategoryTwoModel> {

        @Bind(R.id.product_url)
        ImageView productUrl;
        @Bind(R.id.product_name)
        TextView productName;
        @Bind(R.id.category_details_llt)
        LinearLayout categoryDetailsLlt;
        private CategoryTwoModel mCategoryTwoModel;
        private int position;

        public CategoryTwoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindDatas(CategoryTwoModel data) {
            this.mCategoryTwoModel = data;
            this.position = getLayoutPosition();
            ImageLoader.getInstance().displayImage(mCategoryTwoModel.getImgUrl(), productUrl, getOptions());
            productName.setText(mCategoryTwoModel.getName());
        }

    }
}
