package com.cloud.basicfun.beans;

import com.cloud.core.beans.BaseBean;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/2/23
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class ALiYunOssRole extends BaseBean {

    private String accessKeyId = "";
    private String accessKeySecret = "";
    private String securityToken = "";
    private String endpoint = "";
    private String bucket = "";
    private String expiration = "";
    //上传目录
    private String dir = "";

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
