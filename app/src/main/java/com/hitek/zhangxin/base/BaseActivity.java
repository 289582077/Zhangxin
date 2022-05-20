package com.hitek.zhangxin.base;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hitek.zhangxin.utils.ToastUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * @author zzj
 * @date 2017/10/23
 */

public abstract class BaseActivity extends AppCompatActivity {


    private AlertDialog mAlertDialog;
    private String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        //统计应用启动数据

        AndPermission.with(this)
                .requestCode(100)
                .permission(permissions)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {

                        AndPermission.defaultSettingDialog(BaseActivity.this, 400)
                                .setTitle("权限申请失败")
                                .setMessage("您拒绝了我们必要的一些权限，已经没法愉快的玩耍了，请在设置中授权！")
                                .setPositiveButton("好，去设置")
                                .show();
                    }
                })
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        initView();
                        initData();
                        initListener();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        ToastUtil.showToast(getApplicationContext(),"请求权限失败", Toast.LENGTH_SHORT);
                        if (AndPermission.hasAlwaysDeniedPermission(BaseActivity.this, deniedPermissions)) {
                            // 第一种：用AndPermission默认的提示语。
                            AndPermission.defaultSettingDialog(BaseActivity.this, 400).show();

                        }else {
                            return;
                        }

                    }
                })
                .start();

    }

    /**
     * 加载布局
     * @return 布局id
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化控件
     */
    protected abstract void initView();
    /**
     * 初始化数据
     */
    protected abstract void initData();
    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 显示对话框
     */
    private void showDialog() {

        if (mAlertDialog == null){
            mAlertDialog = new AlertDialog.Builder(this)
                    .setMessage("您确定要关闭该页面吗?")
                    .setNegativeButton("再逛逛", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null){
                                mAlertDialog.dismiss();
                            }
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (mAlertDialog != null){
                                mAlertDialog.dismiss();
                            }
                            BaseActivity.this.finish();
                        }
                    }).create();
        }
        mAlertDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 400: {
                // 你可以在这里检查你需要的权限是否被允许，并做相应的操作。
                if (AndPermission.hasPermission(this, permissions)) {
                    initView();
                    initData();
                    initListener();
                }else {
                    return;
                }
                break;
            }

        }
    }

}
