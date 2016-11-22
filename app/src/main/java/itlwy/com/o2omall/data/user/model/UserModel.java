package itlwy.com.o2omall.data.user.model;

/**
 * Created by mac on 16/10/5.
 */

public class UserModel {
    private Integer userID;
    private String token;
    private String userName;
    private String nickName;
    private String email;
    private String phone;
    private String vipLevel;
    private String logo;
    private String validatedTime;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getValidatedTime() {
        return validatedTime;
    }

    public void setValidatedTime(String validatedTime) {
        this.validatedTime = validatedTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userID=" + userID +
                ", token='" + token + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", vipLevel='" + vipLevel + '\'' +
                ", validatedTime='" + validatedTime + '\'' +
                '}';
    }
}
