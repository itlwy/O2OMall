package itlwy.com.o2omall.login;

import android.os.Bundle;

import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.base.BaseMVPActivity;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.factory.FragmentFactory;
import itlwy.com.o2omall.utils.UIManager;

public class LoginActivity extends BaseMVPActivity {


    private LoginPresenter mLoginPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(getFragmentContain());
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = (LoginFragment) FragmentFactory.
                    createFragment(this, ConstantValue.LOGINFRAGMENT);
        }
        UserRepository userRepository = new UserRepository();
        // Create the presenter
        mLoginPresenter = LoginPresenter.newInstance(loginFragment,userRepository);
        UIManager.getInstance().changeFragment(this, getFragmentContain(), loginFragment, false, null);

    }

}
