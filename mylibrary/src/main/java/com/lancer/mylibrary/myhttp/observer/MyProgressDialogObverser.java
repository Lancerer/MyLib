package com.lancer.mylibrary.myhttp.observer;


import android.app.ProgressDialog;
import android.content.Context;
import com.james.android.library.http.subscriber.OnNextListener;
import com.james.android.library.http.subscriber.OnNextWithErrorListener;
import com.lancer.serviceandreceiver.myhttp.RxExceptionUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MyProgressDialogObverser<T> implements Observer<T> {

    private static final String TAG = MyProgressDialogObverser.class.getSimpleName();
    private OnNextListener<T> mTOnNextListener;
    private OnNextWithErrorListener mOnNextWithErrorListener;
    private ProgressDialog mProgressDialog;
    private boolean mIsshowing = false;
    private Disposable d;
    private Context mContext;

    public MyProgressDialogObverser(OnNextListener<T> TOnNextListener, Context context) {
        mTOnNextListener = TOnNextListener;
        mContext = context;
    }

    public MyProgressDialogObverser(OnNextWithErrorListener onNextWithErrorListener, Context context) {
        mOnNextWithErrorListener = onNextWithErrorListener;
        mContext = context;
    }


    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        if (mProgressDialog == null && mIsshowing == false) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("正在加载中");
            mProgressDialog.show();
            mIsshowing = true;
        }
    }

    @Override
    public void onNext(T t) {
        if (mTOnNextListener != null) {
            mTOnNextListener.onNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        RxExceptionUtil.exceptionHandler(e);
        hidDialog();

    }

    @Override
    public void onComplete() {
        if (mIsshowing) {
            hidDialog();
        }
    }

    public void hidDialog() {
        if (mProgressDialog != null && mIsshowing == true)
            mProgressDialog.dismiss();
        mProgressDialog = null;
    }
}
