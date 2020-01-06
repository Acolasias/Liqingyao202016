package com.bawei.lqy.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawei.lqy.R;
import com.bawei.lqy.base.BaseActivity;
import com.bawei.lqy.contract.IMainContract;
import com.bawei.lqy.database.DaoMaster;
import com.bawei.lqy.database.DaoSession;
import com.bawei.lqy.database.LeftGsonBeanDao;
import com.bawei.lqy.database.RightGsonBeanDao;
import com.bawei.lqy.model.bean.GsonBeanLeft;
import com.bawei.lqy.model.bean.GsonBeanRight;
import com.bawei.lqy.model.dao.LeftGsonBean;
import com.bawei.lqy.model.dao.RightGsonBean;
import com.bawei.lqy.presenter.IMainPresenter;
import com.bawei.lqy.utile.NetUtile;
import com.bawei.lqy.view.adapter.MyAdapterLeft;
import com.bawei.lqy.view.adapter.MyAdapterRight;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<IMainPresenter> implements IMainContract.IView {

    @BindView(R.id.rv_Left)
    RecyclerView rv_Left;
    @BindView(R.id.rv_Right)
    RecyclerView rv_Right;
    private LeftGsonBeanDao leftGsonBeanDao;
    private RightGsonBeanDao rightGsonBeanDao;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    protected void initData() {
        DaoSession daoSession = DaoMaster.newDevSession(this, "app.db");
        leftGsonBeanDao = daoSession.getLeftGsonBeanDao();
        rightGsonBeanDao = daoSession.getRightGsonBeanDao();
        if(NetUtile.getInstance().hasNet(this)){
            mPresenter.onLeftData();
        }else {
            LeftGsonBean unique = leftGsonBeanDao.queryBuilder().unique();
            String getJson = unique.getGetJson();
            GsonBeanLeft beanLeft = new Gson().fromJson(getJson, GsonBeanLeft.class);
            List<String> category = beanLeft.getCategory();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            rv_Left.setLayoutManager(linearLayoutManager);
            MyAdapterLeft myAdapterLeft = new MyAdapterLeft(category);
            myAdapterLeft.setOnItemClickListener(new MyAdapterLeft.onItemClickListener() {
                @Override
                public void onItemClick( int position) {
                    List<RightGsonBean> list = rightGsonBeanDao.queryBuilder().list();
                    RightGsonBean rightGsonBean = list.get(position);
                    String json = rightGsonBean.getGetJson();
                    GsonBeanRight beanRight = new Gson().fromJson(json, GsonBeanRight.class);
                    List<GsonBeanRight.DataBean> data = beanRight.getData();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                    rv_Right.setLayoutManager(gridLayoutManager);
                    MyAdapterRight myAdapterRight = new MyAdapterRight(data);
                    rv_Right.setAdapter(myAdapterRight);
                }
            });
            rv_Left.setAdapter(myAdapterLeft);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected IMainPresenter providerPresenter() {
        return new IMainPresenter();
    }

    @Override
    protected int LayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onLeftSueccess(GsonBeanLeft beanLeft) {
        List<String> category = beanLeft.getCategory();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_Left.setLayoutManager(linearLayoutManager);
        MyAdapterLeft myAdapterLeft = new MyAdapterLeft(category);
        myAdapterLeft.setOnItemClickListener(new MyAdapterLeft.onItemClickListener() {
            @Override
            public void onItemClick( int position) {
                String s = category.get(position);
                EventBus.getDefault().post(s);
            }
        });
        rv_Left.setAdapter(myAdapterLeft);
        String s = beanLeft.getCategory().get(0);
        mPresenter.onRightData(s);
        leftGsonBeanDao.deleteAll();
        String json = new Gson().toJson(beanLeft);
        LeftGsonBean leftGsonBean = new LeftGsonBean(json);
        leftGsonBeanDao.insert(leftGsonBean);
    }

    @Override
    public void onLeftFailure(Throwable throwable) {
        Toast.makeText(this, "左侧列表请求失败", Toast.LENGTH_SHORT).show();
        Log.i("xxx", throwable.getMessage());
    }

    @Override
    public void onRightSueccess(GsonBeanRight beanRight) {

        List<GsonBeanRight.DataBean> data = beanRight.getData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rv_Right.setLayoutManager(gridLayoutManager);
        MyAdapterRight myAdapterRight = new MyAdapterRight(data);
        myAdapterRight.setOnItemClickListener(new MyAdapterRight.onItemClickListener() {
            @Override
            public void onItemClick(String name) {
                Toast.makeText(MainActivity.this, ""+name, Toast.LENGTH_SHORT).show();
            }
        });
        rv_Right.setAdapter(myAdapterRight);
        String s = new Gson().toJson(beanRight);
        RightGsonBean rightGsonBean = new RightGsonBean(s);
        rightGsonBeanDao.insert(rightGsonBean);
    }

    @Subscribe
    public void onEventData(String string){
        mPresenter.onRightData(string);
    }
    @Override
    public void onRightFailure(Throwable throwable) {

        Toast.makeText(this, "右侧商品请求失败", Toast.LENGTH_SHORT).show();
        Log.i("xxx", throwable.getMessage());
    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
