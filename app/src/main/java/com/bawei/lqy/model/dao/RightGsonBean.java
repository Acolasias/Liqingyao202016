package com.bawei.lqy.model.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Time:2020/1/6 0006上午 09:54202001
 * 邮箱:2094158527@qq.com
 * 作者:李庆瑶
 * 类功能:
 */
@Entity
public class RightGsonBean {
    String getJson;

    @Generated(hash = 1767702084)
    public RightGsonBean(String getJson) {
        this.getJson = getJson;
    }

    @Generated(hash = 296606651)
    public RightGsonBean() {
    }

    public String getGetJson() {
        return this.getJson;
    }

    public void setGetJson(String getJson) {
        this.getJson = getJson;
    }
}
