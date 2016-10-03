package itlwy.com.o2omall.home.presenter;

import java.util.ArrayList;
import java.util.List;

import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.bean.CategoryOne;
import itlwy.com.o2omall.bean.CategoryTwo;
import itlwy.com.o2omall.home.contract.CategoryContract;

/**
 * Created by mac on 16/10/3.
 */

public class CategoryPresenter extends BasePresenter implements CategoryContract.ICategoryPresenter {

    private CategoryContract.ICategoryView view;

    public CategoryPresenter(CategoryContract.ICategoryView view) {
        this.view = view;
        this.view.setPresenter(this);
    }
    @Override
    public void subscribe() {
        load();
    }

    private void load() {
        view.showLoadingView();
        view.bindViewDatas(prepareDatas());
        view.showSuccessView();
    }

    public static CategoryPresenter newInstance(CategoryContract.ICategoryView view){
        return new CategoryPresenter(view);
    }

    private List<CategoryOne> prepareDatas() {
        List<CategoryOne> categoryOneList = new ArrayList<CategoryOne>();
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
}
