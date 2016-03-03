package itlwy.com.o2omall.fragment;

import android.content.Context;
import android.view.View;

import itlwy.com.o2omall.base.BaseFragment;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.bean.Product;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ExampleFragment extends BaseFragment<Void, Product> {
    private ExampleViewHolder holder;

    @Override
    public Product load() {
        return null;
    }

    @Override
    public View createSuccessView() {
        holder = new ExampleViewHolder(getActivity());
        return holder.getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return null;
    }

    @Override
    protected void bindViewDatas(Product result) {
        holder.setData(result);
    }

    public class ExampleViewHolder extends BaseHolder<Product, Object> {
        public ExampleViewHolder(Context ctx) {
            super(ctx);
        }

        @Override
        public View initView() {
//            View view = View.inflate(getContext(), ResId, null);
//            ButterKnife.bind(this, view);
            return null;
        }

        @Override
        public void refreshView(Product data) {
//            changef
        }
    }
}