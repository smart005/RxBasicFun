package com.cloud.basicfun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cloud.basicfun.enums.LifeCycleStatus;
import com.cloud.basicfun.events.OnBehaviorStatistics;
import com.cloud.basicfun.update.UpdateBLL;
import com.cloud.basicfun.update.VersionUpdateProperties;
import com.cloud.basicfun.utils.BundleMap;
import com.cloud.basicfun.utils.BundleUtils;
import com.cloud.basicfun.utils.WinObjectUtils;
import com.cloud.core.configs.BaseCConfig;
import com.cloud.core.configs.ConfigItem;
import com.cloud.core.configs.OnConfigItemUrlListener;
import com.cloud.core.configs.RxCoreConfigItems;
import com.cloud.core.enums.ResFolderType;
import com.cloud.core.utils.ResUtils;
import com.cloud.core.utils.SharedPrefUtils;
import com.cloud.resources.RedirectUtils;
import com.cloud.resources.hvlayout.HeaderScrollHelper;

import java.util.Map;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/4
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseFragment extends Fragment implements HeaderScrollHelper.ScrollableContainer {

    /**
     * 当前分页索引
     */
    protected int currPageIndex = 1;

    protected void receiveRSCResult(Intent intent) {

    }

    protected void receiveRSCResult(Intent intent, String action) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mwoutils.onCreate(getContext(), savedInstanceState);
        OnBehaviorStatistics behaviorStatistics = BaseApplication.getInstance().getOnBehaviorStatistics();
        if (behaviorStatistics != null) {
            behaviorStatistics.onPageStatistics(this, LifeCycleStatus.ViewCreated);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public View findViewById(int id) {
        return getActivity().findViewById(id);
    }

    protected int getCurrPageIndex() {
        return currPageIndex = 1;
    }

    /**
     * 实例Fragment对象
     *
     * @param t    要实例的Fragment类对象
     * @param args bundle对象
     * @param <T>
     * @return
     */
    public static <T extends BaseFragment> T newInstance(T t, Bundle args) {
        if (args != null) {
            t.setArguments(args);
        }
        return t;
    }

    /**
     * 实例Fragment对象
     *
     * @param t   要实例的Fragment类对象
     * @param <T>
     * @return
     */
    public static <T extends BaseFragment> T newInstance(T t) {
        return newInstance(t, (Bundle) null);
    }

    @Override
    public void onResume() {
        super.onResume();
        OnBehaviorStatistics behaviorStatistics = BaseApplication.getInstance().getOnBehaviorStatistics();
        if (behaviorStatistics != null) {
            behaviorStatistics.onPageStatistics(this, LifeCycleStatus.Resume);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        OnBehaviorStatistics behaviorStatistics = BaseApplication.getInstance().getOnBehaviorStatistics();
        if (behaviorStatistics != null) {
            behaviorStatistics.onPageStatistics(this, LifeCycleStatus.Pause);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mwoutils.onStart(getActivity());
        OnBehaviorStatistics behaviorStatistics = BaseApplication.getInstance().getOnBehaviorStatistics();
        if (behaviorStatistics != null) {
            behaviorStatistics.onPageStatistics(this, LifeCycleStatus.Start);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mwoutils.onDestroy(getActivity());
        OnBehaviorStatistics behaviorStatistics = BaseApplication.getInstance().getOnBehaviorStatistics();
        if (behaviorStatistics != null) {
            behaviorStatistics.onPageStatistics(this, LifeCycleStatus.DestroyView);
        }
    }

    private WinObjectUtils mwoutils = new WinObjectUtils() {
        @Override
        protected void receiveRSCResult(Intent intent) {
            BaseFragment.this.receiveRSCResult(intent);
        }

        @Override
        protected void receiveRSCResult(Intent intent, String action) {
            BaseFragment.this.receiveRSCResult(intent, action);
        }

        @Override
        protected void onCheckVersionUpdateListener() {
            long updateTime = SharedPrefUtils.getPrefLong(getActivity(), "UPDATE_VERSION_KEY", 0);
            long currTime = System.currentTimeMillis();
            long time = currTime - updateTime;
            if (updateTime == 0 || ubll.isCheckComplete()) {
                if (time > 0 && (time / 3600) <= 6) {
                    return;
                }
                SharedPrefUtils.setPrefLong(getActivity(), "UPDATE_VERSION_KEY", System.currentTimeMillis());
                VersionUpdateProperties properties = new VersionUpdateProperties();
                properties.setActivity(getActivity());
                RxCoreConfigItems configItems = BaseCConfig.getInstance().getConfigItems(getActivity());
                ConfigItem versionCheck = configItems.getAppVersionCheck();
                if (versionCheck.isState()) {
                    //设置图标
                    ConfigItem appIcon = configItems.getAppIcon();
                    ResFolderType folderType = ResFolderType.getResFolderType(appIcon.getType());
                    int appIconRresId = ResUtils.getResource(getActivity(), appIcon.getName(), folderType);
                    properties.setAppIcon(appIconRresId);
                    properties.setIsAutoUpdate(true);
                    properties.setIsCheckUpdatePrompt(false);
                    //设置请求地址
                    BaseApplication currapp = BaseApplication.getInstance();
                    Object urlListener = currapp.getObjectValue(versionCheck.getUrlType());
                    if (urlListener != null && urlListener instanceof OnConfigItemUrlListener) {
                        OnConfigItemUrlListener listener = (OnConfigItemUrlListener) urlListener;
                        String url = listener.getUrl(versionCheck.getUrlType());
                        properties.setCheckUpdateUrl(url);
                        ubll.checkVersionUpdate(properties);
                    }
                }
            }
        }
    };

    public UpdateBLL ubll = new UpdateBLL() {
        @Override
        protected void onCheckCompleted() {

        }
    };

    public Bundle getBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return new Bundle();
        } else {
            return bundle;
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

    /**
     * Fragment回调方法,在其它页面拿到当前Fragment对象后调用onFragmentCall时执行
     *
     * @param action    区分是哪一次回调
     * @param bundleMap BundleMap参数集合
     */
    public void onFragmentCall(String action, BundleMap bundleMap) {

    }

    /**
     * 使用{@link com.cloud.resources.hvlayout.HeaderTabsViewLayout}控件时,
     * Fragment需要通过此回调返回具体滚动的视图
     *
     * @return
     */
    @Override
    public View getScrollableView() {
        return null;
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
