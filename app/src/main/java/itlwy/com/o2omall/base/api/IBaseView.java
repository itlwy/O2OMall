package itlwy.com.o2omall.base.api;

/**
 * Created by mac on 16/10/2.
 */

public interface IBaseView<T> {
    void showLoadingView();
    void showEmptyView();
    void showErrorView();
    void showSuccessView();
    void setPresenter(T presenter);
}
