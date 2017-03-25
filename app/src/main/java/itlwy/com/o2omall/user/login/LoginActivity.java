package itlwy.com.o2omall.user.login;

import android.os.Bundle;
import android.widget.Toast;

import com.lndroid.lndroidlib.base.BaseMVPActivity;
import com.lndroid.lndroidlib.factory.FragmentFactory;
import com.lndroid.lndroidlib.utils.UIManager;

import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.data.user.UserRepository;

public class LoginActivity extends BaseMVPActivity {


    private LoginPresenter mLoginPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(getFragmentContain());
        try {
            if (loginFragment == null) {
                // Create the fragment
                loginFragment = (LoginFragment) FragmentFactory.
                        createFragment(ConstantValue.LOGINFRAGMENT, true);
            }
            UserRepository userRepository = new UserRepository();
            // Create the presenter
            mLoginPresenter = LoginPresenter.newInstance(loginFragment, userRepository);
            UIManager.getInstance().changeFragment(this, getFragmentContain(), loginFragment, false, null);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
