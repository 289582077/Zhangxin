package com.hitek.zhangxin.entity;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.hitek.zhangxin.entity.CustomerEntity;

import com.hitek.zhangxin.entity.CustomerEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig customerEntityDaoConfig;

    private final CustomerEntityDao customerEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        customerEntityDaoConfig = daoConfigMap.get(CustomerEntityDao.class).clone();
        customerEntityDaoConfig.initIdentityScope(type);

        customerEntityDao = new CustomerEntityDao(customerEntityDaoConfig, this);

        registerDao(CustomerEntity.class, customerEntityDao);
    }
    
    public void clear() {
        customerEntityDaoConfig.clearIdentityScope();
    }

    public CustomerEntityDao getCustomerEntityDao() {
        return customerEntityDao;
    }

}