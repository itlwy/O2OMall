package itlwy.com.o2omall.home.contract;

import java.util.List;

import itlwy.com.o2omall.base.api.IBasePresenter;
import itlwy.com.o2omall.base.api.IBaseView;
import itlwy.com.o2omall.data.model.SectionModel;

/**
 * Created by mac on 16/10/3.
 */

public class HomeContract {
    public interface IHomePresenter extends IBasePresenter {
        List<SectionModel> getMoreDatas();
    }

    public interface IHomeView extends IBaseView<IHomePresenter> {
        void bindViewDatas(List<SectionModel> result);

    }
}
