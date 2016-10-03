package itlwy.com.o2omall.home.presenter;

import itlwy.com.o2omall.base.BasePresenter;
import itlwy.com.o2omall.home.contract.MyContract;

/**
 * Created by mac on 16/10/3.
 */

public class MyPresenter extends BasePresenter implements MyContract.IMyPresenter {

    private MyContract.IMyView view;

    public MyPresenter(MyContract.IMyView view ) {
        this.view  = view;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        view.bindViewDatas("");
        view.showSuccessView();
    }

    public static MyPresenter newInstance(MyContract.IMyView view ){
        return new MyPresenter(view);
    }
}
