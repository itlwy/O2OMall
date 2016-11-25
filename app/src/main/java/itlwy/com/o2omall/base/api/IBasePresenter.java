package itlwy.com.o2omall.base.api;

/**
 * Created by mac on 16/10/2.
 */

public interface IBasePresenter<T> {
    void subscribe(T param);
    void unsubscribe();
}
