/**
 * @Title: UpdateBLL.java
 * @Description: apk更新业务处理
 * @author: lijinghuan
 * @data: 2015年4月30日 上午11:59:11
 */
package com.cloud.basicfun.update;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.cloud.basicfun.BaseApplication;
import com.cloud.basicfun.beans.UpdateInfoBean;
import com.cloud.basicfun.enums.RxReceiverActions;
import com.cloud.core.Action;
import com.cloud.core.RxCoreUtils;
import com.cloud.core.beans.ApkInfo;
import com.cloud.core.beans.InstanceUpdateServiceInfoEntity;
import com.cloud.core.beans.LoadingRes;
import com.cloud.core.config.RxConfig;
import com.cloud.core.enums.ApkDownloadType;
import com.cloud.core.enums.Direction;
import com.cloud.core.enums.MaskAlign;
import com.cloud.core.loadings.MaskLoading;
import com.cloud.core.logger.Logger;
import com.cloud.core.okrx.OkRxManager;
import com.cloud.core.okrx.RequestState;
import com.cloud.core.utils.JsonUtils;
import com.cloud.resources.AppUpdate;
import com.cloud.resources.RedirectUtils;

public class UpdateBLL {

    private boolean CompulsoryUpdateFlag = false;
    private boolean CheckCompleteFlag = true;
    private VersionUpdateProperties versionUpdateProperties = new VersionUpdateProperties();
    private MaskLoading mloading = null;
    private boolean isDisplayCheckUpdatePrompt = false;
    //检测次数
    private int checkCount = 0;

    protected void onCheckCompleted() {

    }

    public boolean isCompulsoryUpdate() {
        return CompulsoryUpdateFlag;
    }

    public boolean isCheckComplete() {
        return CheckCompleteFlag;
    }

    private AppUpdate au = new AppUpdate() {
        @Override
        protected void onVersionUpdateListener(ApkInfo appinfo,
                                               boolean isCompulsoryUpdate) {
            BaseApplication.getInstance().setHasNewVersion(true);
            CompulsoryUpdateFlag = isCompulsoryUpdate;
            CheckCompleteFlag = true;
            onCheckCompleted();
            sendVersionUpdateRmindBroadcast();
        }

        @Override
        protected void onLasterVersionListener() {
            CheckCompleteFlag = true;
            BaseApplication.getInstance().setHasNewVersion(false);
            onCheckCompleted();
            sendVersionUpdateRmindBroadcast();
        }

        @Override
        protected void onComplated() {
            CheckCompleteFlag = true;
            onCheckCompleted();
        }

        @Override
        protected void onLaterUpdate() {
            CheckCompleteFlag = true;
            onCheckCompleted();
        }

        @Override
        protected void onNowUpdate(ApkInfo appinfo, boolean isCompulsoryUpdate) {
            CheckCompleteFlag = true;
            onCheckCompleted();
        }

        @Override
        protected void onBeginLoadingPrompt(String text) {
            if (mloading != null) {
                mloading.show(text, MaskAlign.CENTER, false, Direction.NONE);
            }
        }

        @Override
        protected void onEndLoadingPrompt() {
            if (mloading != null && mloading.isShowing()) {
                mloading.hideMaskLoading();
            }
        }

        @Override
        protected MaskLoading onInitLoading(
                InstanceUpdateServiceInfoEntity miusinfo) {
            if (mloading == null) {
                buildLoading(miusinfo);
            }
            return mloading;
        }
    };

    private void buildLoading(InstanceUpdateServiceInfoEntity miusinfo) {
        LoadingRes lres = new LoadingRes();
        lres.maskbackground = miusinfo.getMaskLoadingBackground();
        lres.animation = miusinfo.getMaskLoadingAnimation();
        mloading = new MaskLoading(miusinfo.getContext(),
                miusinfo.getMaskLoadingTheme());
        mloading.setResource(lres);
    }

