package itlwy.com.o2omall.data;

import itlwy.com.o2omall.data.user.model.UserModel;

/**
 * Created by mac on 16/11/16.
 */
public class ClientKernal {
    private static ClientKernal ourInstance = new ClientKernal();

    public static ClientKernal getInstance() {
        return ourInstance;
    }

    private ClientKernal() {

    }

    private UserModel mUserModel;

    public UserModel getUserModel() {
        return mUserModel;
    }

    public void setUserModel(UserModel userModel) {
        mUserModel = userModel;
    }
}
