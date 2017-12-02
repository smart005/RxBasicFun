package com.cloud.basicfun.daos;

import com.cloud.core.Action;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/15
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class DaoManager extends BaseDaoManager {
    private static DaoManager daoManager = null;

    public static DaoManager getInstance() {
        return daoManager == null ? daoManager = new DaoManager() : daoManager;
    }

    public void getBreakPointBeanDao(final Action<BreakPointBeanDao> action) {
        if (action == null) {
            return;
        }
        super.getDao(new Action<DaoSession>() {
            @Override
            public void call(DaoSession daoSession) {
                BreakPointBeanDao beanDao = daoSession.getBreakPointBeanDao();
                if (beanDao == null) {
                    return;
                }
                BreakPointBeanDao.createTable(daoSession.getDatabase(), true);
                action.call(beanDao);
            }
        });
    }

    public void getAppendPositionBeanDao(final Action<AppendPositionBeanDao> action) {
        if (action == null) {
            return;
        }
        super.getDao(new Action<DaoSession>() {
            @Override
            public void call(DaoSession daoSession) {
                AppendPositionBeanDao beanDao = daoSession.getAppendPositionBeanDao();
                if (beanDao == null) {
                    return;
                }
                AppendPositionBeanDao.createTable(daoSession.getDatabase(), true);
                action.call(beanDao);
            }
        });
    }

    public void getAddressItemDao(final Action<AddressItemDao> action) {
        if (action == null) {
            return;
        }
        super.getDao(new Action<DaoSession>() {
            @Override
            public void call(DaoSession daoSession) {
                AddressItemDao itemDao = daoSession.getAddressItemDao();
                if (itemDao == null) {
                    return;
                }
                AddressItemDao.createTable(daoSession.getDatabase(), true);
                action.call(itemDao);
            }
        });
    }
}
