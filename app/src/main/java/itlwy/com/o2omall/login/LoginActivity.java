package itlwy.com.o2omall.login;

import android.os.Bundle;

import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.base.BaseMVPActivity;
import itlwy.com.o2omall.factory.FragmentFactory;
import itlwy.com.o2omall.utils.UIManager;

public class LoginActivity extends BaseMVPActivity {


    private LoginPresenter mLoginPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
//        LoginFragment loginFragment =
//                (LoginFragment) getSupportFragmentManager().findFragmentById(getFragmentContain());
//        // Create the presenter
//        if (loginFragment == null) {
//            // Create the fragment
//            loginFragment = LoginFragment.newInstance();
//            ActivityUtils.addFragmentToActivity(
//                    getSupportFragmentManager(), loginFragment, getFragmentContain());
//        }
//        mLoginPresenter = new LoginPresenter(loginFragment);

        LoginFragment loginFragment = (LoginFragment) FragmentFactory.
                createFragment(this, ConstantValue.LOGINFRAGMENT);
        LoginPresenter.newInstance(loginFragment);
        UIManager.getInstance().changeFragment(this, getFragmentContain(), loginFragment, false, null);

    }

}
