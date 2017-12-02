package com.cloud.basicfun.picker;

import android.content.Context;
import android.view.View;

import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.resources.picker.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/23
 * @Description:自定义选择选择器
 * @Modifier:
 * @ModifyContent:
 */
public class CusPickerUtils {

    private List<OptionsItem> optionsItems = new ArrayList<>();

    protected void onOptionsSelected(OptionsItem optionsItem, View v) {

    }

    public OptionsPickerView.Builder getBuilder(Context context) {
        OptionsPickerView.Builder builder = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                try {
                    if (optionsItems.size() > options1) {
                        OptionsItem optionsItem = optionsItems.get(options1);
                        if (optionsItem != null) {
                            onOptionsSelected(optionsItem, v);
                        }
                    }
                } catch (Exception e) {
                    Logger.L.error(e);
                }
            }
        });
        return builder;
    }

    public void setOptionsItems(List<OptionsItem> optionsItems) {
        if (ObjectJudge.isNullOrEmpty(optionsItems)) {
            return;
        }
        this.optionsItems.clear();
        this.optionsItems.addAll(optionsItems);
    }

    public void show(OptionsPickerView.Builder builder, View v) {
        try {
            if (builder == null) {
                return;
            }
            OptionsPickerView build = builder.build();
            build.setPicker(optionsItems);
            if (v == null) {
                build.show(true);
            } else {
                build.show(v, true);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }
}
