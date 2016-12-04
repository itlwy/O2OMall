package itlwy.com.o2omall.home.contract;

import com.lndroid.lndroidlib.base.api.IBasePresenter;
import com.lndroid.lndroidlib.base.api.IBaseView;

/**
 * Created by mac on 16/10/3.
 */

public class MyContract {
    public interface IMyPresenter extends IBasePresenter {

        void uploadMyLogo(String imageName);
    }

    public interface IMyView extends IBaseView<IMyPresenter> {
        void bindViewDatas(String result);
        void refreshMyLogo(String imageUrl);
    }
}
