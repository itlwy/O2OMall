package itlwy.com.o2omall.user.login;

import android.support.v4.app.Fragment;

import com.lndroid.lndroidlib.base.BasePresenter;
import com.lndroid.lndroidlib.data.ProgressSubscriber;

import itlwy.com.o2omall.GlobalParams;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.data.user.model.UserModel;

/**
 * Created by mac on 16/10/2.
 */

public class LoginPresenter extends BasePresenter implements LoginContract.ILoginPresenter {
    private UserRepository mUserRepository;
    private LoginContract.ILoginView mView;

    public LoginPresenter(LoginContract.ILoginView view, UserRepository userRepository) {
        mView = view;
        mUserRepository = userRepository;
        view.setPresenter(this);
    }

    public static LoginPresenter newInstance(LoginContract.ILoginView view, UserRepository userRepository) {
        return new LoginPresenter(view, userRepository);
    }

    @Override
    public void subscribe(Object param) {
        mView.showSuccessView();
    }

    @Override
    public void login(String userName, String password) {
        ProgressSubscriber subscriber = new ProgressSubscriber<UserModel>(((Fragment) mView).getActivity()) {
            @Override
            public void onNext(UserModel userModel) {
                ClientKernal.getInstance().setUserModel(userModel);
                GlobalParams.isLogin = true;
                mView.showToast("登陆成功!");
                ((Fragment) mView).getActivity().finish();
            }
        };
        mUserRepository.login(subscriber, userName, password);
        mSubscriptions.add(subscriber);
    }
}
