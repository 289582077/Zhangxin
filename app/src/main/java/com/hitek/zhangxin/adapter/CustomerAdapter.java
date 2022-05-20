package com.hitek.zhangxin.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hitek.zhangxin.R;
import com.hitek.zhangxin.entity.CustomerEntity;

import java.util.List;

/**
 * @author zzj
 * @date 2018/7/6
 */
public class CustomerAdapter extends BaseQuickAdapter<CustomerEntity,BaseViewHolder>{
    public CustomerAdapter(int layoutResId, @Nullable List<CustomerEntity> data) {
        super(layoutResId, data);
    }

    public CustomerAdapter(@Nullable List<CustomerEntity> data) {
        super(data);
    }

    public CustomerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomerEntity item) {
        helper.setText(R.id.tv_rv_balance,"余额："+item.getBalance());
        helper.setText(R.id.tv_rv_name,"姓名："+item.getName());
        helper.setText(R.id.tv_rv_phone,"手机号："+item.getPhone());
    }
}
