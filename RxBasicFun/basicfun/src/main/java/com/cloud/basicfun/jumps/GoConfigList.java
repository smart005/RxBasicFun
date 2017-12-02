package com.cloud.basicfun.jumps;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/9
 * @Description:配置列表
 * @Modifier:
 * @ModifyContent:
 */
public class GoConfigList {
    /**
     * 配置唯一标识码
     */
    private String uniqueTag = "";
    /**
     * 资源位描述
     */
    private String description = "";
    /**
     * 配置项
     */
    private String configs = null;

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

    public String getConfigs() {
        return configs;
    }

    public void setConfigs(String configs) {
        this.configs = configs;
    }
}
