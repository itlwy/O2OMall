package itlwy.com.o2omall.data.model;

import java.util.List;

/**
 * Created by Administrator on 2016/2/17.
 */
public class CategoryOne {
    private int id;

    private String name;

    private List<CategoryTwo> twoList;

    public List<CategoryTwo> getTwoList() {
        return twoList;
    }

    public void setTwoList(List<CategoryTwo> twoList) {
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
