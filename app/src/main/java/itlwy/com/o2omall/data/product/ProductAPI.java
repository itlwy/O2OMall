package itlwy.com.o2omall.data.product;

import com.google.gson.JsonObject;
import com.lndroid.lndroidlib.data.HttpResultModel;

import java.util.List;

import itlwy.com.o2omall.data.product.model.AdvertModel;
import itlwy.com.o2omall.data.product.model.ProductModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mac on 16/11/21.
 */

public interface ProductAPI {
    /**
     * 获取首页广告栏广告
     *
     * @return
     */
    @GET("product/advert")
    Observable<HttpResultModel<List<AdvertModel>>> getAdvertInfo();

    /**
     * 获取首页的热销商品列表
     *
     * @param pageNum 页数
     * @param count   每页的数量
     * @return
     */
    @GET("product/home_products")
    Observable<HttpResultModel<List<ProductModel>>> getHomeProducts(@Query("pageNum") int pageNum, @Query("count") int count);
//    Observable<HttpResultModel<List<JsonObject>>> getHomeProducts(@Query("pageNum") int pageNum, @Query("count") int count);

    /**
     * 获取分类信息
     *
     * @return
     */
    @GET("product/category")
    Observable<HttpResultModel<List<JsonObject>>> getCategoryInfo();

    /**
     * 获取分类2下的商品列表
     *
     * @param category2ID
     * @return
     */
    @GET("product/product_list")
    Observable<HttpResultModel<List<ProductModel>>> getProductList(@Query("category2ID") int category2ID);

    /**
     * 返回商品对应的附件列表
     *
     * @param productID
     * @return
     */
    @GET("product/product_atts")
    Observable<HttpResultModel<List<ProductModel.ProductAtt>>> getProductAtts(@Query("productID") int productID);

    @POST("product/submit_orders")
    @FormUrlEncoded
    Observable<HttpResultModel<String>> submitOrder(@Field("token") String token, @Field("param") String param);
}
