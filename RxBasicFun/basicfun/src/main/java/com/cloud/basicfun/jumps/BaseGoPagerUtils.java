package com.cloud.basicfun.jumps;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.text.TextUtils;

import com.cloud.basicfun.BaseApplication;
import com.cloud.basicfun.R;
import com.cloud.basicfun.utils.BundleUtils;
import com.cloud.core.Action;
import com.cloud.core.Action0;
import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.core.okrx.OkRxManager;
import com.cloud.core.okrx.RequestState;
import com.cloud.core.utils.AppInfoUtils;
import com.cloud.core.utils.JsonUtils;
import com.cloud.resources.dialog.LoadingDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/8
 * @Description:页面路由工具类
 * @Modifier:
 * @ModifyContent:
 */
public class BaseGoPagerUtils {

    private static BaseGoPagerUtils goPagerUtils = null;
    private LoadingDialog mloading = new LoadingDialog();

    public static BaseGoPagerUtils getInstance() {
        return goPagerUtils == null ? goPagerUtils = new BaseGoPagerUtils() : goPagerUtils;
    }

    /**
     * @param activity
     * @param t
     * @param currGoVersion             当前跳转app版本
     * @param onH5Callback
     * @param onApiCallbackSuccessful
     * @param onProtozoaCallback
     * @param requestConfigUrl          除uniqueTag外url全路径
     * @param onGoReceiveParamsCallback 接收参数回调
     * @param <T>
     */
    public <T extends BaseGoBean> void startActivity(Activity activity,
                                                     T t,
                                                     String currGoAppVersion,
                                                     OnH5Callback onH5Callback,
                                                     OnApiCallbackSuccessful onApiCallbackSuccessful,
                                                     OnProtozoaCallback onProtozoaCallback,
                                                     OnRequestConfigUrl onRequestConfigUrl,
                                                     OnGoReceiveParamsCallback onGoReceiveParamsCallback) {
        try {
            if (activity == null || t == null || TextUtils.isEmpty(currGoAppVersion)) {
                return;
            }
            if (t.isToH5()) {
                if (onH5Callback != null) {
                    onH5Callback.onCallback(t.getUrl());
                }
            } else {
                if (ObjectJudge.isNullOrEmpty(t.getConfigs())) {
                    if (TextUtils.isEmpty(t.getUniqueTag()) ||
                            onRequestConfigUrl == null ||
                            onApiCallbackSuccessful == null ||
                            onProtozoaCallback == null) {
                        return;
                    }
                    String requestUrl = onRequestConfigUrl.onUrl(t.getUniqueTag());
                    mloading.showDialog(activity, R.string.processing_just, null);
                    requestGoConfig(activity,
                            requestUrl,
                            onProtozoaCallback,
                            onApiCallbackSuccessful,
                            onGoReceiveParamsCallback,
                            currGoAppVersion);
                } else {
                    //兼容版本,待后面处理
                    if (onProtozoaCallback != null) {
                        goConfigDealwith(onProtozoaCallback,
                                onGoReceiveParamsCallback,
                                t.getConfigs(),
                                currGoAppVersion);
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private void requestGoConfig(Activity activity,
                                 String requestConfigUrl,
                                 final OnProtozoaCallback onProtozoaCallback,
                                 final OnApiCallbackSuccessful onApiCallbackSuccessful,
                                 final OnGoReceiveParamsCallback onGoReceiveParamsCallback,
                                 final String currGoAppVersion) {
        OkRxManager.getInstance().get(activity,
                requestConfigUrl,
                null,
                null,
                false,
                "",
                0,
                new Action<String>() {
                    @Override
                    public void call(String response) {
                        if (onApiCallbackSuccessful == null) {
                            return;
                        }
                        String configs = onApiCallbackSuccessful.onGetConfigs(response);
                        if (TextUtils.isEmpty(configs)) {
                            return;
                        }
                        List<GoConfigItem> configItems = JsonUtils.parseArray(configs, GoConfigItem.class);
                        if (ObjectJudge.isNullOrEmpty(configItems)) {
                            return;
                        }
                        goConfigDealwith(onProtozoaCallback,
                                onGoReceiveParamsCallback,
                                configItems,
                                currGoAppVersion);
                    }
                },
                new Action<RequestState>() {
                    @Override
                    public void call(RequestState requestState) {
                        if (requestState == RequestState.Completed) {
                            mloading.dismiss();
                        }
                    }
                }, null, "");
    }

    private void goConfigDealwith(OnProtozoaCallback onProtozoaCallback,
                                  OnGoReceiveParamsCallback onGoReceiveParamsCallback,
                                  List<GoConfigItem> configItems,
                                  String currGoAppVersion) {
        try {
            GoConfigItem configItem = null;
            for (GoConfigItem item : configItems) {
                if (item.isEnabled() &&
                        item.getDeviceType() == DeviceType.Android.getValue() &&
                        item.getVersion().contains(currGoAppVersion)) {
                    configItem = item;
                    break;
                }
            }
            if (configItem == null) {
                return;
            }
            if (onGoReceiveParamsCallback != null) {
                GoConfigItem goConfigItem = onGoReceiveParamsCallback.onGoReceiveParams(configItem);
                if (goConfigItem != null) {
                    configItem = goConfigItem;
                }
            }
            //获取bundle
            Bundle bundle = new Bundle();
            HashMap<String, Object> params = configItem.getParams();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                BundleUtils.setBundleValue(bundle, entry.getKey(), entry.getValue());
            }
            onProtozoaCallback.onCallback(configItem.getPageName(), bundle);
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }
}
