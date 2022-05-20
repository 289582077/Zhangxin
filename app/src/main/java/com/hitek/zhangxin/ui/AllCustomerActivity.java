package com.hitek.zhangxin.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hitek.zhangxin.MyApp;
import com.hitek.zhangxin.R;
import com.hitek.zhangxin.adapter.CustomerAdapter;
import com.hitek.zhangxin.base.BaseActivity;
import com.hitek.zhangxin.entity.CustomerEntity;
import com.hitek.zhangxin.entity.CustomerEntityDao;
import com.hitek.zhangxin.entity.DaoSession;

import java.util.ArrayList;
import java.util.List;

public class AllCustomerActivity extends BaseActivity {


    private RecyclerView mRecyclerView;
    private CustomerAdapter mAdapter;
    private List<CustomerEntity> mCustomerData;
    private CustomerEntityDao mCustomerEntityDao;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_all_customer;
    }

    @Override
    protected void initView() {
        DaoSession daoSession = ((MyApp) getApplication()).getDaoSession();
        mCustomerEntityDao = daoSession.getCustomerEntityDao();
        mCustomerData= new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_customer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CustomerAdapter(R.layout.customer_item, null);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mAdapter.setNewData(getCustomerData());
    }

    @Override
    protected void initListener() {

    }

    public List<CustomerEntity> getCustomerData() {

        return mCustomerEntityDao.loadAll();
    }
}
