package itlwy.com.o2omall.data;

import android.content.Context;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by mac on 16/11/19.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;

    public ProgressSubscriber(Context context) {
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
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
        Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
