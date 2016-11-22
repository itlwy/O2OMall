package itlwy.com.o2omall.data.product.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mac on 16/10/4.
 */

public class CategoryTwoModel implements Parcelable {
    public static final String Tag = "CategoryTwoModel";
    private int previousId;
    private int id;
    private String name;
    private String imgUrl;

    public int getPreviousId() {
        return previousId;
    }

    public void setPreviousId(int previousId) {
        this.previousId = previousId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.previousId);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imgUrl);
    }

    public CategoryTwoModel() {
    }

    protected CategoryTwoModel(Parcel in) {
        this.previousId = in.readInt();
        this.id = in.readInt();
        this.name = in.readString();
        this.imgUrl = in.readString();
    }

    public static final Parcelable.Creator<CategoryTwoModel> CREATOR = new Parcelable.Creator<CategoryTwoModel>() {
        @Override
        public CategoryTwoModel createFromParcel(Parcel source) {
            return new CategoryTwoModel(source);
        }

        @Override
        public CategoryTwoModel[] newArray(int size) {
            return new CategoryTwoModel[size];
        }
    };
}
