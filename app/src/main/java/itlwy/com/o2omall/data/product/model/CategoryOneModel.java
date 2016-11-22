package itlwy.com.o2omall.data.product.model;

import java.util.List;

/**
 * Created by Administrator on 2016/2/17.
 */
public class CategoryOneModel {
    private int id;

    private String name;

    private List<CategoryTwoModel> twoList;

    public List<CategoryTwoModel> getTwoList() {
        return twoList;
    }

    public void setTwoList(List<CategoryTwoModel> twoList) {
        this.twoList = twoList;
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

    @Override
    public String toString() {
        return "CategoryData [id=" + id + ", name=" + name + "]";
    }
}
