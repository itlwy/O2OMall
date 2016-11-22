package itlwy.com.o2omall.data.product;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import itlwy.com.o2omall.data.HttpResultModel;
import itlwy.com.o2omall.data.product.model.AdvertModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mac on 16/11/21.
 */

public interface ProductAPI {
    /**
     * 获取首页广告栏广告
     * @return
     */
    @GET("product/advert")
    Observable<HttpResultModel<AdvertModel>> getAdvertInfo();

    /**
     * 获取首页的热销商品列表
     *
     * @param pageNum 页数
     * @param count   每页的数量
     * @return
     */
    @GET("product/home_products")
    Observable<HttpResultModel<List<JsonObject>>> getHomeProducts(@Query("pageNum") int pageNum, @Query("count") int count);
}
