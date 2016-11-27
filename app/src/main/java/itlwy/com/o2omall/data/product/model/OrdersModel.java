package itlwy.com.o2omall.data.product.model;

import java.util.List;

/**
 * Created by mac on 16/10/6.
 */

public class OrdersModel {
    private String ordersID;
    private int addressID;
    private String payWay;
    private int state;
    private String distributionWay;
    private String distributionNum;
    private double distributionPrice;
    private double totalPrice;
    private double actualPrice;
    private int userID;
    private String userName;
    private List<ProductModel> products;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public String getOrdersID() {
        return ordersID;
    }

    public void setOrdersID(String ordersID) {
        this.ordersID = ordersID;
    }


    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDistributionWay() {
        return distributionWay;
    }

    public void setDistributionWay(String distributionWay) {
        this.distributionWay = distributionWay;
    }

    public String getDistributionNum() {
        return distributionNum;
    }

    public void setDistributionNum(String distributionNum) {
        this.distributionNum = distributionNum;
    }

    public double getDistributionPrice() {
        return distributionPrice;
    }

    public void setDistributionPrice(double distributionPrice) {
        this.distributionPrice = distributionPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
