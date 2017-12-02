package com.cloud.basicfun.events;

import com.cloud.basicfun.enums.LifeCycleStatus;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/21
 * @Description:行为统计(页面统计)
 * @Modifier:
 * @ModifyContent:
 */
public interface OnBehaviorStatistics {

    public <T> void onPageStatistics(T pager, LifeCycleStatus lifeCycleStatus);
}
