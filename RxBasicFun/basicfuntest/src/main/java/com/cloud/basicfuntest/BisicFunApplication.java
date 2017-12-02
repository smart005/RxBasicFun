package com.cloud.basicfuntest;

import com.cloud.basicfun.BaseApplication;
import com.cloud.basicfun.daos.AddressItemDao;
import com.cloud.basicfun.daos.AppendPositionBeanDao;
import com.cloud.basicfun.daos.BreakPointBeanDao;
import com.cloud.basicfun.picker.AddressPickerUtils;
import com.cloud.core.beans.StorageInitParam;
import com.cloud.core.greens.DBManager;
import com.cloud.core.okrx.OkRxBase;
import com.cloud.core.utils.StorageUtils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/23
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BisicFunApplication extends BaseApplication {

    static {
        //目录配置
        StorageUtils.setOnStorageInitListener(new StorageUtils.OnStorageInitListener() {
            @Override
            public StorageInitParam getStorageInit() {
                StorageInitParam storageInitParam = new StorageInitParam();
                storageInitParam.setAppDir("mibao");
                return storageInitParam;
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OkRxBase.getInstance().init(this);
        DBManager.getInstance().initializeBaseDb(this, "test",
                AppendPositionBeanDao.class,
                BreakPointBeanDao.class,
                AddressItemDao.class);
        AddressPickerUtils.getInstance().saveOrUpdateAssetsAddressToDb(this, "allarea.rx");
    }
}
