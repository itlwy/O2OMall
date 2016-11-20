package itlwy.com.o2omall.register;

import itlwy.com.o2omall.base.api.IBasePresenter;
import itlwy.com.o2omall.base.api.IBaseView;

public class RegisterContract {
    public interface IRegisterPresenter extends IBasePresenter {
        void register(String submitJsonStr);
    }

    public interface IRegisterView extends IBaseView<IRegisterPresenter> {

    }
}