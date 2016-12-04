package itlwy.com.o2omall.register;

import android.app.Activity;
import android.content.Context;

import com.lndroid.lndroidlib.base.BasePresenter;
import com.lndroid.lndroidlib.data.ProgressSubscriber;

import itlwy.com.o2omall.GlobalParams;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.data.user.model.UserModel;

public class RegisterPresenter extends BasePresenter implements RegisterContract.IRegisterPresenter {
    private UserRepository repository;
    private RegisterContract.IRegisterView view;

    public RegisterPresenter(RegisterContract.IRegisterView view, UserRepository userRepository) {
        this.view = view;
        this.repository = userRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe(Object param) {
        view.showSuccessView();
    }

    public static RegisterPresenter newInstance(RegisterContract.IRegisterView view, UserRepository userRepository) {
        return new RegisterPresenter(view, userRepository);
    }

    @Override
    public void register(String submitJsonStr) {
        ProgressSubscriber<UserModel> subscriber = new ProgressSubscriber<UserModel>((Context) view) {
            @Override
            public void onNext(UserModel userModel) {
                ClientKernal.getInstance().setUserModel(userModel);
                GlobalParams.isLogin = true;
                view.showToast("注册成功!");
                ((Activity) view).setResult(Activity.RESULT_OK);
                ((Activity) view).finish();
            }
        };
        addSubscriber(subscriber);
        repository.register(subscriber, submitJsonStr);
    }
}
