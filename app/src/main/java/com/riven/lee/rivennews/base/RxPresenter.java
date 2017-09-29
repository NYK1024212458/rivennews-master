package com.riven.lee.rivennews.base;

import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by rivenLee
 * 2017/9/27.
 * O -))))>
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubScribe();
    }

    public void addSubscribe(Disposable disposable){
        if(mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        Log.e("TAG","请求入列");
        mCompositeDisposable.add(disposable);

    }
    protected void unSubScribe(){
        if(mCompositeDisposable != null){
            mCompositeDisposable.clear();
        }
        Log.d("TAG","停止所有的请求");
    }
}
