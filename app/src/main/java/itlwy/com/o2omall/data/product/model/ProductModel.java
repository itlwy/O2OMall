package itlwy.com.o2omall.data.product.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 16/10/4.
 */

public class ProductModel implements Parcelable {
    public static final String Tag = "ProductModel";
    private int productID;
    private int category1Id;
    private int category2Id;
    private int hotLevel;
    private String name;
    private float price;
    private String classify;
    private String size;
    private List<ProductAtt> productAtts;
    private int num = 1;
    private boolean isCheck;

    public int getHotLevel() {
        return hotLevel;
    }

    public void setHotLevel(int hotLevel) {
        this.hotLevel = hotLevel;
    }

    public List<ProductAtt> getProductAtts() {
        return productAtts;
    }

    public void setProductAtts(List<ProductAtt> productAtts) {
        this.productAtts = productAtts;
    }


    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public int getCategory1Id() {
        return category1Id;
    }

    public void setCategory1Id(int category1Id) {
        this.category1Id = category1Id;
    }

    public int getCategory2Id() {
        return category2Id;
    }

    public void setCategory2Id(int category2Id) {
        this.category2Id = category2Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public static class ProductAtt {
        private int attID;
        private String attUrl;

        public int getAttID() {
            return attID;
        }

        public void setAttID(int attID) {
            this.attID = attID;
        }

        public String getAttUrl() {
            return attUrl;
        }

        public void setAttUrl(String attUrl) {
            this.attUrl = attUrl;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.productID);
        dest.writeInt(this.category1Id);
        dest.writeInt(this.category2Id);
        dest.writeInt(this.hotLevel);
        dest.writeString(this.name);
        dest.writeFloat(this.price);
        dest.writeString(this.classify);
        dest.writeString(this.size);
        dest.writeList(this.productAtts);
        dest.writeInt(this.num);
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
    }

    public ProductModel() {
    }

    protected ProductModel(Parcel in) {
        this.productID = in.readInt();
        this.category1Id = in.readInt();
        this.category2Id = in.readInt();
        this.hotLevel = in.readInt();
        this.name = in.readString();
        this.price = in.readFloat();
        this.classify = in.readString();
        this.size = in.readString();
        this.productAtts = new ArrayList<ProductAtt>();
        in.readList(this.productAtts, ProductAtt.class.getClassLoader());
        this.num = in.readInt();
        this.isCheck = in.readByte() != 0;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel source) {
            return new ProductModel(source);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };
}
