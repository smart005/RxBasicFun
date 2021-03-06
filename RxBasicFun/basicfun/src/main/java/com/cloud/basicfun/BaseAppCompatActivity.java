package com.cloud.basicfun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
 * @CreateTime:2016/6/21
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    /**
     * 当前分页索引
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

    protected int getCurrPageIndex() {
        return currPageIndex = 1;
    }

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

    private WinObjectUtils mwoutils = new WinObjectUtils() {
        @Override
        protected void receiveRSCResult(Intent intent) {
            BaseAppCompatActivity.this.receiveRSCResult(intent);
        }

        @Override
        protected void receiveRSCResult(Intent intent, String action) {
            BaseAppCompatActivity.this.receiveRSCResult(intent, action);
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
                properties.setActivity(BaseAppCompatActivity.this);
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

    public Bundle getBundle() {
        Intent intent = getIntent();
        if (intent == null) {
            return new Bundle();
        } else {
            Bundle bundle = intent.getExtras();
            return bundle == null ? new Bundle() : bundle;
        }
    }

    public String getStringBundle(Bundle bundle, String key, String defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getString(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public String getStringBundle(String key, String defaultValue) {
        Bundle bundle = getBundle();
        return getStringBundle(bundle, key, defaultValue);
    }

    public String getStringBundle(String key) {
        return getStringBundle(key, "");
    }

    public int getIntBundle(Bundle bundle, String key, int defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getInt(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public int getIntBundle(String key, int defaultValue) {
        Bundle bundle = getBundle();
        return getIntBundle(bundle, key, defaultValue);
    }

    public int getIntBundle(String key) {
        return getIntBundle(key, 0);
    }

    public boolean getBooleanBundle(Bundle bundle, String key, boolean defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getBoolean(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public boolean getBooleanBundle(String key, boolean defaultValue) {
        Bundle bundle = getBundle();
        return getBooleanBundle(bundle, key, defaultValue);
    }

    public boolean getBooleanBundle(String key) {
        return getBooleanBundle(key, false);
    }

    public Object getObjectBundle(Bundle bundle, String key, Object defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.get(key);
        } else {
            return defaultValue;
        }
    }

    public Object getObjectBundle(String key, Object defaultValue) {
        Bundle bundle = getBundle();
        return getObjectBundle(bundle, key, defaultValue);
    }

    public Object getObjectBundle(String key) {
        return getObjectBundle(key, null);
    }

    public float getFloatBundle(Bundle bundle, String key, float defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getFloat(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public float getFloatBundle(String key, float defaultValue) {
        Bundle bundle = getBundle();
        return getFloatBundle(bundle, key, defaultValue);
    }

    public float getFloatBundle(String key) {
        return getFloatBundle(key, 0);
    }

    public double getDoubleBundle(Bundle bundle, String key, double defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getDouble(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public double getDoubleBundle(String key, double defaultValue) {
        Bundle bundle = getBundle();
        return getDoubleBundle(bundle, key, defaultValue);
    }

    public double getDoubleBundle(String key) {
        return getDoubleBundle(key, 0);
    }

    public long getLongBundle(Bundle bundle, String key, long defaultValue) {
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getLong(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public long getLongBundle(String key, long defaultValue) {
        Bundle bundle = getBundle();
        return getLongBundle(bundle, key, defaultValue);
    }

    public long getLongBundle(String key) {
        return getLongBundle(key, 0);
    }

    public AppCompatActivity getActivity() {
        return BaseAppCompatActivity.this;
    }

    protected static BundleMap getBundleMap() {
        BundleMap bundleMap = new BundleMap();
        return bundleMap;
    }

    /**
     * 启动activity
     *
     * @param activity
     * @param params   参数
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
