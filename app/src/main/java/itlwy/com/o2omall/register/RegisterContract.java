package itlwy.com.o2omall.register;

import com.lndroid.lndroidlib.base.api.IBasePresenter;
import com.lndroid.lndroidlib.base.api.IBaseView;

public class RegisterContract {
    public interface IRegisterPresenter extends IBasePresenter {
        void register(String submitJsonStr);
    }

    public interface IRegisterView extends IBaseView<IRegisterPresenter> {

    }
}