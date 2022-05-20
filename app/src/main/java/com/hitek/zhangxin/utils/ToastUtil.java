package com.hitek.zhangxin.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zzj on 2017/6/1.
 */

public class ToastUtil {
    private static Toast toast;
    public static void showToast(Context context, CharSequence msg, int duration){
        if (toast==null) {
            toast= Toast.makeText(context,msg,duration);
            toast.show();
        }else {
            toast.setDuration(duration);
            toast.setText(msg);
            toast.show();
        }
    }
}

