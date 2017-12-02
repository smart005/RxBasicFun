package com.cloud.basicfun.jumps;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/8
 * @Description:跳转数据格式
 * @Modifier:
 * @ModifyContent:
 */
public class BaseGoBean {
    /**
     * 数据id
     */
    private int dataId = 0;
    /**
     * true:取url取;false:原生跳转;
     */
    private boolean toH5 = false;
    /**
     * 链接地址
     */
    private String url = "";
    /**
     * oss中对应的文件唯一标识
     */
    private String uniqueTag = "";
    /**
     * 跳转描述
     */
    private String description = "";
    /**
     * 配置列表项
     */
    private List<GoConfigItem> configs = null;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public boolean isToH5() {
        return toH5;
    }

    public void setToH5(boolean toH5) {
        this.toH5 = toH5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUniqueTag() {
        return uniqueTag;
    }

    public void setUniqueTag(String uniqueTag) {
        this.uniqueTag = uniqueTag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GoConfigItem> getConfigs() {
        if (configs == null) {
            configs = new ArrayList<GoConfigItem>();
        }
        return configs;
    }

    public void setConfigs(List<GoConfigItem> configs) {
        this.configs = configs;
    }
}
