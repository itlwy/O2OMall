package itlwy.com.o2omall.home.contract;

import java.util.List;

import itlwy.com.o2omall.base.api.IBasePresenter;
import itlwy.com.o2omall.base.api.IBaseView;
import itlwy.com.o2omall.bean.CategoryOne;

/**
 * Created by mac on 16/10/3.
 */

public class CategoryContract {
    public interface ICategoryPresenter extends IBasePresenter {

    }

    public interface ICategoryView extends IBaseView<ICategoryPresenter> {
        void bindViewDatas(List<CategoryOne> result);
    }
}
