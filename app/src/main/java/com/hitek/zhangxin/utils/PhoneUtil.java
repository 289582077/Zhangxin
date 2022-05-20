package com.hitek.zhangxin.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * @author zzj
 * @date 2017/10/26
 */

public class PhoneUtil {

    @SuppressLint({"HardwareIds"})
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return "" + tm.getDeviceId();
        }
        return null;
    }
    public static String getDeviceId(Context context) {

        String serial = null;

        String m_szDevIDShort = "35" +

                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial";
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
//    /**
//     * 生成设备唯一标识：IMEI、AndroidId、macAddress 三者拼接再 MD5
//     * @return
//     */
//    public static String generateUniqueDeviceId(Context context){
//
//        String imei = "";
//        String androidId = "";
//        String macAddress = "";
//
//
//        imei = getIMEI(context);
//
//        ContentResolver contentResolver = context.getContentResolver();
//        if (contentResolver != null) {
//            androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
//        }
//        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        if (wifiManager != null) {
//            macAddress = wifiManager.getConnectionInfo().getMacAddress();
//        }
//
//        StringBuilder longIdBuilder = new StringBuilder();
//        if (imei != null) {
//            longIdBuilder.append(imei);
//        }
//        if (androidId != null) {
//            longIdBuilder.append(androidId);
//        }
//        if (macAddress != null) {
//            longIdBuilder.append(macAddress);
//        }
//        return MD5Utils.toMd5(longIdBuilder.toString());
//    }
}
