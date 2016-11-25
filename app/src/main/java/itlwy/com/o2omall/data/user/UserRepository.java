package itlwy.com.o2omall.data.user;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.CommonRepository;
import itlwy.com.o2omall.data.HttpResultFunc;
import itlwy.com.o2omall.data.user.model.AddressModel;
import itlwy.com.o2omall.data.user.model.UserModel;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by mac on 16/11/19.
 */

public class UserRepository implements UserDataSource {

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

    public Observable<String> getUploadMyLogoOB(final String imageName) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                File imageFile = new File(BaseApplication.sImagePath + imageName);
                Map<String, String> params = new HashMap<>();
                params.put("userID", String.valueOf(ClientKernal.getInstance().getUserModel().getUserID()));
                params.put("token", ClientKernal.getInstance().getUserModel().getToken());
                try {
                    Response response = OkHttpUtils.post()
                            .addFile("file1", imageName, imageFile)
                            .url(ConstantValue.BASE_URL + "user/upload_my_logo")
                            .params(params)
//                            .headers(headers)//
                            .build()
                            .execute();
                    int code = response.code();
                    if (200 == code) {
                        subscriber.onNext(response.body().string());
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Exception("errorCode:" + code));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });

    }

    public void getOwnAddressList(Subscriber<List<AddressModel>> subscriber, int userID) {
        CommonRepository.processResult(mUserApi.getOwnAddressList(userID), new HttpResultFunc<List<AddressModel>>(),
                subscriber);
    }


    @Override
    public void addAddress(Subscriber<String> subscriber, AddressModel addressModel) {
        Gson gson = new Gson();
        CommonRepository.processResult(mUserApi.addAddress(gson.toJson(addressModel)), new HttpResultFunc<String>(),
                subscriber);
    }

    @Override
    public void updateAddress(Subscriber<String> subscriber, AddressModel addressModel) {
        Gson gson = new Gson();
        CommonRepository.processResult(mUserApi.updateAddress(gson.toJson(addressModel)),
                new HttpResultFunc<String>(), subscriber);
    }

    @Override
    public void deleteAddress(Subscriber<String> subscriber, int addressID) {
        CommonRepository.processResult(mUserApi.deleteAddress(addressID), new HttpResultFunc<String>(), subscriber);
    }

}
