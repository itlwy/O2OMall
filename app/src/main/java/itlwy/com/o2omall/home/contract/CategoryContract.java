package itlwy.com.o2omall.home.contract;

import java.util.List;

import com.lndroid.lndroidlib.base.api.IBasePresenter;
import com.lndroid.lndroidlib.base.api.IBaseView;
import itlwy.com.o2omall.data.product.model.CategoryOneModel;

/**
 * Created by mac on 16/10/3.
 */

public class CategoryContract {
    public interface ICategoryPresenter extends IBasePresenter {

    }

    public interface ICategoryView extends IBaseView<ICategoryPresenter> {
        void bindViewDatas(List<CategoryOneModel> result);
    }
}
