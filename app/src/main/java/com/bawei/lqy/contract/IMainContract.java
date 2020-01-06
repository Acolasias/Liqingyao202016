package com.bawei.lqy.contract;

import com.bawei.lqy.model.bean.GsonBeanLeft;
import com.bawei.lqy.model.bean.GsonBeanRight;

/**
 * Time:2020/1/6 0006上午 09:01202001
 * 邮箱:2094158527@qq.com
 * 作者:李庆瑶
 * 类功能:
 */
public interface IMainContract {

    interface IView{
        void onLeftSueccess(GsonBeanLeft beanLeft);
        void onLeftFailure(Throwable throwable);
        void onRightSueccess(GsonBeanRight beanRight);
        void onRightFailure(Throwable throwable);
    }

    interface IPresenter{
        void onLeftData();
        void onRightData(String category);
    }

    interface IModel{
        void onLeftData(ILeftCallback iLeftCallback);
        interface ILeftCallback{
            void onLeftSueccess(GsonBeanLeft beanLeft);
            void onLeftFailure(Throwable throwable);
        }
        void onRightData(String category,IRightCallback iRightCallback);
        interface IRightCallback{
            void onRightSueccess(GsonBeanRight beanRight);
            void onRightFailure(Throwable throwable);
        }
    }
}
