package com.riven.lee.rivennews.base;

/**
 * Created by rivenLee
 * 2017/9/27.
 * O -))))> MVP Presenter的基类
 */

public interface BasePresenter<T extends BaseView>  {

    /**
     * 绑定
     * @param view
     */
    void attachView(T view);

    /**
     * 解绑
     */
    void detachView();

}
