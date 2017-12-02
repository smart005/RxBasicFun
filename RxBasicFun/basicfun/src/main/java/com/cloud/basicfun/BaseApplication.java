package com.cloud.basicfun;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.cloud.basicfun.events.OnBehaviorStatistics;
import com.cloud.basicfun.picker.AddressPickerUtils;
import com.cloud.core.Func0;
import com.cloud.core.exception.CrashHandler;
import com.cloud.core.logger.Logger;

import java.util.HashMap;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/3
 * @Description:Application基本
 * @Modifier:
 * @ModifyContent:
 */
public class BaseApplication extends Application {

    private static BaseApplication mbapp = null;
    /**
     * 是否包含新版本
     */
    private boolean hasNewVersion = false;
    private HashMap<String, Boolean> flagList = new HashMap<String, Boolean>();
    private HashMap<String, Object> objList = new HashMap<String, Object>();

    private OnBehaviorStatistics onBehaviorStatistics = null;

    /**
     * 检测更新url(配置文件)
     */
    private String checkUpdateUrl = "";
    /**
     * 返回图标
     */
    private int returnIcon = 0;

    /**
     * token action
     */
    private Func0<String> tokenAction = null;
    private boolean isBackGround = false;

    protected void onAppSiwtchToBack() {

    }

    protected void onAppSiwtchToFront() {

    }

    public void setReturnIcon(int icon) {
        this.returnIcon = icon;
    }

    public int getReturnIcon() {
        return this.returnIcon;
    }

    public void setHasNewVersion(boolean hasNewVersion) {
        this.hasNewVersion = hasNewVersion;
    }

    public boolean isHasNewVersion() {
        return this.hasNewVersion;
    }

    /**
     * 是否包含状态值
     *
     * @param flagKey 根据key获取标识状态是否符合
     * @return
     */
    public boolean hasFlagStatus(String flagKey) {
        if (flagList == null) {
            return false;
        }
        if (flagList.containsKey(flagKey)) {
            return flagList.get(flagKey);
        }
        return false;
    }

    /**
     * 添加状态值
     *
     * @param flagKey
     * @param status  状态
     */
    public void addOrUpdateFlagStatus(String flagKey, boolean status) {
        if (flagList == null) {
            return;
        }
        removeFlagStatus(flagKey);
        flagList.put(flagKey, status);
    }

    /**
     * 移除状态值
     *
     * @param flagKey
     */
    public void removeFlagStatus(String flagKey) {
        if (flagList == null) {
            return;
        }
        if (flagList.containsKey(flagKey)) {
            flagList.remove(flagKey);
        }
    }

    /**
     * 是否包含对象值
     *
     * @param objKey 根据key获取标识状态是否符合
     * @return
     */
    public boolean hasObjectValue(String objKey) {
        if (objList == null) {
            return false;
        }
        if (objList.containsKey(objKey)) {
            return true;
        }
        return false;
    }

    /**
     * 添加对象值
     *
     * @param objKey
     * @param value  对象
     */
    public void addOrUpdateObjectValue(String objKey, Object value) {
        if (objList == null) {
            return;
        }
        removeObjectValue(objKey);
        objList.put(objKey, value);
    }

    public Object getObjectValue(String objKey) {
        if (objList != null && objList.containsKey(objKey)) {
            return objList.get(objKey);
        }
        return null;
    }

    /**
     * 移除对象值
     *
     * @param objKey
     */
    public void removeObjectValue(String objKey) {
        if (objList == null) {
            return;
        }
        if (objList.containsKey(objKey)) {
            objList.remove(objKey);
        }
    }


    /**
     * 获取检测更新url(配置文件)
     */
    public String getCheckUpdateUrl() {
        if (checkUpdateUrl == null) {
            checkUpdateUrl = "";
        }
        return checkUpdateUrl;
    }

    /**
     * 设置检测更新url(配置文件)
     *
     * @param checkUpdateUrl
     */
    public void setCheckUpdateUrl(String checkUpdateUrl) {
        this.checkUpdateUrl = checkUpdateUrl;
    }

    /**
     * 设置行为统计监听
     *
     * @param behaviorStatistics
     */
    public void setOnBehaviorStatistics(OnBehaviorStatistics behaviorStatistics) {
        this.onBehaviorStatistics = behaviorStatistics;
    }

    /**
     * 获取行为统计监听
     *
     * @return
     */
    public OnBehaviorStatistics getOnBehaviorStatistics() {
        return this.onBehaviorStatistics;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            mbapp = this;
            crashHandler.init(this, getPackageName());
            registerActivityLifecycle();
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private CrashHandler crashHandler = new CrashHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            super.uncaughtException(thread, ex);
        }
    };

    public static BaseApplication getInstance() {
        return mbapp;
    }

    /**
     * 获取token
     * action
     */
    public Func0<String> getTokenAction() {
        return this.tokenAction;
    }

    /**
     * 设置token
     * action
     *
     * @param getTokenAction
     */
    public void setTokenAction(Func0<String> tokenAction) {
        this.tokenAction = tokenAction;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isBackGround = true;
            onAppSiwtchToBack();
        }
    }

    private void registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (isBackGround) {
                    isBackGround = false;
                    onAppSiwtchToFront();
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
