package itlwy.com.o2omall.data.product;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import itlwy.com.o2omall.data.CommonRepository;
import itlwy.com.o2omall.data.HttpException;
import itlwy.com.o2omall.data.HttpResultFunc;
import itlwy.com.o2omall.data.product.model.AdvertModel;
import itlwy.com.o2omall.data.product.model.ProductModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mac on 16/11/21.
 */

public class ProductRepository {

    private ProductAPI mProductAPI;

    public ProductRepository() {
        mProductAPI = CommonRepository.getInstance().getRetrofit().create(ProductAPI.class);
    }

    public void getAdvertInfo(Subscriber<AdvertModel> subscriber) {
        CommonRepository.processResult(mProductAPI.getAdvertInfo(), new HttpResultFunc<AdvertModel>(),
                subscriber);
    }

    //    {
//        "productID": 2,
//            "name": "新款气质修身裙11",
//            "price": 128,
//            "classify": "灰色;黑色",
//            "size": "M;L;XL;XXL",
//            "category1ID": 1,
//            "category2ID": 1,
//            "hotLevel": 6,
//            "imageUrl": "http://img2.imgtn.bdimg.com/it/u=1703120248,1445846308&fm=21&gp=0.jpg"
//    }
    public void getHomeProducts(Subscriber<List<ProductModel>> subscriber, int pageNum, int count) {
        mProductAPI.getHomeProducts(pageNum, count).map(new HttpResultFunc<List<JsonObject>>())
                .map(new Func1<List<JsonObject>, List<ProductModel>>() {
                    @Override
                    public List<ProductModel> call(List<JsonObject> resultArr) {
                        List<ProductModel> productList = new ArrayList<>();
                        ProductModel productModel = new ProductModel();
                        List<ProductModel.ProductAtt> attList = new ArrayList<>();
                        int prevProductID = 0;
                        JsonObject prevItemJson = null;
                        for (int i = 0; i < resultArr.size(); i++) {
                            JsonObject itemJson = resultArr.get(i);
                            int curProductID = itemJson.get("productID").getAsInt();
                            if (prevProductID != 0 && curProductID != prevProductID) {
                                productModel.setProductAtts(attList);
                                productModel.setCategory1Id(prevItemJson.get("category1ID").getAsInt());
                                productModel.setCategory2Id(prevItemJson.get("category2ID").getAsInt());
                                productModel.setClassify(prevItemJson.get("classify").getAsString());
                                productModel.setHotLevel(prevItemJson.get("hotLevel").getAsInt());
                                productModel.setName(prevItemJson.get("name").getAsString());
                                productModel.setPrice((float) prevItemJson.get("price").getAsDouble());
                                productModel.setProductID(curProductID);
                                productModel.setSize(prevItemJson.get("size").getAsString());
                                productList.add(productModel);
                                productModel = new ProductModel();
                                attList = new ArrayList<>();
                            }
                            ProductModel.ProductAtt att = new ProductModel.ProductAtt();
                            if (!itemJson.get("imageUrl").isJsonNull()) {
                                att.setAttUrl(itemJson.get("imageUrl").getAsString());
                            }
                            attList.add(att);
                            if (i == (resultArr.size() - 1)) {  // last one
                                productModel.setProductAtts(attList);
                                productModel.setCategory1Id(itemJson.get("category1ID").getAsInt());
                                productModel.setCategory2Id(itemJson.get("category2ID").getAsInt());
                                productModel.setClassify(itemJson.get("classify").getAsString());
                                productModel.setHotLevel(itemJson.get("hotLevel").getAsInt());
                                productModel.setName(itemJson.get("name").getAsString());
                                productModel.setPrice((float) itemJson.get("price").getAsDouble());
                                productModel.setProductID(curProductID);
                                productModel.setSize(itemJson.get("size").getAsString());
                                productList.add(productModel);
                                break;
                            }
                            prevItemJson = itemJson;
                            prevProductID = curProductID;

                        }
                        if (productList.size() == 0) {
                            throw new HttpException("获取数据为空");
                        } else
                            return productList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
