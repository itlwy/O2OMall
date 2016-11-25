package itlwy.com.o2omall.home.presenter;

import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.home.contract.ShopCarContract;

/**
 * Created by mac on 16/10/3.
 */

public class ShopCarPresenter extends BasePresenter implements ShopCarContract.IShopCarPresenter {
    private ShopCarContract.IShopCarView view;

    public ShopCarPresenter(ShopCarContract.IShopCarView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe(Object param) {
        view.showLoadingView();
        view.bindViewDatas(BaseApplication.getProductModelShopcar());
        view.showSuccessView();
    }

    public static ShopCarPresenter newInstance(ShopCarContract.IShopCarView view) {
        return new ShopCarPresenter(view);
    }

//    /**
//     * 从服务器获取购物车商品数据
//     */
//    private List<ProductModel> getCartProducts() {
//        /**
//         * 刷新数量和选中标记集合
//         */
//        ArrayList<ProductModel> productModels = new ArrayList<ProductModel>();
//        for (int i = 0; i < 5; i++) {
//            ProductModel data = new ProductModel();
//            data.setId(i);
//            data.setImgUrl("http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg");
//            data.setInfo("上岛咖啡上岛咖啡上岛咖啡上岛咖啡上岛咖啡上岛咖啡");
//            data.setPrice(120);
//            productModels.add(data);
//        }
//        return productModels;
//    }
}
