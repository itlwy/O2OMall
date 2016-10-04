package itlwy.com.o2omall.product.presenter;

import java.util.ArrayList;
import java.util.List;

import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.data.model.CategoryTwo;
import itlwy.com.o2omall.data.model.Product;
import itlwy.com.o2omall.product.contract.ProductListContract;

/**
 * Created by mac on 16/10/3.
 */

public class ProductListPresenter extends BasePresenter implements ProductListContract.IProductListPresenter {
    private ProductListContract.IProductListView view;

    public ProductListPresenter(ProductListContract.IProductListView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    public static ProductListPresenter newInstance(ProductListContract.IProductListView view) {

        return new ProductListPresenter(view);
    }

    @Override
    public void subscribe() {

    }

    /**
     * 得到url
     */
    private String getUrls() {
        String urlString = "http://i2.sinaimg.cn/IT/h/2009-12-05/1259942752_UQ03Yv.jpg";
        return urlString;
    }

    /**
     * 得到listview数据
     */
    private List<Product> getListData() {
        List<Product> list_datas = new ArrayList<Product>();
        for (int i = 0; i < 10; i++) {
            Product data = new Product();
            data.setId(i);
            data.setImgUrl(getUrls());
            data.setInfo("樱桃（Cherry） G80-3060HLCUS-2 红轴黑橙二色键帽 60周年限量版机械键盘");
            data.setPrice(1953);
            list_datas.add(data);
        }
        return list_datas;
    }

    @Override
    public void subscribe(CategoryTwo categoryTwo) {
        view.showLoadingView();
        view.bindViewDatas(getListData());
        view.showSuccessView();
    }
}
