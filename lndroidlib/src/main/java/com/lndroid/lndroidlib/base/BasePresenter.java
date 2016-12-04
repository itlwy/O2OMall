package com.lndroid.lndroidlib.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mac on 16/10/2.
 */

public abstract class BasePresenter {
    protected CompositeSubscription mSubscriptions;

    public BasePresenter() {
        mSubscriptions = new CompositeSubscription();
    }

    public void unsubscribe() {
        mSubscriptions.clear();
    }

    public void addSubscriber(Subscription s) {
        mSubscriptions.add(s);
    }

}
