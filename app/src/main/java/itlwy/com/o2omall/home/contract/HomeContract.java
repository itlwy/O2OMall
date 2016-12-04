package itlwy.com.o2omall.home.contract;

import java.util.List;

import com.lndroid.lndroidlib.base.api.IBasePresenter;
import com.lndroid.lndroidlib.base.api.IBaseView;
import itlwy.com.o2omall.data.product.model.AdvertModel;
import itlwy.com.o2omall.data.product.model.ProductModel;

/**
 * Created by mac on 16/10/3.
 */

public class HomeContract {
    public interface IHomePresenter extends IBasePresenter {
        void getMoreDatas();
    }

    public interface IHomeView extends IBaseView<IHomePresenter> {
        void bindViewDatas(List<ProductModel> result);

        void bindHeaderDatas(List<AdvertModel> adverts);

        void setLoadMoreFinish(boolean flag, List<ProductModel> moreDatas, int statusCode);
    }
}
