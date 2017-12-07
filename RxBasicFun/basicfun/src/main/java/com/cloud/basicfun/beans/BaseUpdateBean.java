package com.cloud.basicfun.beans;

import com.cloud.core.beans.BaseBean;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/9/22
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseUpdateBean<T> extends BaseBean {

    /**
     * 数据对象
     */
    private T data = null;
    /**
     * 分页索引
     */
    private int pageNum = 0;
    /**
     * 分页大小
     */
    private int pageSize = 0;
    /**
     * 头记录数
     */
    private int total = 0;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
