package itlwy.com.o2omall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lndroid.lndroidlib.adapter.BaseRCAdapter;
import com.lndroid.lndroidlib.base.BaseRCHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.data.product.model.ProductModel;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ProductListAdapter extends BaseRCAdapter<ProductModel> {

    public ProductListAdapter(Context context, List<ProductModel> datas) {
        super(context, datas);
    }

    @Override
    protected BaseRCHolder getItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_productlist, parent, false);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(params);
        return new ProductListItemHolder(v);
    }

    @Override
    protected BaseRCHolder getHeadViewHolder(ViewGroup parent) {
        return null;
    }

    public class ProductListItemHolder extends BaseRCHolder<ProductModel> {
        @Bind(R.id.iv_pd_second_layer)
        ImageView ivPdSecondLayer;
        @Bind(R.id.tv_item_pd_info)
        TextView tvItemPdInfo;
        @Bind(R.id.tv_item_pd_price)
        TextView tvItemPdPrice;

        public ProductListItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindDatas(ProductModel productModel) {
            tvItemPdInfo.setText(productModel.getName());
            tvItemPdPrice.setText("$"+ productModel.getPrice());
            ImageLoader.getInstance().displayImage(productModel.getMainImageUrl(),ivPdSecondLayer,getOptions());
        }
    }
}
