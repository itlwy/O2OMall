package itlwy.com.o2omall.data.user;

import java.util.List;

import itlwy.com.o2omall.data.user.model.AddressModel;
import itlwy.com.o2omall.data.user.model.UserModel;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by mac on 16/10/4.
 */

public interface UserDataSource {

    void login(Subscriber<UserModel> subscriber, String userName, String password);

    void register(Subscriber<UserModel> subscriber, String params);

    Observable<String> getUploadMyLogoOB(final String imageName);

    void getOwnAddressList(Subscriber<List<AddressModel>> subscriber, int userID);

    void addAddress(Subscriber<String> subscriber, AddressModel addressModel);

    void updateAddress(Subscriber<String> subscriber, AddressModel addressModel);

    void deleteAddress(Subscriber<String> subscriber, int userID);

}
