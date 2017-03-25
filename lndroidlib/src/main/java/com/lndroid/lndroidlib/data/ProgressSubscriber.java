package com.lndroid.lndroidlib.data;

import android.content.Context;
import android.widget.Toast;

import com.lndroid.lndroidlib.base.api.IBaseView;
import rx.Subscriber;

/**
 * Created by mac on 16/11/19.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private IBaseView view;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;

    public ProgressSubscriber(Context context, IBaseView view) {
        this.context = context;
        this.view = view;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    public ProgressSubscriber(Context context) {
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    public void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
        if (view != null) {
            view.showSuccessView();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (view != null) {
            if (e instanceof HttpException) {
                if (((HttpException) e).getResultCode() == HttpException.NO_DATA) {
                    view.showEmptyView();
                }
            } else {
                view.showErrorView(e.getMessage());
            }
        } else {
            Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
