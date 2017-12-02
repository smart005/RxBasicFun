package com.cloud.basicfun.utils;

import android.content.Context;
import android.text.TextUtils;

import com.cloud.basicfun.beans.APIReturnResult;
import com.cloud.basicfun.beans.ArgFieldItem;
import com.cloud.basicfun.beans.H5GetAPIMethodArgsBean;
import com.cloud.basicfun.enums.APIRequestState;
import com.cloud.core.Action;
import com.cloud.core.Action0;
import com.cloud.core.Func2;
import com.cloud.core.ObjectJudge;
import com.cloud.core.enums.RuleParams;
import com.cloud.core.logger.Logger;
import com.cloud.core.okrx.OkRxManager;
import com.cloud.core.okrx.RequestState;
import com.cloud.core.utils.JsonUtils;
import com.cloud.core.utils.PathsUtils;
import com.cloud.core.utils.ValidUtils;
import com.lzy.okgo.model.HttpParams;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/1/4
 * @Description:H5交互API工具类
 * @Modifier:
 * @ModifyContent:
 */
public class H5InteractionAPIUtils {

    /**
     * H5请求api接口方法
     *
     * @param context
     * @param baseUrl 请求api的基地址
     * @param extras  {"apiName":"login",
     *                "args":[{"fieldName":"userName","fieldValue":"test","fieldType":"string"},
     *                {"fieldName":"password","fieldValue":"123456","fieldType":"string"}]}
     */
    public static void getAPIMethod(Context context, String baseUrl, String extras, final Func2<Object, APIRequestState, APIReturnResult> callback) {
        try {
            if (context == null || !ValidUtils.valid(RuleParams.Url.getValue(), baseUrl) || TextUtils.isEmpty(extras)) {
                return;
            }
            final H5GetAPIMethodArgsBean h5GetAPIMethodArgsBean = JsonUtils.parseT(extras, H5GetAPIMethodArgsBean.class);
            if (h5GetAPIMethodArgsBean == null || TextUtils.isEmpty(h5GetAPIMethodArgsBean.getApiName())) {
                return;
            }
            String url = PathsUtils.combine(baseUrl, h5GetAPIMethodArgsBean.getApiName());
            HttpParams params = new HttpParams();
            if (!ObjectJudge.isNullOrEmpty(h5GetAPIMethodArgsBean.getArgs())) {
                for (ArgFieldItem argFieldItem : h5GetAPIMethodArgsBean.getArgs()) {
                    params.put(argFieldItem.getFieldName(), argFieldItem.getFieldValue());
                }
            }
            OkRxManager.getInstance().get(context,
                    url,
                    null,
                    params,
                    false,
                    "",
                    0,
                    new Action<String>() {
                        @Override
                        public void call(String response) {
                            if (callback != null) {
                                APIReturnResult apiReturnResult = new APIReturnResult();
                                apiReturnResult.setResponse(response);
                                apiReturnResult.setTarget(h5GetAPIMethodArgsBean == null ? "" : h5GetAPIMethodArgsBean.getTarget());
                                callback.call(APIRequestState.Success, apiReturnResult);
                            }
                        }
                    },
                    new Action<RequestState>() {
                        @Override
                        public void call(RequestState requestState) {
                            if (callback != null && requestState == RequestState.Completed) {
                                callback.call(APIRequestState.Complate, null);
                            }
                        }
                    }, null, "");
        } catch (
                Exception e)

        {
            Logger.L.error("h5 get api method error:", e);
        }
    }
}
