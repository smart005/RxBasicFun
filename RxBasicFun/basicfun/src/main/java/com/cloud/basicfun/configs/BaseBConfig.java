package com.cloud.basicfun.configs;

import com.cloud.resources.configs.BaseRConfig;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/12/5
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseBConfig extends BaseRConfig {

    private static BaseBConfig baseBConfig = new BaseBConfig();

    public static BaseBConfig getInstance() {
        return baseBConfig;
    }
}
