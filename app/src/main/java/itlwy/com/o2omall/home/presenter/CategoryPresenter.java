package itlwy.com.o2omall.home.presenter;

import android.support.v4.app.Fragment;

import com.lndroid.lndroidlib.base.BasePresenter;
import com.lndroid.lndroidlib.data.ProgressSubscriber;

import java.util.List;

import itlwy.com.o2omall.data.product.ProductRepository;
import itlwy.com.o2omall.data.product.model.CategoryOneModel;
import itlwy.com.o2omall.home.contract.CategoryContract;

/**
 * Created by mac on 16/10/3.
 */

public class CategoryPresenter extends BasePresenter implements CategoryContract.ICategoryPresenter {

    private ProductRepository repository;
    private CategoryContract.ICategoryView view;

    public CategoryPresenter(CategoryContract.ICategoryView view, ProductRepository productRepository) {
        this.view = view;
        this.repository = productRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe(Object param) {
        load();
    }

    private void load() {
        ProgressSubscriber<List<CategoryOneModel>> subscriber =
                new ProgressSubscriber<List<CategoryOneModel>>(((Fragment) view).getActivity(),view) {

                    @Override
                    public void onNext(List<CategoryOneModel> categoryOneModels) {
                        view.bindViewDatas(categoryOneModels);
                        view.showSuccessView();
                    }
                };

        repository.getCategoryInfo(subscriber);
        addSubscriber(subscriber);
    }

    public static CategoryPresenter newInstance(CategoryContract.ICategoryView view, ProductRepository productRepository) {
        return new CategoryPresenter(view, productRepository);
    }

}
