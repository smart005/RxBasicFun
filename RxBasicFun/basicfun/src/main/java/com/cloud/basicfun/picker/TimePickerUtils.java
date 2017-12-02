package com.cloud.basicfun.picker;

import android.content.Context;
import android.view.View;

import com.cloud.resources.picker.TimePickerView;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/23
 * @Description:时间选择器
 * @Modifier:
 * @ModifyContent:
 */
public class TimePickerUtils {

    protected void onTimeSelected(long timestamp, View v) {

    }

    public TimePickerView.Builder getBuilder(Context context) {
        TimePickerView.Builder builder = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                onTimeSelected(date == null ? 0 : date.getTime(), v);
            }
        });
        builder.setDate(Calendar.getInstance());
        builder.setType(new boolean[]{true, true, true, false, false, false});
        return builder;
    }

    public void show(TimePickerView.Builder builder, View v) {
        if (builder == null) {
            return;
        }
        TimePickerView build = builder.build();
        if (v == null) {
            build.show();
        } else {
            build.show(v);
        }
    }
}
