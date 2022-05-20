package com.hitek.zhangxin.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author zzj
 * @date 2017/10/26
 */

public class CacheUtils {
    public static String getAppCache(Context context) {
        long size = 0;
        try {

            String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
            File externalCacheDir = new File(path);

            long folderSize = DataCleanManager.getFolderSize(externalCacheDir);
            File directory2 = new File("/data/data/"
                    + context.getPackageName() + "/cache");
            long folderSize2 = DataCleanManager.getFolderSize(directory2);
            File directory3 = new File("/data/data/"
                    + context.getPackageName() + "/shared_prefs");
            long folderSize3 = DataCleanManager.getFolderSize(directory3);

            File directory4 = new File("/data/data/"
                    + context.getPackageName() + "/files");
            long folderSize4 = DataCleanManager.getFolderSize(directory4);
            File directory5 = new File("/data/data/"
                    + context.getPackageName() + "/databases");
            long folderSize5 = DataCleanManager.getFolderSize(directory5);
            size = folderSize + folderSize2 + folderSize3 + folderSize4 + folderSize5;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataCleanManager.getFormatSize(size);
    }

}
