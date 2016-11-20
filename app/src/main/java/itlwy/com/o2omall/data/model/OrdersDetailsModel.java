package itlwy.com.o2omall.data.model;

/**
 * Created by mac on 16/10/6.
 */

public class OrdersDetailsModel {
    private String ordersObjectID;
    private String productObjectID;
    private String productName;
    private int count;
    private double totalPrice;

    public String getOrdersObjectID() {
        return ordersObjectID;
    }

    public void setOrdersObjectID(String ordersObjectID) {
        this.ordersObjectID = ordersObjectID;
    }

    public String getProductObjectID() {
        return productObjectID;
    }

    public void setProductObjectID(String productObjectID) {
        this.productObjectID = productObjectID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
