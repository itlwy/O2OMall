package itlwy.com.o2omall;

/**
 * Created by Administrator on 2016/2/22.
 */
public enum  FragmentTag {
    TAG_HOME("itlwy.com.o2omall.fragment.HomeFragment"),
    TAG_CATEGORY("itlwy.com.o2omall.fragment.CategoryFragment"),
    TAG_SCAN("com.wiipu.mall.fragment.ScanFragment"),
    TAG_CART("itlwy.com.o2omall.fragment.ShopCarFragment"),
    TAG_MY("itlwy.com.o2omall.fragment.MyFragment");

    String tag;

    FragmentTag(String tag) {
        this.tag = tag;
    }
    public String getTag(){
        return tag;
    }
}
