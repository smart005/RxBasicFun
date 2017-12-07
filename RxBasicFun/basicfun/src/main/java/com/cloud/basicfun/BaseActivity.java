package com.cloud.basicfun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cloud.basicfun.enums.LifeCycleStatus;
import com.cloud.basicfun.events.OnBehaviorStatistics;
import com.cloud.basicfun.update.UpdateBLL;
import com.cloud.basicfun.update.VersionUpdateProperties;
import com.cloud.basicfun.utils.BundleMap;
import com.cloud.basicfun.utils.BundleUtils;
import com.cloud.basicfun.utils.WinObjectUtils;
import com.cloud.core.Action0;
import com.cloud.core.configs.BaseCConfig;
import com.cloud.core.configs.ConfigItem;
import com.cloud.core.configs.OnConfigItemUrlListener;
import com.cloud.core.configs.RxCoreConfigItems;
import com.cloud.core.enums.ResFolderType;
import com.cloud.core.utils.ResUtils;
import com.cloud.core.utils.SharedPrefUtils;
import com.cloud.resources.RedirectUtils;

import java.util.Map;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/4
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseActivity extends Activity {

    /**
     * 当前分页索引,当加载更多时对此索引加1表示加载下一页数据
     */
    protected int currPageIndex = 1;

    protected void receiveRSCResult(Intent intent) {

    }

    protected void receiveRSCResult(Intent intent, String action) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mwoutils.onCreate(this, savedInstanceState);
        OnBehaviorStatistics behaviorStatistics = BaseApplication.getInstance().getOnBehaviorStatistics();
        if (behaviorStatistics != null) {
            behaviorStatistics.onPageStatistics(this, LifeCycleStatus.Create);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 将分页索引currPageIndex进行初始化设置为1
     *
     * @return
     */
    protected int getCurrPageIndex() {
        return currPageIndex = 1;
    }

    private WinObjectUtils mwoutils = new WinObjectUtils() {
        @Override
        protected void receiveRSCResult(Intent intent) {
            BaseActivity.this.receiveRSCResult(intent);
        }

        @Override
        protected void receiveRSCResult(Intent intent, String action) {
            BaseActivity.this.receiveRSCResult(intent, action);
        }

        @Override
        protected void onCheckVersionUpdateListener() {
            long updateTime = SharedPrefUtils.getPrefLong(getActivity(), "UPDATE_VERSION_KEY", 0);
            long currTime = System.currentTimeMillis();
            long time = currTime - updateTime;
            if (updateTime == 0 || mUBll.isCheckComplete()) {
                if (time > 0 && (time / 3600) <= 6) {
                    return;
                }
                SharedPrefUtils.setPrefLong(getActivity(), "UPDATE_VERSION_KEY", System.currentTimeMillis());
                VersionUpdateProperties properties = new VersionUpdateProperties();
                properties.setActivity(BaseActivity.this);
                RxCoreConfigItems configItems = BaseCConfig.getInstance().getConfigItems(getActivity());
                ConfigItem versionCheck = configItems.getAppVersionCheck();
                if (versionCheck.isState()) {
                    BaseApplication currapp = BaseApplication.getInstance();
                    Object urlListener = currapp.getObjectValue(versionCheck.getUrlType());
                    if (urlListener != null && urlListener instanceof OnConfigItemUrlListener) {
                        OnConfigItemUrlListener listener = (OnConfigItemUrlListener) urlListener;
                        String url = listener.getUrl(versionCheck.getUrlType());
                        ConfigItem appIcon = configItems.getAppIcon();
                        ResFolderType folderType = ResFolderType.getResFolderType(appIcon.getType());
                        int appIconRresId = ResUtils.getResource(getActivity(), appIcon.getName(), folderType);
                        properties.setAppIcon(appIconRresId);
                        properties.setIsAutoUpdate(true);
                        properties.setIsCheckUpdatePrompt(false);
                        properties.setCheckUpdateUrl(url);
                        mUBll.checkVersionUpdate(properties);
                    }
                }
            }
        }
    };

    public UpdateBLL mUBll = new UpdateBLL() {
        @Override
        protected void onCheckCompleted() {
            BaseApplication application = BaseApplication.getInstance();
            if (application != null) {
                Action0 updateCheckComplate = application.getUpdateCheckComplate();
                if (updateCheckComplate != null) {
                    updateCheckComplate.call();
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mwoutils.onResume(this);
        OnBehaviorStatistics behaviorStatistics = BaseApplication.getInstance().getOnBehaviorStatistics();
        if (behaviorStatistics != null) {
            behaviorStatistics.onPageStatistics(this, LifeCycleStatus.Resume);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mwoutils.onPause(this);
        OnBehaviorStatistics behaviorStatistics = BaseApplication.getInstance().getOnBehaviorStatistics();
        if (behaviorStatistics != null) {
            behaviorStatistics.onPageStatistics(this, LifeCycleStatus.Pause);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mwoutils.onStart(this);
        OnBehaviorStatistics behaviorStatistics = BaseApplication.getInstance().getOnBehaviorStatistics();
        if (behaviorStatistics != null) {
            behaviorStatistics.onPageStatistics(this, LifeCycleStatus.Start);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mwoutils.onDestroy(this);
        OnBehaviorStatistics behaviorStatistics = BaseApplication.getInstance().getOnBehaviorStatistics();
        if (behaviorStatistics != null) {
            behaviorStatistics.onPageStatistics(this, LifeCycleStatus.Destroy);
        }
    }

    /**
     * 获取bundle对象
     *
     * @return
     */
    public Bundle getBundle() {
        Intent intent = getIntent();
        if (intent == null) {
            return new Bundle();
        } else {
            Bundle bundle = intent.getExtras();
            return bundle == null ? new Bundle() : bundle;
        }
    }

    /**
     * 从bundle中获取字符串
     *
     * @param bundle       bundle对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public String getStringBundle(Bundle bundle, String key, String defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getString(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 从bundle中获取字符串
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public String getStringBundle(String key, String defaultValue) {
        Bundle bundle = getBundle();
        return getStringBundle(bundle, key, defaultValue);
    }

    /**
     * 从bundle中获取字符串
     *
     * @param key
     * @return
     */
    public String getStringBundle(String key) {
        return getStringBundle(key, "");
    }

    /**
     * 从bundle中获取int值
     *
     * @param bundle       bundle对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public int getIntBundle(Bundle bundle, String key, int defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getInt(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 从bundle中获取int值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public int getIntBundle(String key, int defaultValue) {
        Bundle bundle = getBundle();
        return getIntBundle(bundle, key, defaultValue);
    }

    /**
     * 从bundle中获取int值
     *
     * @param key
     * @return
     */
    public int getIntBundle(String key) {
        return getIntBundle(key, 0);
    }

    /**
     * 从bundle中获取boolean值
     *
     * @param bundle       bundle对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public boolean getBooleanBundle(Bundle bundle, String key, boolean defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getBoolean(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 从bundle中获取boolean值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public boolean getBooleanBundle(String key, boolean defaultValue) {
        Bundle bundle = getBundle();
        return getBooleanBundle(bundle, key, defaultValue);
    }

    /**
     * 从bundle中获取boolean值
     *
     * @param key
     * @return
     */
    public boolean getBooleanBundle(String key) {
        return getBooleanBundle(key, false);
    }

    /**
     * 从bundle中获取object值
     *
     * @param bundle       bundle对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public Object getObjectBundle(Bundle bundle, String key, Object defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.get(key);
        } else {
            return defaultValue;
        }
    }

    /**
     * 从bundle中获取object值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public Object getObjectBundle(String key, Object defaultValue) {
        Bundle bundle = getBundle();
        return getObjectBundle(bundle, key, defaultValue);
    }

    /**
     * 从bundle中获取object值
     *
     * @param key
     * @return
     */
    public Object getObjectBundle(String key) {
        return getObjectBundle(key, null);
    }

    /**
     * 从bundle中获取float值
     *
     * @param bundle       bundle对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public float getFloatBundle(Bundle bundle, String key, float defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getFloat(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 从bundle中获取float值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public float getFloatBundle(String key, float defaultValue) {
        Bundle bundle = getBundle();
        return getFloatBundle(bundle, key, defaultValue);
    }

    /**
     * 从bundle中获取float值
     *
     * @param key
     * @return
     */
    public float getFloatBundle(String key) {
        return getFloatBundle(key, 0);
    }

    /**
     * 从bundle中获取double值
     *
     * @param bundle       bundle对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public double getDoubleBundle(Bundle bundle, String key, double defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getDouble(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 从bundle中获取double值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public double getDoubleBundle(String key, double defaultValue) {
        Bundle bundle = getBundle();
        return getDoubleBundle(bundle, key, defaultValue);
    }

    /**
     * 从bundle中获取double值
     *
     * @param key
     * @return
     */
    public double getDoubleBundle(String key) {
        return getDoubleBundle(key, 0);
    }

    /**
     * 从bundle中获取long值
     *
     * @param bundle       bundle对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public long getLongBundle(Bundle bundle, String key, long defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getLong(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * 从bundle中获取long值
     *
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public long getLongBundle(String key, long defaultValue) {
        Bundle bundle = getBundle();
        return getLongBundle(bundle, key, defaultValue);
    }

    /**
     * 从bundle中获取long值
     *
     * @param key
     * @return
     */
    public long getLongBundle(String key) {
        return getLongBundle(key, 0);
    }

    /**
     * 获取当前Activity对象
     *
     * @return
     */
    public Activity getActivity() {
        return BaseActivity.this;
    }

    /**
     * 获取bundle map集合
     * 调用方法startActivity时传入的参数
     *
     * @return
     */
    protected static BundleMap getBundleMap() {
        BundleMap bundleMap = new BundleMap();
        return bundleMap;
    }

    /**
     * 启动activity
     *
     * @param activity  提供上下文的activity
     * @param cls       要启动的activity类对象
     * @param paramsMap 参数集合,一般配合getBundleMap()使用
     */
    protected static void startActivity(Activity activity, Class<?> cls, BundleMap paramsMap) {
        Bundle bundle = new Bundle();
        if (paramsMap != null) {
            for (Map.Entry<String, Object> entry : paramsMap.getMap().entrySet()) {
                BundleUtils.setBundleValue(bundle, entry.getKey(), entry.getValue());
            }
        }
        RedirectUtils.startActivity(activity, cls, bundle);
    }
}
