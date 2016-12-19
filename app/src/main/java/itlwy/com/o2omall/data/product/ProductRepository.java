package itlwy.com.o2omall.data.product;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lndroid.lndroidlib.data.HttpException;
import com.lndroid.lndroidlib.data.HttpResultFunc;

import java.util.ArrayList;
import java.util.List;

import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.CommonRepository;
import itlwy.com.o2omall.data.product.model.AdvertModel;
import itlwy.com.o2omall.data.product.model.CategoryOneModel;
import itlwy.com.o2omall.data.product.model.CategoryTwoModel;
import itlwy.com.o2omall.data.product.model.OrdersModel;
import itlwy.com.o2omall.data.product.model.ProductModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mac on 16/11/21.
 */

public class ProductRepository {
// revert 测试
    private ProductAPI mProductAPI;

    public ProductRepository() {
        mProductAPI = CommonRepository.getInstance().getRetrofit().create(ProductAPI.class);
    }

    public void getAdvertInfo(Subscriber<List<AdvertModel>> subscriber) {
        CommonRepository.processResult(mProductAPI.getAdvertInfo(), new HttpResultFunc<List<AdvertModel>>(),
                subscriber);
    }

    public void getCategoryInfo(Subscriber<List<CategoryOneModel>> subscriber) {
        mProductAPI.getCategoryInfo().map(new HttpResultFunc<List<JsonObject>>())
                .map(new Func1<List<JsonObject>, List<CategoryOneModel>>() {
                    @Override
                    public List<CategoryOneModel> call(List<JsonObject> resultArr) {
                        List<CategoryOneModel> categoryOneModels = new ArrayList<>();
                        CategoryOneModel categoryOneModel = new CategoryOneModel();
                        List<CategoryTwoModel> categoryTwoModels = new ArrayList<>();
                        int prevCategory1ID = 0;
                        JsonObject prevItemJson = null;
                        for (int i = 0; i < resultArr.size(); i++) {
                            JsonObject itemJson = resultArr.get(i);
                            int curCategory1ID = itemJson.get("category1ID").getAsInt();
                            if (prevCategory1ID != 0 && curCategory1ID != prevCategory1ID) {
                                categoryOneModel.setTwoList(categoryTwoModels);
                                categoryOneModel.setName(prevItemJson.get("category1name").getAsString());
                                categoryOneModel.setId(prevItemJson.get("category1ID").getAsInt());
                                categoryOneModels.add(categoryOneModel);
                                categoryOneModel = new CategoryOneModel();
                                categoryTwoModels = new ArrayList<>();
                            }
                            CategoryTwoModel categoryTwoModel = new CategoryTwoModel();
                            categoryTwoModel.setId(itemJson.get("category2ID").getAsInt());
                            categoryTwoModel.setName(itemJson.get("category2name").getAsString());
                            if (!itemJson.get("category2image").isJsonNull()) {
                                categoryTwoModel.setImgUrl(itemJson.get("category2image").getAsString());
                            }
                            categoryTwoModels.add(categoryTwoModel);
                            if (i == (resultArr.size() - 1)) {  // last one
                                categoryOneModel.setTwoList(categoryTwoModels);
                                categoryOneModel.setName(itemJson.get("category1name").getAsString());
                                categoryOneModel.setId(itemJson.get("category1ID").getAsInt());
                                categoryOneModels.add(categoryOneModel);
                                break;
                            }
                            prevItemJson = itemJson;
                            prevCategory1ID = curCategory1ID;

                        }
                        if (categoryOneModels.size() == 0) {
                            throw new HttpException("获取数据为空",HttpException.NO_DATA);
                        } else
                            return categoryOneModels;
                    }
                }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getHomeProducts(Subscriber<List<ProductModel>> subscriber, int pageNum, int count) {
        CommonRepository.processResult(mProductAPI.getHomeProducts(pageNum, count), new HttpResultFunc<List<ProductModel>>(),
                subscriber);
//        mProductAPI.getHomeProducts(pageNum, count).map(new HttpResultFunc<List<JsonObject>>())
//                .map(new Func1<List<JsonObject>, List<ProductModel>>() {
//                    @Override
//                    public List<ProductModel> call(List<JsonObject> resultArr) {
//                        List<ProductModel> productList = new ArrayList<>();
//                        ProductModel productModel = new ProductModel();
//                        List<ProductModel.ProductAtt> attList = new ArrayList<>();
//                        int prevProductID = 0;
//                        JsonObject prevItemJson = null;
//                        for (int i = 0; i < resultArr.size(); i++) {
//                            JsonObject itemJson = resultArr.get(i);
//                            int curProductID = itemJson.get("productID").getAsInt();
//                            if (prevProductID != 0 && curProductID != prevProductID) {
//                                productModel.setProductAtts(attList);
//                                productModel.setCategory1Id(prevItemJson.get("category1ID").getAsInt());
//                                productModel.setCategory2Id(prevItemJson.get("category2ID").getAsInt());
//                                productModel.setClassify(prevItemJson.get("classify").getAsString());
//                                productModel.setHotLevel(prevItemJson.get("hotLevel").getAsInt());
//                                productModel.setName(prevItemJson.get("name").getAsString());
//                                productModel.setPrice((float) prevItemJson.get("price").getAsDouble());
//                                productModel.setProductID(curProductID);
//                                productModel.setSize(prevItemJson.get("size").getAsString());
//                                productList.add(productModel);
//                                productModel = new ProductModel();
//                                attList = new ArrayList<>();
//                            }
//                            ProductModel.ProductAtt att = new ProductModel.ProductAtt();
//                            if (!itemJson.get("imageUrl").isJsonNull()) {
//                                att.setAttUrl(itemJson.get("imageUrl").getAsString());
//                            }
//                            attList.add(att);
//                            if (i == (resultArr.size() - 1)) {  // last one
//                                productModel.setProductAtts(attList);
//                                productModel.setCategory1Id(itemJson.get("category1ID").getAsInt());
//                                productModel.setCategory2Id(itemJson.get("category2ID").getAsInt());
//                                productModel.setClassify(itemJson.get("classify").getAsString());
//                                productModel.setHotLevel(itemJson.get("hotLevel").getAsInt());
//                                productModel.setName(itemJson.get("name").getAsString());
//                                productModel.setPrice((float) itemJson.get("price").getAsDouble());
//                                productModel.setProductID(curProductID);
//                                productModel.setSize(itemJson.get("size").getAsString());
//                                productList.add(productModel);
//                                break;
//                            }
//                            prevItemJson = itemJson;
//                            prevProductID = curProductID;
//
//                        }
//                        if (productList.size() == 0) {
//                            throw new HttpException("获取数据为空");
//                        } else
//                            return productList;
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
    }

    public void getProductList(Subscriber<List<ProductModel>> subscriber, int category2ID) {
        CommonRepository.processResult(mProductAPI.getProductList(category2ID), new HttpResultFunc<List<ProductModel>>(),
                subscriber);
    }

    public void getProductAtts(Subscriber<List<ProductModel.ProductAtt>> subscriber, int productID) {
        CommonRepository.processResult(mProductAPI.getProductAtts(productID), new HttpResultFunc<List<ProductModel.ProductAtt>>(),
                subscriber);
    }

    public void submitOrder(Subscriber<String> subscriber, OrdersModel orderModel) {
        Gson gson = new Gson();
        CommonRepository.processResult(mProductAPI.submitOrder(ClientKernal.getInstance().getUserModel()
                .getToken(), gson.toJson(orderModel)), new HttpResultFunc<String>(), subscriber);
    }
}
