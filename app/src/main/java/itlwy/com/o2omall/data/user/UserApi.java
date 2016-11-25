package itlwy.com.o2omall.data.user;

import java.util.List;

import itlwy.com.o2omall.data.HttpResultModel;
import itlwy.com.o2omall.data.user.model.AddressModel;
import itlwy.com.o2omall.data.user.model.UserModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mac on 16/11/19.
 */

public interface UserApi {
    @POST("user/login")
    @FormUrlEncoded
    Observable<HttpResultModel<UserModel>> login(@Field("username") String userName,
                                                 @Field("password") String password);

    @POST("user/register")
    @FormUrlEncoded
    Observable<HttpResultModel<UserModel>> register(@Field("params") String params);

    @POST("user/address_add")
    @FormUrlEncoded
    Observable<HttpResultModel<String>> addAddress(@Field("params") String params);

    @POST("user/address_update")
    @FormUrlEncoded
    Observable<HttpResultModel<String>> updateAddress(@Field("params") String params);

    @POST("user/address_delete")
    @FormUrlEncoded
    Observable<HttpResultModel<String>> deleteAddress(@Field("addressID") int addressID);

    @GET("user/address_list")
    Observable<HttpResultModel<List<AddressModel>>> getOwnAddressList(@Query("userID") int userID);


//    @POST("user/upload_my_logo")
//    @FormUrlEncoded
//    Observable<HttpResultModel<String>> uploadMyLogo(@Field("imageName") String imageName,
//                                                     @Field("imageSuffix") String imageSuffix);
}
