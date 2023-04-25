package com.sunmnet.mediaroom.brand.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class SingleTaskObserver<T> implements Observer<T> {

    private Disposable disposable;

    public Disposable getDisposable() {
        synchronized (this) {
            return disposable;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        synchronized (this) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            disposable = d;
        }
    }

    @Override
    public void onComplete() {

    }

    public void dispose() {
        synchronized (this) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
}
