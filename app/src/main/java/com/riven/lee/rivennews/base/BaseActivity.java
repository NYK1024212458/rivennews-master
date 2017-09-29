package com.riven.lee.rivennews.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.riven.lee.rivennews.app.APP;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by rivenLee
 * 2017/9/27.
 * O -))))>MVP Activity的基类
 */

public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView {

    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        if(mPresenter != null){
            mPresenter.attachView(this);
        }
        APP.getInstance().addActivity(this);
        initEventAndData();
    }

    protected void setToolBar(Toolbar toolbar, String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //添加返回按钮
        getSupportActionBar().setDisplayShowHomeEnabled(true);   //是否显示左上角的图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
        }
        mUnBinder.unbind();
        APP.getInstance().removeActivity(this);
    }

    protected abstract int getLayout();
    protected abstract void initEventAndData();
}
