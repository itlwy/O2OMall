package com.lndroid.lndroidlib.base.api;

/**
 * Created by mac on 16/10/2.
 */

public interface IBaseView<T> {
//    void showLoadingView();

    void showEmptyView();

    void showErrorView(String error);

    void showSuccessView();

    void showToast(String messaga);

    void killMyself();

    void setPresenter(T presenter);
}
