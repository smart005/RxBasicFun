package com.cloud.basicfuntest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cloud.basicfun.picker.AddressPickerUtils;
import com.cloud.basicfun.picker.CusPickerUtils;
import com.cloud.basicfun.picker.OptionsItem;
import com.cloud.basicfun.picker.TimePickerUtils;
import com.cloud.resources.picker.OptionsPickerView;
import com.cloud.resources.picker.TimePickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/21
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class PickerActivity extends Activity {

    @BindView(R.id.city_btn)
    Button cityBtn;
    @BindView(R.id.time_btn)
    Button timeBtn;
    @BindView(R.id.cus_btn)
    Button cusBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.city_btn)
    public void onCityClick() {
        ShowPickerView();
    }

    AddressPickerUtils pickerUtils = new AddressPickerUtils() {
        @Override
        protected void onAddressPicked(OptionsItem province, OptionsItem city, OptionsItem region, View v) {

        }
    };

    private void ShowPickerView() {
        OptionsPickerView.Builder builder = pickerUtils.getBuilder(this);
        builder.setTitleText("城市选择");
        builder.setDividerColor(Color.BLACK);
        builder.setTextColorCenter(Color.BLACK);
        builder.setContentTextSize(20);
        builder.setCyclic(true, false, false);
        pickerUtils.show(this, builder, "allarea.rx");
    }

    @OnClick(R.id.time_btn)
    public void onTimeClick() {
        TimePickerView.Builder builder = timePickerUtils.getBuilder(this);
        timePickerUtils.show(builder, null);
    }

    private TimePickerUtils timePickerUtils = new TimePickerUtils() {
        @Override
        protected void onTimeSelected(long timestamp, View v) {

        }
    };

    @OnClick(R.id.cus_btn)
    public void onCusClick() {
        OptionsPickerView.Builder builder = cusPickerUtils.getBuilder(this);
        List<OptionsItem> items = new ArrayList<OptionsItem>();
        items.add(new OptionsItem("选项1"));
        items.add(new OptionsItem("选项2"));
        items.add(new OptionsItem("选项3"));
        items.add(new OptionsItem("选项4"));
        items.add(new OptionsItem("选项5"));
        items.add(new OptionsItem("选项6"));
        cusPickerUtils.setOptionsItems(items);
        cusPickerUtils.show(builder, null);
    }

    private CusPickerUtils cusPickerUtils = new CusPickerUtils() {
        @Override
        protected void onOptionsSelected(OptionsItem optionsItem, View v) {

        }
    };
}
