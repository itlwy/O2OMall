package com.lndroid.lndroidlib.base.api;

/**
 * Created by mac on 16/10/2.
 */

public interface IBasePresenter<T> {
    void subscribe(T param);
    void unsubscribe();
}
