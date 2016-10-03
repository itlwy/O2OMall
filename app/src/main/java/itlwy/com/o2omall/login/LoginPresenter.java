package itlwy.com.o2omall.login;

import itlwy.com.o2omall.base.BasePresenter;

/**
 * Created by mac on 16/10/2.
 */

public class LoginPresenter extends BasePresenter implements LoginContract.ILoginPresenter {
    private LoginContract.ILoginView view;

    public LoginPresenter(LoginContract.ILoginView view) {
        this.view = view;
        view.setPresenter(this);
    }

    public static LoginPresenter newInstance(LoginContract.ILoginView view){
        return new LoginPresenter(view);
    }
    @Override
    public void subscribe() {
        view.showSuccessView();
    }
}
