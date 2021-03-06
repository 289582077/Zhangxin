package com.hitek.zhangxin.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.TimeUtils;
import com.hitek.zhangxin.MyApp;
import com.hitek.zhangxin.R;
import com.hitek.zhangxin.base.BaseActivity;
import com.hitek.zhangxin.entity.CustomerEntity;
import com.hitek.zhangxin.entity.CustomerEntityDao;
import com.hitek.zhangxin.entity.DaoSession;
import com.hitek.zhangxin.utils.ToastUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private EditText mEtSearch;
    private ImageButton mIvSearch;
    private TextView mTvBalance;
    private TextView mTvName;
    private TextView mTvLevel;
    private TextView mTvPoint;
    private CustomerEntityDao mCustomerEntityDao;
    private Button mNew;
    private Button mDelete;
    private Button mRecharge;
    private Button mChargebacks;
    private Button mSearchAll;
    private TextView mTvHistory;
    private Button mCoupon;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        DaoSession daoSession = ((MyApp) getApplication()).getDaoSession();
        mCustomerEntityDao = daoSession.getCustomerEntityDao();
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mIvSearch = (ImageButton) findViewById(R.id.ib_search);
        mTvBalance = (TextView) findViewById(R.id.tv_balance);
        mTvLevel = (TextView) findViewById(R.id.tv_level);
        mTvPoint = (TextView) findViewById(R.id.tv_points);
        mTvPoint = (TextView) findViewById(R.id.tv_points);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvHistory = (TextView) findViewById(R.id.tv_history);
        mNew = (Button) findViewById(R.id.btn_new);
        mDelete = (Button) findViewById(R.id.btn_delete);
        mRecharge = (Button) findViewById(R.id.btn_recharge);
        mChargebacks = (Button) findViewById(R.id.btn_chargebacks);
        mSearchAll = (Button) findViewById(R.id.btn_search_all);
        mCoupon = (Button) findViewById(R.id.btn_coupon);
    }

    @Override
    protected void initData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100000; i++) {
//
//                    String money = i+"0.56";
//                    double balance = Double.parseDouble(doubleToString(Double.parseDouble(money)));
//                    CustomerEntity entity = new CustomerEntity();
//                    entity.setBalance(balance);
//                    entity.setPhone(Long.parseLong(i+""));
//                    entity.setPoints(0);
//                    mCustomerEntityDao.insert(entity);
//
//
//                }
//            }
//        }).start();

    }

    @Override
    protected void initListener() {
        mIvSearch.setOnClickListener(this);
        mNew.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mRecharge.setOnClickListener(this);
        mChargebacks.setOnClickListener(this);
        mSearchAll.setOnClickListener(this);
        mCoupon.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String phoneNumber = mEtSearch.getText().toString().trim();
        if (v.getId()==R.id.btn_search_all){
            //????????????
            Intent intent = new Intent(this, AllCustomerActivity.class);
            startActivity(intent);
            return;
        }else if (phoneNumber.isEmpty()) {
            ToastUtil.showToast(this,"??????????????????",Toast.LENGTH_SHORT);
            return;
        }
        final long phone = Long.parseLong(phoneNumber);
        final CustomerEntity customerEntity = mCustomerEntityDao.load(phone);

        switch (v.getId()) {
            //??????
            case R.id.ib_search:

                if (customerEntity!=null) {
                    double balance = customerEntity.getBalance();
                    String name = customerEntity.getName();
                    String date = customerEntity.getDate();
                    mTvName.setText("?????????"+name);
                    mTvBalance.setText("?????????"+balance);
                    if (date!=null&&(!date.isEmpty())) {
                        double oldMoney = customerEntity.getOldMoney();
                        mTvHistory.setText("?????????????????????"+date+"?????????"+oldMoney+"???");
                    }else {
                        mTvHistory.setText("??????????????????");
                    }
                }else {
                    ToastUtil.showToast(this,"???????????????",Toast.LENGTH_SHORT);
                    mTvBalance.setText("?????????");
                    mTvName.setText("?????????");
                    mTvHistory.setText("??????????????????");
                }

                break;
            //??????
            case R.id.btn_new:
                if (customerEntity!=null) {
                    ToastUtil.showToast(this,"???????????????",Toast.LENGTH_SHORT);
                }else {
                    new MaterialDialog.Builder(this)
                            .title("????????????")
                            .customView(R.layout.dialog_add,true)
                            .positiveText(R.string.agree)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    String money = ((EditText) dialog.findViewById(R.id.et_money)).getText().toString().trim();
                                    String name = ((EditText) dialog.findViewById(R.id.et_name)).getText().toString().trim();
                                    if (money.isEmpty()||name.isEmpty()) {
                                        ToastUtil.showToast(getApplicationContext(),"??????????????????????????????",Toast.LENGTH_SHORT);
                                    }else {
                                        double balance = Double.parseDouble(doubleToString(Double.parseDouble(money)));
                                        CustomerEntity entity = new CustomerEntity();
                                        entity.setBalance(balance);
                                        entity.setPhone(phone);
                                        entity.setPoints(0);
                                        entity.setName(name);
                                        mCustomerEntityDao.insert(entity);
                                        mCustomerEntityDao.insertOrReplaceInTx();
                                        mTvBalance.setText("?????????"+mCustomerEntityDao.load(phone).getBalance());
                                        mTvName.setText("?????????"+name);
                                        mTvHistory.setText("??????????????????");
                                        dialog.dismiss();

                                    }

                                }
                            })
                            .negativeText(R.string.disagree)
                            .show();
                }
                break;
            //??????
            case R.id.btn_delete:
                if (customerEntity!=null) {
                    new MaterialDialog.Builder(this)
                            .title("????????????")
                            .content("????????????????????????"+phone+"???????????????")
                            .positiveText(R.string.agree)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    mCustomerEntityDao.deleteByKey(phone);
                                    mEtSearch.setText("");
                                    mTvBalance.setText("?????????");
                                    mTvName.setText("?????????");
                                    mTvHistory.setText("??????????????????");
                                    dialog.dismiss();
                                }
                            })
                            .negativeText(R.string.disagree)
                            .show();

                }else {
                    ToastUtil.showToast(this,"???????????????",Toast.LENGTH_SHORT);
                }
                break;
            //??????
            case R.id.btn_recharge:
                if (customerEntity!=null) {

                    new MaterialDialog.Builder(this)
                            .title("????????????")

                            .positiveText(R.string.agree)
                            .customView(R.layout.dialog_input,true)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //??????
                                    String money = ((EditText) dialog.findViewById(R.id.et_money)).getText().toString().trim();
                                    double balance = Double.parseDouble(doubleToString(Double.parseDouble(money)));
                                    double oldBalance = customerEntity.getBalance();
                                    customerEntity.setBalance(balance+oldBalance);
                                    mCustomerEntityDao.update(customerEntity);

                                    mTvBalance.setText("?????????"+mCustomerEntityDao.load(phone).getBalance());
                                    dialog.dismiss();
                                }
                            })
                            .negativeText(R.string.disagree)
                            .show();

                }else {
                    ToastUtil.showToast(this,"???????????????",Toast.LENGTH_SHORT);
                }
                break;
            //??????
            case R.id.btn_chargebacks:
                if (customerEntity!=null) {

                    new MaterialDialog.Builder(this)
                            .title("??????")
                            .positiveText(R.string.agree)
                            .customView(R.layout.dialog_input,true)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //??????
                                    String money = ((EditText) dialog.findViewById(R.id.et_money)).getText().toString().trim();
                                    double balance = Double.parseDouble(doubleToString(Double.parseDouble(money)));
                                    double oldBalance = customerEntity.getBalance();


                                    double newBalance = Double.parseDouble(doubleToString(oldBalance - balance));
                                    if (newBalance>=0) {
                                        customerEntity.setBalance(newBalance);
                                        customerEntity.setOldMoney(balance);
                                        customerEntity.setDate(TimeUtils.getNowString(new SimpleDateFormat("yyyy???MM???dd???")));
                                        mCustomerEntityDao.update(customerEntity);
                                        mTvBalance.setText("?????????"+mCustomerEntityDao.load(phone).getBalance());
                                    }else {
                                        ToastUtil.showToast(getApplicationContext(),"????????????",Toast.LENGTH_SHORT);
                                    }

                                    dialog.dismiss();
                                }
                            })
                            .negativeText(R.string.disagree)
                            .show();

                }else {
                    ToastUtil.showToast(this,"???????????????",Toast.LENGTH_SHORT);
                }
                break;

            //?????????
            case R.id.btn_coupon:
                if (customerEntity!=null) {
                    Intent intent = new Intent(this, CouponActivity.class);
                    intent.putExtra("phone",phoneNumber);
                    startActivity(intent);

                }else {
                    ToastUtil.showToast(this,"???????????????",Toast.LENGTH_SHORT);
                }

                break;

                default:
                    break;
        }
    }
    public  String doubleToString(double num){
        //??????0.00????????????0???#.##??????????????????
        return new DecimalFormat("0.00").format(num);
    }
}
