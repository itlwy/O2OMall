package itlwy.com.o2omall.data.user.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mac on 16/10/6.
 */

public class AddressModel implements Parcelable {
    private String detailsAddress;
    private String phone;
    private String reg;
    private String receiver;
    private int userID;
    private int addressID;
    private int isDefault;

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getDetailsAddress() {
        return detailsAddress;
    }

    public void setDetailsAddress(String detailsAddress) {
        this.detailsAddress = detailsAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.detailsAddress);
        dest.writeString(this.phone);
        dest.writeString(this.reg);
        dest.writeString(this.receiver);
        dest.writeInt(this.userID);
        dest.writeInt(this.addressID);
        dest.writeInt(this.isDefault);
    }

    public AddressModel() {
    }

    protected AddressModel(Parcel in) {
        this.detailsAddress = in.readString();
        this.phone = in.readString();
        this.reg = in.readString();
        this.receiver = in.readString();
        this.userID = in.readInt();
        this.addressID = in.readInt();
        this.isDefault = in.readInt();
    }

    public static final Parcelable.Creator<AddressModel> CREATOR = new Parcelable.Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel source) {
            return new AddressModel(source);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };
}
