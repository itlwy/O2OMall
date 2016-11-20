package itlwy.com.o2omall.data.model;

/**
 * Created by mac on 16/10/6.
 */

public class OrdersModel  {
    private String addressObjectID;
    private String payWay;
    private int state;
    private String distributionWay;
    private String distributionNum;
    private double distributionPrice;
    private double totalPrice;
    private double actualPrice;
    private String userObjectID;
    private String userName;

    public String getAddressObjectID() {
        return addressObjectID;
    }

    public void setAddressObjectID(String addressObjectID) {
        this.addressObjectID = addressObjectID;
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

    public String getUserObjectID() {
        return userObjectID;
    }

    public void setUserObjectID(String userObjectID) {
        this.userObjectID = userObjectID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
