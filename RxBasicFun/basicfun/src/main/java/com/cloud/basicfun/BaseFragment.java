package com.cloud.basicfun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cloud.basicfun.enums.LifeCycleStatus;
import com.cloud.basicfun.events.OnBehaviorStatistics;
import com.cloud.basicfun.update.UpdateBLL;
import com.cloud.basicfun.update.VersionUpdateProperties;
import com.cloud.basicfun.utils.BundleMap;
import com.cloud.basicfun.utils.WinObjectUtils;
import com.cloud.core.RxCoreUtils;
import com.cloud.core.config.RxConfig;
import com.cloud.core.utils.SharedPrefUtils;
import com.cloud.resources.hvlayout.HeaderScrollHelper;

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

    public static <T extends BaseFragment> T newInstance(T t, Bundle args) {
        if (args != null) {
            t.setArguments(args);
        }
        return t;
    }

    public static <T extends BaseFragment> T newInstance(T t, String param) {
        Bundle args = new Bundle();
        RxConfig config = RxCoreUtils.getInstance().getConfig(t.getContext());
        args.putString(config.getArgParam(), param);
        t.setArguments(args);
        return t;
    }

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
                RxConfig config = RxCoreUtils.getInstance().getConfig(getActivity());
                properties.setAppIcon(config.getAppIcon());
                properties.setIsAutoUpdate(true);
                properties.setIsCheckUpdatePrompt(false);
                properties.setCheckUpdateUrl(BaseApplication.getInstance().getCheckUpdateUrl());
                ubll.checkVersionUpdate(properties);
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

    public void onFragmentCall(String action, BundleMap bundleMap) {
        //回调方法
    }

    @Override
    public View getScrollableView() {
        return null;
    }
}