    /**
     * 检测版本更新
     *
     * @param properties 版本更新属性
     */
    public void checkVersionUpdate(VersionUpdateProperties properties) {
        this.versionUpdateProperties = properties;
        if (CheckCompleteFlag) {
            try {
                CheckCompleteFlag = false;
                //防止并发执行2次以上
                checkCount++;
                if (checkCount > 1) {
                    checkCount = 0;
                    return;
                }
                this.isDisplayCheckUpdatePrompt = isDisplayCheckUpdatePrompt;
                InstanceUpdateServiceInfoEntity miusinfo = au.getInstanceUSInfo(properties.getActivity(), properties.getCheckUpdateUrl(), properties.getAppIcon());
                if (!properties.getIsAutoUpdate()) {
                    if (miusinfo.isDisplayCheckUpdatePrompt()) {
                        if (mloading == null) {
                            buildLoading(miusinfo);
                        }
                        mloading.show(miusinfo.getCheckUpdatePromptText(), MaskAlign.CENTER, false, Direction.NONE);
                    }
                }
                RxConfig config = RxCoreUtils.getInstance().getConfig(properties.getActivity());
                if (TextUtils.isEmpty(config.getRequestUpdateInfoUrl())) {
                    if (mloading != null) {
                        mloading.dismiss();
                    }
                    onCheckCompleted();
                    CheckCompleteFlag = true;
                } else {
                    OkRxManager.getInstance().get(properties.getActivity(),
                            config.getRequestUpdateInfoUrl(),
                            null,
                            null,
                            false,
                            "",
                            0,
                            new Action<String>() {
                                @Override
                                public void call(String response) {
                                    UpdateInfoBean updateInfoBean = JsonUtils.parseT(response, UpdateInfoBean.class);
                                    updateInfoDealwithAndStart(updateInfoBean, versionUpdateProperties.getAppIcon());
                                }
                            },
                            new Action<RequestState>() {
                                @Override
                                public void call(RequestState requestState) {
                                    if (requestState == RequestState.Completed) {
                                        if (mloading != null) {
                                            mloading.dismiss();
                                        }
                                        onCheckCompleted();
                                        CheckCompleteFlag = true;
                                    }
                                }
                            }, null, "");
                }
            } catch (Exception e) {
                if (mloading != null) {
                    mloading.dismiss();
                }
                onCheckCompleted();
                CheckCompleteFlag = true;
                Logger.L.error("update version request error:", e);
            }
        }
    }

    /**
     * @param result  更新信息
     * @param appicon 应用图标
     */
    private void updateInfoDealwithAndStart(UpdateInfoBean result, int appicon) {
        RxConfig config = RxCoreUtils.getInstance().getConfig(versionUpdateProperties.getActivity());
        if (config.getApiConfig().getApiRet().contains(result.getCode())) {
            if (result == null) {
                return;
            }
            ApkInfo mapkinfo = new ApkInfo();
            mapkinfo.setVersionCode(result.getVersionCode());
            mapkinfo.setVersionName(result.getVersionName());
            mapkinfo.setUpdateType(result.getUpdateType());
            mapkinfo.setApkName(result.getApkName());
            mapkinfo.setUpdateDescription(result.getUpdateDescription());
            mapkinfo.setDownloadType(result.getDownloadType());
            mapkinfo.setApkUrl(result.getApkUrl());
            mapkinfo.setApkPackgeName(BaseApplication.getInstance().getPackageName());
            mapkinfo.setEnablePart(result.isEnablePart());
            mapkinfo.setUpdateUsers(result.getUpdateUsers());
            au.setApkInfo(mapkinfo);
            au.setDisplayUpdateRemindForAutoUpdate(true);
            if (mapkinfo.getDownloadType() == 1) {
                au.start(versionUpdateProperties.getActivity(), ApkDownloadType.WINDOW, versionUpdateProperties.getIsAutoUpdate(),
                        isDisplayCheckUpdatePrompt, versionUpdateProperties.getCheckUpdateUrl(), versionUpdateProperties.getAppIcon());
            } else if (mapkinfo.getDownloadType() == 2) {
                au.start(versionUpdateProperties.getActivity(), ApkDownloadType.NOTIFICATION,
                        versionUpdateProperties.getIsAutoUpdate(), isDisplayCheckUpdatePrompt, versionUpdateProperties.getCheckUpdateUrl(), versionUpdateProperties.getAppIcon());
            } else {
                au.start(versionUpdateProperties.getActivity(), ApkDownloadType.NONE, versionUpdateProperties.getIsAutoUpdate(),
                        isDisplayCheckUpdatePrompt, versionUpdateProperties.getCheckUpdateUrl(), versionUpdateProperties.getAppIcon());
            }
        } else {
            if (mloading != null) {
                mloading.dismiss();
            }
            onCheckCompleted();
            CheckCompleteFlag = true;
        }
    }

    private void sendVersionUpdateRmindBroadcast() {
        Context context = BaseApplication.getInstance().getApplicationContext();
        Bundle mbundle = new Bundle();
        mbundle.putBoolean(RxReceiverActions.VERSION_UPDATE_REMIND.getValue(), true);
        RedirectUtils.sendBroadcast(context, mbundle);
    }
}
