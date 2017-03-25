package itlwy.com.o2omall.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lndroid.lndroidlib.base.BaseMVPFragment;
import com.lndroid.lndroidlib.utils.UIManager;
import com.lndroid.lndroidlib.view.LoadingPage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.adapter.CategoryOneAdapter;
import itlwy.com.o2omall.adapter.CategoryTwoAdapter;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.data.product.model.CategoryOneModel;
import itlwy.com.o2omall.data.product.model.CategoryTwoModel;
import itlwy.com.o2omall.home.contract.CategoryContract;
import itlwy.com.o2omall.product.ProductActivity;

/**
 * Created by Administrator on 2016/2/17.
 */
public class CategoryFragment extends BaseMVPFragment implements CategoryContract.ICategoryView { //BaseFragment<Void, List<CategoryOneModel>>

    private List<CategoryOneModel> mCategoryOneModelList;
    private CategoryHolder holder;
    private CategoryContract.ICategoryPresenter presenter;


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
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        holder = new CategoryHolder(getActivity());
        return holder.getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.CATEGORYFRAGMENT;
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
    protected void inits(Bundle savedInstanceState) {

    }

    @Override
    public void bindViewDatas(List<CategoryOneModel> result) {
        holder.setData(result);
    }


    @Override
    public void setPresenter(CategoryContract.ICategoryPresenter presenter) {
        this.presenter = presenter;
    }


    public class CategoryHolder extends BaseHolder<List<CategoryOneModel>, Void> {

        @Bind(R.id.category_one)
        RecyclerView categoryOne;
        @Bind(R.id.category_two)
        RecyclerView categoryTwo;
        private CategoryOneAdapter categoryOneAdapter;
        private CategoryTwoAdapter categoryTwoAdapter;

        public CategoryHolder(Context ctx) {
            super(ctx);
        }

        @Override
        public View initView() {
            View v = View.inflate(getContext(), R.layout.fragment_category, null);
            ButterKnife.bind(this, v);
            return v;
        }

        @Override
        public void refreshView(List<CategoryOneModel> list) {
            mCategoryOneModelList = list;
            if (categoryOneAdapter == null) {
                categoryOneAdapter = new CategoryOneAdapter(getContext(), mCategoryOneModelList);
            }
            if (categoryTwoAdapter == null) {
                categoryTwoAdapter = new CategoryTwoAdapter(getContext(), mCategoryOneModelList.get(0).getTwoList());
            }
            categoryOne.setAdapter(categoryOneAdapter);
            categoryTwo.setAdapter(categoryTwoAdapter);

            LinearLayoutManager managerOne = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            categoryOne.setLayoutManager(managerOne);

            GridLayoutManager managerTwo = new GridLayoutManager(getActivity(), 3);
            categoryTwo.setLayoutManager(managerTwo);

            categoryOneAdapter.setOnItemClickListener(new CategoryOneAdapter.OnItemClick() {
                @Override
                public void onItemClick(View view, int position) {
                    List<CategoryTwoModel> twoList = mCategoryOneModelList.get(position).getTwoList();
                    categoryTwoAdapter.setmDatas(twoList);
                }
            });
            categoryTwoAdapter.setOnItemClickListener(new CategoryTwoAdapter.OnItemClick() {
                @Override
                public void onItemClick(View view, int position) {
//                    ViewUtils.showSnack(view, "点击了" + categoryTwoAdapter.getmDatas().get(position).getName());
                    CategoryTwoModel categoryTwoModelData = categoryTwoAdapter.getmDatas().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(CategoryTwoModel.Tag, categoryTwoModelData);
                    UIManager.getInstance().changeActivity(getActivity(), ProductActivity.class, bundle);
                }
            });
        }
    }
}
