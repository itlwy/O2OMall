package itlwy.com.o2omall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/2/17.
 */
public class CategoryTwo implements Parcelable {
    public static final String Tag = "CategoryTwo";
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
    public String toString() {
        return "SecondCategoryData [id=" + id + ", name=" + name + ", imgUrl="
                + imgUrl + "]";
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

    public CategoryTwo() {
    }

    protected CategoryTwo(Parcel in) {
        this.previousId = in.readInt();
        this.id = in.readInt();
        this.name = in.readString();
        this.imgUrl = in.readString();
    }

    public static final Parcelable.Creator<CategoryTwo> CREATOR = new Parcelable.Creator<CategoryTwo>() {
        public CategoryTwo createFromParcel(Parcel source) {
            return new CategoryTwo(source);
        }

        public CategoryTwo[] newArray(int size) {
            return new CategoryTwo[size];
        }
    };
}
