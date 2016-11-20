package itlwy.com.o2omall.data.user;

import itlwy.com.o2omall.data.CommonRepository;
import itlwy.com.o2omall.data.HttpResultFunc;
import itlwy.com.o2omall.data.user.model.UserModel;
import rx.Subscriber;

/**
 * Created by mac on 16/11/19.
 */

public class UserRepository {

    private UserApi mUserApi;

    public UserRepository() {
        mUserApi = CommonRepository.getInstance().getRetrofit().create(UserApi.class);
    }

    public void login(Subscriber<UserModel> subscriber, String userName, String password) {
        CommonRepository.processResult(mUserApi.login(userName, password), new HttpResultFunc<UserModel>(),
                subscriber);

    }

    public void register(Subscriber<UserModel> subscriber, String params) {
        CommonRepository.processResult(mUserApi.register(params), new HttpResultFunc<UserModel>(),
                subscriber);
    }
}
