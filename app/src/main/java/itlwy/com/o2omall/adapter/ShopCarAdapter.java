package itlwy.com.o2omall.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseRCHolder;
import itlwy.com.o2omall.data.product.model.ProductModel;

/**
 * Created by Administrator on 2016/2/18.
 */
public class ShopCarAdapter extends BaseRCAdapter<ProductModel> {

    private OnPriceChangedListener onPriceChangedListener;
    private OnCheckChangedListener onCheckChangedListener;


    /**
     * 总价改变监听器
     */
    public interface OnPriceChangedListener {
        void onPriceChanged(float price);
    }

    public void setOnPriceChangedListener(
            OnPriceChangedListener onPriceChangedListener) {
        this.onPriceChangedListener = onPriceChangedListener;
    }

    /**
     * 选中状态监听器
     */
    public interface OnCheckChangedListener {
        /**
         * 全选时
         */
        void onCheckAll(boolean isAllCheck);
    }

    public void setOnCheckChangedListener(OnCheckChangedListener onCheckChangedListener) {
        this.onCheckChangedListener = onCheckChangedListener;
    }


    public ShopCarAdapter(Context context, List<ProductModel> mDatas) {
        super(context, mDatas);
    }


    @Override
    protected BaseRCHolder getItemViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_cart_lv, parent, false);
        return new ShopCarItemHolder(v);
    }

    @Override
    protected BaseRCHolder getHeadViewHolder(ViewGroup parent) {
        return null;
    }


    /**
     * 计算总价
     */
    public float calculatePrice() {
        float price = 0;
        for (int i = 0; i < getmDatas().size(); i++) {
            if (getmDatas().get(i).isCheck()) {
                price += (getmDatas().get(i).getNum()) * (getmDatas().get(i).getPrice());
            }
        }
        return price;
    }


    public class ShopCarItemHolder extends BaseRCHolder<ProductModel> {

        @Bind(R.id.item_cart_cb)
        CheckBox itemCartCb;
        @Bind(R.id.item_cart_iv)
        ImageView itemCartIv;
        @Bind(R.id.item_cart_tv_info)
        TextView itemCartTvInfo;
        @Bind(R.id.item_cart_tv_price)
        TextView itemCartTvPrice;
        @Bind(R.id.item_cart_btn_sub)
        Button itemCartBtnSub;
        @Bind(R.id.item_cart_et_num)
        EditText itemCartEtNum;
        @Bind(R.id.item_cart_btn_add)
        Button itemCartBtnAdd;
        private int position;
        private ProductModel mProductModel;

        public ShopCarItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindDatas(ProductModel pro) {
            this.position = getLayoutPosition();
            mProductModel = pro;
            ImageLoader.getInstance().displayImage(pro.getMainImageUrl(), itemCartIv, getOptions());
            itemCartTvInfo.setText(mProductModel.getName());
            itemCartTvPrice.setText("$" + pro.getPrice());
            itemCartCb.setChecked(mProductModel.isCheck());
            itemCartEtNum.setText(mProductModel.getNum() + "");
            itemCartCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    mProductModel.setIsCheck(isChecked);
                    if (onCheckChangedListener != null) {
                        SparseBooleanArray isCheckedArr = new SparseBooleanArray();
                        for (int i = 0; i < getmDatas().size(); i++) {
                            isCheckedArr.put(i, getmDatas().get(i).isCheck());
                        }
                        int index = isCheckedArr.indexOfValue(false);
                        if (index < 0) {  //全选
                            onCheckChangedListener.onCheckAll(true);
                        }
                        index = isCheckedArr.indexOfValue(true);
                        if (index < 0) {  //全不选
                            onCheckChangedListener.onCheckAll(false);
                        }
                    }
                    if (onPriceChangedListener != null) {
                        onPriceChangedListener.onPriceChanged(calculatePrice());
                    }
                }
            });
            itemCartBtnSub.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int num = Integer.parseInt(itemCartEtNum.getText().toString());
                    itemCartEtNum.setText((--num) + "");
                    int n = ShopCarItemHolder.this.getLayoutPosition();
                    mProductModel.setNum(num);
                    if (num <= 0) {
                        getmDatas().remove(n);
                        notifyItemRemoved(n);
                    }
                }
            });
            itemCartBtnAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int num = Integer.parseInt(itemCartEtNum.getText().toString());
                    itemCartEtNum.setText((++num) + "");
                    mProductModel.setNum(num);
                }
            });
            itemCartEtNum.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    int num = Integer.parseInt(s.toString());
                    if (num == 0) {
                    } else {
                        mProductModel.setNum(num);
                    }
                    if (onPriceChangedListener != null) {
                        onPriceChangedListener.onPriceChanged(calculatePrice());
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

    }
}
