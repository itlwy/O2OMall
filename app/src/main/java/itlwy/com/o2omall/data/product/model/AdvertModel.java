package itlwy.com.o2omall.data.product.model;

/**
 * Created by mac on 16/11/21.
 */

public class AdvertModel {
    private int advertID;
    private String imageUrl;
    private String protocol;

    public int getAdvertID() {
        return advertID;
    }

    public void setAdvertID(int advertID) {
        this.advertID = advertID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
