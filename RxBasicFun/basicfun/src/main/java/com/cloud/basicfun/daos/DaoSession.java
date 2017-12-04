package com.cloud.basicfun.daos;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.cloud.basicfun.picker.AddressItem;
import com.cloud.basicfun.beans.AppendPositionBean;
import com.cloud.basicfun.beans.BreakPointBean;

import com.cloud.basicfun.daos.AddressItemDao;
import com.cloud.basicfun.daos.AppendPositionBeanDao;
import com.cloud.basicfun.daos.BreakPointBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig addressItemDaoConfig;
    private final DaoConfig appendPositionBeanDaoConfig;
    private final DaoConfig breakPointBeanDaoConfig;

    private final AddressItemDao addressItemDao;
    private final AppendPositionBeanDao appendPositionBeanDao;
    private final BreakPointBeanDao breakPointBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        addressItemDaoConfig = daoConfigMap.get(AddressItemDao.class).clone();
        addressItemDaoConfig.initIdentityScope(type);

        appendPositionBeanDaoConfig = daoConfigMap.get(AppendPositionBeanDao.class).clone();
        appendPositionBeanDaoConfig.initIdentityScope(type);

        breakPointBeanDaoConfig = daoConfigMap.get(BreakPointBeanDao.class).clone();
        breakPointBeanDaoConfig.initIdentityScope(type);

        addressItemDao = new AddressItemDao(addressItemDaoConfig, this);
        appendPositionBeanDao = new AppendPositionBeanDao(appendPositionBeanDaoConfig, this);
        breakPointBeanDao = new BreakPointBeanDao(breakPointBeanDaoConfig, this);

        registerDao(AddressItem.class, addressItemDao);
        registerDao(AppendPositionBean.class, appendPositionBeanDao);
        registerDao(BreakPointBean.class, breakPointBeanDao);
    }
    
    public void clear() {
        addressItemDaoConfig.clearIdentityScope();
        appendPositionBeanDaoConfig.clearIdentityScope();
        breakPointBeanDaoConfig.clearIdentityScope();
    }

    public AddressItemDao getAddressItemDao() {
        return addressItemDao;
    }

    public AppendPositionBeanDao getAppendPositionBeanDao() {
        return appendPositionBeanDao;
    }

    public BreakPointBeanDao getBreakPointBeanDao() {
        return breakPointBeanDao;
    }

}