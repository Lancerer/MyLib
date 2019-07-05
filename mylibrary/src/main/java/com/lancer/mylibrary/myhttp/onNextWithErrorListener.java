package com.lancer.mylibrary.myhttp;

public interface onNextWithErrorListener<T> {
    void onNext(T t);

    void onError(Throwable e);
}
