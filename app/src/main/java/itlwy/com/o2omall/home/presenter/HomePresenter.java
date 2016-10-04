package itlwy.com.o2omall.home.presenter;

import java.util.ArrayList;
import java.util.List;

import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.data.model.Section;
import itlwy.com.o2omall.home.contract.HomeContract;

/**
 * Created by mac on 16/10/3.
 */

public class HomePresenter extends BasePresenter implements HomeContract.IHomePresenter {
    private HomeContract.IHomeView view;

    public HomePresenter(HomeContract.IHomeView view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        load();
    }

    private List<Section> downloadDatas() {
        List<Section> list = new ArrayList<Section>();
        for (int i = 0; i < 5; i++) {
            Section sec = new Section();
            List<Section.Item> items = new ArrayList<Section.Item>();
            sec.setTitle(String.format("组%d", i));
            for (int k = 0; k < 5; k++) {
                Section.Item item = sec.new Item();
                item.setDesc(String.format("描述:%d", k));
                item.setPic("http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg");
                item.setPrice(200);
                items.add(item);
            }
            sec.setItems(items);
            list.add(sec);
        }
        return list;
    }

    private void load() {
        view.showLoadingView();
        view.bindViewDatas(downloadDatas());
        view.showSuccessView();
    }

    public static HomePresenter newInstance(HomeContract.IHomeView view) {
        return new HomePresenter(view);
    }

    @Override
    public List<Section> getMoreDatas() {
        return downloadDatas();
    }
}
