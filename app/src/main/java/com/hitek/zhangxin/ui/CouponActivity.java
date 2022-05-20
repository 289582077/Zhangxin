package com.hitek.zhangxin.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hitek.zhangxin.MyApp;
import com.hitek.zhangxin.R;
import com.hitek.zhangxin.base.BaseActivity;
import com.hitek.zhangxin.entity.CustomerEntity;
import com.hitek.zhangxin.entity.CustomerEntityDao;
import com.hitek.zhangxin.entity.DaoSession;
import com.hitek.zhangxin.utils.ToastUtil;

import java.text.DecimalFormat;

public class CouponActivity extends BaseActivity implements View.OnClickListener {


    private CustomerEntityDao mCustomerEntityDao;
    private TextView mTvName;
    private TextView mTvBalance;
    private TextView mTvPhone;
    private TextView mTvCoupon;
    private Button mBtnIncrease;
    private Button mBtnDecreasee;
    private CustomerEntity mCustomerEntity;
    private long mPhone;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_coupon;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra("phone");
        DaoSession daoSession = ((MyApp) getApplication()).getDaoSession();
        mCustomerEntityDao = daoSession.getCustomerEntityDao();
        mPhone = Long.parseLong(phoneNumber);
        mCustomerEntity = mCustomerEntityDao.load(mPhone);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvBalance = (TextView) findViewById(R.id.tv_balance);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvCoupon = (TextView) findViewById(R.id.tv_coupon);
        mBtnIncrease = (Button) findViewById(R.id.btn_increase);
        mBtnDecreasee = (Button) findViewById(R.id.btn_decrease);

        mTvName.setText("姓名："+ mCustomerEntity.getName());
        mTvBalance.setText("余额："+ mCustomerEntity.getBalance());
        mTvPhone.setText("手机号："+phoneNumber);
        mTvCoupon.setText("优惠劵余额："+ mCustomerEntity.getCoupon());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mBtnIncrease.setOnClickListener(this);
        mBtnDecreasee.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_increase:
                new MaterialDialog.Builder(this)
                        .title("充值优惠劵")

                        .positiveText(R.string.agree)
                        .customView(R.layout.dialog_input,true)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //充值
                                String money = ((EditText) dialog.findViewById(R.id.et_money)).getText().toString().trim();
                                double coupon = Double.parseDouble(doubleToString(Double.parseDouble(money)));
                                double oldCoupon = mCustomerEntity.getCoupon();
                                mCustomerEntity.setCoupon(coupon+oldCoupon);
                                mCustomerEntityDao.update(mCustomerEntity);
                                mCustomerEntityDao.insertOrReplaceInTx();
                                mTvCoupon.setText("优惠劵余额："+mCustomerEntityDao.load(mPhone).getCoupon());
                                dialog.dismiss();
                            }
                        })
                        .negativeText(R.string.disagree)
                        .show();


                break;
            case R.id.btn_decrease:
                new MaterialDialog.Builder(this)
                        .title("扣除优惠劵")

                        .positiveText(R.string.agree)
                        .customView(R.layout.dialog_input,true)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //扣款
                                String money = ((EditText) dialog.findViewById(R.id.et_money)).getText().toString().trim();
                                double coupon = Double.parseDouble(doubleToString(Double.parseDouble(money)));
                                double oldCoupon = mCustomerEntity.getCoupon();

                                double newCoupon = Double.parseDouble(doubleToString(oldCoupon - coupon));
                                if (newCoupon>=0) {
                                    mCustomerEntity.setCoupon(newCoupon);

                                    mCustomerEntityDao.update(mCustomerEntity);
                                    mTvCoupon.setText("优惠劵余额："+mCustomerEntityDao.load(mPhone).getCoupon());
                                }else {
                                    ToastUtil.showToast(getApplicationContext(),"优惠劵余额不足", Toast.LENGTH_SHORT);
                                }

                                dialog.dismiss();
                            }
                        })
                        .negativeText(R.string.disagree)
                        .show();

                break;
                default:
                    break;
        }
    }
    public  String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }
}
