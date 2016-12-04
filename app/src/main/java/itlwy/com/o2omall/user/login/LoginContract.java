package itlwy.com.o2omall.user.login;

import com.lndroid.lndroidlib.base.api.IBasePresenter;
import com.lndroid.lndroidlib.base.api.IBaseView;

/**
 * Created by mac on 16/10/2.
 */

public class LoginContract {
    public interface ILoginPresenter extends IBasePresenter {
        void login(String userName,String password);
    }

    public interface ILoginView<ILoginPresenter> extends IBaseView<ILoginPresenter> {

    }
}
