package itlwy.com.o2omall;

/**
 * Created by Administrator on 2015/12/22.
 */
public interface ConstantValue {
    /* fragment功能项*/
    String HOMEFRAGMENT = "itlwy.com.o2omall.home.fragment.HomeFragment";  // 主(home) 界面
    String MYFRAGMENT = "itlwy.com.o2omall.home.fragment.MyFragment";   // 我的 界面
    String CATEGORYFRAGMENT = "itlwy.com.o2omall.home.fragment.CategoryFragment";  // 分类界面
    String SHOPCARFRAGMENT = "itlwy.com.o2omall.home.fragment.ShopCarFragment";   //  （主界面）购物车界面
    String SHOPCARFRAGMENTALONE = "itlwy.com.o2omall.home.fragment.ShopCarFragment";  //  单独的购物车界面

    String PRODUCTLISTFRAGMENT = "itlwy.com.o2omall.product.fragment.ProductListFragment"; // 商品列表界面
    String PRODUCTFRAGMENT = "itlwy.com.o2omall.product.fragment.ProductFragment";   // 商品详情界面
    String LOGINFRAGMENT = "itlwy.com.o2omall.user.login.LoginFragment";    //  登陆界面

    String ADDRESSMANAGERFRAGMENT = "itlwy.com.o2omall.user.address.AddressManagerFragment"; // 地址管理界面
    String ADDRESSADDFRAGMENT = "itlwy.com.o2omall.user.address.AddressAddFragment";   // 添加地址界面
    String ADDRESSEDITFRAGMENT = "itlwy.com.o2omall.user.address.AddressEditFragment";   //  地址编辑界面

    String ORDERPRODUCTSFRAGMENT = "itlwy.com.o2omall.product.fragment.OrderProductsFragment";  //  订单 商品列表
    String ORDERFRAGMENT = "itlwy.com.o2omall.product.fragment.OrderFragment";   //  订单信息填写界面

    /* fragment功能项*/

//    String BASE_URL = "http://192.168.0.189:8088/api/";
    String BASE_URL = "http://192.168.1.101:8088/api/";
//    String BASE_URL = "http://10.1.48.23:8088/api/";
}
