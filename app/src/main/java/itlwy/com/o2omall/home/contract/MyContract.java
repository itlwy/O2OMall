package itlwy.com.o2omall.home.contract;

import itlwy.com.o2omall.base.api.IBasePresenter;
import itlwy.com.o2omall.base.api.IBaseView;

/**
 * Created by mac on 16/10/3.
 */

public class MyContract {
    public interface IMyPresenter extends IBasePresenter {

    }

    public interface IMyView extends IBaseView<IMyPresenter> {
        void bindViewDatas(String result);
    }
}
