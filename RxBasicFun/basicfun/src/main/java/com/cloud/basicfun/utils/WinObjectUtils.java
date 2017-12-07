package com.cloud.basicfun.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.cloud.basicfun.BaseApplication;
import com.cloud.basicfun.configs.BaseBConfig;
import com.cloud.basicfun.enums.RxReceiverActions;
import com.cloud.core.ObjectJudge;
import com.cloud.core.RSCReceiver;
import com.cloud.core.configs.RxCoreConfigItems;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.NetworkUtils;
import com.lzy.okgo.OkGo;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016-2-1 上午11:27:15
 * @Description: 用于BaseActivity、BaseFragment、BaseFragmentActivity、
 * BaseGestureActivity共用方法或变量定义
 * @Modifier:
 * @ModifyContent:
 */
public class WinObjectUtils {

    private boolean isRegisterBroadcast = false;

    protected void receiveRSCResult(Intent intent) {

    }

    protected void receiveRSCResult(Intent intent, String action) {

    }

    protected void onCheckVersionUpdateListener() {

    }

    public void onResume(Activity activity) {
        String className = activity.getLocalClassName();
        int sindex = className.indexOf("ui.");
        if (sindex >= 0) {
            className = className.substring(sindex + 3);
        }
        RxCoreConfigItems configItems = BaseBConfig.getInstance().getConfigItems(activity);
        if (!ObjectJudge.isNullOrEmpty(configItems.getUpdateCheckActivityNames())) {
            if (configItems.getUpdateCheckActivityNames().contains(className)) {
                onCheckVersionUpdateListener();
            }
        }
    }

    public void onPause(Activity activity) {

    }

    public void onStart(Activity activity) {
        RxCoreConfigItems configItems = BaseBConfig.getInstance().getConfigItems(activity);
        if (!TextUtils.isEmpty(configItems.getReceiveAction())) {
            registerReceiver(activity, configItems.getReceiveAction());
        }
        BaseCommonUtils.sendNetworkStateBroadcast(activity);
    }

    public void onDestroy(Activity activity) {
        try {
            if (isRegisterBroadcast) {
                isRegisterBroadcast = false;
                activity.unregisterReceiver(mrscreceiver);
            }
            //清除当前activity打开标记
            String className = activity.getLocalClassName();
            int sindex = className.indexOf("ui.");
            if (sindex >= 0) {
                className = className.substring(sindex + 3);
            }
            if (BaseApplication.getInstance().hasObjectValue(className)) {
                BaseApplication.getInstance().removeObjectValue(className);
            }
            OkGo okGo = OkGo.getInstance();
            if (okGo != null) {
                okGo.cancelTag(this);
            }
        } catch (Exception e) {
            Logger.L.error("onDestroy error:", e);
        }
    }

    public void onDestroyView() {

    }

    private void registerReceiver(Activity activity, String action) {
        isRegisterBroadcast = true;
        mrscreceiver.registerAction(activity, action);
    }

    private RSCReceiver mrscreceiver = new RSCReceiver() {
        @Override
        protected void receiveBroadResult(Activity activity, Intent intent) {
            try {
                if (intent == null) {
                    return;
                }
                Bundle mbundle = intent.getExtras();
                if (mbundle == null) {
                    return;
                }
                RxCoreConfigItems configItems = BaseBConfig.getInstance().getConfigItems(activity);
                if (!TextUtils.isEmpty(configItems.getReceiveAction())) {
                    if (TextUtils.equals(intent.getAction(), configItems.getReceiveAction())) {
                        receiveRSCResult(intent);
                    } else {
                        receiveRSCResult(intent, intent.getAction());
                    }
                }
                // 排除启动页、引导页、网络查看页面
                String className = activity.getLocalClassName();
                int sindex = className.indexOf("ui.");
                if (sindex >= 0) {
                    className = className.substring(sindex + 3);
                }
                if (!ObjectJudge.isNullOrEmpty(configItems.getNetStateRemindFilterActivityNames())) {
                    if (!configItems.getNetStateRemindFilterActivityNames().contains(className)) {
                        boolean netstate = mbundle.getBoolean(RxReceiverActions.NETWORK_CHANGE.getValue(), false);
                        if (netstate) {
                            if (NetworkUtils.isConnected(activity.getApplicationContext())) {
                                BaseCommonUtils.hideNetworkStateView(activity.getWindow());
                            } else {
                                BaseCommonUtils.showNetworkStateView(activity);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Logger.L.error(e);
            }
        }
    };

    public void onCreate(Context context, Bundle savedInstanceState) {

    }
}
