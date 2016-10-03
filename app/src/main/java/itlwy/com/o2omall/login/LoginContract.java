package itlwy.com.o2omall.login;

import itlwy.com.o2omall.base.api.IBasePresenter;
import itlwy.com.o2omall.base.api.IBaseView;

/**
 * Created by mac on 16/10/2.
 */

public class LoginContract {
    public interface ILoginPresenter extends IBasePresenter {

    }

    public interface ILoginView<ILoginPresenter> extends IBaseView<ILoginPresenter> {

    }
}
