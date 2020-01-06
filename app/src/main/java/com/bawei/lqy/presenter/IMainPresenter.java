package com.bawei.lqy.presenter;

import com.bawei.lqy.base.BasePresenter;
import com.bawei.lqy.contract.IMainContract;
import com.bawei.lqy.model.IMainModel;
import com.bawei.lqy.model.bean.GsonBeanLeft;
import com.bawei.lqy.model.bean.GsonBeanRight;

/**
 * Time:2020/1/6 0006上午 09:19202001
 * 邮箱:2094158527@qq.com
 * 作者:李庆瑶
 * 类功能:
 */
public class IMainPresenter extends BasePresenter<IMainContract.IView> implements IMainContract.IPresenter {

    private IMainModel iMainModel;

    @Override
    protected void initModel() {
        iMainModel = new IMainModel();
    }

    @Override
    public void onLeftData() {

        iMainModel.onLeftData(new IMainContract.IModel.ILeftCallback() {
            @Override
            public void onLeftSueccess(GsonBeanLeft beanLeft) {
                view.onLeftSueccess(beanLeft);
            }

            @Override
            public void onLeftFailure(Throwable throwable) {

                view.onLeftFailure(throwable);
            }
        });
    }

    @Override
    public void onRightData(String category) {

        iMainModel.onRightData(category, new IMainContract.IModel.IRightCallback() {
            @Override
            public void onRightSueccess(GsonBeanRight beanRight) {
                view.onRightSueccess(beanRight);
            }

            @Override
            public void onRightFailure(Throwable throwable) {

                view.onRightFailure(throwable);
            }
        });
    }
}
