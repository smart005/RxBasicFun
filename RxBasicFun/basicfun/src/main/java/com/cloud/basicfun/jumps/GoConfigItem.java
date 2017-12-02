package com.cloud.basicfun.jumps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/9
 * @Description:跳转配置项
 * @Modifier:
 * @ModifyContent:
 */
public class GoConfigItem {
    /**
     * 1:ios;2:android;
     */
    private int deviceType = 0;
    /**
     * 页面名称
     */
    private String pageName = "";
    /**
     * 兼容版本
     */
    private List<String> version = null;
    /**
     * 目标页面接收参数
     */
    private HashMap<String, Object> params = null;
    /**
     * 该配置项是否启用
     */
    private boolean enabled = true;

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public List<String> getVersion() {
        if (version == null) {
            version = new ArrayList<String>();
        }
        return version;
    }

    public void setVersion(List<String> version) {
        this.version = version;
    }

    public HashMap<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
