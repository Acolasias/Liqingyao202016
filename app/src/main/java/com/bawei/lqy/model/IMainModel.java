package com.bawei.lqy.model;

import android.util.Log;

import com.bawei.lqy.contract.IMainContract;
import com.bawei.lqy.model.bean.GsonBeanLeft;
import com.bawei.lqy.model.bean.GsonBeanRight;
import com.bawei.lqy.utile.NetUtile;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Time:2020/1/6 0006上午 09:17202001
 * 邮箱:2094158527@qq.com
 * 作者:李庆瑶
 * 类功能:
 */
public class IMainModel implements IMainContract.IModel {
    @Override
    public void onLeftData(ILeftCallback iLeftCallback) {
        NetUtile.getInstance().getApi().getLeft()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GsonBeanLeft>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GsonBeanLeft beanLeft) {

                        iLeftCallback.onLeftSueccess(beanLeft);
                        //Log.i("xxx",beanLeft+"");
                    }

                    @Override
                    public void onError(Throwable e) {

                        iLeftCallback.onLeftFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onRightData(String category, IRightCallback iRightCallback) {

        NetUtile.getInstance().getApi().getRight(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GsonBeanRight>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GsonBeanRight beanRight) {

                        iRightCallback.onRightSueccess(beanRight);
                    }

                    @Override
                    public void onError(Throwable e) {

                        iRightCallback.onRightFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
