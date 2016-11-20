package itlwy.com.o2omall.home.presenter;

import java.util.ArrayList;
import java.util.List;

import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.data.model.CategoryOneModel;
import itlwy.com.o2omall.data.model.CategoryTwoModel;
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

    private List<CategoryOneModel> prepareDatas() {
        List<CategoryOneModel> categoryOneModelList = new ArrayList<CategoryOneModel>();
            for (int i = 1; i < 5; i++) {
                List<CategoryTwoModel> categoryTwoModelList = new ArrayList<CategoryTwoModel>();
                CategoryOneModel dataOne = new CategoryOneModel();
                dataOne.setId(i);
                dataOne.setName("分类" + i);
                for (int k = 1; k < 6; k++) {
                    CategoryTwoModel dataTwo = new CategoryTwoModel();
                    dataTwo.setName("商品" + k);
                    dataTwo.setId(k);
                    dataTwo.setImgUrl("http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg");
                    dataTwo.setPreviousId(i);
                    categoryTwoModelList.add(dataTwo);
                }
                dataOne.setTwoList(categoryTwoModelList);
                categoryOneModelList.add(dataOne);
            }
            return categoryOneModelList;
    }
}
