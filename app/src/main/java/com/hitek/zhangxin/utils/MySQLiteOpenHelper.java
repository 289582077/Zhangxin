package com.hitek.zhangxin.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.hitek.zhangxin.entity.CustomerEntityDao;
import com.hitek.zhangxin.entity.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * @author zzj
 * @date 2019/2/28
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper{
    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        },CustomerEntityDao.class);
    }
}
