package com.hitek.zhangxin;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.hitek.zhangxin.entity.DaoMaster;
import com.hitek.zhangxin.entity.DaoSession;
import com.hitek.zhangxin.utils.MySQLiteOpenHelper;

import org.greenrobot.greendao.database.Database;

import greendao.GreenDaoContext;

/**
 * @author zzj
 * @date 2018/5/31
 */
public class MyApp extends Application{
    /** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(new GreenDaoContext(this), ENCRYPTED ? "vip-db-encrypted" : "vip-db");
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(new GreenDaoContext(this), ENCRYPTED ? "vip-db-encrypted" : "vip-db");

        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();

        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {

        return daoSession;
    }

}
