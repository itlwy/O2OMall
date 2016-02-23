package itlwy.com.o2omall.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.Activity.ProductActivity;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.adapter.CategoryOneAdapter;
import itlwy.com.o2omall.adapter.CategoryTwoAdapter;
import itlwy.com.o2omall.base.BaseFragment;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.bean.CategoryOne;
import itlwy.com.o2omall.bean.CategoryTwo;
import itlwy.com.o2omall.utils.UIManager;

/**
 * Created by Administrator on 2016/2/17.
 */
public class CategoryFragment extends BaseFragment<Void, List<CategoryOne>> {

    private List<CategoryOne> categoryOneList;

    @Override
    public List<CategoryOne> load() {
        return prepareDatas();
    }

    @Override
    public View createSuccessView() {
        return new CategoryHolder(getActivity()).getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.CATEGORYFRAGMENT;
    }

    private List<CategoryOne> prepareDatas() {
        if (categoryOneList == null) {
            categoryOneList = new ArrayList<CategoryOne>();
            for (int i = 1; i < 5; i++) {
                List<CategoryTwo> categoryTwoList = new ArrayList<CategoryTwo>();
                CategoryOne dataOne = new CategoryOne();
                dataOne.setId(i);
                dataOne.setName("分类" + i);
                for (int k = 1; k < 6; k++) {
                    CategoryTwo dataTwo = new CategoryTwo();
                    dataTwo.setName("商品" + k);
                    dataTwo.setId(k);
                    dataTwo.setImgUrl("http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg");
                    dataTwo.setPreviousId(i);
                    categoryTwoList.add(dataTwo);
                }
                dataOne.setTwoList(categoryTwoList);
                categoryOneList.add(dataOne);
            }
            return categoryOneList;
        }
        return categoryOneList;
    }


    public class CategoryHolder extends BaseHolder<List<CategoryOne>, Void> {

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
        public void refreshView(List<CategoryOne> list) {
            if (categoryOneAdapter == null) {
                categoryOneAdapter = new CategoryOneAdapter(getContext(), categoryOneList);
            }
            if (categoryTwoAdapter == null) {
                categoryTwoAdapter = new CategoryTwoAdapter(getContext(), categoryOneList.get(0).getTwoList());
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
                    List<CategoryTwo> twoList = categoryOneList.get(position).getTwoList();
                    categoryTwoAdapter.setmDatas(twoList);
                }
            });
            categoryTwoAdapter.setOnItemClickListener(new CategoryTwoAdapter.OnItemClick() {
                @Override
                public void onItemClick(View view, int position) {
//                    ViewUtils.showSnack(view, "点击了" + categoryTwoAdapter.getmDatas().get(position).getName());
                    CategoryTwo categoryTwoData = categoryTwoAdapter.getmDatas().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(CategoryTwo.Tag,categoryTwoData);
                    UIManager.getInstance().changeActivity(getActivity(), ProductActivity.class,bundle);
                }
            });
        }
    }
}
